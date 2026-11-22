package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.redis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import tools.jackson.databind.json.JsonMapper;

@SuppressWarnings("unchecked")
class RedisChangesReaderAdapterTest {

    private final StringRedisTemplate template = mock(StringRedisTemplate.class);
    private final ValueOperations<String, String> valores = mock(ValueOperations.class);
    private final HashOperations<String, Object, Object> hashes = mock(HashOperations.class);
    private final RedisChangesReaderAdapter adapter = new RedisChangesReaderAdapter(
        template,
        new RedisMensajeMapper(JsonMapper.builder().build())
    );

    @BeforeEach
    void setUp() {
        when(template.opsForValue()).thenReturn(valores);
        when(template.<Object, Object>opsForHash()).thenReturn(hashes);
    }

    @Test
    void leeLasVersiones() {
        when(valores.get("catedra:sync:current-version")).thenReturn("206");
        when(valores.get("catedra:sync:oldest-available-version")).thenReturn("200");

        assertThat(adapter.obtenerVersionActual()).isEqualTo(206L);
        assertThat(adapter.obtenerVersionMasAntigua()).isEqualTo(200L);
    }

    @Test
    void sinVersionesDevuelveNull() {
        assertThat(adapter.obtenerVersionActual()).isNull();
        assertThat(adapter.obtenerVersionMasAntigua()).isNull();
    }

    @Test
    void leeLosCambiosDeUnaVersion() {
        when(valores.get("catedra:sync:changes:6")).thenReturn(
            "{\"changes\":{\"professionalCategories\":[101],\"professionals\":[],\"weeklySchedules\":[]}}"
        );

        assertThat(adapter.obtenerCambios(6L)).hasValueSatisfying(c -> assertThat(c.categorias()).containsExactly(101L));
    }

    @Test
    void sinCambiosEnRedisDevuelveVacio() {
        assertThat(adapter.obtenerCambios(99L)).isEmpty();
    }

    @Test
    void leeUnProfesionalUnaCategoriaYUnHorario() {
        String fechas = "\"createdAt\":\"2026-01-01T10:00:00Z\",\"updatedAt\":\"2026-02-01T10:00:00Z\"";
        when(hashes.get("catedra:sync:professionals", "204")).thenReturn(
            "{\"id\":204,\"categoryId\":101,\"firstName\":\"Ana\",\"lastName\":\"Lopez\",\"enabled\":true," + fechas + "}"
        );
        when(hashes.get("catedra:sync:professional-categories", "101")).thenReturn(
            "{\"id\":101,\"name\":\"Cardiologia\",\"description\":null,\"enabled\":true," + fechas + "}"
        );
        when(hashes.get("catedra:sync:weekly-schedules", "7")).thenReturn(
            "{\"id\":7,\"professionalId\":204,\"dayOfWeek\":\"MONDAY\",\"startTime\":\"09:00\",\"endTime\":\"12:00\",\"slotDurationMinutes\":30,\"enabled\":true," +
                fechas +
                "}"
        );

        assertThat(adapter.obtenerProfesional(204L)).hasValueSatisfying(p -> assertThat(p.getFirstName()).isEqualTo("Ana"));
        assertThat(adapter.obtenerCategoria(101L)).hasValueSatisfying(c -> assertThat(c.getName()).isEqualTo("Cardiologia"));
        assertThat(adapter.obtenerHorario(7L)).hasValueSatisfying(h -> assertThat(h.getDayOfWeek()).isEqualTo("MONDAY"));
    }

    @Test
    void siElElementoNoEstaEnRedisDevuelveVacio() {
        assertThat(adapter.obtenerProfesional(1L)).isEmpty();
        assertThat(adapter.obtenerCategoria(1L)).isEmpty();
        assertThat(adapter.obtenerHorario(1L)).isEmpty();
    }
}
