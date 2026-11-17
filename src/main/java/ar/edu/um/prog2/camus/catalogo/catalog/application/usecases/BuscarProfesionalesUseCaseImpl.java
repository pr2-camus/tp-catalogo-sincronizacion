package ar.edu.um.prog2.camus.catalogo.catalog.application.usecases;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CriteriosBusqueda;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.BuscarProfesionalesUseCase;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BuscarProfesionalesUseCaseImpl implements BuscarProfesionalesUseCase {

    private final CatalogoRepositoryPort consulta;

    public BuscarProfesionalesUseCaseImpl(CatalogoRepositoryPort consulta) {
        this.consulta = consulta;
    }

    @Override
    public List<Profesional> buscar(CriteriosBusqueda criterios) {
        List<Profesional> resultado = null;
        if (criterios.categoryId() != null) {
            resultado = combinar(resultado, consulta.buscarPorCategoria(criterios.categoryId()));
        }
        if (criterios.name() != null) {
            resultado = combinar(resultado, consulta.buscarPorNombre(criterios.name()));
        }
        if (criterios.enabled() != null) {
            resultado = combinar(resultado, consulta.buscarPorHabilitado(criterios.enabled()));
        }
        if (criterios.availableOn() != null) {
            resultado = combinar(resultado, consulta.buscarPorDiaDeSemana(criterios.availableOn().getDayOfWeek().name()));
        }
        return resultado != null ? resultado : consulta.obtenerProfesionales();
    }

    /** Se queda solo con los profesionales que cumplen todos los filtros aplicados hasta ahora. */
    private static List<Profesional> combinar(List<Profesional> actual, List<Profesional> nuevos) {
        if (actual == null) {
            return nuevos;
        }
        Set<Long> ids = nuevos.stream().map(Profesional::getId).collect(Collectors.toSet());
        return actual
            .stream()
            .filter(p -> ids.contains(p.getId()))
            .toList();
    }
}
