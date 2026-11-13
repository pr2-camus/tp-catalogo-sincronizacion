package ar.edu.um.prog2.camus.catalogo.catalog.application.usecases;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CriteriosBusqueda;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.BuscarProfesionalesUseCase;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.ConsultaCatalogoPort;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BuscarProfesionalesUseCaseImpl implements BuscarProfesionalesUseCase {

    private final ConsultaCatalogoPort consulta;

    public BuscarProfesionalesUseCaseImpl(ConsultaCatalogoPort consulta) {
        this.consulta = consulta;
    }

    @Override
    public List<Profesional> buscar(CriteriosBusqueda criterios) {
        // Se aplica el primer filtro presente.
        if (criterios.categoryId() != null) {
            return consulta.buscarPorCategoria(criterios.categoryId());
        }
        if (criterios.name() != null) {
            return consulta.buscarPorNombre(criterios.name());
        }
        if (criterios.enabled() != null) {
            return consulta.buscarPorHabilitado(criterios.enabled());
        }
        if (criterios.availableOn() != null) {
            return consulta.buscarPorDiaDeSemana(criterios.availableOn().getDayOfWeek().name());
        }
        return consulta.obtenerProfesionales();
    }
}
