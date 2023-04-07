package fr.weytensjohann.springcefim.feature.database;

import jakarta.persistence.*;

@Entity
@Table(name = "avis")
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int avisId;

    @Column(name = "note")
    private int avisNote;
    @Column(name = "avistext")
    private String avisText;
    @Column(name = "fk_produit")
    private int avisProduit;
    @Column(name = "fk_client")
    private int avisClient;
}
