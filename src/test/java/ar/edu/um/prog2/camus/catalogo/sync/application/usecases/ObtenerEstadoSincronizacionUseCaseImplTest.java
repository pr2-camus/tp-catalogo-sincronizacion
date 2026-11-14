package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.sync.domain.model.EstadoSincronizacion;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.EstadoSincronizacionPort;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ObtenerEstadoSincronizacionUseCaseImplTest {

    private final EstadoSincronizacionPort port = mock(EstadoSincronizacionPort.class);
    private final ObtenerEstadoSincronizacionUseCaseImpl useCase = new ObtenerEstadoSincronizacionUseCaseImpl(port);

    @Test
    void devuelveElEstadoGuardado() {
        EstadoSincronizacion estado = new EstadoSincronizacion(206L, Instant.parse("2026-11-12T21:20:41Z"));
        when(port.obtenerEstado()).thenReturn(Optional.of(estado));

        assertThat(useCase.obtener()).contains(estado);
    }

    @Test
    void sinDatosDevuelveVacio() {
        when(port.obtenerEstado()).thenReturn(Optional.empty());

        assertThat(useCase.obtener()).isEmpty();
    }
}
