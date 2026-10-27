package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.shared.infrastructure.config.CatedraProperties;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.Snapshot;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.SnapshotClientPort;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SnapshotRestAdapter implements SnapshotClientPort {

    private final RestClient restClient;

    public SnapshotRestAdapter(CatedraProperties catedraProperties) {
        this.restClient = RestClient.builder()
            .baseUrl(catedraProperties.getApiUrl())
            .defaultHeader("Authorization", "Bearer " + catedraProperties.getJwt())
            .build();
    }

    @Override
    public Snapshot obtenerSnapshot() {
        SnapshotResponse response = restClient.get().uri("/api/synchronization/snapshot").retrieve().body(SnapshotResponse.class);

        return mapToDomain(response);
    }

    private Snapshot mapToDomain(SnapshotResponse response) {
        Snapshot snapshot = new Snapshot();
        snapshot.setSnapshotVersion(response.snapshotVersion());

        List<CategoriaProfesional> categorias = response
            .professionalCategories()
            .stream()
            .map(c -> {
                CategoriaProfesional cat = new CategoriaProfesional();
                cat.setId(c.id());
                cat.setName(c.name());
                cat.setDescription(c.description());
                cat.setEnabled(c.enabled());
                cat.setCreatedAt(c.createdAt());
                cat.setUpdatedAt(c.updatedAt());
                return cat;
            })
            .toList();

        List<Profesional> profesionales = response
            .professionals()
            .stream()
            .map(p -> {
                Profesional prof = new Profesional();
                prof.setId(p.id());
                prof.setCategoryId(p.categoryId());
                prof.setFirstName(p.firstName());
                prof.setLastName(p.lastName());
                prof.setEnabled(p.enabled());
                prof.setCreatedAt(p.createdAt());
                prof.setUpdatedAt(p.updatedAt());
                return prof;
            })
            .toList();

        List<HorarioSemanal> horarios = response
            .weeklySchedules()
            .stream()
            .map(h -> {
                HorarioSemanal horario = new HorarioSemanal();
                horario.setId(h.id());
                horario.setProfessionalId(h.professionalId());
                horario.setDayOfWeek(h.dayOfWeek());
                horario.setStartTime(h.startTime());
                horario.setEndTime(h.endTime());
                horario.setSlotDurationMinutes(h.slotDurationMinutes());
                horario.setEnabled(h.enabled());
                horario.setCreatedAt(h.createdAt());
                horario.setUpdatedAt(h.updatedAt());
                return horario;
            })
            .toList();

        snapshot.setProfessionalCategories(categorias);
        snapshot.setProfessionals(profesionales);
        snapshot.setWeeklySchedules(horarios);
        return snapshot;
    }

    // ── DTOs de respuesta ─────────────────────────────────────────────────

    @JsonIgnoreProperties(ignoreUnknown = true)
    record SnapshotResponse(
        Long snapshotVersion,
        List<CategoriaDto> professionalCategories,
        List<ProfesionalDto> professionals,
        List<HorarioDto> weeklySchedules
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record CategoriaDto(Long id, String name, String description, Boolean enabled, Instant createdAt, Instant updatedAt) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record ProfesionalDto(
        Long id,
        Long categoryId,
        String firstName,
        String lastName,
        Boolean enabled,
        Instant createdAt,
        Instant updatedAt
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record HorarioDto(
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
