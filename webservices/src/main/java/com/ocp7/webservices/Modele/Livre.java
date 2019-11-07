package com.ocp7.webservices.Modele;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="livre")
public class Livre {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="livre_id")
    private int livreId;

    @Column(name="nom")
    private String nom;

    @Column(name="description")
    private String description;

    @Column(name="categorie")
    private String categorie;


    @Column(name="disponibilite")
    private int disponibilite;

    @Column(name="quantite")
    private int quantite;

    @Transient
    private Date dateRetourPlusProche;

    @Transient
    private int nbrReservations;

    public int getId() {
        return livreId;
    }

    public void setId(int id) {
        this.livreId = id;
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

    public int getDisponibilite() {
        return disponibilite;
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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Date getDateRetourPlusProche() {
        return dateRetourPlusProche;
    }

    public int getNbrReservations() {
        return nbrReservations;
    }

    public void setNbrReservations(int nbrReservations) {
        this.nbrReservations = nbrReservations;
    }

    public void setDateRetourPlusProche(Date dateRetourPlusProche) {
        this.dateRetourPlusProche = dateRetourPlusProche;
    }

    public Livre() {;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "livreId=" + livreId +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", categorie='" + categorie + '\'' +
                ", disponibilite=" + disponibilite +
                ", quantite=" + quantite +
                ", dateRetourPlusProche=" + dateRetourPlusProche +
                ", nbrReservations=" + nbrReservations +
                '}';
    }
}
