package com.ocp7.webservices.Controller;

import com.ocp7.webservices.Controller.exceptions.ImpossibleAjouterPretException;
import com.ocp7.webservices.Controller.exceptions.PretNotFoundException;
import com.ocp7.webservices.DAO.PretDAO;
import com.ocp7.webservices.Modele.Livre;
import com.ocp7.webservices.Modele.Pret;
import com.ocp7.webservices.Service.LivreService;
import com.ocp7.webservices.Service.PretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.font.TrueTypeFont;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class PretController {
    @Autowired
    private PretService pretService;

    @Autowired
    private LivreService livreService;


    @RequestMapping(value="prets", method= RequestMethod.GET)
    public List<Pret> listPrets(){

        List <Pret>list=pretService.listePrets();
        if(list==null) throw new PretNotFoundException("Aucun prêt n'existe");

        return list ;
    }

    @RequestMapping(value = "showFormPret", method = RequestMethod.GET)
    public Pret showFormForAdd(@RequestParam("livreId") int theId){
        Pret lePret=new Pret();
        return lePret;
    }

    @PostMapping(value = "saveFormPret")
    public ResponseEntity savePret(@RequestBody Pret lePret){
        pretService.savePret(lePret);
        return new ResponseEntity(lePret,HttpStatus.OK);

    }

    @GetMapping(value = "updateFormPret")
    public Pret showFormForUpdate(@RequestParam("pretId") int id){
        Pret lePret=pretService.get(id);
        if(lePret==null) throw new PretNotFoundException("Ce pret n'existe pas");
        return lePret;
    }

    @RequestMapping(value = "deletePret", method = RequestMethod.GET)
    public void delete(@RequestParam("pretId") int id) {
        pretService.delete(id);
    }

    @RequestMapping(value = "userprets",method = RequestMethod.GET)
    public List<Pret> userPrets(String utilisateur){
        List<Pret> userList=pretService.userPrets(utilisateur);
        if(userList==null) throw new PretNotFoundException("Aucun pret n'existe");
        return userList;
    }

    @GetMapping(value = "getPret")
    public Pret getPret(@RequestParam("pretId") int id){
        Pret lePret=pretService.get(id);
        if(lePret==null) throw new PretNotFoundException("Ce pret n'existe pas");
        return lePret;
    }

    @GetMapping(value = "pretRetourPlusProche")
    public Pret pretBackPlusTot(@RequestParam("livre") String livre){
        return pretService.pretRetourPlusProche(livre);

    }
}
