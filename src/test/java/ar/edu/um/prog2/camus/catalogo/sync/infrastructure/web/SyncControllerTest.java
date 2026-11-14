package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.web;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ar.edu.um.prog2.camus.catalogo.sync.application.service.SyncService;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.EstadoSincronizacion;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.web.mapper.EstadoSincronizacionDtoMapper;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class SyncControllerTest {

    private final SyncService service = mock(SyncService.class);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(
        new SyncController(service, new EstadoSincronizacionDtoMapper())
    ).build();

    @Test
    void elEstadoMuestraLaVersionLocal() throws Exception {
        when(service.obtenerEstado()).thenReturn(Optional.of(new EstadoSincronizacion(206L, Instant.parse("2026-11-12T21:20:41Z"))));

        mockMvc.perform(get("/api/sync/status")).andExpect(status().isOk()).andExpect(jsonPath("$.versionLocal").value(206));
    }

    @Test
    void sinDatosElEstadoVieneVacio() throws Exception {
        when(service.obtenerEstado()).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/sync/status")).andExpect(status().isOk()).andExpect(jsonPath("$.versionLocal").value(nullValue()));
    }

    @Test
    void forzarLaSincronizacionLlamaAlServicio() throws Exception {
        mockMvc.perform(post("/api/sync/full")).andExpect(status().isOk());

        verify(service).forzarSincronizacionCompleta();
    }
}
