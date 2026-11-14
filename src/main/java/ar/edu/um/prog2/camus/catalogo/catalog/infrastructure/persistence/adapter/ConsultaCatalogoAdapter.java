package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.adapter;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.ConsultaCatalogoPort;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.mapper.CatalogoEntityMapper;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaCategoryRepository;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaProfessionalRepository;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaWeeklyScheduleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ConsultaCatalogoAdapter implements ConsultaCatalogoPort {

    private final JpaCategoryRepository categoryRepository;
    private final JpaProfessionalRepository professionalRepository;
    private final JpaWeeklyScheduleRepository weeklyScheduleRepository;
    private final CatalogoEntityMapper mapper;

    public ConsultaCatalogoAdapter(
        JpaCategoryRepository categoryRepository,
        JpaProfessionalRepository professionalRepository,
        JpaWeeklyScheduleRepository weeklyScheduleRepository,
        CatalogoEntityMapper mapper
    ) {
        this.categoryRepository = categoryRepository;
        this.professionalRepository = professionalRepository;
        this.weeklyScheduleRepository = weeklyScheduleRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CategoriaProfesional> obtenerCategorias() {
        return categoryRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Profesional> obtenerProfesionales() {
        return professionalRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Profesional> buscarPorCategoria(Long categoriaId) {
        return professionalRepository.findByCategoryId(categoriaId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Profesional> buscarPorNombre(String nombre) {
        return professionalRepository.findByNombre(nombre).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Profesional> buscarPorHabilitado(Boolean habilitado) {
        return professionalRepository.findByEnabled(habilitado).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Profesional> buscarPorDiaDeSemana(String diaDeSemana) {
        return professionalRepository.findByDayOfWeek(diaDeSemana).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Profesional> obtenerProfesional(Long id) {
        return professionalRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<HorarioSemanal> obtenerHorariosHabilitados(Long profesionalId) {
        return weeklyScheduleRepository.findByProfessionalIdAndEnabled(profesionalId, true).stream().map(mapper::toDomain).toList();
    }
}
