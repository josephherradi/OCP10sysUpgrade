package com.ocp7.webservices.Controller;

import com.ocp7.webservices.Controller.exceptions.ImpossibleAjouterReservationException;
import com.ocp7.webservices.DAO.LivreDAO;
import com.ocp7.webservices.DAO.PretDAO;
import com.ocp7.webservices.DAO.ReservationDAO;
import com.ocp7.webservices.Modele.Livre;
import com.ocp7.webservices.Modele.Pret;
import com.ocp7.webservices.Modele.Reservation;
import com.ocp7.webservices.Service.LivreService;
import com.ocp7.webservices.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private PretDAO pretDAO;


    @RequestMapping(value = "livre/{livreId}/reservation/showFormResa", method = RequestMethod.GET)
    public Reservation showFormForAdd(@PathVariable("livreId") int pretId, Model model) {
        Reservation laReservation = new Reservation();
        return laReservation;
    }

    @RequestMapping(value = "livre/{livreId}/reservation/saveFormResa", method = RequestMethod.POST)
    public ResponseEntity saveReservation(@PathVariable("livreId") int livreId, @RequestBody Reservation laReservation) {
        reservationService.saveReservation(livreId, laReservation);
        return new ResponseEntity(laReservation, HttpStatus.CREATED);
    }

    @RequestMapping(value = "userReservations", method = RequestMethod.GET)
    public List<Reservation> userReservations(String utilisateur) {
        return reservationService.utilisateurReservations(utilisateur);
    }

    @RequestMapping(value = "livreReservations", method = RequestMethod.GET)
    public List<Reservation> livreReservations(@RequestParam("livre") String livre) {
        return reservationService.livreReservations(livre);
    }

    @RequestMapping(value = "reservations/getResa", method = RequestMethod.GET)
    public Reservation getResa(@RequestParam("reservationId") int reservationId) {
        Reservation laReservation = reservationDAO.findById(reservationId).orElse(null);
        return laReservation;
    }

    @RequestMapping(value = "reservations/annulResa", method = RequestMethod.POST)
    public ResponseEntity<Reservation> annuleResa(@RequestBody Reservation laReservation) {
        laReservation.setStatut("Annule");
        Reservation resa = reservationDAO.save(laReservation);
        return new ResponseEntity<Reservation>(resa, HttpStatus.OK);
    }

    @RequestMapping(value = "deleteResa", method = RequestMethod.GET)
    public void delete(@RequestParam("resaId") int id) {reservationDAO.deleteById(id);
    }
}


