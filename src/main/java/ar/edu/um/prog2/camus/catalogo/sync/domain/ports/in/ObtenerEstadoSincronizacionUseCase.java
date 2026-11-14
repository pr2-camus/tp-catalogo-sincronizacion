package ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in;

import ar.edu.um.prog2.camus.catalogo.sync.domain.model.EstadoSincronizacion;
import java.util.Optional;

public interface ObtenerEstadoSincronizacionUseCase {
    Optional<EstadoSincronizacion> obtener();
}
