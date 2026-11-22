package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class SyncStateEntityTest {

    @Test
    void laFilaDeEstadoTieneSiempreElIdUno() {
        assertThat(new SyncStateEntity().getId()).isEqualTo(1L);
    }

    @Test
    void guardaLaVersionYLaFechaDeActualizacion() {
        SyncStateEntity estado = new SyncStateEntity();
        Instant ahora = Instant.parse("2026-11-22T12:00:00Z");

        estado.setCurrentVersion(206L);
        estado.setUpdatedAt(ahora);

        assertThat(estado.getCurrentVersion()).isEqualTo(206L);
        assertThat(estado.getUpdatedAt()).isEqualTo(ahora);
    }
}
