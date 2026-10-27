package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWeeklyScheduleRepository extends JpaRepository<WeeklyScheduleEntity, Long> {}
