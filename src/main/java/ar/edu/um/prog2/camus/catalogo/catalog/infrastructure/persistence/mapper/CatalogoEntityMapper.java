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

    public CategoryEntity toEntity(CategoriaProfesional c) {
        CategoryEntity e = new CategoryEntity();
        e.setId(c.getId());
        e.setName(c.getName());
        e.setDescription(c.getDescription());
        e.setEnabled(c.getEnabled());
        e.setCreatedAt(c.getCreatedAt());
        e.setUpdatedAt(c.getUpdatedAt());
        return e;
    }

    public ProfessionalEntity toEntity(Profesional p) {
        ProfessionalEntity e = new ProfessionalEntity();
        e.setId(p.getId());
        e.setCategoryId(p.getCategoryId());
        e.setFirstName(p.getFirstName());
        e.setLastName(p.getLastName());
        e.setEnabled(p.getEnabled());
        e.setCreatedAt(p.getCreatedAt());
        e.setUpdatedAt(p.getUpdatedAt());
        return e;
    }

    public WeeklyScheduleEntity toEntity(HorarioSemanal h) {
        WeeklyScheduleEntity e = new WeeklyScheduleEntity();
        e.setId(h.getId());
        e.setProfessionalId(h.getProfessionalId());
        e.setDayOfWeek(h.getDayOfWeek());
        e.setStartTime(h.getStartTime());
        e.setEndTime(h.getEndTime());
        e.setSlotDurationMinutes(h.getSlotDurationMinutes());
        e.setEnabled(h.getEnabled());
        e.setCreatedAt(h.getCreatedAt());
        e.setUpdatedAt(h.getUpdatedAt());
        return e;
    }

    public CategoriaProfesional toDomainModel(CategoryEntity e) {
        CategoriaProfesional c = new CategoriaProfesional();
        c.setId(e.getId());
        c.setName(e.getName());
        c.setDescription(e.getDescription());
        c.setEnabled(e.getEnabled());
        c.setCreatedAt(e.getCreatedAt());
        c.setUpdatedAt(e.getUpdatedAt());
        return c;
    }

    public Profesional toDomainModel(ProfessionalEntity e) {
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

    public HorarioSemanal toDomainModel(WeeklyScheduleEntity e) {
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
