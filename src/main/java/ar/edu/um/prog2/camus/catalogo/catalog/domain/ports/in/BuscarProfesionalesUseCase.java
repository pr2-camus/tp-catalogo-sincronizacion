package ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CriteriosBusqueda;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import java.util.List;

public interface BuscarProfesionalesUseCase {
    List<Profesional> buscar(CriteriosBusqueda criterios);
}
