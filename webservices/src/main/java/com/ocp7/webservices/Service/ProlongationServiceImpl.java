package com.ocp7.webservices.Service;

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

        if (laProlongation.getStatut().equalsIgnoreCase("Validee") && lePret.getPretProlonge().equals(Boolean.FALSE) && lePret.getRendu().equals(Boolean.FALSE)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lePret.getDateRetour());
            cal.add(Calendar.WEEK_OF_MONTH, 4);
            lePret.setDateRetour(cal.getTime());
            lePret.setPretProlonge(Boolean.TRUE);
            pretDAO.save(lePret);
        }
        laProlongation.setPretId(preId);
        laProlongation.setNomLivre(pretDAO.findById(preId).orElse(null).getNomLivre());
        if ((new Date()).before(lePret.getDateRetour())) {
            prolongationDAO.save(laProlongation);
        }
        if (laProlongation == null || lePret.getRendu().equals(Boolean.TRUE) || (new Date()).after(lePret.getDateRetour()))
            throw new ImpossibleAjouterProlongationException("Erreur, impossible d'ajouter cette prolongation");
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
