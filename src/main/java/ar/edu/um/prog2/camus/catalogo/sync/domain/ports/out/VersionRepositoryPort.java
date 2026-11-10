package ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out;

public interface VersionRepositoryPort {
    Long obtenerVersionLocal();
    void guardarVersion(Long version);
}
