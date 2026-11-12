package ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import java.util.List;

public interface CatalogoRepositoryPort {
    void guardarProfesionales(List<Profesional> profesionales);
    void guardarCategorias(List<CategoriaProfesional> categorias);
    void guardarHorarios(List<HorarioSemanal> horarios);
    List<Profesional> obtenerTodosProfesionales();
    void limpiarYReemplazar(List<CategoriaProfesional> categorias, List<Profesional> profesionales, List<HorarioSemanal> horarios);
}
