package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.ProcesarEventoCatalogoUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarAlDiaUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.EventoProcesadoPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Procesa un evento de catalogo de forma idempotente. Registro del evento y sincronizacion van en la
 * misma transaccion: si la sincronizacion falla, el evento no queda marcado como procesado y se puede
 * reintentar cuando llegue de nuevo.
 */
@Component
public class ProcesarEventoCatalogoUseCaseImpl implements ProcesarEventoCatalogoUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcesarEventoCatalogoUseCaseImpl.class);

    private final EventoProcesadoPort eventoProcesadoPort;
    private final SincronizarAlDiaUseCase sincronizarAlDiaUseCase;

    public ProcesarEventoCatalogoUseCaseImpl(EventoProcesadoPort eventoProcesadoPort, SincronizarAlDiaUseCase sincronizarAlDiaUseCase) {
        this.eventoProcesadoPort = eventoProcesadoPort;
        this.sincronizarAlDiaUseCase = sincronizarAlDiaUseCase;
    }

    @Override
    @Transactional
    public void procesar(String eventId) {
        if (eventoProcesadoPort.yaProcesado(eventId)) {
            log.info("Evento duplicado ignorado: {}", eventId);
            return;
        }
        eventoProcesadoPort.registrar(eventId);
        log.info("Evento CatalogUpdated recibido: {}", eventId);
        sincronizarAlDiaUseCase.sincronizarAlDia();
    }
}
