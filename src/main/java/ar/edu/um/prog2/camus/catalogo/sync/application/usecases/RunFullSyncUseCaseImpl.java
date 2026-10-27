package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.Snapshot;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.SnapshotClientPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RunFullSyncUseCaseImpl implements SincronizarCatalogoUseCase {

    private static final Logger log = LoggerFactory.getLogger(RunFullSyncUseCaseImpl.class);

    private final SnapshotClientPort snapshotClient;
    private final CatalogoRepositoryPort catalogoRepository;
    private final VersionRepositoryPort versionRepository;

    public RunFullSyncUseCaseImpl(
        SnapshotClientPort snapshotClient,
        CatalogoRepositoryPort catalogoRepository,
        VersionRepositoryPort versionRepository
    ) {
        this.snapshotClient = snapshotClient;
        this.catalogoRepository = catalogoRepository;
        this.versionRepository = versionRepository;
    }

    @Override
    @Transactional
    public void sincronizarCompleto() {
        log.info("Iniciando sincronizacion completa...");

        Snapshot snapshot = snapshotClient.obtenerSnapshot();

        catalogoRepository.limpiarYReemplazar(
            snapshot.getProfessionalCategories(),
            snapshot.getProfessionals(),
            snapshot.getWeeklySchedules()
        );

        versionRepository.guardarVersion(snapshot.getSnapshotVersion());

        log.info("Sincronizacion completa finalizada. Version aplicada: {}", snapshot.getSnapshotVersion());
    }

    @Override
    @Transactional
    public void sincronizarIncremental(Long nuevaVersion) {
        // Se implementa en CAT-9
        log.info("Sincronizacion incremental pendiente de implementacion.");
    }
}
