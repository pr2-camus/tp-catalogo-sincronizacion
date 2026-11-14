package ar.edu.um.prog2.camus.catalogo.catalog.application.usecases;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.ListarCategoriasUseCase;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ListarCategoriasUseCaseImpl implements ListarCategoriasUseCase {

    private final CatalogoRepositoryPort consulta;

    public ListarCategoriasUseCaseImpl(CatalogoRepositoryPort consulta) {
        this.consulta = consulta;
    }

    @Override
    public List<CategoriaProfesional> listar() {
        return consulta.obtenerCategorias();
    }
}
