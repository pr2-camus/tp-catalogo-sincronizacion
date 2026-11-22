package ar.edu.um.prog2.camus.catalogo.catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class CategoryEntity extends CatalogEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    public CategoryEntity() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
