package ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import ar.edu.um.prog2.camus.catalogo.shared.infrastructure.config.CatedraProperties;
import ar.edu.um.prog2.camus.catalogo.sync.domain.model.Snapshot;
import ar.edu.um.prog2.camus.catalogo.sync.infrastructure.client.mapper.SnapshotDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class SnapshotRestAdapterTest {

    private static final String SNAPSHOT = """
    {"snapshotVersion":206,"campoQueNoConocemos":"x",
     "professionalCategories":[{"id":101,"name":"Cardiologia","description":null,"enabled":true,
       "createdAt":"2026-01-01T10:00:00Z","updatedAt":"2026-02-01T10:00:00Z"}],
     "professionals":[{"id":204,"categoryId":101,"firstName":"Ana","lastName":"Lopez","enabled":true,
       "createdAt":"2026-01-01T10:00:00Z","updatedAt":"2026-02-01T10:00:00Z"}],
     "weeklySchedules":[{"id":7,"professionalId":204,"dayOfWeek":"MONDAY","startTime":"09:00","endTime":"12:00",
       "slotDurationMinutes":30,"enabled":true,"createdAt":"2026-01-01T10:00:00Z","updatedAt":"2026-02-01T10:00:00Z"}]}
    """;

    private final CatedraProperties propiedades = mock(CatedraProperties.class);
    private final RestClient.Builder builder = RestClient.builder();
    private final MockRestServiceServer servidor = MockRestServiceServer.bindTo(builder).build();
    private SnapshotRestAdapter adapter;

    @BeforeEach
    void setUp() {
        when(propiedades.getApiUrl()).thenReturn("http://catedra.test");
        when(propiedades.getJwt()).thenReturn("token-de-prueba");
        adapter = new SnapshotRestAdapter(builder, propiedades, new SnapshotDtoMapper());
    }

    @Test
    void pideElSnapshotConElTokenYLoConvierteAModeloDeDominio() {
        servidor
            .expect(requestTo("http://catedra.test/api/synchronization/snapshot"))
            .andExpect(method(HttpMethod.GET))
            .andExpect(header("Authorization", "Bearer token-de-prueba"))
            .andRespond(withSuccess(SNAPSHOT, MediaType.APPLICATION_JSON));

        Snapshot snapshot = adapter.obtenerSnapshot();

        servidor.verify();
        assertThat(snapshot.getSnapshotVersion()).isEqualTo(206L);
        assertThat(snapshot.getProfessionalCategories()).hasSize(1);
        assertThat(snapshot.getProfessionals()).extracting("firstName").containsExactly("Ana");
        assertThat(snapshot.getWeeklySchedules()).extracting("dayOfWeek").containsExactly("MONDAY");
    }
}
