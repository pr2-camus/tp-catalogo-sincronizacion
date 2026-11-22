package ar.edu.um.prog2.camus.catalogo.catalog.domain.model;

public class CategoriaProfesional extends ElementoCatalogo {

    private String name;
    private String description;

    public CategoriaProfesional() {}

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
