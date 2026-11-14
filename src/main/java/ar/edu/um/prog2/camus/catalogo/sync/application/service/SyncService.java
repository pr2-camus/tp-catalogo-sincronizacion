package ar.edu.um.prog2.camus.catalogo.sync.application.service;

import ar.edu.um.prog2.camus.catalogo.sync.domain.model.EstadoSincronizacion;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.ObtenerEstadoSincronizacionUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SyncService {

    private final SincronizarCatalogoUseCase sincronizarCompletoUseCase;
    private final ObtenerEstadoSincronizacionUseCase obtenerEstadoUseCase;

    public SyncService(SincronizarCatalogoUseCase sincronizarCompletoUseCase, ObtenerEstadoSincronizacionUseCase obtenerEstadoUseCase) {
        this.sincronizarCompletoUseCase = sincronizarCompletoUseCase;
        this.obtenerEstadoUseCase = obtenerEstadoUseCase;
    }

    public void forzarSincronizacionCompleta() {
        sincronizarCompletoUseCase.sincronizarCompleto();
    }

    public Optional<EstadoSincronizacion> obtenerEstado() {
        return obtenerEstadoUseCase.obtener();
    }
}
