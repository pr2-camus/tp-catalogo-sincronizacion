package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.controller;

import ar.edu.um.prog2.camus.catalogo.catalog.application.service.CatalogoService;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CriteriosBusqueda;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.dto.AgendaResponse;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.dto.CategoriaResponse;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.dto.ProfesionalResponse;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.mapper.CatalogoDtoMapper;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfessionalController {

    private final CatalogoService catalogoService;
    private final CatalogoDtoMapper mapper;

    public ProfessionalController(CatalogoService catalogoService, CatalogoDtoMapper mapper) {
        this.catalogoService = catalogoService;
        this.mapper = mapper;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoriaResponse>> listarCategorias() {
        return ResponseEntity.ok(catalogoService.listarCategorias().stream().map(mapper::toResponse).toList());
    }

    @GetMapping("/professionals")
    public ResponseEntity<List<ProfesionalResponse>> buscarProfesionales(
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Boolean enabled,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate availableOn
    ) {
        CriteriosBusqueda criterios = new CriteriosBusqueda(categoryId, name, enabled, availableOn);
        return ResponseEntity.ok(catalogoService.buscarProfesionales(criterios).stream().map(mapper::toResponse).toList());
    }

    @GetMapping("/professionals/{id}/agenda")
    public ResponseEntity<AgendaResponse> obtenerAgenda(@PathVariable Long id) {
        return catalogoService.obtenerAgenda(id).map(mapper::toResponse).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
