package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.dto;

import java.time.Instant;

public record ProfesionalResponse(
    Long id,
    Long categoryId,
    String firstName,
    String lastName,
    Boolean enabled,
    Instant createdAt,
    Instant updatedAt
) {}
