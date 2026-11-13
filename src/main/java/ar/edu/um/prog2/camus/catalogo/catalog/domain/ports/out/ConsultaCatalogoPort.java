package ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import java.util.List;
import java.util.Optional;

public interface ConsultaCatalogoPort {
    List<CategoriaProfesional> obtenerCategorias();
    List<Profesional> obtenerProfesionales();
    List<Profesional> buscarPorCategoria(Long categoriaId);
    List<Profesional> buscarPorNombre(String nombre);
    List<Profesional> buscarPorHabilitado(Boolean habilitado);
    List<Profesional> buscarPorDiaDeSemana(String diaDeSemana);
    Optional<Profesional> obtenerProfesional(Long id);
    List<HorarioSemanal> obtenerHorariosHabilitados(Long profesionalId);
}
