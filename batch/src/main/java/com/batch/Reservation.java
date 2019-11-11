package com.batch;

import java.util.Date;

public class Reservation {
    private int id;
    private String nomLivre;
    private Date  dateRetour;
    private String utilisateur;
    private int numListeAttente;
    private String statut;
    private String mail;

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomLivre() {
        return nomLivre;
    }

    public void setNomLivre(String nomLivre) {
        this.nomLivre = nomLivre;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
