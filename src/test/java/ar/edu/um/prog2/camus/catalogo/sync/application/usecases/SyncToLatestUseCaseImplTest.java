package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import static org.mockito.Mockito.*;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.RedisPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SyncToLatestUseCaseImplTest {

    @Mock
    private RedisPort redisPort;

    @Mock
    private VersionRepositoryPort versionRepository;

    @Mock
    private CatalogoRepositoryPort catalogoRepository;

    @Mock
    private SincronizarCatalogoUseCase fullSyncUseCase;

    private SyncToLatestUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new SyncToLatestUseCaseImpl(redisPort, versionRepository, catalogoRepository, fullSyncUseCase, new ObjectMapper());
    }

    @Test
    void cuandoVersionLocalEsIgualAActual_noHaceNada() {
        when(versionRepository.obtenerVersionLocal()).thenReturn(7L);
        when(redisPort.obtenerVersionActual()).thenReturn(7L);
        when(redisPort.obtenerVersionMasAntigua()).thenReturn(4L);

        useCase.sincronizarAlDia();

        verify(fullSyncUseCase, never()).sincronizarCompleto();
        verify(versionRepository, never()).guardarVersion(any());
    }

    @Test
    void cuandoHayHueco_ejecutaSnapshotCompleto() {
        // local=3, oldest=4 → hueco porque local < oldest - 1
        when(versionRepository.obtenerVersionLocal()).thenReturn(3L);
        when(redisPort.obtenerVersionActual()).thenReturn(7L);
        when(redisPort.obtenerVersionMasAntigua()).thenReturn(4L);

        useCase.sincronizarAlDia();

        verify(fullSyncUseCase).sincronizarCompleto();
    }

    @Test
    void cuandoBaseVacia_ejecutaSnapshotCompleto() {
        when(versionRepository.obtenerVersionLocal()).thenReturn(null);
        when(redisPort.obtenerVersionActual()).thenReturn(7L);
        when(redisPort.obtenerVersionMasAntigua()).thenReturn(4L);

        useCase.sincronizarAlDia();

        verify(fullSyncUseCase).sincronizarCompleto();
    }

    @Test
    void cuandoVersionLocalMenorQueActual_aplicaVersionesEnOrden() throws Exception {
        // local=5, oldest=4, current=7 → aplica 6 y 7
        when(versionRepository.obtenerVersionLocal()).thenReturn(5L);
        when(redisPort.obtenerVersionActual()).thenReturn(7L);
        when(redisPort.obtenerVersionMasAntigua()).thenReturn(4L);

        String changesJson = "{\"version\":6,\"changes\":{\"professionalCategories\":[],\"professionals\":[],\"weeklySchedules\":[]}}";
        when(redisPort.obtenerCambios(6L)).thenReturn(changesJson);
        when(redisPort.obtenerCambios(7L)).thenReturn(changesJson.replace("6", "7"));

        useCase.sincronizarAlDia();

        verify(versionRepository).guardarVersion(6L);
        verify(versionRepository).guardarVersion(7L);
        verify(fullSyncUseCase, never()).sincronizarCompleto();
    }
}
