package com.ocp7.clientlecteurs.beans;

import java.util.Date;

public class ReservationBean {
    private int reservationId;
    private Date dateDemande;
    private String statut;
    private String livre;
    private String utilisateur;
    private String numListeAttente;
    private Date dateRetourPlusProche;
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

    public String getNumListeAttente() {
        return numListeAttente;
    }

    public void setNumListeAttente(String numListeAttente) {
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
        return "ReservationBean{" +
                "reservationId=" + reservationId +
                ", dateDemande=" + dateDemande +
                ", statut='" + statut + '\'' +
                ", livre='" + livre + '\'' +
                ", utilisateur='" + utilisateur + '\'' +
                ", numListeAttente='" + numListeAttente + '\'' +
                ", dateRetourPlusProche=" + dateRetourPlusProche +
                ", notified=" + notified +
                '}';
    }
}
