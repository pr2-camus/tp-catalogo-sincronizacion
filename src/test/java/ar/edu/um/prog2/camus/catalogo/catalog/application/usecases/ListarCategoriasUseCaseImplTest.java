package ar.edu.um.prog2.camus.catalogo.catalog.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListarCategoriasUseCaseImplTest {

    @Test
    void devuelveLasCategoriasDelCatalogo() {
        CatalogoRepositoryPort consulta = mock(CatalogoRepositoryPort.class);
        List<CategoriaProfesional> categorias = List.of(new CategoriaProfesional());
        when(consulta.obtenerCategorias()).thenReturn(categorias);

        assertThat(new ListarCategoriasUseCaseImpl(consulta).listar()).isSameAs(categorias);
    }
}
