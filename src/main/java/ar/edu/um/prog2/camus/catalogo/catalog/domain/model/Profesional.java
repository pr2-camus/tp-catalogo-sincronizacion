package ar.edu.um.prog2.camus.catalogo.catalog.domain.model;

public class Profesional extends ElementoCatalogo {

    private Long categoryId;
    private String firstName;
    private String lastName;

    public Profesional() {}

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
