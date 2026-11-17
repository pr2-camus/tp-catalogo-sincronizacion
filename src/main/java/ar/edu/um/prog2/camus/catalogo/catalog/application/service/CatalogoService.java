package ar.edu.um.prog2.camus.catalogo.catalog.application.service;

import ar.edu.um.prog2.camus.catalogo.catalog.application.exception.ProfesionalNoEncontradoException;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.AgendaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CriteriosBusqueda;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.BuscarProfesionalesUseCase;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.ListarCategoriasUseCase;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.ObtenerAgendaUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CatalogoService {

    private final ListarCategoriasUseCase listarCategorias;
    private final BuscarProfesionalesUseCase buscarProfesionales;
    private final ObtenerAgendaUseCase obtenerAgenda;

    public CatalogoService(
        ListarCategoriasUseCase listarCategorias,
        BuscarProfesionalesUseCase buscarProfesionales,
        ObtenerAgendaUseCase obtenerAgenda
    ) {
        this.listarCategorias = listarCategorias;
        this.buscarProfesionales = buscarProfesionales;
        this.obtenerAgenda = obtenerAgenda;
    }

    public List<CategoriaProfesional> listarCategorias() {
        return listarCategorias.listar();
    }

    public List<Profesional> buscarProfesionales(CriteriosBusqueda criterios) {
        return buscarProfesionales.buscar(criterios);
    }

    public AgendaProfesional obtenerAgenda(Long profesionalId) {
        return obtenerAgenda.obtenerAgenda(profesionalId).orElseThrow(() -> new ProfesionalNoEncontradoException(profesionalId));
    }
}
