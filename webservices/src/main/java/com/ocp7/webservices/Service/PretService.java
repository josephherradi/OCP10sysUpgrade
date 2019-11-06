package com.ocp7.webservices.Service;

import com.ocp7.webservices.Modele.Pret;

import java.util.List;

public interface PretService {
    public List<Pret> listePrets();
    public  Pret savePret(Pret lePret);
    public Pret get(int theId);
    public void delete(int theId);
    public List<Pret> userPrets(String utilisateur);
    public Pret pretRetourPlusProche(String livre);


}
