package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {
    List<ProfessionalEntity> findByCategoryId(Long categoryId);

    @Query(
        "SELECT p FROM ProfessionalEntity p WHERE " +
            "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :nombre, '%')) OR " +
            "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :nombre, '%'))"
    )
    List<ProfessionalEntity> findByNombre(@Param("nombre") String nombre);

    List<ProfessionalEntity> findByEnabled(Boolean enabled);

    @Query(
        "SELECT DISTINCT p FROM ProfessionalEntity p JOIN WeeklyScheduleEntity w ON w.professionalId = p.id " +
            "WHERE w.enabled = true AND w.dayOfWeek = :dayOfWeek"
    )
    List<ProfessionalEntity> findByDayOfWeek(@Param("dayOfWeek") String dayOfWeek);
}
