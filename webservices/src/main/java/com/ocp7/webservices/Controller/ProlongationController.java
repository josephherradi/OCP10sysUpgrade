package com.ocp7.webservices.Controller;

import com.ocp7.webservices.Controller.exceptions.ImpossibleAjouterProlongationException;
import com.ocp7.webservices.Controller.exceptions.ProlongationNotFoundException;
import com.ocp7.webservices.Modele.Pret;
import com.ocp7.webservices.Modele.Prolongation;
import com.ocp7.webservices.Service.PretService;
import com.ocp7.webservices.Service.ProlongationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class ProlongationController {
    @Autowired
    private ProlongationService prolongationService;

    @Autowired
    private PretService pretService;

    @RequestMapping(value="pret/{pretId}/prolongation/showFormProlo",method = RequestMethod.GET)
    public Prolongation showFormForAdd(@PathVariable("pretId") int pretId, Model model){
        Prolongation laProlongation= new Prolongation();

        return laProlongation;
    }

    @RequestMapping(value = "pret/{pretId}/prolongation/saveFormProlo",method =RequestMethod.POST)
    public ResponseEntity saveProlongation(@PathVariable("pretId") int pretId, @RequestBody Prolongation laProlongation) {
        prolongationService.saveProlongation(pretId, laProlongation);
        return new ResponseEntity(laProlongation,HttpStatus.CREATED);
    }


    @RequestMapping(value = "userprolongations",method = RequestMethod.GET)
    public List<Prolongation> userProlongations(String utilisateur){
        List<Prolongation> userList=prolongationService.userProlongation(utilisateur);
        return userList;
    }

    @RequestMapping(value="prolongationsList",method=RequestMethod.GET)
    public List<Prolongation> prolongationsList(){
        List<Prolongation> prolongList=prolongationService.ProlongationList();
        return prolongList ;
    }

    @RequestMapping(value="pret/{pretId}/prolongation/updateProlongation",method=RequestMethod.GET)
    public Prolongation getProlongation(@PathVariable("pretId") int pretId,@RequestParam("prolongationId") int prolongationId) {
        Prolongation laProlongation=prolongationService.getProlongation(prolongationId);
        if(laProlongation==null) throw new ProlongationNotFoundException("cette prolongation n'existe pas");
        return laProlongation;
    }
    @RequestMapping(value = "deleteProlo", method = RequestMethod.GET)
    public void delete(@RequestParam("proloId") int id) {
        prolongationService.delete(id);
    }
}
