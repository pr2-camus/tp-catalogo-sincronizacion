package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.SyncStateEntity;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.EstadoSincronizacion;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.repository.JpaSyncStateRepositorySync;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class EstadoSincronizacionAdapterTest {

    private final JpaSyncStateRepositorySync repository = mock(JpaSyncStateRepositorySync.class);
    private final EstadoSincronizacionAdapter adapter = new EstadoSincronizacionAdapter(repository);

    @Test
    void devuelveLaVersionYLaFechaGuardadas() {
        Instant fecha = Instant.parse("2026-11-12T21:20:41Z");
        SyncStateEntity entity = mock(SyncStateEntity.class);
        when(entity.getCurrentVersion()).thenReturn(206L);
        when(entity.getUpdatedAt()).thenReturn(fecha);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        assertThat(adapter.obtenerEstado()).contains(new EstadoSincronizacion(206L, fecha));
    }

    @Test
    void sinRegistroDevuelveVacio() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThat(adapter.obtenerEstado()).isEmpty();
    }
}
