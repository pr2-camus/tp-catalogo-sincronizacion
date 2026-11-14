package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.web.mapper;

import ar.edu.um.prog2.camus.catalogo.sync.domain.model.EstadoSincronizacion;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.web.dto.EstadoSincronizacionResponse;
import org.springframework.stereotype.Component;

@Component
public class EstadoSincronizacionDtoMapper {

    public EstadoSincronizacionResponse toResponse(EstadoSincronizacion estado) {
        return new EstadoSincronizacionResponse(estado.versionLocal(), estado.ultimaActualizacion());
    }
}
