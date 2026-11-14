package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.scheduling;

import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarAlArrancarUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarAlDiaUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** Dispara la sincronizacion al arrancar y de forma periodica. No tiene logica propia. */
@Component
public class SyncScheduler {

    private static final Logger log = LoggerFactory.getLogger(SyncScheduler.class);

    private final SincronizarAlArrancarUseCase sincronizarAlArrancarUseCase;
    private final SincronizarAlDiaUseCase sincronizarAlDiaUseCase;

    public SyncScheduler(SincronizarAlArrancarUseCase sincronizarAlArrancarUseCase, SincronizarAlDiaUseCase sincronizarAlDiaUseCase) {
        this.sincronizarAlArrancarUseCase = sincronizarAlArrancarUseCase;
        this.sincronizarAlDiaUseCase = sincronizarAlDiaUseCase;
    }

    @EventListener(
        value = ApplicationReadyEvent.class,
        condition = "@environment.getProperty('sync.arranque-habilitado', 'true') == 'true'"
    )
    public void sincronizarAlArrancar() {
        sincronizarAlArrancarUseCase.sincronizarAlArrancar();
    }

    @Scheduled(fixedDelayString = "${sync.intervalo-ms:300000}")
    public void sincronizarPeriodico() {
        log.info("Sincronizacion periodica...");
        sincronizarAlDiaUseCase.sincronizarAlDia();
    }
}
