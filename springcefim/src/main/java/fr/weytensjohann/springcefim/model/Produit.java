package fr.weytensjohann.springcefim.model;

import jakarta.persistence.*;

// 2 annotations par entité :
// @Entity : Permet de faire le lien entre classe et BDD
// @Table : Permet de préciser le nom de la table à mapper
@Entity
@Table(name = "produits")
public class Produit {

    // Définit l'ID de notre BDD (clé primaire)
    // @Id : Permet de définir la clé primaire
    // @GeneratedValue : Permet de préciser la génération de la clé primaire
    // Strategy IDENTITY : Permet de préciser que la génération se fait à partir d'une colonne spécifique en BDD
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int productId;

    // @Column : Permet de mapper l'attribut à son champ en BDD
    @Column(name = "nom")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "prix_unitaire_HT")
    private double unitPrice;

    public int getProductId() {
        return productId;
    }

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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
