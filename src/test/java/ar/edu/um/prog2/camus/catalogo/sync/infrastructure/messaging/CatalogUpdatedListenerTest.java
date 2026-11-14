package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.messaging;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.ProcesarEventoCatalogoUseCase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.Acknowledgment;
import tools.jackson.databind.json.JsonMapper;

class CatalogUpdatedListenerTest {

    private final ProcesarEventoCatalogoUseCase useCase = mock(ProcesarEventoCatalogoUseCase.class);
    private final Acknowledgment ack = mock(Acknowledgment.class);
    private final CatalogUpdatedListener listener = new CatalogUpdatedListener(useCase, JsonMapper.builder().build());

    private static ConsumerRecord<String, String> record(String value) {
        return new ConsumerRecord<>("catalogo", 0, 0L, "k", value);
    }

    @Test
    void eventoValidoSeProcesaYSeConfirma() {
        listener.onCatalogUpdated(record("{\"eventId\":\"evt-1\"}"), ack);

        verify(useCase).procesar("evt-1");
        verify(ack).acknowledge();
    }

    @Test
    void jsonInvalidoSeDescartaYSeConfirma() {
        listener.onCatalogUpdated(record("no es json"), ack);

        verifyNoInteractions(useCase);
        verify(ack).acknowledge();
    }

    @Test
    void mensajeSinEventIdSeDescartaYSeConfirma() {
        listener.onCatalogUpdated(record("{\"otro\":1}"), ack);

        verifyNoInteractions(useCase);
        verify(ack).acknowledge();
    }

    @Test
    void siFallaElProcesamientoNoSeConfirmaParaQueSeReintente() {
        doThrow(new IllegalStateException("falla")).when(useCase).procesar("evt-1");

        listener.onCatalogUpdated(record("{\"eventId\":\"evt-1\"}"), ack);

        verify(ack, never()).acknowledge();
    }
}
