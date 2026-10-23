package ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in;

public interface SincronizarCatalogoUseCase {
    void sincronizarCompleto();
    void sincronizarIncremental(Long nuevaVersion);
}
