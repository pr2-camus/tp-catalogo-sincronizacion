package ar.edu.um.prog2.camus.catalogo.sync.domain.model;

import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.CategoriaProfesional;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.HorarioSemanal;
import ar.edu.um.prog2.camus.catalogo.catalog.domain.model.Profesional;
import java.util.List;

public class Snapshot {

    private Long snapshotVersion;
    private List<CategoriaProfesional> professionalCategories;
    private List<Profesional> professionals;
    private List<HorarioSemanal> weeklySchedules;

    public Snapshot() {}

    public Long getSnapshotVersion() {
        return snapshotVersion;
    }

    public void setSnapshotVersion(Long v) {
        this.snapshotVersion = v;
    }

    public List<CategoriaProfesional> getProfessionalCategories() {
        return professionalCategories;
    }

    public void setProfessionalCategories(List<CategoriaProfesional> c) {
        this.professionalCategories = c;
    }

    public List<Profesional> getProfessionals() {
        return professionals;
    }

    public void setProfessionals(List<Profesional> p) {
        this.professionals = p;
    }

    public List<HorarioSemanal> getWeeklySchedules() {
        return weeklySchedules;
    }

    public void setWeeklySchedules(List<HorarioSemanal> h) {
        this.weeklySchedules = h;
    }
}
