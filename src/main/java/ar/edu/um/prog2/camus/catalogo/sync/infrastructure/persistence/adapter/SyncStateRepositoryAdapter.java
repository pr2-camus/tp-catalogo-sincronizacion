package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.adapter;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.SyncStateEntity;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.repository.JpaSyncStateRepositorySync;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class SyncStateRepositoryAdapter implements VersionRepositoryPort {

    private final JpaSyncStateRepositorySync repository;

    public SyncStateRepositoryAdapter(JpaSyncStateRepositorySync repository) {
        this.repository = repository;
    }

    @Override
    public Long obtenerVersionLocal() {
        return repository.findById(1L).map(SyncStateEntity::getCurrentVersion).orElse(null);
    }

    @Override
    public void guardarVersion(Long version) {
        SyncStateEntity entity = repository.findById(1L).orElse(new SyncStateEntity());
        entity.setCurrentVersion(version);
        entity.setUpdatedAt(Instant.now());
        repository.save(entity);
    }
}
