package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sync_state")
public class SyncStateEntity extends BaseEntity {

    @Column(name = "current_version", nullable = false)
    private Long currentVersion;

    /** Hay una sola fila de estado, con id 1. */
    public SyncStateEntity() {
        setId(1L);
    }

    public Long getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Long currentVersion) {
        this.currentVersion = currentVersion;
    }
}
