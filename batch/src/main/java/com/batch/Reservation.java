package com.batch;

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

    @Column(name="livre")
    private String livre;

    @Column(name="utilisateur")
    private String utilisateur;

    @Column(name="numListeAttente")
    private int numListeAttente;

    @Transient
    private  Date dateRetourPlusProche;

    @Column(name="notified")
    private Boolean notified;


    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getLivre() {
        return livre;
    }

    public void setLivre(String livre) {
        this.livre = livre;
    }



    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public int getNumListeAttente() {
        return numListeAttente;
    }

    public void setNumListeAttente(int numListeAttente) {
        this.numListeAttente = numListeAttente;
    }

    public Date getDateRetourPlusProche() {
        return dateRetourPlusProche;
    }

    public void setDateRetourPlusProche(Date dateRetourPlusProche) {
        this.dateRetourPlusProche = dateRetourPlusProche;
    }


    public Boolean getNotified() {
        return notified;
    }

    public void setNotified(Boolean notified) {
        this.notified = notified;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", dateDemande=" + dateDemande +
                ", statut='" + statut + '\'' +
                ", livre='" + livre + '\'' +
                ", utilisateur='" + utilisateur + '\'' +
                ", numListeAttente=" + numListeAttente +
                ", dateRetourPlusProche=" + dateRetourPlusProche +
                ", notified=" + notified +
                '}';
    }
}
