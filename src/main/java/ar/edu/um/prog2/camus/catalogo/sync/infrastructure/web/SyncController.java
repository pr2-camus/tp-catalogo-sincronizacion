package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.web;

import ar.edu.um.prog2.camus.catalogo.sync.application.service.SyncService;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.web.dto.EstadoSincronizacionResponse;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.web.mapper.EstadoSincronizacionDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync")
public class SyncController {

    private final SyncService syncService;
    private final EstadoSincronizacionDtoMapper mapper;

    public SyncController(SyncService syncService, EstadoSincronizacionDtoMapper mapper) {
        this.syncService = syncService;
        this.mapper = mapper;
    }

    @PostMapping("/full")
    public ResponseEntity<Void> forzarSincronizacion() {
        syncService.forzarSincronizacionCompleta();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<EstadoSincronizacionResponse> estado() {
        return ResponseEntity.ok(
            syncService
                .obtenerEstado()
                .map(mapper::toResponse)
                .orElseGet(() -> new EstadoSincronizacionResponse(null, null))
        );
    }
}
