package ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out;

import ar.edu.um.prog2.camus.catalogo.sync.domain.model.Snapshot;

public interface SnapshotClientPort {
    Snapshot obtenerSnapshot();
}
