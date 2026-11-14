package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.CategoryEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.mapper.CatalogoEntityMapper;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaCategoryRepository;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaProfessionalRepository;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaWeeklyScheduleRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ConsultaCatalogoAdapterTest {

    private final JpaCategoryRepository categorias = mock(JpaCategoryRepository.class);
    private final JpaProfessionalRepository profesionales = mock(JpaProfessionalRepository.class);
    private final JpaWeeklyScheduleRepository horarios = mock(JpaWeeklyScheduleRepository.class);
    private final ConsultaCatalogoAdapter adapter = new ConsultaCatalogoAdapter(
        categorias,
        profesionales,
        horarios,
        new CatalogoEntityMapper()
    );

    private static ProfessionalEntity profesional(long id) {
        ProfessionalEntity e = new ProfessionalEntity();
        e.setId(id);
        e.setFirstName("Ana");
        return e;
    }

    @Test
    void obtieneLasCategorias() {
        CategoryEntity c = new CategoryEntity();
        c.setId(101L);
        when(categorias.findAll()).thenReturn(List.of(c));

        assertThat(adapter.obtenerCategorias()).extracting("id").containsExactly(101L);
    }

    @Test
    void obtieneTodosLosProfesionales() {
        when(profesionales.findAll()).thenReturn(List.of(profesional(1L), profesional(2L)));

        assertThat(adapter.obtenerProfesionales()).extracting("id").containsExactly(1L, 2L);
    }

    @Test
    void buscaPorCategoria() {
        when(profesionales.findByCategoryId(101L)).thenReturn(List.of(profesional(1L)));

        assertThat(adapter.buscarPorCategoria(101L)).extracting("id").containsExactly(1L);
    }

    @Test
    void buscaPorNombre() {
        when(profesionales.findByNombre("Ana")).thenReturn(List.of(profesional(3L)));

        assertThat(adapter.buscarPorNombre("Ana")).extracting("id").containsExactly(3L);
    }

    @Test
    void buscaPorHabilitado() {
        when(profesionales.findByEnabled(false)).thenReturn(List.of(profesional(4L)));

        assertThat(adapter.buscarPorHabilitado(false)).extracting("id").containsExactly(4L);
    }

    @Test
    void buscaPorDiaDeSemana() {
        when(profesionales.findByDayOfWeek("MONDAY")).thenReturn(List.of(profesional(5L)));

        assertThat(adapter.buscarPorDiaDeSemana("MONDAY")).extracting("id").containsExactly(5L);
    }

    @Test
    void obtieneUnProfesionalPorId() {
        when(profesionales.findById(204L)).thenReturn(Optional.of(profesional(204L)));
        when(profesionales.findById(999L)).thenReturn(Optional.empty());

        assertThat(adapter.obtenerProfesional(204L)).isPresent();
        assertThat(adapter.obtenerProfesional(999L)).isEmpty();
    }

    @Test
    void obtieneSoloLosHorariosHabilitadosDelProfesional() {
        WeeklyScheduleEntity h = new WeeklyScheduleEntity();
        h.setId(7L);
        when(horarios.findByProfessionalIdAndEnabled(204L, true)).thenReturn(List.of(h));

        assertThat(adapter.obtenerHorariosHabilitados(204L)).extracting("id").containsExactly(7L);
    }
}
