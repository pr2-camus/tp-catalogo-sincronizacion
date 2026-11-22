package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.CambiosCatalogo;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.RedisPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
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
        useCase = new SyncToLatestUseCaseImpl(redisPort, versionRepository, catalogoRepository, fullSyncUseCase);
    }

    private void versiones(Long local, Long actual, Long masAntigua) {
        when(versionRepository.obtenerVersionLocal()).thenReturn(local);
        when(redisPort.obtenerVersionActual()).thenReturn(actual);
        when(redisPort.obtenerVersionMasAntigua()).thenReturn(masAntigua);
    }

    @Test
    void cuandoVersionLocalEsIgualAActual_noHaceNada() {
        versiones(7L, 7L, 4L);

        useCase.sincronizarAlDia();

        verify(fullSyncUseCase, never()).sincronizarCompleto();
        verify(versionRepository, never()).guardarVersion(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void cuandoNoHayVersionActualEnRedis_noHaceNada() {
        versiones(5L, null, 4L);

        useCase.sincronizarAlDia();

        verify(fullSyncUseCase, never()).sincronizarCompleto();
        verify(versionRepository, never()).guardarVersion(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void cuandoHayHueco_ejecutaSnapshotCompleto() {
        versiones(3L, 7L, 4L);

        useCase.sincronizarAlDia();

        verify(fullSyncUseCase).sincronizarCompleto();
    }

    @Test
    void cuandoBaseVacia_ejecutaSnapshotCompleto() {
        versiones(null, 7L, 4L);

        useCase.sincronizarAlDia();

        verify(fullSyncUseCase).sincronizarCompleto();
    }

    @Test
    void cuandoVersionLocalMenorQueActual_aplicaVersionesEnOrden() {
        versiones(5L, 7L, 4L);
        when(redisPort.obtenerCambios(6L)).thenReturn(Optional.of(new CambiosCatalogo(List.of(), List.of(), List.of())));
        when(redisPort.obtenerCambios(7L)).thenReturn(Optional.of(new CambiosCatalogo(List.of(), List.of(), List.of())));

        useCase.sincronizarAlDia();

        InOrder orden = inOrder(versionRepository);
        orden.verify(versionRepository).guardarVersion(6L);
        orden.verify(versionRepository).guardarVersion(7L);
        verify(fullSyncUseCase, never()).sincronizarCompleto();
    }

    @Test
    void aplicarVersionGuardaLosElementosAfectadosYLaVersion() {
        CategoriaProfesional categoria = new CategoriaProfesional();
        Profesional profesional = new Profesional();
        HorarioSemanal horario = new HorarioSemanal();
        when(redisPort.obtenerCambios(6L)).thenReturn(Optional.of(new CambiosCatalogo(List.of(1L), List.of(2L), List.of(3L))));
        when(redisPort.obtenerCategoria(1L)).thenReturn(Optional.of(categoria));
        when(redisPort.obtenerProfesional(2L)).thenReturn(Optional.of(profesional));
        when(redisPort.obtenerHorario(3L)).thenReturn(Optional.of(horario));

        useCase.aplicarVersion(6L);

        verify(catalogoRepository).guardarCategorias(List.of(categoria));
        verify(catalogoRepository).guardarProfesionales(List.of(profesional));
        verify(catalogoRepository).guardarHorarios(List.of(horario));
        verify(versionRepository).guardarVersion(6L);
    }

    @Test
    void aplicarVersionIgnoraLosElementosQueYaNoEstanEnRedis() {
        when(redisPort.obtenerCambios(6L)).thenReturn(Optional.of(new CambiosCatalogo(List.of(1L), List.of(), List.of())));
        when(redisPort.obtenerCategoria(1L)).thenReturn(Optional.empty());

        useCase.aplicarVersion(6L);

        verify(catalogoRepository, never()).guardarCategorias(org.mockito.ArgumentMatchers.any());
        verify(versionRepository).guardarVersion(6L);
    }

    @Test
    void siFaltanLosCambiosEnRedis_haceSnapshotCompleto() {
        versiones(5L, 6L, 4L);
        when(redisPort.obtenerCambios(6L)).thenReturn(Optional.empty());

        useCase.sincronizarAlDia();

        verify(fullSyncUseCase).sincronizarCompleto();
        verify(versionRepository, never()).guardarVersion(org.mockito.ArgumentMatchers.any());
    }
}
