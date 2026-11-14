package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarAlDiaUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import org.junit.jupiter.api.Test;

class SincronizarAlArrancarUseCaseImplTest {

    private final VersionRepositoryPort versiones = mock(VersionRepositoryPort.class);
    private final SincronizarCatalogoUseCase completo = mock(SincronizarCatalogoUseCase.class);
    private final SincronizarAlDiaUseCase alDia = mock(SincronizarAlDiaUseCase.class);
    private final SincronizarAlArrancarUseCaseImpl useCase = new SincronizarAlArrancarUseCaseImpl(versiones, completo, alDia);

    @Test
    void conBaseVaciaHaceSincronizacionCompleta() {
        when(versiones.obtenerVersionLocal()).thenReturn(null);

        useCase.sincronizarAlArrancar();

        verify(completo).sincronizarCompleto();
        verifyNoInteractions(alDia);
    }

    @Test
    void conVersionLocalSincronizaAlDia() {
        when(versiones.obtenerVersionLocal()).thenReturn(206L);

        useCase.sincronizarAlArrancar();

        verify(alDia).sincronizarAlDia();
        verifyNoInteractions(completo);
    }
}
