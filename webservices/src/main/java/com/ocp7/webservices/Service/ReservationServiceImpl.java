package com.ocp7.webservices.Service;

import com.ocp7.webservices.DAO.ReservationDAO;
import com.ocp7.webservices.Modele.Reservation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationDAO reservationDAO;

    @Override
    public Reservation saveReservation(Integer livreId, String utilisateur, Reservation laReservation) {

        return reservationDAO.save(laReservation);
    }

    @Override
    public List<Reservation> utilisateurReservation(String utilisateur) {
        return reservationDAO.findByUtilisateur(utilisateur).orElse(null);
    }

    @Override
    public List<Reservation> livreReservation(String livre) {
        return reservationDAO.findByLivre(livre).orElse(null);
    }
}
