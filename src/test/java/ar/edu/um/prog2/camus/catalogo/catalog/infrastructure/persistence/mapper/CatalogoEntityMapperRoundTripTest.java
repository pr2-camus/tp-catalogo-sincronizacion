package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class CatalogoEntityMapperRoundTripTest {

    private static final Instant CREADO = Instant.parse("2026-01-01T10:00:00Z");
    private static final Instant ACTUALIZADO = Instant.parse("2026-02-01T10:00:00Z");

    private final CatalogoEntityMapper mapper = new CatalogoEntityMapper();

    @Test
    void categoriaIdaYVueltaNoPierdeCampos() {
        CategoriaProfesional c = new CategoriaProfesional();
        c.setId(101L);
        c.setName("Cardiologia");
        c.setDescription("Corazon");
        c.setEnabled(true);
        c.setCreatedAt(CREADO);
        c.setUpdatedAt(ACTUALIZADO);

        assertThat(mapper.toDomainModel(mapper.toEntity(c)))
            .usingRecursiveComparison()
            .isEqualTo(c);
    }

    @Test
    void profesionalIdaYVueltaNoPierdeCampos() {
        Profesional p = new Profesional();
        p.setId(204L);
        p.setCategoryId(101L);
        p.setFirstName("Ana");
        p.setLastName("Lopez");
        p.setEnabled(true);
        p.setCreatedAt(CREADO);
        p.setUpdatedAt(ACTUALIZADO);

        assertThat(mapper.toDomainModel(mapper.toEntity(p)))
            .usingRecursiveComparison()
            .isEqualTo(p);
    }

    @Test
    void horarioIdaYVueltaNoPierdeCampos() {
        HorarioSemanal h = new HorarioSemanal();
        h.setId(7L);
        h.setProfessionalId(204L);
        h.setDayOfWeek("MONDAY");
        h.setStartTime("09:00");
        h.setEndTime("12:00");
        h.setSlotDurationMinutes(30);
        h.setEnabled(true);
        h.setCreatedAt(CREADO);
        h.setUpdatedAt(ACTUALIZADO);

        assertThat(mapper.toDomainModel(mapper.toEntity(h)))
            .usingRecursiveComparison()
            .isEqualTo(h);
    }
}
