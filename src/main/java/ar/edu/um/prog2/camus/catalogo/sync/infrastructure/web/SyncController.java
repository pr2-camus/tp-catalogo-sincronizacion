package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.web;

import ar.edu.um.prog2.camus.catalogo.sync.application.service.SyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync")
public class SyncController {

    private final SyncService syncService;

    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }

    @PostMapping("/full")
    public ResponseEntity<Void> forzarSincronizacion() {
        syncService.forzarSincronizacionCompleta();
        return ResponseEntity.ok().build();
    }
}
