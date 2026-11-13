package ar.edu.um.prog2.camus.catalogo.sync.domain.model;

import java.time.Instant;

public record EstadoSincronizacion(Long versionLocal, Instant ultimaActualizacion) {}
