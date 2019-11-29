package com.ocp7.webservices.Service;

import com.ocp7.webservices.Controller.exceptions.FunctionalException;
import com.ocp7.webservices.Controller.exceptions.ImpossibleAjouterProlongationException;
import com.ocp7.webservices.DAO.PretDAO;
import com.ocp7.webservices.DAO.ProlongationDAO;
import com.ocp7.webservices.Modele.Pret;
import com.ocp7.webservices.Modele.Prolongation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class ProlongationServiceImpl implements ProlongationService {
    @Autowired
    private ProlongationDAO prolongationDAO;
    
    @Autowired
    private PretDAO pretDAO;

    @Override
    public List<Prolongation> ProlongationList() {
        return prolongationDAO.findAll();
    }

    @Override
    public void saveProlongation(Integer preId, Prolongation laProlongation) {

        Pret lePret=pretDAO.findById(preId).orElse(null);
        String nomLivre=lePret.getNomLivre();
        laProlongation.setPretId(lePret.getId());
        laProlongation.setNomLivre(nomLivre);

        if(laProlongation.getStatut().equalsIgnoreCase("Validee")){
            this.prolongationProcessing(lePret,laProlongation);
            this.checkPretAndProlongation(lePret,laProlongation);
            pretDAO.save(lePret);
            prolongationDAO.save(laProlongation);
        }
        this.checkDateProlongationPret(lePret);
        prolongationDAO.save(laProlongation);

    }

    public void checkPretAndProlongation(Pret lePret,Prolongation laProlongation) throws FunctionalException{


        if(laProlongation ==null){
            throw new ImpossibleAjouterProlongationException("Impossible d'ajouter cette prolongation");
        }
        if(lePret.getPretProlonge().equals(Boolean.FALSE)){
            throw new FunctionalException("Ce prêt n'a pas été prolongé");
        }
        if(lePret.getRendu().equals(Boolean.TRUE)){
            throw new FunctionalException("Ce prêt est déjà rendu");
        }
        }
    public  void checkDateProlongationPret(Pret lePret){
        if((new Date()).after(lePret.getDateRetour())){
            throw new FunctionalException("Impossible de faire une prolongation après la date de retour");
        }
    }

    public  void prolongationProcessing(Pret lePret,Prolongation laProlongation){
        Calendar cal = Calendar.getInstance();
        cal.setTime(lePret.getDateRetour());
        cal.add(Calendar.WEEK_OF_MONTH, 4);
        lePret.setDateRetour(cal.getTime());
        lePret.setPretProlonge(Boolean.TRUE);
    }
    @Override
    public Prolongation getProlongation(int id) {
        return prolongationDAO.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        prolongationDAO.deleteById(id);

    }

    @Override
    public @ResponseBody List<Prolongation> userProlongation(String utilisateur) {
        return prolongationDAO.findByUtilisateur(utilisateur).orElse(null);
    }
}
