package com.ocp7.webservices.Service;

import com.ocp7.webservices.Controller.exceptions.FunctionalException;
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
        Livre leLivre = livreDAO.findById(livreId).orElse(null);
        List<Reservation> bookReservations = reservationDAO.findByLivre(leLivre.getNom()).orElse(null);
        List<Reservation> resasBook = reservationDAO.findByLivreAndStatut(livreDAO.findById(livreId).orElse(null).getNom(), "en attente").orElse(null);
        String utilisateur = laReservation.getUtilisateur();
        List<Pret> pretsFoundBeforeResa = pretDAO.FindByLivreAndUser(livreDAO.findById(livreId).orElse(null).getNom(), utilisateur).orElse(null);
        List<Reservation> userBookResa = reservationDAO.findByLivreAndUserAndStatut(livreDAO.findById(livreId).orElse(null).getNom(), utilisateur, "en attente").orElse(null);
        Integer dispoLivre = livreDAO.findById(livreId).orElse(null).getDisponibilite();
        Integer qteLivre = livreDAO.findById(livreId).orElse(null).getQuantite();

        laReservation.setLivre(leLivre.getNom());
        laReservation.setDateDemande(new Date());
        laReservation.setStatut("en attente");
        laReservation.setNotified(Boolean.FALSE);


        laReservation=this.checkReservationQueuePosition(laReservation, bookReservations);
        this.checkSizeQueueList(qteLivre,resasBook);
        this.checkUserPret(pretsFoundBeforeResa);
        this.checkUserResa(userBookResa);
        this.checkBookDispo(dispoLivre);


        reservationDAO.save(laReservation);
    }

        //RG0

        public Reservation checkReservationQueuePosition(Reservation laReservation, List<Reservation> bookReservations){
        if(bookReservations==null){
            laReservation.setNumListeAttente(1);
        } else {
            Reservation lastResa= Collections.max(bookReservations, Comparator.comparing(s -> s.getNumListeAttente()));
            laReservation.setNumListeAttente(lastResa.getNumListeAttente()+1);
        }
        return laReservation;

    }

        //RG1 La liste de réservation ne peut comporter qu’un maximum de personnes correspondant à 2x le nombre d’exemplaires de l’ouvrage.


         public  void checkSizeQueueList(Integer qteLivre, List<Reservation> resasBook)throws FunctionalException{

            if((resasBook.size() + 1) > 2 * qteLivre){
               throw new FunctionalException("Liste d'attente compète");
            }
         }

         //RG2 Il n’est pas possible pour un usager de réserver un ouvrage qu’il a déjà en cours d’emprunt

        public  void checkUserPret(List<Pret> pretsFoundBeforeResa)throws FunctionalException{
        if(pretsFoundBeforeResa!=null){
            throw new FunctionalException("Impossible de réserver ce livre en ayant un prêt en cours");
        }
        }

        // RG3 Impossibel de d'effectuer plusieurs réservations du même livre

        public  void checkUserResa(List<Reservation> userBookResa) throws  FunctionalException{

        if(userBookResa!=null){
            throw new FunctionalException("Vous avez déjà une réservation de ce livre");
        }
        }

        // RG4

        public  void checkBookDispo(Integer dispoLivre) throws FunctionalException{
        if (dispoLivre!=0){
            throw new FunctionalException("la réservation n'est possible que si le livre est indisponible, ils existent des exemplaires disponibles");
        }
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
