package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.SyncStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSyncStateRepository extends JpaRepository<SyncStateEntity, Long> {}
