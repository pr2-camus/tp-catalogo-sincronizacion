package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web;

import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.CategoryEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaCategoryRepository;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaProfessionalRepository;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.repository.JpaWeeklyScheduleRepository;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.persistence.repository.JpaSyncStateRepositorySync;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfessionalController {

    private final JpaProfessionalRepository professionalRepository;
    private final JpaCategoryRepository categoryRepository;
    private final JpaWeeklyScheduleRepository weeklyScheduleRepository;
    private final JpaSyncStateRepositorySync syncStateRepository;

    public ProfessionalController(
        JpaProfessionalRepository professionalRepository,
        JpaCategoryRepository categoryRepository,
        JpaWeeklyScheduleRepository weeklyScheduleRepository,
        JpaSyncStateRepositorySync syncStateRepository
    ) {
        this.professionalRepository = professionalRepository;
        this.categoryRepository = categoryRepository;
        this.weeklyScheduleRepository = weeklyScheduleRepository;
        this.syncStateRepository = syncStateRepository;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryEntity>> listarCategorias() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/professionals")
    public ResponseEntity<List<ProfessionalEntity>> buscarProfesionales(
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Boolean enabled,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate availableOn
    ) {
        List<ProfessionalEntity> resultado;

        if (categoryId != null) {
            resultado = professionalRepository.findByCategoryId(categoryId);
        } else if (name != null) {
            resultado = professionalRepository.findByNombre(name);
        } else if (enabled != null) {
            resultado = professionalRepository.findByEnabled(enabled);
        } else if (availableOn != null) {
            String dayOfWeek = availableOn.getDayOfWeek().name();
            resultado = professionalRepository.findByDayOfWeek(dayOfWeek);
        } else {
            resultado = professionalRepository.findAll();
        }

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/professionals/{id}/agenda")
    public ResponseEntity<Map<String, Object>> obtenerAgenda(@PathVariable Long id) {
        return professionalRepository
            .findById(id)
            .map(prof -> {
                List<WeeklyScheduleEntity> horarios = weeklyScheduleRepository
                    .findAll()
                    .stream()
                    .filter(h -> h.getProfessionalId().equals(id) && h.getEnabled())
                    .toList();
                return ResponseEntity.ok(Map.of("professional", prof, "weeklySchedules", horarios));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sync/status")
    public ResponseEntity<Map<String, Object>> syncStatus() {
        return syncStateRepository
            .findById(1L)
            .map(state ->
                ResponseEntity.ok(
                    Map.<String, Object>of("versionLocal", state.getCurrentVersion(), "ultimaActualizacion", state.getUpdatedAt())
                )
            )
            .orElse(ResponseEntity.ok(Map.of("versionLocal", "sin datos")));
    }
}
