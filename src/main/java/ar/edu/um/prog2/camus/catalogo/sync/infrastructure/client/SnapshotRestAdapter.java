package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client;

import ar.edu.um.prog2.camus.catalogo.shared.infrastructure.config.CatedraProperties;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.Snapshot;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.SnapshotClientPort;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.dto.SnapshotResponse;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.mapper.SnapshotDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SnapshotRestAdapter implements SnapshotClientPort {

    private static final String SNAPSHOT_URI = "/api/synchronization/snapshot";

    private final RestClient restClient;
    private final SnapshotDtoMapper mapper;

    @Autowired
    public SnapshotRestAdapter(CatedraProperties catedraProperties, SnapshotDtoMapper mapper) {
        this(RestClient.builder(), catedraProperties, mapper);
    }

    SnapshotRestAdapter(RestClient.Builder builder, CatedraProperties catedraProperties, SnapshotDtoMapper mapper) {
        this.restClient = builder
            .baseUrl(catedraProperties.getApiUrl())
            .defaultHeader("Authorization", "Bearer " + catedraProperties.getJwt())
            .build();
        this.mapper = mapper;
    }

    @Override
    public Snapshot obtenerSnapshot() {
        SnapshotResponse response = restClient.get().uri(SNAPSHOT_URI).retrieve().body(SnapshotResponse.class);
        return mapper.toDomain(response);
    }
}
