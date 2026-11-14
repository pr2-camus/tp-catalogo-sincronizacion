package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.adapter;

import ar.edu.um.prog2.camus.catalogo.sync.domain.model.EstadoSincronizacion;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.EstadoSincronizacionPort;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.repository.JpaSyncStateRepositorySync;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class EstadoSincronizacionAdapter implements EstadoSincronizacionPort {

    private final JpaSyncStateRepositorySync repository;

    public EstadoSincronizacionAdapter(JpaSyncStateRepositorySync repository) {
        this.repository = repository;
    }

    @Override
    public Optional<EstadoSincronizacion> obtenerEstado() {
        return repository.findById(1L).map(s -> new EstadoSincronizacion(s.getCurrentVersion(), s.getUpdatedAt()));
    }
}
