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

    @ManyToOne
    @JoinColumn(name="livre_id")
    private Livre livre;

    @Column(name="utilisateur")
    private String utilisateur;

    @Column(name="numListeAttente")
    private String numListeAttente;
}
