package ar.edu.um.prog2.camus.catalogo.catalog.domain.model;

import java.time.LocalDate;

public record CriteriosBusqueda(Long categoryId, String name, Boolean enabled, LocalDate availableOn) {}
