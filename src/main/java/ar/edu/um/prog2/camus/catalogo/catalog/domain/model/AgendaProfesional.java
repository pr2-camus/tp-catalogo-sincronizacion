package ar.edu.um.prog2.camus.catalogo.catalog.domain.model;

import java.util.List;

public record AgendaProfesional(Profesional profesional, List<HorarioSemanal> horarios) {}
