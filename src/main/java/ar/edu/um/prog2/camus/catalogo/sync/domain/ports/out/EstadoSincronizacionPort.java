package ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out;

import ar.edu.um.prog2.camus.catalogo.sync.domain.model.EstadoSincronizacion;
import java.util.Optional;

public interface EstadoSincronizacionPort {
    Optional<EstadoSincronizacion> obtenerEstado();
}
