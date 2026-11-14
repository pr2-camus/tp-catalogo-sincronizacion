package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.CategoryEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class CatalogoEntityMapperTest {

    private static final Instant CREADO = Instant.parse("2026-09-01T03:00:00Z");
    private static final Instant ACTUALIZADO = Instant.parse("2026-09-02T03:00:00Z");
    private final CatalogoEntityMapper mapper = new CatalogoEntityMapper();

    @Test
    void mapeaUnaCategoria() {
        CategoryEntity e = new CategoryEntity();
        e.setId(101L);
        e.setName("Cardiologia");
        e.setDescription("Atencion cardiovascular");
        e.setEnabled(true);
        e.setCreatedAt(CREADO);
        e.setUpdatedAt(ACTUALIZADO);

        CategoriaProfesional c = mapper.toDomain(e);

        assertThat(c.getId()).isEqualTo(101L);
        assertThat(c.getName()).isEqualTo("Cardiologia");
        assertThat(c.getDescription()).isEqualTo("Atencion cardiovascular");
        assertThat(c.getEnabled()).isTrue();
        assertThat(c.getCreatedAt()).isEqualTo(CREADO);
        assertThat(c.getUpdatedAt()).isEqualTo(ACTUALIZADO);
    }

    @Test
    void mapeaUnProfesional() {
        ProfessionalEntity e = new ProfessionalEntity();
        e.setId(204L);
        e.setCategoryId(101L);
        e.setFirstName("Ana");
        e.setLastName("Lopez");
        e.setEnabled(false);
        e.setCreatedAt(CREADO);
        e.setUpdatedAt(ACTUALIZADO);

        Profesional p = mapper.toDomain(e);

        assertThat(p.getId()).isEqualTo(204L);
        assertThat(p.getCategoryId()).isEqualTo(101L);
        assertThat(p.getFirstName()).isEqualTo("Ana");
        assertThat(p.getLastName()).isEqualTo("Lopez");
        assertThat(p.getEnabled()).isFalse();
        assertThat(p.getCreatedAt()).isEqualTo(CREADO);
        assertThat(p.getUpdatedAt()).isEqualTo(ACTUALIZADO);
    }

    @Test
    void mapeaUnHorarioSemanal() {
        WeeklyScheduleEntity e = new WeeklyScheduleEntity();
        e.setId(1L);
        e.setProfessionalId(204L);
        e.setDayOfWeek("MONDAY");
        e.setStartTime("09:00");
        e.setEndTime("12:00");
        e.setSlotDurationMinutes(30);
        e.setEnabled(true);
        e.setCreatedAt(CREADO);
        e.setUpdatedAt(ACTUALIZADO);

        HorarioSemanal h = mapper.toDomain(e);

        assertThat(h.getId()).isEqualTo(1L);
        assertThat(h.getProfessionalId()).isEqualTo(204L);
        assertThat(h.getDayOfWeek()).isEqualTo("MONDAY");
        assertThat(h.getStartTime()).isEqualTo("09:00");
        assertThat(h.getEndTime()).isEqualTo("12:00");
        assertThat(h.getSlotDurationMinutes()).isEqualTo(30);
        assertThat(h.getEnabled()).isTrue();
        assertThat(h.getCreatedAt()).isEqualTo(CREADO);
        assertThat(h.getUpdatedAt()).isEqualTo(ACTUALIZADO);
    }
}
