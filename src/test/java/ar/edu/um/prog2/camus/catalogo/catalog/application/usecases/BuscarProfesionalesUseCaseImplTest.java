package ar.edu.um.prog2.camus.catalogo.catalog.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CriteriosBusqueda;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.ConsultaCatalogoPort;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class BuscarProfesionalesUseCaseImplTest {

    private final ConsultaCatalogoPort consulta = mock(ConsultaCatalogoPort.class);
    private final BuscarProfesionalesUseCaseImpl useCase = new BuscarProfesionalesUseCaseImpl(consulta);
    private final List<Profesional> resultado = List.of(new Profesional());

    private static Profesional profesional(long id) {
        Profesional p = new Profesional();
        p.setId(id);
        return p;
    }

    @Test
    void sinFiltrosDevuelveTodosLosProfesionales() {
        when(consulta.obtenerProfesionales()).thenReturn(resultado);

        assertThat(useCase.buscar(new CriteriosBusqueda(null, null, null, null))).isSameAs(resultado);
    }

    @Test
    void filtraPorCategoria() {
        when(consulta.buscarPorCategoria(101L)).thenReturn(resultado);

        assertThat(useCase.buscar(new CriteriosBusqueda(101L, null, null, null))).isSameAs(resultado);
    }

    @Test
    void filtraPorNombre() {
        when(consulta.buscarPorNombre("Mart")).thenReturn(resultado);

        assertThat(useCase.buscar(new CriteriosBusqueda(null, "Mart", null, null))).isSameAs(resultado);
    }

    @Test
    void filtraPorHabilitadoIncluidoElValorFalso() {
        when(consulta.buscarPorHabilitado(false)).thenReturn(resultado);

        assertThat(useCase.buscar(new CriteriosBusqueda(null, null, false, null))).isSameAs(resultado);
    }

    @Test
    void filtraPorDisponibilidadSegunElDiaDeLaSemana() {
        when(consulta.buscarPorDiaDeSemana("MONDAY")).thenReturn(resultado);

        assertThat(useCase.buscar(new CriteriosBusqueda(null, null, null, LocalDate.of(2026, 11, 16)))).isSameAs(resultado);
    }

    @Test
    void combinaCategoriaYHabilitadoQuedandoseConLosQueCumplenAmbos() {
        when(consulta.buscarPorCategoria(101L)).thenReturn(List.of(profesional(1L), profesional(2L)));
        when(consulta.buscarPorHabilitado(true)).thenReturn(List.of(profesional(2L), profesional(3L)));

        assertThat(useCase.buscar(new CriteriosBusqueda(101L, null, true, null)))
            .extracting(Profesional::getId)
            .containsExactly(2L);
    }

    @Test
    void siNingunoCumpleTodosLosFiltrosDevuelveVacio() {
        when(consulta.buscarPorCategoria(101L)).thenReturn(List.of(profesional(1L)));
        when(consulta.buscarPorHabilitado(false)).thenReturn(List.of(profesional(2L)));

        assertThat(useCase.buscar(new CriteriosBusqueda(101L, null, false, null))).isEmpty();
    }

    @Test
    void combinaLosCuatroFiltros() {
        when(consulta.buscarPorCategoria(101L)).thenReturn(List.of(profesional(1L), profesional(2L), profesional(3L)));
        when(consulta.buscarPorNombre("Mart")).thenReturn(List.of(profesional(2L), profesional(3L)));
        when(consulta.buscarPorHabilitado(true)).thenReturn(List.of(profesional(3L), profesional(4L)));
        when(consulta.buscarPorDiaDeSemana("MONDAY")).thenReturn(List.of(profesional(3L), profesional(5L)));

        assertThat(useCase.buscar(new CriteriosBusqueda(101L, "Mart", true, LocalDate.of(2026, 11, 16))))
            .extracting(Profesional::getId)
            .containsExactly(3L);
    }
}
