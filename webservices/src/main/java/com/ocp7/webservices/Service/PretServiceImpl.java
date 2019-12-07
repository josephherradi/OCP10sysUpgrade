package com.ocp7.webservices.Service;

import com.ocp7.webservices.Controller.exceptions.FunctionalException;
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



        Boolean tagNewPret=lePret.getTagForUpdate().equals(Boolean.FALSE);
        Livre leLivre=livreDAO.findById(lePret.getIdLivre()).orElse(null);


        /* nouveau pret  */
        if( tagNewPret){
            this.checkDispoLivre(lePret);
            this.nouveauPretProcessing(lePret,leLivre);
            livreDAO.save(leLivre);
            pretDAO.save(lePret);

        }

        /* retour pret */
        Boolean tagUpdate= lePret.getTagForUpdate().equals(Boolean.TRUE);
        Boolean tagRendu=  lePret.getRendu().equals(Boolean.TRUE);


        if(tagUpdate && tagRendu){
            this.retourPretProcessing(lePret,leLivre);
            pretDAO.save(lePret);
            livreDAO.save(leLivre);


        }

    }

    public void checkDispoLivre(Pret lePret) throws  FunctionalException{
        Integer dispoLivre=livreDAO.findById(lePret.getIdLivre()).orElse(null).getDisponibilite();
        Boolean tagNewPret=lePret.getTagForUpdate().equals(Boolean.FALSE);
        if(tagNewPret&&dispoLivre==0){
            throw new FunctionalException("Auncun livre n'est disponible pour le prêt");
        }
    }

    public void nouveauPretProcessing(Pret lePret, Livre leLivre){
        Date datVar=new Date();
        lePret.setDatePret(datVar);
        Calendar cal = Calendar.getInstance();
        cal.setTime(datVar);
        cal.add(Calendar.WEEK_OF_MONTH,4);
        lePret.setDateRetour(cal.getTime());
        leLivre.setDisponibilite(leLivre.getDisponibilite()-1);
        lePret.setDateRetour(cal.getTime());
        lePret.setRendu(Boolean.FALSE);
        lePret.setTagForUpdate(Boolean.TRUE);
    }

    public void retourPretProcessing(Pret lePret, Livre leLivre){
        Integer livreDispoAvant=leLivre.getDisponibilite();
        leLivre.setDisponibilite(livreDispoAvant+1);
        lePret.setDateRetour(new Date());
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
