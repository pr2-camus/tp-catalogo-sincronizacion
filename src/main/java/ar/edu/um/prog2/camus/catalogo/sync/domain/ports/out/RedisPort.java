package ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.CambiosCatalogo;
import java.util.Optional;

public interface RedisPort {
    Long obtenerVersionActual();

    Long obtenerVersionMasAntigua();

    Optional<CambiosCatalogo> obtenerCambios(Long version);

    Optional<Profesional> obtenerProfesional(Long id);

    Optional<CategoriaProfesional> obtenerCategoria(Long id);

    Optional<HorarioSemanal> obtenerHorario(Long id);
}
