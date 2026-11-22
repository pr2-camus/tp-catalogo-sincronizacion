package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.redis;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.CambiosCatalogo;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.RedisPort;
import java.util.Optional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisChangesReaderAdapter implements RedisPort {

    private static final String VERSION_ACTUAL = "catedra:sync:current-version";
    private static final String VERSION_MAS_ANTIGUA = "catedra:sync:oldest-available-version";
    private static final String CAMBIOS = "catedra:sync:changes:";
    private static final String PROFESIONALES = "catedra:sync:professionals";
    private static final String CATEGORIAS = "catedra:sync:professional-categories";
    private static final String HORARIOS = "catedra:sync:weekly-schedules";

    private final StringRedisTemplate redisTemplate;
    private final RedisMensajeMapper mapper;

    public RedisChangesReaderAdapter(StringRedisTemplate redisTemplate, RedisMensajeMapper mapper) {
        this.redisTemplate = redisTemplate;
        this.mapper = mapper;
    }

    @Override
    public Long obtenerVersionActual() {
        return leerVersion(VERSION_ACTUAL);
    }

    @Override
    public Long obtenerVersionMasAntigua() {
        return leerVersion(VERSION_MAS_ANTIGUA);
    }

    @Override
    public Optional<CambiosCatalogo> obtenerCambios(Long version) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(CAMBIOS + version)).map(mapper::toCambios);
    }

    @Override
    public Optional<Profesional> obtenerProfesional(Long id) {
        return leerHash(PROFESIONALES, id).map(mapper::toProfesional);
    }

    @Override
    public Optional<CategoriaProfesional> obtenerCategoria(Long id) {
        return leerHash(CATEGORIAS, id).map(mapper::toCategoria);
    }

    @Override
    public Optional<HorarioSemanal> obtenerHorario(Long id) {
        return leerHash(HORARIOS, id).map(mapper::toHorario);
    }

    private Long leerVersion(String clave) {
        String value = redisTemplate.opsForValue().get(clave);
        return value != null ? Long.parseLong(value) : null;
    }

    private Optional<String> leerHash(String clave, Long id) {
        return Optional.ofNullable((String) redisTemplate.opsForHash().get(clave, String.valueOf(id)));
    }
}
