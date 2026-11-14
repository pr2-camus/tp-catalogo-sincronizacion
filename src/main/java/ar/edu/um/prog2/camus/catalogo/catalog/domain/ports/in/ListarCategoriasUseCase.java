package ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import java.util.List;

public interface ListarCategoriasUseCase {
    List<CategoriaProfesional> listar();
}
