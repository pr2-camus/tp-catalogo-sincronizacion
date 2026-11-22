package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.redis;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.CambiosCatalogo;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/** Convierte los JSON que la catedra deja en Redis en objetos de dominio. */
@Component
public class RedisMensajeMapper {

    private final ObjectMapper objectMapper;

    public RedisMensajeMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CambiosCatalogo toCambios(String json) {
        JsonNode changes = objectMapper.readTree(json).get("changes");
        return new CambiosCatalogo(
            ids(changes.get("professionalCategories")),
            ids(changes.get("professionals")),
            ids(changes.get("weeklySchedules"))
        );
    }

    public CategoriaProfesional toCategoria(String json) {
        JsonNode node = objectMapper.readTree(json);
        CategoriaProfesional categoria = new CategoriaProfesional();
        categoria.setId(node.get("id").asLong());
        categoria.setName(node.get("name").asText());
        categoria.setDescription(node.has("description") && !node.get("description").isNull() ? node.get("description").asText() : null);
        categoria.setEnabled(node.get("enabled").asBoolean());
        categoria.setCreatedAt(Instant.parse(node.get("createdAt").asText()));
        categoria.setUpdatedAt(Instant.parse(node.get("updatedAt").asText()));
        return categoria;
    }

    public Profesional toProfesional(String json) {
        JsonNode node = objectMapper.readTree(json);
        Profesional profesional = new Profesional();
        profesional.setId(node.get("id").asLong());
        profesional.setCategoryId(node.get("categoryId").asLong());
        profesional.setFirstName(node.get("firstName").asText());
        profesional.setLastName(node.get("lastName").asText());
        profesional.setEnabled(node.get("enabled").asBoolean());
        profesional.setCreatedAt(Instant.parse(node.get("createdAt").asText()));
        profesional.setUpdatedAt(Instant.parse(node.get("updatedAt").asText()));
        return profesional;
    }

    public HorarioSemanal toHorario(String json) {
        JsonNode node = objectMapper.readTree(json);
        HorarioSemanal horario = new HorarioSemanal();
        horario.setId(node.get("id").asLong());
        horario.setProfessionalId(node.get("professionalId").asLong());
        horario.setDayOfWeek(node.get("dayOfWeek").asText());
        horario.setStartTime(node.get("startTime").asText());
        horario.setEndTime(node.get("endTime").asText());
        horario.setSlotDurationMinutes(node.get("slotDurationMinutes").asInt());
        horario.setEnabled(node.get("enabled").asBoolean());
        horario.setCreatedAt(Instant.parse(node.get("createdAt").asText()));
        horario.setUpdatedAt(Instant.parse(node.get("updatedAt").asText()));
        return horario;
    }

    private static List<Long> ids(JsonNode array) {
        List<Long> ids = new ArrayList<>();
        for (JsonNode id : array) {
            ids.add(id.asLong());
        }
        return ids;
    }
}
