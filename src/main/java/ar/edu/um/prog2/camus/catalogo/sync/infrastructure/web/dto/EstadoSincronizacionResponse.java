package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.web.dto;

import java.time.Instant;

public record EstadoSincronizacionResponse(Long versionLocal, Instant ultimaActualizacion) {}
