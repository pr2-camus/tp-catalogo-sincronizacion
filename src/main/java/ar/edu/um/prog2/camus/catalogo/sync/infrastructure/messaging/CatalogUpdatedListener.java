package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.messaging;

import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.ProcesarEventoCatalogoUseCase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/** Escucha los eventos de catalogo de la catedra. El offset se confirma despues de procesar. */
@Component
public class CatalogUpdatedListener {

    private static final Logger log = LoggerFactory.getLogger(CatalogUpdatedListener.class);

    private final ProcesarEventoCatalogoUseCase useCase;
    private final ObjectMapper objectMapper;

    public CatalogUpdatedListener(ProcesarEventoCatalogoUseCase useCase, ObjectMapper objectMapper) {
        this.useCase = useCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
        topics = "${kafka.topics.catalogo}",
        groupId = "${spring.kafka.consumer.group-id}",
        autoStartup = "${kafka.listener.enabled:true}"
    )
    public void onCatalogUpdated(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String eventId = leerEventId(record.value());
        if (eventId == null) {
            log.warn("Mensaje descartado: no es un JSON valido o no trae eventId");
            ack.acknowledge();
            return;
        }
        try {
            useCase.procesar(eventId);
            ack.acknowledge();
        } catch (RuntimeException e) {
            log.error("Error procesando evento de catalogo {}: {}", eventId, e.getMessage(), e);
        }
    }

    private String leerEventId(String value) {
        try {
            JsonNode node = objectMapper.readTree(value).get("eventId");
            return node == null || node.isNull() ? null : node.asText();
        } catch (RuntimeException e) {
            return null;
        }
    }
}
