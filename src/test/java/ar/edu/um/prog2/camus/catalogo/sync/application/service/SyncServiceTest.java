package ar.edu.um.prog2.camus.catalogo.sync.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.sync.domain.model.EstadoSincronizacion;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.ObtenerEstadoSincronizacionUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class SyncServiceTest {

    private final SincronizarCatalogoUseCase completo = mock(SincronizarCatalogoUseCase.class);
    private final ObtenerEstadoSincronizacionUseCase estado = mock(ObtenerEstadoSincronizacionUseCase.class);
    private final SyncService service = new SyncService(completo, estado);

    @Test
    void forzarSincronizacionCompletaDelegaEnElCasoDeUso() {
        service.forzarSincronizacionCompleta();

        verify(completo).sincronizarCompleto();
    }

    @Test
    void obtenerEstadoDevuelveLoQueInformaElCasoDeUso() {
        EstadoSincronizacion esperado = mock(EstadoSincronizacion.class);
        when(estado.obtener()).thenReturn(Optional.of(esperado));

        assertThat(service.obtenerEstado()).containsSame(esperado);
    }

    @Test
    void obtenerEstadoSinDatosDevuelveVacio() {
        when(estado.obtener()).thenReturn(Optional.empty());

        assertThat(service.obtenerEstado()).isEmpty();
    }
}
