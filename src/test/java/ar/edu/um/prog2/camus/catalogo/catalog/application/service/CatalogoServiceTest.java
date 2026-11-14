package ar.edu.um.prog2.camus.catalogo.catalog.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.AgendaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CriteriosBusqueda;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.BuscarProfesionalesUseCase;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.ListarCategoriasUseCase;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.ObtenerAgendaUseCase;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CatalogoServiceTest {

    private final ListarCategoriasUseCase listar = mock(ListarCategoriasUseCase.class);
    private final BuscarProfesionalesUseCase buscar = mock(BuscarProfesionalesUseCase.class);
    private final ObtenerAgendaUseCase agenda = mock(ObtenerAgendaUseCase.class);
    private final CatalogoService service = new CatalogoService(listar, buscar, agenda);

    @Test
    void delegaElListadoDeCategorias() {
        List<CategoriaProfesional> categorias = List.of(new CategoriaProfesional());
        when(listar.listar()).thenReturn(categorias);

        assertThat(service.listarCategorias()).isSameAs(categorias);
    }

    @Test
    void delegaLaBusquedaDeProfesionales() {
        CriteriosBusqueda criterios = new CriteriosBusqueda(101L, null, null, null);
        List<Profesional> resultado = List.of(new Profesional());
        when(buscar.buscar(criterios)).thenReturn(resultado);

        assertThat(service.buscarProfesionales(criterios)).isSameAs(resultado);
    }

    @Test
    void delegaLaAgenda() {
        Optional<AgendaProfesional> resultado = Optional.of(new AgendaProfesional(new Profesional(), List.of()));
        when(agenda.obtenerAgenda(204L)).thenReturn(resultado);

        assertThat(service.obtenerAgenda(204L)).isSameAs(resultado);
    }
}
