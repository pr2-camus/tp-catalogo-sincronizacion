package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.ProcessedEventEntity;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.repository.JpaProcessedEventRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class EventoProcesadoAdapterTest {

    private final JpaProcessedEventRepository repository = mock(JpaProcessedEventRepository.class);
    private final EventoProcesadoAdapter adapter = new EventoProcesadoAdapter(repository);

    @Test
    void yaProcesadoConsultaPorId() {
        when(repository.existsById("evt-1")).thenReturn(true);

        assertThat(adapter.yaProcesado("evt-1")).isTrue();
        assertThat(adapter.yaProcesado("otro")).isFalse();
    }

    @Test
    void registrarGuardaElIdYLaFecha() {
        adapter.registrar("evt-1");

        ArgumentCaptor<ProcessedEventEntity> captor = ArgumentCaptor.forClass(ProcessedEventEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getEventId()).isEqualTo("evt-1");
        assertThat(captor.getValue().getProcessedAt()).isNotNull();
    }
}
