package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.mapper;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.AgendaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.dto.AgendaResponse;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.dto.CategoriaResponse;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.dto.HorarioSemanalResponse;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.dto.ProfesionalResponse;
import org.springframework.stereotype.Component;

@Component
public class CatalogoDtoMapper {

    public CategoriaResponse toResponse(CategoriaProfesional c) {
        return new CategoriaResponse(c.getId(), c.getName(), c.getDescription(), c.getEnabled(), c.getCreatedAt(), c.getUpdatedAt());
    }

    public ProfesionalResponse toResponse(Profesional p) {
        return new ProfesionalResponse(
            p.getId(),
            p.getCategoryId(),
            p.getFirstName(),
            p.getLastName(),
            p.getEnabled(),
            p.getCreatedAt(),
            p.getUpdatedAt()
        );
    }

    public HorarioSemanalResponse toResponse(HorarioSemanal h) {
        return new HorarioSemanalResponse(
            h.getId(),
            h.getProfessionalId(),
            h.getDayOfWeek(),
            h.getStartTime(),
            h.getEndTime(),
            h.getSlotDurationMinutes(),
            h.getEnabled(),
            h.getCreatedAt(),
            h.getUpdatedAt()
        );
    }

    public AgendaResponse toResponse(AgendaProfesional a) {
        return new AgendaResponse(toResponse(a.profesional()), a.horarios().stream().map(this::toResponse).toList());
    }
}
