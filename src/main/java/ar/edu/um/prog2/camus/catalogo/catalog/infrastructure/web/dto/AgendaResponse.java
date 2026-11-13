package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.dto;

import java.util.List;

public record AgendaResponse(ProfesionalResponse professional, List<HorarioSemanalResponse> weeklySchedules) {}
