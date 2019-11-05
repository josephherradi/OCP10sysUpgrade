package com.ocp7.webservices.Service;

import com.ocp7.webservices.Modele.Reservation;

import java.util.List;

public interface ReservationService {
    public Reservation saveReservation(Integer livreId, String utilisateur, Reservation laReservation );
    public List<Reservation> utilisateurReservation(String utilisateur);
    public List<Reservation> livreReservation(String livre);


}
