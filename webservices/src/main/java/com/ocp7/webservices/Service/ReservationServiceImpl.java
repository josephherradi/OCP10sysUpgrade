package com.ocp7.webservices.Service;

import com.ocp7.webservices.DAO.LivreDAO;
import com.ocp7.webservices.DAO.ReservationDAO;
import com.ocp7.webservices.Modele.Livre;
import com.ocp7.webservices.Modele.Pret;
import com.ocp7.webservices.Modele.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private LivreDAO livreDAO;

    @Autowired
    private  PretService pretService;

    @Override
    public Reservation saveReservation(Integer livreId, Reservation laReservation) {
        Livre leLivre=livreDAO.findById(livreId).orElse(null);
        laReservation.setLivre(leLivre.getNom());
        laReservation.setDateDemande(new Date());
        laReservation.setStatut("en attente");
        laReservation.setNotified(Boolean.FALSE);
        List<Reservation> bookReservations=reservationDAO.findByLivre(leLivre.getNom()).orElse(null);
        if(bookReservations==null){
        laReservation.setNumListeAttente(1);
        } else {
            Reservation lastResa= Collections.max(bookReservations, Comparator.comparing(s -> s.getNumListeAttente()));
            laReservation.setNumListeAttente(lastResa.getNumListeAttente()+1);
        }

        return reservationDAO.save(laReservation);
    }

    @Override
    public @ResponseBody List<Reservation> utilisateurReservations(String utilisateur) {
        List<Reservation> userResas=reservationDAO.findByUtilisateur(utilisateur).orElse(null);
        ListIterator<Reservation> iterator=userResas.listIterator();
        while (iterator.hasNext()){
            Reservation r=iterator.next();
            Pret pretBackPlustot=pretService.pretRetourPlusProche(r.getLivre());
            if(pretBackPlustot==null) {
                r.setDateRetourPlusProche(null);
            } else {
                r.setDateRetourPlusProche(pretBackPlustot.getDateRetour());
            }
            iterator.set(r);
            }
        return userResas;
    }

    @Override
    public @ResponseBody List<Reservation> livreReservations(String livre) {
        return reservationDAO.findByLivre(livre).orElse(null);
    }
}
