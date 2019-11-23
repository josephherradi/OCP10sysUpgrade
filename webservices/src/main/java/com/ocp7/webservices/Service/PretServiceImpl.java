package com.ocp7.webservices.Service;

import com.ocp7.webservices.Controller.exceptions.ImpossibleAjouterPretException;
import com.ocp7.webservices.DAO.LivreDAO;
import com.ocp7.webservices.DAO.PretDAO;
import com.ocp7.webservices.Modele.Livre;
import com.ocp7.webservices.Modele.Pret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PretServiceImpl implements PretService {
    @Autowired
    private PretDAO pretDAO;

    @Autowired
    private LivreDAO livreDAO;

    @Override
    public List<Pret> listePrets() {
        return pretDAO.findAll();
    }

    @Override
    public void savePret(Pret lePret) {

        Date datVar=new Date();
        lePret.setDatePret(datVar);
        Calendar cal = Calendar.getInstance();
        cal.setTime(datVar);
        cal.add(Calendar.WEEK_OF_MONTH,4);
        lePret.setDateRetour(cal.getTime());
        /* nouveau pret  */
        if( lePret.getTagForUpdate().equals(Boolean.FALSE) && ((livreDAO.findById(lePret.getIdLivre()).orElse(null).getDisponibilite()!=0))){
            Livre leLivre=livreDAO.findById(lePret.getIdLivre()).orElse(null);
            leLivre.setDisponibilite(leLivre.getDisponibilite()-1);
            lePret.setDateRetour(cal.getTime());
            lePret.setRendu(Boolean.FALSE);
            livreDAO.save(leLivre);
            pretDAO.save(lePret);

        }
        /* retour pret */

        if(lePret.getTagForUpdate().equals(Boolean.TRUE)&&lePret.getRendu().equals(Boolean.TRUE)){
            Livre leLivre=livreDAO.findById(lePret.getIdLivre()).orElse(null);
            leLivre.setDisponibilite(leLivre.getDisponibilite()+1);
            livreDAO.save(leLivre);
            lePret.setRendu(Boolean.TRUE);
            lePret.setDateRetour(new Date());
            pretDAO.save(lePret);

        }
        if(lePret==null || (livreDAO.findById(lePret.getIdLivre()).orElse(null)).getDisponibilite()==0) throw new ImpossibleAjouterPretException("Impossible d'ajouter ce pret");


    }


    @Override
    public Pret get(int theId) {
        return pretDAO.findById(theId).orElse(null);
    }

    @Override
    public void delete(int theId) {
        pretDAO.deleteById(theId);

    }

    @Override
    public List<Pret> userPrets(String utilisateur) {
        return pretDAO.findByUtilisateur(utilisateur);
    }

    @Override
    public Pret pretRetourPlusProche(String livre) {

        List<Pret> listPretsParLivre=pretDAO.findByLivre(livre).orElse(null);
        Pret pretRetourPlusproche=null;
        if(listPretsParLivre!=null){
        pretRetourPlusproche= Collections.min(listPretsParLivre, Comparator.comparing(c -> c.getDateRetour()));}
        return pretRetourPlusproche;
    }


}
