package ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import java.util.List;

public interface BuscarProfesionalesUseCase {
    List<Profesional> buscarTodos();
    List<Profesional> buscarPorCategoria(Long categoriaId);
    List<Profesional> buscarPorNombre(String nombre);
    List<Profesional> buscarHabilitados();
}
