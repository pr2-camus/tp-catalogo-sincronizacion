package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.mapper;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.CategoryEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import org.springframework.stereotype.Component;

@Component
public class CatalogoEntityMapper {

    public CategoriaProfesional toDomain(CategoryEntity e) {
        CategoriaProfesional c = new CategoriaProfesional();
        c.setId(e.getId());
        c.setName(e.getName());
        c.setDescription(e.getDescription());
        c.setEnabled(e.getEnabled());
        c.setCreatedAt(e.getCreatedAt());
        c.setUpdatedAt(e.getUpdatedAt());
        return c;
    }

    public Profesional toDomain(ProfessionalEntity e) {
        Profesional p = new Profesional();
        p.setId(e.getId());
        p.setCategoryId(e.getCategoryId());
        p.setFirstName(e.getFirstName());
        p.setLastName(e.getLastName());
        p.setEnabled(e.getEnabled());
        p.setCreatedAt(e.getCreatedAt());
        p.setUpdatedAt(e.getUpdatedAt());
        return p;
    }

    public HorarioSemanal toDomain(WeeklyScheduleEntity e) {
        HorarioSemanal h = new HorarioSemanal();
        h.setId(e.getId());
        h.setProfessionalId(e.getProfessionalId());
        h.setDayOfWeek(e.getDayOfWeek());
        h.setStartTime(e.getStartTime());
        h.setEndTime(e.getEndTime());
        h.setSlotDurationMinutes(e.getSlotDurationMinutes());
        h.setEnabled(e.getEnabled());
        h.setCreatedAt(e.getCreatedAt());
        h.setUpdatedAt(e.getUpdatedAt());
        return h;
    }
}
