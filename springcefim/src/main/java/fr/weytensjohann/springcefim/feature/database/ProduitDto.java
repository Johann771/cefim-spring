package fr.weytensjohann.springcefim.feature.database;

import jakarta.persistence.Tuple;

import java.util.Objects;

public class ProduitDto {
    private Integer id;
    private String nom;
    private String description;

    public ProduitDto(Integer id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public ProduitDto(Tuple tuple) {
        this.id = (Integer) tuple.get(0);
        this.nom = (String) tuple.get(1);
        this.description = (String) tuple.get(2);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitDto that = (ProduitDto) o;
        return Objects.equals(id, that.id) && Objects.equals(nom, that.nom) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, description);
    }
}