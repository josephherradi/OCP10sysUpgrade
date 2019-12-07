package com.batch;

public class ReservationBean {
    Integer reservation_Id;
    String nom;
    String utilisateur;
    Integer num_Liste_Attente;
    String mail;
    String statut;

    public Integer getReservation_Id() {
        return reservation_Id;
    }

    public void setReservation_Id(Integer reservation_Id) {
        this.reservation_Id = reservation_Id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getNum_Liste_Attente() {
        return num_Liste_Attente;
    }

    public void setNum_Liste_Attente(Integer num_Liste_Attente) {
        this.num_Liste_Attente = num_Liste_Attente;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
