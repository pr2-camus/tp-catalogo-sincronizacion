package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import ar.edu.um.prog2.camus.catalogo.sync.domain.model.EstadoSincronizacion;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.ObtenerEstadoSincronizacionUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.EstadoSincronizacionPort;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ObtenerEstadoSincronizacionUseCaseImpl implements ObtenerEstadoSincronizacionUseCase {

    private final EstadoSincronizacionPort estadoPort;

    public ObtenerEstadoSincronizacionUseCaseImpl(EstadoSincronizacionPort estadoPort) {
        this.estadoPort = estadoPort;
    }

    @Override
    public Optional<EstadoSincronizacion> obtener() {
        return estadoPort.obtenerEstado();
    }
}
