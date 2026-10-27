package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.adapter;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.CategoryEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaCategoryRepository;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaProfessionalRepository;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaWeeklyScheduleRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CatalogoRepositoryAdapter implements CatalogoRepositoryPort {

    private final JpaCategoryRepository categoryRepository;
    private final JpaProfessionalRepository professionalRepository;
    private final JpaWeeklyScheduleRepository weeklyScheduleRepository;

    public CatalogoRepositoryAdapter(
        JpaCategoryRepository categoryRepository,
        JpaProfessionalRepository professionalRepository,
        JpaWeeklyScheduleRepository weeklyScheduleRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.professionalRepository = professionalRepository;
        this.weeklyScheduleRepository = weeklyScheduleRepository;
    }

    @Override
    public void guardarProfesionales(List<Profesional> profesionales) {
        professionalRepository.saveAll(profesionales.stream().map(this::toEntity).toList());
    }

    @Override
    public void guardarCategorias(List<CategoriaProfesional> categorias) {
        categoryRepository.saveAll(categorias.stream().map(this::toEntity).toList());
    }

    @Override
    public void guardarHorarios(List<HorarioSemanal> horarios) {
        weeklyScheduleRepository.saveAll(horarios.stream().map(this::toEntity).toList());
    }

    @Override
    public List<Profesional> obtenerTodosProfesionales() {
        return professionalRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void limpiarYReemplazar(List<CategoriaProfesional> categorias, List<Profesional> profesionales, List<HorarioSemanal> horarios) {
        weeklyScheduleRepository.deleteAll();
        professionalRepository.deleteAll();
        categoryRepository.deleteAll();

        categoryRepository.saveAll(categorias.stream().map(this::toEntity).toList());
        professionalRepository.saveAll(profesionales.stream().map(this::toEntity).toList());
        weeklyScheduleRepository.saveAll(horarios.stream().map(this::toEntity).toList());
    }

    // ── Mappers ───────────────────────────────────────────────────────────

    private CategoryEntity toEntity(CategoriaProfesional c) {
        CategoryEntity e = new CategoryEntity();
        e.setId(c.getId());
        e.setName(c.getName());
        e.setDescription(c.getDescription());
        e.setEnabled(c.getEnabled());
        e.setCreatedAt(c.getCreatedAt());
        e.setUpdatedAt(c.getUpdatedAt());
        return e;
    }

    private ProfessionalEntity toEntity(Profesional p) {
        ProfessionalEntity e = new ProfessionalEntity();
        e.setId(p.getId());
        e.setCategoryId(p.getCategoryId());
        e.setFirstName(p.getFirstName());
        e.setLastName(p.getLastName());
        e.setEnabled(p.getEnabled());
        e.setCreatedAt(p.getCreatedAt());
        e.setUpdatedAt(p.getUpdatedAt());
        return e;
    }

    private WeeklyScheduleEntity toEntity(HorarioSemanal h) {
        WeeklyScheduleEntity e = new WeeklyScheduleEntity();
        e.setId(h.getId());
        e.setProfessionalId(h.getProfessionalId());
        e.setDayOfWeek(h.getDayOfWeek());
        e.setStartTime(h.getStartTime());
        e.setEndTime(h.getEndTime());
        e.setSlotDurationMinutes(h.getSlotDurationMinutes());
        e.setEnabled(h.getEnabled());
        e.setCreatedAt(h.getCreatedAt());
        e.setUpdatedAt(h.getUpdatedAt());
        return e;
    }

    private Profesional toDomain(ProfessionalEntity e) {
        Profesional p = new Profesional();
        p.setId(e.getId());
        p.setCategoryId(e.getCategoryId());
        p.setFirstName(e.getFirstName());
        p.setLastName(e.getLastName());
        p.setEnabled(e.getEnabled());
        p.setCreatedAt(e.getCreatedAt());
        p.setUpdatedAt(e.getUpdatedAt());
        return p;
    }
}
