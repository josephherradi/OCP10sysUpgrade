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
    public ResponseEntity<Reservation> saveReservation(@PathVariable("livreId") int livreId, @RequestBody Reservation laReservation) {
        List<Reservation> resasBook = reservationDAO.findByLivreAndStatut(livreDAO.findById(livreId).orElse(null).getNom(),"en attente").orElse(null);
        String utilisateur = laReservation.getUtilisateur();
        List<Pret> pretsFoundBeforeResa = pretDAO.FindByLivreAndUser(livreDAO.findById(livreId).orElse(null).getNom(), utilisateur).orElse(null);
        List<Reservation> userBookResa= reservationDAO.findByLivreAndUserAndStatut(livreDAO.findById(livreId).orElse(null).getNom(), utilisateur,"en attente").orElse(null);
        /*implémentation des règles de gestion pour pouvoir procéder à une réservatiob  */
        if (((resasBook == null) || ((resasBook.size() + 1) <= 2 * livreDAO.findById(livreId).orElse(null).getQuantite()))
                &&(pretsFoundBeforeResa==null)&&(userBookResa==null)) {

            Reservation lareservation = reservationService.saveReservation(livreId, laReservation);
            return new ResponseEntity<Reservation>(laReservation, HttpStatus.CREATED);
        } else
            throw new ImpossibleAjouterReservationException("Reservation impossible, merci de verifier les conditions de reservations");

    }

    @RequestMapping(value = "userReservations", method = RequestMethod.GET)
    public List<Reservation> userReservations(String utilisateur) {
        return reservationService.utilisateurReservations(utilisateur);
    }

    @RequestMapping(value = "livreReservations", method = RequestMethod.GET)
    public List<Reservation> livreReservations(@RequestParam("livre") String livre) {
        return reservationService.livreReservations(livre);
    }


}
