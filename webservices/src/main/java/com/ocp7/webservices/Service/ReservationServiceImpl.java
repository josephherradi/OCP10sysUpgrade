package com.ocp7.webservices.Service;

import com.ocp7.webservices.Controller.exceptions.ImpossibleAjouterReservationException;
import com.ocp7.webservices.DAO.LivreDAO;
import com.ocp7.webservices.DAO.PretDAO;
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
    private PretDAO pretDAO;

    @Autowired
    private  PretService pretService;

    @Override
    public void saveReservation(Integer livreId, Reservation laReservation) {
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

        List<Reservation> resasBook = reservationDAO.findByLivreAndStatut(livreDAO.findById(livreId).orElse(null).getNom(),"en attente").orElse(null);
        String utilisateur = laReservation.getUtilisateur();
        List<Pret> pretsFoundBeforeResa = pretDAO.FindByLivreAndUser(livreDAO.findById(livreId).orElse(null).getNom(), utilisateur).orElse(null);
        List<Reservation> userBookResa= reservationDAO.findByLivreAndUserAndStatut(livreDAO.findById(livreId).orElse(null).getNom(), utilisateur,"en attente").orElse(null);
        Integer dispoLivre=livreDAO.findById(livreId).orElse(null).getDisponibilite();

        /*implémentation des règles de gestion  */
        /*
        - La liste de réservation ne peut comporter qu’un maximum de personnes correspondant à 2x le nombre d’exemplaires de l’ouvrage.

        - Il n’est pas possible pour un usager de réserver un ouvrage qu’il a déjà en cours d’emprunt

        - Il n'est pas possible de procéder à plusieurs réservations du même livre

        - La réservation n'est possible que si la disponibilité du livre est 0

         */


        if (((resasBook == null) || ((resasBook.size() + 1) <= 2 * livreDAO.findById(livreId).orElse(null).getQuantite()))
                &&(pretsFoundBeforeResa==null)&&(userBookResa==null)&&(dispoLivre==0)) {
            reservationDAO.save(laReservation);
        }else
            throw new ImpossibleAjouterReservationException("Reservation impossible, merci de verifier les conditions de reservations");
    }


    @Override
    public @ResponseBody List<Reservation> utilisateurReservations(String utilisateur) {
        List<Reservation> userResas=reservationDAO.findByUtilisateur(utilisateur).orElse(null);
        if (userResas!=null){
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
            }}
        return userResas;
    }

    @Override
    public @ResponseBody List<Reservation> livreReservations(String livre) {
        return reservationDAO.findByLivre(livre).orElse(null);
    }
}
