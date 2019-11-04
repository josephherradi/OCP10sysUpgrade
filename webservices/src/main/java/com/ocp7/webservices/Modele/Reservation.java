package com.ocp7.webservices.Modele;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int reservationId;

    @Column(name="dateDemande",updatable = false)
    private Date dateDemande;

    @Column(name="statut")
    private String statut;

    @Column(name="nom_livre")
    private String nomLivre;

    @Column(name="utilisateur")
    private String utilisateur;

    @Column(name="numListeAttente")
    private String numListeAttente;
}
