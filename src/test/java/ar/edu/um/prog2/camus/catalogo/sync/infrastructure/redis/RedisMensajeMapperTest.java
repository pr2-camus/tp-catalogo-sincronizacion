package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.redis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.CambiosCatalogo;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

class RedisMensajeMapperTest {

    private final RedisMensajeMapper mapper = new RedisMensajeMapper(JsonMapper.builder().build());

    @Test
    void leeLosIdsAfectadosDeUnaVersion() {
        String json = """
        {"version":6,"changes":{"professionalCategories":[101],"professionals":[204,205],"weeklySchedules":[]}}
        """;

        CambiosCatalogo cambios = mapper.toCambios(json);

        assertThat(cambios.categorias()).containsExactly(101L);
        assertThat(cambios.profesionales()).containsExactly(204L, 205L);
        assertThat(cambios.horarios()).isEmpty();
    }

    @Test
    void leeUnaCategoriaConDescripcion() {
        String json = """
        {"id":101,"name":"Cardiologia","description":"Corazon","enabled":true,
         "createdAt":"2026-01-01T10:00:00Z","updatedAt":"2026-02-01T10:00:00Z"}
        """;

        CategoriaProfesional categoria = mapper.toCategoria(json);

        assertThat(categoria.getId()).isEqualTo(101L);
        assertThat(categoria.getName()).isEqualTo("Cardiologia");
        assertThat(categoria.getDescription()).isEqualTo("Corazon");
        assertThat(categoria.getEnabled()).isTrue();
        assertThat(categoria.getUpdatedAt()).isEqualTo(Instant.parse("2026-02-01T10:00:00Z"));
    }

    @Test
    void leeUnaCategoriaConDescripcionNula() {
        String json = """
        {"id":102,"name":"Pediatria","description":null,"enabled":false,
         "createdAt":"2026-01-01T10:00:00Z","updatedAt":"2026-02-01T10:00:00Z"}
        """;

        assertThat(mapper.toCategoria(json).getDescription()).isNull();
    }

    @Test
    void leeUnProfesional() {
        String json = """
        {"id":204,"categoryId":101,"firstName":"Ana","lastName":"Lopez","enabled":true,
         "createdAt":"2026-01-01T10:00:00Z","updatedAt":"2026-02-01T10:00:00Z"}
        """;

        Profesional profesional = mapper.toProfesional(json);

        assertThat(profesional.getId()).isEqualTo(204L);
        assertThat(profesional.getCategoryId()).isEqualTo(101L);
        assertThat(profesional.getFirstName()).isEqualTo("Ana");
        assertThat(profesional.getLastName()).isEqualTo("Lopez");
        assertThat(profesional.getEnabled()).isTrue();
    }

    @Test
    void leeUnHorarioSemanal() {
        String json = """
        {"id":7,"professionalId":204,"dayOfWeek":"MONDAY","startTime":"09:00","endTime":"12:00",
         "slotDurationMinutes":30,"enabled":true,
         "createdAt":"2026-01-01T10:00:00Z","updatedAt":"2026-02-01T10:00:00Z"}
        """;

        HorarioSemanal horario = mapper.toHorario(json);

        assertThat(horario.getId()).isEqualTo(7L);
        assertThat(horario.getProfessionalId()).isEqualTo(204L);
        assertThat(horario.getDayOfWeek()).isEqualTo("MONDAY");
        assertThat(horario.getStartTime()).isEqualTo("09:00");
        assertThat(horario.getEndTime()).isEqualTo("12:00");
        assertThat(horario.getSlotDurationMinutes()).isEqualTo(30);
    }

    @Test
    void unJsonInvalidoLanzaExcepcion() {
        assertThatThrownBy(() -> mapper.toCambios("no es json")).isInstanceOf(RuntimeException.class);
    }
}
