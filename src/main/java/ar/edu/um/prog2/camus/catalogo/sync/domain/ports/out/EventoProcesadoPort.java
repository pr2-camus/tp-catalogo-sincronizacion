package ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out;

public interface EventoProcesadoPort {
    boolean yaProcesado(String eventId);

    void registrar(String eventId);
}
