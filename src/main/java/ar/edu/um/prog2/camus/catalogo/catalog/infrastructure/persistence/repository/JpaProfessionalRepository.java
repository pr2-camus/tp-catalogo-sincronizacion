package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {}
