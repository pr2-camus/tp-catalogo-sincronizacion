package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.prog2.camus.catalogo.sync.domain.model.Snapshot;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.dto.SnapshotResponse;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.dto.SnapshotResponse.CategoriaDto;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.dto.SnapshotResponse.HorarioDto;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.dto.SnapshotResponse.ProfesionalDto;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class SnapshotDtoMapperTest {

    private static final Instant CREADO = Instant.parse("2026-01-01T10:00:00Z");
    private static final Instant ACTUALIZADO = Instant.parse("2026-02-01T10:00:00Z");

    private final SnapshotDtoMapper mapper = new SnapshotDtoMapper();

    @Test
    void conviertePasandoTodosLosCamposALosModelosDeDominio() {
        SnapshotResponse response = new SnapshotResponse(
            206L,
            List.of(new CategoriaDto(101L, "Cardiologia", "Corazon", true, CREADO, ACTUALIZADO)),
            List.of(new ProfesionalDto(204L, 101L, "Ana", "Lopez", true, CREADO, ACTUALIZADO)),
            List.of(new HorarioDto(7L, 204L, "MONDAY", "09:00", "12:00", 30, true, CREADO, ACTUALIZADO))
        );

        Snapshot snapshot = mapper.toDomain(response);

        assertThat(snapshot.getSnapshotVersion()).isEqualTo(206L);
        assertThat(snapshot.getProfessionalCategories())
            .singleElement()
            .satisfies(c -> {
                assertThat(c.getId()).isEqualTo(101L);
                assertThat(c.getName()).isEqualTo("Cardiologia");
                assertThat(c.getDescription()).isEqualTo("Corazon");
                assertThat(c.getEnabled()).isTrue();
                assertThat(c.getCreatedAt()).isEqualTo(CREADO);
                assertThat(c.getUpdatedAt()).isEqualTo(ACTUALIZADO);
            });
        assertThat(snapshot.getProfessionals())
            .singleElement()
            .satisfies(p -> {
                assertThat(p.getId()).isEqualTo(204L);
                assertThat(p.getCategoryId()).isEqualTo(101L);
                assertThat(p.getFirstName()).isEqualTo("Ana");
                assertThat(p.getLastName()).isEqualTo("Lopez");
            });
        assertThat(snapshot.getWeeklySchedules())
            .singleElement()
            .satisfies(h -> {
                assertThat(h.getId()).isEqualTo(7L);
                assertThat(h.getProfessionalId()).isEqualTo(204L);
                assertThat(h.getDayOfWeek()).isEqualTo("MONDAY");
                assertThat(h.getStartTime()).isEqualTo("09:00");
                assertThat(h.getEndTime()).isEqualTo("12:00");
                assertThat(h.getSlotDurationMinutes()).isEqualTo(30);
            });
    }
}
