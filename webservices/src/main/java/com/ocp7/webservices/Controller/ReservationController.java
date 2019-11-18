package com.ocp7.webservices.Controller;

import com.ocp7.webservices.Controller.exceptions.ImpossibleAjouterReservationException;
import com.ocp7.webservices.DAO.LivreDAO;
import com.ocp7.webservices.DAO.ReservationDAO;
import com.ocp7.webservices.Modele.Livre;
import com.ocp7.webservices.Modele.Reservation;
import com.ocp7.webservices.Service.LivreService;
import com.ocp7.webservices.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private LivreDAO livreDAO;

    @Autowired
    private ReservationDAO reservationDAO;


    @RequestMapping(value = "livre/{livreId}/reservation/showFormResa", method = RequestMethod.GET)
    public Reservation showFormForAdd(@PathVariable("livreId") int pretId, Model model) {
        Reservation laReservation = new Reservation();
        return laReservation;
    }

    @RequestMapping(value = "livre/{livreId}/reservation/saveFormResa", method = RequestMethod.POST)
    public ResponseEntity<Reservation> saveReservation(@PathVariable("livreId") int livreId, @RequestBody Reservation laReservation) {
        List<Reservation> resasBook = reservationDAO.findByLivre(livreDAO.findById(livreId).orElse(null).getNom()).orElse(null);

        if (resasBook==null||(resasBook.size()+1) <= 2 * livreDAO.findById(livreId).orElse(null).getQuantite()) {

            Reservation lareservation = reservationService.saveReservation(livreId, laReservation);
            return new ResponseEntity<Reservation>(laReservation, HttpStatus.CREATED);
        } else
            throw new ImpossibleAjouterReservationException("Liste d'attente complète, impossible de réserver pour l'instant");

    }

    @RequestMapping(value = "userReservations",method=RequestMethod.GET)
    public List<Reservation> userReservations(String utilisateur){
        return reservationService.utilisateurReservations(utilisateur);
    }

    @RequestMapping(value = "livreReservations",method = RequestMethod.GET)
    public List<Reservation> livreReservations(@RequestParam("livre") String livre){
        return reservationService.livreReservations(livre);
    }




}
