package ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out;

public interface RedisPort {
    Long obtenerVersionActual();
    Long obtenerVersionMasAntigua();
    String obtenerCambios(Long version);
    String obtenerProfesional(Long id);
    String obtenerCategoria(Long id);
    String obtenerHorario(Long id);
}
