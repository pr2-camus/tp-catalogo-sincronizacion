package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.web.dto;

import java.time.Instant;

public record HorarioSemanalResponse(
    Long id,
    Long professionalId,
    String dayOfWeek,
    String startTime,
    String endTime,
    Integer slotDurationMinutes,
    Boolean enabled,
    Instant createdAt,
    Instant updatedAt
) {}
