package com.ocp7.webservices.Service;

import com.ocp7.webservices.DAO.LivreDAO;
import com.ocp7.webservices.DAO.ReservationDAO;
import com.ocp7.webservices.Modele.Livre;
import com.ocp7.webservices.Modele.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private LivreDAO livreDAO;

    @Override
    public Reservation saveReservation(Integer livreId, Reservation laReservation) {
        Livre leLivre=livreDAO.findById(livreId).orElse(null);
        laReservation.setLivre(leLivre.getNom());
        laReservation.setDateDemande(new Date());
        laReservation.setStatut("en attente");


        return reservationDAO.save(laReservation);
    }

    @Override
    public @ResponseBody List<Reservation> utilisateurReservations(String utilisateur) {
        return reservationDAO.findByUtilisateur(utilisateur).orElse(null);
    }

    @Override
    public @ResponseBody List<Reservation> livreReservations(String livre) {
        return reservationDAO.findByLivre(livre).orElse(null);
    }
}
