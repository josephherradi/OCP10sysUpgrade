package com.ocp7.clientlecteurs.beans;


import java.util.Date;

public class LivreBean {
    private int id;
    private String nom;
    private String description;
    private int disponibilite;
    private String categorie;
    private Date dateRetourPlusProche;
    private int nbrReservations;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public int getDisponibilite() {
        return disponibilite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisponibilite(int disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Date getDateRetourPlusProche() {
        return dateRetourPlusProche;
    }

    public void setDateRetourPlusProche(Date dateRetourPlusProche) {
        this.dateRetourPlusProche = dateRetourPlusProche;
    }

    public int getNbrReservations() {
        return nbrReservations;
    }

    public void setNbrReservations(int nbrReservations) {
        this.nbrReservations = nbrReservations;
    }

    public LivreBean() {
    }

    @Override
    public String toString() {
        return "LivreBean{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", disponibilite=" + disponibilite +
                ", categorie='" + categorie + '\'' +
                ", dateRetourPlusProche=" + dateRetourPlusProche +
                ", nbrReservations=" + nbrReservations +
                '}';
    }
}
