package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.redis;

import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.RedisPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisChangesReaderAdapter implements RedisPort {

    private final StringRedisTemplate redisTemplate;

    public RedisChangesReaderAdapter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Long obtenerVersionActual() {
        String value = redisTemplate.opsForValue().get("catedra:sync:current-version");
        return value != null ? Long.parseLong(value) : null;
    }

    @Override
    public Long obtenerVersionMasAntigua() {
        String value = redisTemplate.opsForValue().get("catedra:sync:oldest-available-version");
        return value != null ? Long.parseLong(value) : null;
    }

    @Override
    public String obtenerCambios(Long version) {
        return redisTemplate.opsForValue().get("catedra:sync:changes:" + version);
    }

    @Override
    public String obtenerProfesional(Long id) {
        return (String) redisTemplate.opsForHash().get("catedra:sync:professionals", String.valueOf(id));
    }

    @Override
    public String obtenerCategoria(Long id) {
        return (String) redisTemplate.opsForHash().get("catedra:sync:professional-categories", String.valueOf(id));
    }

    @Override
    public String obtenerHorario(Long id) {
        return (String) redisTemplate.opsForHash().get("catedra:sync:weekly-schedules", String.valueOf(id));
    }
}
