package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "sync_state")
public class SyncStateEntity {

    @Id
    private Long id = 1L;

    @Column(name = "current_version", nullable = false)
    private Long currentVersion;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public SyncStateEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Long currentVersion) {
        this.currentVersion = currentVersion;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
