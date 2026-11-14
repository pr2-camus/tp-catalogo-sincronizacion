package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.dto;

import java.time.Instant;

public record CategoriaResponse(Long id, String name, String description, Boolean enabled, Instant createdAt, Instant updatedAt) {}
