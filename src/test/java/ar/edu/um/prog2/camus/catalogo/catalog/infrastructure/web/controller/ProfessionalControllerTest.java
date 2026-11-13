package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ar.edu.um.prog2.camus.catalogo.catalog.application.service.CatalogoService;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.AgendaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CriteriosBusqueda;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.mapper.CatalogoDtoMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ProfessionalControllerTest {

    private final CatalogoService service = mock(CatalogoService.class);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ProfessionalController(service, new CatalogoDtoMapper())).build();

    private static Profesional ana() {
        Profesional p = new Profesional();
        p.setId(204L);
        p.setCategoryId(101L);
        p.setFirstName("Ana");
        p.setLastName("Lopez");
        p.setEnabled(true);
        return p;
    }

    @Test
    void laAgendaMantieneLaFormaQueLeeTurnos() throws Exception {
        HorarioSemanal h = new HorarioSemanal();
        h.setId(1L);
        h.setProfessionalId(204L);
        h.setDayOfWeek("MONDAY");
        h.setStartTime("09:00");
        h.setEndTime("12:00");
        h.setSlotDurationMinutes(30);
        h.setEnabled(true);
        when(service.obtenerAgenda(204L)).thenReturn(Optional.of(new AgendaProfesional(ana(), List.of(h))));

        mockMvc
            .perform(get("/api/professionals/204/agenda"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.professional.id").value(204))
            .andExpect(jsonPath("$.professional.firstName").value("Ana"))
            .andExpect(jsonPath("$.weeklySchedules[0].dayOfWeek").value("MONDAY"))
            .andExpect(jsonPath("$.weeklySchedules[0].slotDurationMinutes").value(30));
    }

    @Test
    void unProfesionalInexistenteDevuelve404() throws Exception {
        when(service.obtenerAgenda(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/professionals/999/agenda")).andExpect(status().isNotFound());
    }

    @Test
    void labusquedaPasaLosFiltrosAlServicio() throws Exception {
        when(service.buscarProfesionales(new CriteriosBusqueda(null, null, false, null))).thenReturn(List.of(ana()));

        mockMvc
            .perform(get("/api/professionals").param("enabled", "false"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(204));
        verify(service).buscarProfesionales(new CriteriosBusqueda(null, null, false, null));
    }

    @Test
    void listaLasCategorias() throws Exception {
        CategoriaProfesional c = new CategoriaProfesional();
        c.setId(101L);
        c.setName("Cardiologia");
        when(service.listarCategorias()).thenReturn(List.of(c));

        mockMvc.perform(get("/api/categories")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Cardiologia"));
    }
}
