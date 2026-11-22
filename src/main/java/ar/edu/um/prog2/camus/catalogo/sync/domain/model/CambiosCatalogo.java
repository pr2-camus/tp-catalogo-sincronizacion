package ar.edu.um.prog2.camus.catalogo.sync.domain.model;

import java.util.List;

/** Ids de los elementos del catálogo que cambiaron en una versión de la cátedra. */
public record CambiosCatalogo(List<Long> categorias, List<Long> profesionales, List<Long> horarios) {}
