package ar.edu.um.prog2.camus.catalogo.sync.application.service;

import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SyncService {

    private static final Logger log = LoggerFactory.getLogger(SyncService.class);

    private final SincronizarCatalogoUseCase sincronizarUseCase;
    private final VersionRepositoryPort versionRepository;

    public SyncService(SincronizarCatalogoUseCase sincronizarUseCase, VersionRepositoryPort versionRepository) {
        this.sincronizarUseCase = sincronizarUseCase;
        this.versionRepository = versionRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sincronizarAlArrancar() {
        Long versionLocal = versionRepository.obtenerVersionLocal();
        if (versionLocal == null) {
            log.info("Base vacia, ejecutando sincronizacion completa inicial...");
            sincronizarUseCase.sincronizarCompleto();
        } else {
            log.info("Version local existente: {}. Omitiendo sincronizacion inicial.", versionLocal);
        }
    }

    public void forzarSincronizacionCompleta() {
        sincronizarUseCase.sincronizarCompleto();
    }
}
