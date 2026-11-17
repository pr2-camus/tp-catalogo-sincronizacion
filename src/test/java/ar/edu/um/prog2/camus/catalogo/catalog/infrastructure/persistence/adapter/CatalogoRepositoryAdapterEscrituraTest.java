package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.mapper.CatalogoEntityMapper;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaCategoryRepository;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaProfessionalRepository;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaWeeklyScheduleRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class CatalogoRepositoryAdapterEscrituraTest {

    private final JpaCategoryRepository categorias = mock(JpaCategoryRepository.class);
    private final JpaProfessionalRepository profesionales = mock(JpaProfessionalRepository.class);
    private final JpaWeeklyScheduleRepository horarios = mock(JpaWeeklyScheduleRepository.class);
    private final CatalogoRepositoryAdapter adapter = new CatalogoRepositoryAdapter(
        categorias,
        profesionales,
        horarios,
        new CatalogoEntityMapper()
    );

    @Test
    void guardaCategorias() {
        CategoriaProfesional c = new CategoriaProfesional();
        c.setId(101L);

        adapter.guardarCategorias(List.of(c));

        verify(categorias).saveAll(any());
    }

    @Test
    void guardaProfesionales() {
        Profesional p = new Profesional();
        p.setId(204L);

        adapter.guardarProfesionales(List.of(p));

        verify(profesionales).saveAll(any());
    }

    @Test
    void guardaHorarios() {
        HorarioSemanal h = new HorarioSemanal();
        h.setId(7L);

        adapter.guardarHorarios(List.of(h));

        verify(horarios).saveAll(any());
    }

    @Test
    void limpiarYReemplazarBorraPrimeroLosHijosYDespuesGuarda() {
        CategoriaProfesional c = new CategoriaProfesional();
        c.setId(101L);
        Profesional p = new Profesional();
        p.setId(204L);
        HorarioSemanal h = new HorarioSemanal();
        h.setId(7L);

        adapter.limpiarYReemplazar(List.of(c), List.of(p), List.of(h));

        InOrder orden = inOrder(horarios, profesionales, categorias);
        orden.verify(horarios).deleteAll();
        orden.verify(profesionales).deleteAll();
        orden.verify(categorias).deleteAll();
        orden.verify(categorias).saveAll(any());
        orden.verify(profesionales).saveAll(any());
        orden.verify(horarios).saveAll(any());
    }
}
