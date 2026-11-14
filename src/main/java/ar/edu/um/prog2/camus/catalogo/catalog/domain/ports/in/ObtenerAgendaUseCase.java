package ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.in;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.AgendaProfesional;
import java.util.Optional;

public interface ObtenerAgendaUseCase {
    Optional<AgendaProfesional> obtenerAgenda(Long profesionalId);
}
