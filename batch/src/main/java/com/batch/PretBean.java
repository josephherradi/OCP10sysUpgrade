package com.batch;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="pret")
public class PretBean {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private Integer idLivre;
    private String nomLivre;

    @Column(updatable=false)
    private Date datePret;
    private Date dateRetour;
    private Boolean pretProlonge;
    private String utilisateur;
    private Boolean tagForUpdate;
    private Boolean rendu;


    public PretBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(Integer idLivre) {
        this.idLivre = idLivre;
    }

    public Date getDatePret() {
        return datePret;
    }

    public void setDatePret(Date datePret) {
        this.datePret = datePret;
    }

    public Boolean getPretProlonge() {
        return pretProlonge;
    }

    public void setPretProlonge(Boolean pretProlonge) {
        this.pretProlonge = pretProlonge;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getNomLivre() {
        return nomLivre;
    }

    public void setNomLivre(String nomLivre) {
        this.nomLivre = nomLivre;
    }


    public Boolean getTagForUpdate() {
        return tagForUpdate;
    }

    public void setTagForUpdate(Boolean tagForUpdate) {
        this.tagForUpdate = tagForUpdate;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Boolean getRendu() {
        return rendu;
    }

    public void setRendu(Boolean rendu) {
        this.rendu = rendu;
    }

    @Override
    public String toString() {
        return "Pret{" +
                "id=" + id +
                ", idLivre=" + idLivre +
                ", nomLivre='" + nomLivre + '\'' +
                ", datePret=" + datePret +
                ", dateRetour=" + dateRetour +
                ", pretProlonge=" + pretProlonge +
                ", utilisateur='" + utilisateur + '\'' +
                ", tagForUpdate=" + tagForUpdate +
                ", rendu=" + rendu +
                '}';
    }
}
