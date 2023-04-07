package fr.weytensjohann.springcefim.feature.database;

import jakarta.persistence.Tuple;

import java.util.Objects;

public class ProduitDto {
    private Integer id;
    private String nom;
    private String description;

    // Création d'un constructeur qui prend chaque attribut en paramètre (utile pour nos tests pour créer des Produit déterminés à l'avance)
    public ProduitDto(Integer id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    // Création d'un constructeur qui prend un Tuple en paramètre et qui le transforme pour créer un ProduitDto
    public ProduitDto(Tuple tuple) {
        // Ici, on prend dans l'ordre d'écriture de la requête
        // On a "select product_id, name, description from produit"
        // Donc dans l'ordre :
        // 0 : product_id
        // 1 : name
        // 2 : description
        // Il nous reste ensuite à parcourir notre tuple, comme si c'était une liste avec la méthode get qui prend un index en paramètre (0, 1 ou 2 ici)
        // Et ensuite de le caster dans le bon type car le Tuple nous renvoie par défaut un Object
        this.id = (Integer) tuple.get(0);
        this.nom = (String) tuple.get(1);
        this.description = (String) tuple.get(2);
    }

    // GETTER // SETTER (pour les générer automatiquement, pour IntelliJ, aller dans le Menu > Code > Generate puis Getter/Setter)
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

    // Redéfinition de equals et hashcode pour faciliter les tests
    // equals() : Comparer les valeurs des attributs des objets
    // Peut être auto généré si il s'agit d'une simple comparaison des attributs (voir generation getter/setter)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitDto that = (ProduitDto) o;
        return Objects.equals(id, that.id) && Objects.equals(nom, that.nom) && Objects.equals(description, that.description);
    }
    //hashCode() : Optimise les performances lors de la détection d'unicité dans les collections telles que les Map pour l'unicité des clés, les Set, etc.
    @Override
    public int hashCode() {
        return Objects.hash(id, nom, description);
    }
}