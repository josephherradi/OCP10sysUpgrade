package com.ocp7.webservices.Service;

import com.ocp7.webservices.Modele.Reservation;

import java.util.List;

public interface ReservationService {
    public Reservation saveReservation(Integer livreId, Reservation laReservation );
    public List<Reservation> utilisateurReservations(String utilisateur);
    public List<Reservation> livreReservations(String livre);


}
