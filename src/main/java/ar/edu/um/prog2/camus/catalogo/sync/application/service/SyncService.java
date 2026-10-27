package ar.edu.um.prog2.camus.catalogo.sync.application.service;

import ar.edu.um.prog2.camus.catalogo.sync.application.usecases.SyncToLatestUseCaseImpl;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SyncService {

    private static final Logger log = LoggerFactory.getLogger(SyncService.class);

    private final SincronizarCatalogoUseCase fullSyncUseCase;
    private final SyncToLatestUseCaseImpl syncToLatestUseCase;
    private final VersionRepositoryPort versionRepository;

    public SyncService(
        SincronizarCatalogoUseCase fullSyncUseCase,
        SyncToLatestUseCaseImpl syncToLatestUseCase,
        VersionRepositoryPort versionRepository
    ) {
        this.fullSyncUseCase = fullSyncUseCase;
        this.syncToLatestUseCase = syncToLatestUseCase;
        this.versionRepository = versionRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sincronizarAlArrancar() {
        Long versionLocal = versionRepository.obtenerVersionLocal();
        if (versionLocal == null) {
            log.info("Base vacia, ejecutando sincronizacion completa inicial...");
            fullSyncUseCase.sincronizarCompleto();
        } else {
            log.info("Version local: {}. Sincronizando al dia...", versionLocal);
            syncToLatestUseCase.sincronizarAlDia();
        }
    }

    @Scheduled(fixedDelayString = "${sync.intervalo-ms:300000}")
    public void sincronizarPeriodico() {
        log.info("Sincronizacion periodica...");
        syncToLatestUseCase.sincronizarAlDia();
    }

    public void forzarSincronizacionCompleta() {
        fullSyncUseCase.sincronizarCompleto();
    }
}
