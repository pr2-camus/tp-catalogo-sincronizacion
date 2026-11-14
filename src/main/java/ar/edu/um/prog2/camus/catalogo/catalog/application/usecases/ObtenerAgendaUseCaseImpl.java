package ar.edu.um.prog2.camus.catalogo.catalog.application.usecases;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.AgendaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in.ObtenerAgendaUseCase;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ObtenerAgendaUseCaseImpl implements ObtenerAgendaUseCase {

    private final CatalogoRepositoryPort consulta;

    public ObtenerAgendaUseCaseImpl(CatalogoRepositoryPort consulta) {
        this.consulta = consulta;
    }

    @Override
    public Optional<AgendaProfesional> obtenerAgenda(Long profesionalId) {
        return consulta
            .obtenerProfesional(profesionalId)
            .map(profesional -> new AgendaProfesional(profesional, consulta.obtenerHorariosHabilitados(profesionalId)));
    }
}
