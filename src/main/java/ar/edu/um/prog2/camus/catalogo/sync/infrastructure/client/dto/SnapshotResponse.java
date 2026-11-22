package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import java.util.List;

/** Respuesta de /api/synchronization/snapshot de la catedra. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SnapshotResponse(
    Long snapshotVersion,
    List<CategoriaDto> professionalCategories,
    List<ProfesionalDto> professionals,
    List<HorarioDto> weeklySchedules
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CategoriaDto(Long id, String name, String description, Boolean enabled, Instant createdAt, Instant updatedAt) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ProfesionalDto(
        Long id,
        Long categoryId,
        String firstName,
        String lastName,
        Boolean enabled,
        Instant createdAt,
        Instant updatedAt
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record HorarioDto(
        Long id,
        Long professionalId,
        String dayOfWeek,
        String startTime,
        String endTime,
        Integer slotDurationMinutes,
        Boolean enabled,
        Instant createdAt,
        Instant updatedAt
    ) {}
}
