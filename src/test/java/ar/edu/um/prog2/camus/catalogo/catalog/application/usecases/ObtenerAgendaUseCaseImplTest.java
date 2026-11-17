package ar.edu.um.prog2.camus.catalogo.catalog.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.AgendaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ObtenerAgendaUseCaseImplTest {

    private final CatalogoRepositoryPort consulta = mock(CatalogoRepositoryPort.class);
    private final ObtenerAgendaUseCaseImpl useCase = new ObtenerAgendaUseCaseImpl(consulta);

    @Test
    void devuelveElProfesionalConSusHorariosHabilitados() {
        Profesional profesional = new Profesional();
        profesional.setId(204L);
        HorarioSemanal horario = new HorarioSemanal();
        when(consulta.obtenerProfesional(204L)).thenReturn(Optional.of(profesional));
        when(consulta.obtenerHorariosHabilitados(204L)).thenReturn(List.of(horario));

        Optional<AgendaProfesional> agenda = useCase.obtenerAgenda(204L);

        assertThat(agenda).isPresent();
        assertThat(agenda.orElseThrow().profesional()).isSameAs(profesional);
        assertThat(agenda.orElseThrow().horarios()).containsExactly(horario);
    }

    @Test
    void siElProfesionalNoExisteNoConsultaLosHorarios() {
        when(consulta.obtenerProfesional(999L)).thenReturn(Optional.empty());

        assertThat(useCase.obtenerAgenda(999L)).isEmpty();
        verify(consulta, never()).obtenerHorariosHabilitados(anyLong());
    }
}
