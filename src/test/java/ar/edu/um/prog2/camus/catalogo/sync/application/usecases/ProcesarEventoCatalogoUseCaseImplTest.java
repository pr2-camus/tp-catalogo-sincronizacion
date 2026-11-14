package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarAlDiaUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.EventoProcesadoPort;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class ProcesarEventoCatalogoUseCaseImplTest {

    private final EventoProcesadoPort eventos = mock(EventoProcesadoPort.class);
    private final SincronizarAlDiaUseCase alDia = mock(SincronizarAlDiaUseCase.class);
    private final ProcesarEventoCatalogoUseCaseImpl useCase = new ProcesarEventoCatalogoUseCaseImpl(eventos, alDia);

    @Test
    void eventoNuevoSeRegistraYSincroniza() {
        when(eventos.yaProcesado("evt-1")).thenReturn(false);

        useCase.procesar("evt-1");

        InOrder orden = inOrder(eventos, alDia);
        orden.verify(eventos).registrar("evt-1");
        orden.verify(alDia).sincronizarAlDia();
    }

    @Test
    void eventoDuplicadoSeIgnora() {
        when(eventos.yaProcesado("evt-1")).thenReturn(true);

        useCase.procesar("evt-1");

        verify(eventos, never()).registrar("evt-1");
        verify(alDia, never()).sincronizarAlDia();
    }

    @Test
    void siFallaLaSincronizacionLaExcepcionSePropagaParaQueHagaRollback() {
        when(eventos.yaProcesado("evt-2")).thenReturn(false);
        doThrow(new IllegalStateException("redis caido")).when(alDia).sincronizarAlDia();

        assertThatThrownBy(() -> useCase.procesar("evt-2")).isInstanceOf(IllegalStateException.class);
    }
}
