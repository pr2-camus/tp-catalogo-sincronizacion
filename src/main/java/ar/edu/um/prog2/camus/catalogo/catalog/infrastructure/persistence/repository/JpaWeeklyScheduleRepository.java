package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWeeklyScheduleRepository extends JpaRepository<WeeklyScheduleEntity, Long> {
    List<WeeklyScheduleEntity> findByProfessionalIdAndEnabled(Long professionalId, Boolean enabled);
}
