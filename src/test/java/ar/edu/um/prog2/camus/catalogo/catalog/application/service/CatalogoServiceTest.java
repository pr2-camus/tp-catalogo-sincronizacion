package ar.edu.um.prog2.camus.catalogo.catalog.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.catalog.application.exception.ProfesionalNoEncontradoException;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.AgendaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CriteriosBusqueda;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.BuscarProfesionalesUseCase;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.ListarCategoriasUseCase;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.ObtenerAgendaUseCase;
import ar.edu.um.prog2.camus.catalogo.error.ErrorKind;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CatalogoServiceTest {

    private final ListarCategoriasUseCase listar = mock(ListarCategoriasUseCase.class);
    private final BuscarProfesionalesUseCase buscar = mock(BuscarProfesionalesUseCase.class);
    private final ObtenerAgendaUseCase agenda = mock(ObtenerAgendaUseCase.class);
    private final CatalogoService service = new CatalogoService(listar, buscar, agenda);

    @Test
    void listarCategoriasDelegaEnElCasoDeUso() {
        CategoriaProfesional categoria = new CategoriaProfesional();
        when(listar.listar()).thenReturn(List.of(categoria));

        assertThat(service.listarCategorias()).containsExactly(categoria);
    }

    @Test
    void buscarProfesionalesDelegaEnElCasoDeUso() {
        CriteriosBusqueda criterios = new CriteriosBusqueda(101L, null, null, null);
        Profesional profesional = new Profesional();
        when(buscar.buscar(criterios)).thenReturn(List.of(profesional));

        assertThat(service.buscarProfesionales(criterios)).containsExactly(profesional);
    }

    @Test
    void obtenerAgendaDevuelveLaAgendaDelProfesional() {
        AgendaProfesional esperada = new AgendaProfesional(new Profesional(), List.of());
        when(agenda.obtenerAgenda(204L)).thenReturn(Optional.of(esperada));

        assertThat(service.obtenerAgenda(204L)).isSameAs(esperada);
    }

    @Test
    void obtenerAgendaDeUnProfesionalInexistenteLanzaNotFound() {
        when(agenda.obtenerAgenda(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtenerAgenda(999L)).isInstanceOfSatisfying(ProfesionalNoEncontradoException.class, e -> {
            assertThat(e.getKind()).isEqualTo(ErrorKind.NOT_FOUND);
            assertThat(e.getCode()).isEqualTo("PROFESSIONAL_NOT_FOUND");
        });
    }
}
