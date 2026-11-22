package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.mapper;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.Snapshot;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.dto.SnapshotResponse;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.dto.SnapshotResponse.CategoriaDto;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.dto.SnapshotResponse.HorarioDto;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.dto.SnapshotResponse.ProfesionalDto;
import org.springframework.stereotype.Component;

@Component
public class SnapshotDtoMapper {

    public Snapshot toDomain(SnapshotResponse response) {
        Snapshot snapshot = new Snapshot();
        snapshot.setSnapshotVersion(response.snapshotVersion());
        snapshot.setProfessionalCategories(response.professionalCategories().stream().map(this::toCategoria).toList());
        snapshot.setProfessionals(response.professionals().stream().map(this::toProfesional).toList());
        snapshot.setWeeklySchedules(response.weeklySchedules().stream().map(this::toHorario).toList());
        return snapshot;
    }

    public CategoriaProfesional toCategoria(CategoriaDto dto) {
        CategoriaProfesional categoria = new CategoriaProfesional();
        categoria.setId(dto.id());
        categoria.setName(dto.name());
        categoria.setDescription(dto.description());
        categoria.setEnabled(dto.enabled());
        categoria.setCreatedAt(dto.createdAt());
        categoria.setUpdatedAt(dto.updatedAt());
        return categoria;
    }

    public Profesional toProfesional(ProfesionalDto dto) {
        Profesional profesional = new Profesional();
        profesional.setId(dto.id());
        profesional.setCategoryId(dto.categoryId());
        profesional.setFirstName(dto.firstName());
        profesional.setLastName(dto.lastName());
        profesional.setEnabled(dto.enabled());
        profesional.setCreatedAt(dto.createdAt());
        profesional.setUpdatedAt(dto.updatedAt());
        return profesional;
    }

    public HorarioSemanal toHorario(HorarioDto dto) {
        HorarioSemanal horario = new HorarioSemanal();
        horario.setId(dto.id());
        horario.setProfessionalId(dto.professionalId());
        horario.setDayOfWeek(dto.dayOfWeek());
        horario.setStartTime(dto.startTime());
        horario.setEndTime(dto.endTime());
        horario.setSlotDurationMinutes(dto.slotDurationMinutes());
        horario.setEnabled(dto.enabled());
        horario.setCreatedAt(dto.createdAt());
        horario.setUpdatedAt(dto.updatedAt());
        return horario;
    }
}
