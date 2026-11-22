package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.CambiosCatalogo;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarAlDiaUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.RedisPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SyncToLatestUseCaseImpl implements SincronizarAlDiaUseCase {

    private static final Logger log = LoggerFactory.getLogger(SyncToLatestUseCaseImpl.class);

    private final RedisPort redisPort;
    private final VersionRepositoryPort versionRepository;
    private final CatalogoRepositoryPort catalogoRepository;
    private final SincronizarCatalogoUseCase fullSyncUseCase;

    public SyncToLatestUseCaseImpl(
        RedisPort redisPort,
        VersionRepositoryPort versionRepository,
        CatalogoRepositoryPort catalogoRepository,
        SincronizarCatalogoUseCase fullSyncUseCase
    ) {
        this.redisPort = redisPort;
        this.versionRepository = versionRepository;
        this.catalogoRepository = catalogoRepository;
        this.fullSyncUseCase = fullSyncUseCase;
    }

    @Override
    public void sincronizarAlDia() {
        Long versionLocal = versionRepository.obtenerVersionLocal();
        Long versionActual = redisPort.obtenerVersionActual();
        Long versionMasAntigua = redisPort.obtenerVersionMasAntigua();

        if (versionActual == null) {
            log.warn("No se pudo obtener la version actual de Redis.");
            return;
        }
        if (versionLocal != null && versionLocal >= versionActual) {
            log.info("Catalogo al dia. Version local: {}", versionLocal);
            return;
        }
        if (hayHueco(versionLocal, versionMasAntigua)) {
            log.info(
                "Hueco detectado o base vacia. Version local: {}, oldest: {}. Ejecutando snapshot completo.",
                versionLocal,
                versionMasAntigua
            );
            fullSyncUseCase.sincronizarCompleto();
            return;
        }
        aplicarVersionesHasta(versionLocal, versionActual);
    }

    @Transactional
    public void aplicarVersion(long version) {
        CambiosCatalogo cambios = redisPort
            .obtenerCambios(version)
            .orElseThrow(() -> new IllegalStateException("No se encontro changes:" + version + " en Redis."));

        cambios.categorias().forEach(id -> redisPort.obtenerCategoria(id).ifPresent(c -> catalogoRepository.guardarCategorias(List.of(c))));
        cambios
            .profesionales()
            .forEach(id -> redisPort.obtenerProfesional(id).ifPresent(p -> catalogoRepository.guardarProfesionales(List.of(p))));
        cambios.horarios().forEach(id -> redisPort.obtenerHorario(id).ifPresent(h -> catalogoRepository.guardarHorarios(List.of(h))));

        versionRepository.guardarVersion(version);
        log.info("Version {} aplicada correctamente.", version);
    }

    private static boolean hayHueco(Long versionLocal, Long versionMasAntigua) {
        return versionLocal == null || versionMasAntigua == null || versionLocal < versionMasAntigua - 1;
    }

    private void aplicarVersionesHasta(long versionLocal, long versionActual) {
        for (long version = versionLocal + 1; version <= versionActual; version++) {
            try {
                aplicarVersion(version);
            } catch (RuntimeException e) {
                log.error("Error aplicando version {}. Ejecutando snapshot completo.", version, e);
                fullSyncUseCase.sincronizarCompleto();
                return;
            }
        }
    }
}
