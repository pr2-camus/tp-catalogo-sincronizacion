package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.messaging;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.ProcessedEventEntity;
import ar.edu.um.prog2.camus.catalogo.sync.application.usecases.SyncToLatestUseCaseImpl;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.repository.JpaProcessedEventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CatalogUpdatedListener {

    private static final Logger log = LoggerFactory.getLogger(CatalogUpdatedListener.class);

    private final SyncToLatestUseCaseImpl syncToLatestUseCase;
    private final JpaProcessedEventRepository processedEventRepository;
    private final ObjectMapper objectMapper;

    public CatalogUpdatedListener(
        SyncToLatestUseCaseImpl syncToLatestUseCase,
        JpaProcessedEventRepository processedEventRepository,
        ObjectMapper objectMapper
    ) {
        this.syncToLatestUseCase = syncToLatestUseCase;
        this.processedEventRepository = processedEventRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.topics.catalogo}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void onCatalogUpdated(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            JsonNode event = objectMapper.readTree(record.value());
            String eventId = event.get("eventId").asText();

            // Idempotencia: si ya procesamos este evento, ignorarlo
            if (processedEventRepository.existsById(eventId)) {
                log.info("Evento duplicado ignorado: {}", eventId);
                ack.acknowledge();
                return;
            }

            // Guardar el eventId y sincronizar
            ProcessedEventEntity processed = new ProcessedEventEntity();
            processed.setEventId(eventId);
            processed.setProcessedAt(Instant.now());
            processedEventRepository.save(processed);

            log.info("Evento CatalogUpdated recibido: {}", eventId);
            syncToLatestUseCase.sincronizarAlDia();

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error procesando evento de catalogo: {}", e.getMessage(), e);
        }
    }
}
