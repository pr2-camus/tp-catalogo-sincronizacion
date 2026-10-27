package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import static org.mockito.Mockito.*;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.Snapshot;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.SnapshotClientPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RunFullSyncUseCaseImplTest {

    @Mock
    private SnapshotClientPort snapshotClient;

    @Mock
    private CatalogoRepositoryPort catalogoRepository;

    @Mock
    private VersionRepositoryPort versionRepository;

    private RunFullSyncUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new RunFullSyncUseCaseImpl(snapshotClient, catalogoRepository, versionRepository);
    }

    @Test
    void sincronizarCompleto_deberiaReemplazarDatosYGuardarVersion() {
        // Arrange
        Snapshot snapshot = new Snapshot();
        snapshot.setSnapshotVersion(7L);
        snapshot.setProfessionalCategories(List.of(new CategoriaProfesional()));
        snapshot.setProfessionals(List.of(new Profesional()));
        snapshot.setWeeklySchedules(List.of(new HorarioSemanal()));

        when(snapshotClient.obtenerSnapshot()).thenReturn(snapshot);

        // Act
        useCase.sincronizarCompleto();

        // Assert
        verify(catalogoRepository).limpiarYReemplazar(
            snapshot.getProfessionalCategories(),
            snapshot.getProfessionals(),
            snapshot.getWeeklySchedules()
        );
        verify(versionRepository).guardarVersion(7L);
    }

    @Test
    void sincronizarCompleto_siFallaElCliente_noDeberiaGuardarVersion() {
        // Arrange
        when(snapshotClient.obtenerSnapshot()).thenThrow(new RuntimeException("Error de conexion"));

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> useCase.sincronizarCompleto());

        verify(versionRepository, never()).guardarVersion(any());
        verify(catalogoRepository, never()).limpiarYReemplazar(any(), any(), any());
    }

    @Test
    void sincronizarCompleto_siSnapshotVacio_deberiaGuardarVersionIgual() {
        // Arrange
        Snapshot snapshot = new Snapshot();
        snapshot.setSnapshotVersion(1L);
        snapshot.setProfessionalCategories(List.of());
        snapshot.setProfessionals(List.of());
        snapshot.setWeeklySchedules(List.of());

        when(snapshotClient.obtenerSnapshot()).thenReturn(snapshot);

        // Act
        useCase.sincronizarCompleto();

        // Assert
        verify(versionRepository).guardarVersion(1L);
    }
}
