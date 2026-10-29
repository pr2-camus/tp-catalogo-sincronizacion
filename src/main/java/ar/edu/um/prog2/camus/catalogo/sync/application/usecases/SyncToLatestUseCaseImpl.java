package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.ports.out.CatalogoRepositoryPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.RedisPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.SnapshotClientPort;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SyncToLatestUseCaseImpl {

    private static final Logger log = LoggerFactory.getLogger(SyncToLatestUseCaseImpl.class);

    private final RedisPort redisPort;
    private final VersionRepositoryPort versionRepository;
    private final CatalogoRepositoryPort catalogoRepository;
    private final SincronizarCatalogoUseCase fullSyncUseCase;
    private final ObjectMapper objectMapper;

    public SyncToLatestUseCaseImpl(
        RedisPort redisPort,
        VersionRepositoryPort versionRepository,
        CatalogoRepositoryPort catalogoRepository,
        SincronizarCatalogoUseCase fullSyncUseCase,
        ObjectMapper objectMapper
    ) {
        this.redisPort = redisPort;
        this.versionRepository = versionRepository;
        this.catalogoRepository = catalogoRepository;
        this.fullSyncUseCase = fullSyncUseCase;
        this.objectMapper = objectMapper;
    }

    public void sincronizarAlDia() {
        Long versionLocal = versionRepository.obtenerVersionLocal();
        Long versionActual = redisPort.obtenerVersionActual();
        Long versionMasAntigua = redisPort.obtenerVersionMasAntigua();

        if (versionActual == null) {
            log.warn("No se pudo obtener la version actual de Redis.");
            return;
        }

        // Ya estamos al dia
        if (versionLocal != null && versionLocal >= versionActual) {
            log.info("Catalogo al dia. Version local: {}", versionLocal);
            return;
        }

        // Base vacia o hay hueco — sincronizacion completa
        if (versionLocal == null || versionMasAntigua == null || versionLocal < versionMasAntigua - 1) {
            log.info(
                "Hueco detectado o base vacia. Version local: {}, oldest: {}. Ejecutando snapshot completo.",
                versionLocal,
                versionMasAntigua
            );
            fullSyncUseCase.sincronizarCompleto();
            return;
        }

        // Sincronizacion incremental
        for (long v = versionLocal + 1; v <= versionActual; v++) {
            try {
                aplicarVersion(v);
            } catch (Exception e) {
                log.error("Error aplicando version {}. Ejecutando snapshot completo.", v, e);
                fullSyncUseCase.sincronizarCompleto();
                return;
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void aplicarVersion(long version) throws Exception {
        String changesJson = redisPort.obtenerCambios(version);

        if (changesJson == null) {
            throw new IllegalStateException("No se encontro changes:" + version + " en Redis.");
        }

        JsonNode root = objectMapper.readTree(changesJson);
        JsonNode changes = root.get("changes");

        // Aplicar categorias afectadas
        for (JsonNode idNode : changes.get("professionalCategories")) {
            Long id = idNode.asLong();
            String json = redisPort.obtenerCategoria(id);
            if (json != null) {
                CategoriaProfesional cat = parsearCategoria(json);
                catalogoRepository.guardarCategorias(List.of(cat));
            }
        }

        // Aplicar profesionales afectados
        for (JsonNode idNode : changes.get("professionals")) {
            Long id = idNode.asLong();
            String json = redisPort.obtenerProfesional(id);
            if (json != null) {
                Profesional prof = parsearProfesional(json);
                catalogoRepository.guardarProfesionales(List.of(prof));
            }
        }

        // Aplicar horarios afectados
        for (JsonNode idNode : changes.get("weeklySchedules")) {
            Long id = idNode.asLong();
            String json = redisPort.obtenerHorario(id);
            if (json != null) {
                HorarioSemanal horario = parsearHorario(json);
                catalogoRepository.guardarHorarios(List.of(horario));
            }
        }

        versionRepository.guardarVersion(version);
        log.info("Version {} aplicada correctamente.", version);
    }

    private CategoriaProfesional parsearCategoria(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);
        CategoriaProfesional cat = new CategoriaProfesional();
        cat.setId(node.get("id").asLong());
        cat.setName(node.get("name").asText());
        cat.setDescription(node.has("description") && !node.get("description").isNull() ? node.get("description").asText() : null);
        cat.setEnabled(node.get("enabled").asBoolean());
        cat.setCreatedAt(Instant.parse(node.get("createdAt").asText()));
        cat.setUpdatedAt(Instant.parse(node.get("updatedAt").asText()));
        return cat;
    }

    private Profesional parsearProfesional(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);
        Profesional prof = new Profesional();
        prof.setId(node.get("id").asLong());
        prof.setCategoryId(node.get("categoryId").asLong());
        prof.setFirstName(node.get("firstName").asText());
        prof.setLastName(node.get("lastName").asText());
        prof.setEnabled(node.get("enabled").asBoolean());
        prof.setCreatedAt(Instant.parse(node.get("createdAt").asText()));
        prof.setUpdatedAt(Instant.parse(node.get("updatedAt").asText()));
        return prof;
    }

    private HorarioSemanal parsearHorario(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);
        HorarioSemanal h = new HorarioSemanal();
        h.setId(node.get("id").asLong());
        h.setProfessionalId(node.get("professionalId").asLong());
        h.setDayOfWeek(node.get("dayOfWeek").asText());
        h.setStartTime(node.get("startTime").asText());
        h.setEndTime(node.get("endTime").asText());
        h.setSlotDurationMinutes(node.get("slotDurationMinutes").asInt());
        h.setEnabled(node.get("enabled").asBoolean());
        h.setCreatedAt(Instant.parse(node.get("createdAt").asText()));
        h.setUpdatedAt(Instant.parse(node.get("updatedAt").asText()));
        return h;
    }
}
