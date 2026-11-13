package ar.edu.um.prog2.camus.catalogo.sync.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.sync.application.usecases.SyncToLatestUseCaseImpl;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.EstadoSincronizacion;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.ObtenerEstadoSincronizacionUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class SyncServiceTest {

    private final SincronizarCatalogoUseCase completa = mock(SincronizarCatalogoUseCase.class);
    private final SyncToLatestUseCaseImpl alDia = mock(SyncToLatestUseCaseImpl.class);
    private final VersionRepositoryPort versiones = mock(VersionRepositoryPort.class);
    private final ObtenerEstadoSincronizacionUseCase estado = mock(ObtenerEstadoSincronizacionUseCase.class);
    private final SyncService service = new SyncService(completa, alDia, versiones, estado);

    @Test
    void alArrancarConLaBaseVaciaHaceLaSincronizacionCompleta() {
        when(versiones.obtenerVersionLocal()).thenReturn(null);

        service.sincronizarAlArrancar();

        verify(completa).sincronizarCompleto();
        verify(alDia, never()).sincronizarAlDia();
    }

    @Test
    void alArrancarConDatosSoloSePoneAlDia() {
        when(versiones.obtenerVersionLocal()).thenReturn(206L);

        service.sincronizarAlArrancar();

        verify(alDia).sincronizarAlDia();
        verify(completa, never()).sincronizarCompleto();
    }

    @Test
    void laSincronizacionPeriodicaSePoneAlDia() {
        service.sincronizarPeriodico();

        verify(alDia).sincronizarAlDia();
    }

    @Test
    void forzarLaSincronizacionHaceLaCompleta() {
        service.forzarSincronizacionCompleta();

        verify(completa).sincronizarCompleto();
    }

    @Test
    void devuelveElEstadoDeLaSincronizacion() {
        Optional<EstadoSincronizacion> esperado = Optional.of(new EstadoSincronizacion(206L, Instant.parse("2026-11-12T21:20:41Z")));
        when(estado.obtener()).thenReturn(esperado);

        assertThat(service.obtenerEstado()).isSameAs(esperado);
    }
}
