package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.adapter;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.ProcessedEventEntity;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.EventoProcesadoPort;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.repository.JpaProcessedEventRepository;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class EventoProcesadoAdapter implements EventoProcesadoPort {

    private final JpaProcessedEventRepository repository;

    public EventoProcesadoAdapter(JpaProcessedEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean yaProcesado(String eventId) {
        return repository.existsById(eventId);
    }

    @Override
    public void registrar(String eventId) {
        ProcessedEventEntity entity = new ProcessedEventEntity();
        entity.setEventId(eventId);
        entity.setProcessedAt(Instant.now());
        repository.save(entity);
    }
}
