package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.scheduling;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarAlArrancarUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarAlDiaUseCase;
import org.junit.jupiter.api.Test;

class SyncSchedulerTest {

    private final SincronizarAlArrancarUseCase alArrancar = mock(SincronizarAlArrancarUseCase.class);
    private final SincronizarAlDiaUseCase alDia = mock(SincronizarAlDiaUseCase.class);
    private final SyncScheduler scheduler = new SyncScheduler(alArrancar, alDia);

    @Test
    void alArrancarInvocaElCasoDeUsoDeArranque() {
        scheduler.sincronizarAlArrancar();

        verify(alArrancar).sincronizarAlArrancar();
    }

    @Test
    void periodicoSincronizaAlDia() {
        scheduler.sincronizarPeriodico();

        verify(alDia).sincronizarAlDia();
    }
}
