package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {}
