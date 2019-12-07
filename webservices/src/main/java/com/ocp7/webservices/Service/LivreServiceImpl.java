package com.ocp7.webservices.Service;

import com.ocp7.webservices.DAO.LivreDAO;
import com.ocp7.webservices.Modele.Livre;
import com.ocp7.webservices.Modele.Pret;
import com.ocp7.webservices.Modele.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

@Service
public class LivreServiceImpl implements  LivreService {
    @Autowired
    private LivreDAO livreDAO;

    @Autowired
    private PretService pretService;

    @Autowired
    private ReservationService reservationService;

    @Override
    public List<Livre> listeLivres() {
        List<Livre> livresList=livreDAO.findAll();
        ListIterator<Livre> iterator=livresList.listIterator();
        while (iterator.hasNext()) {
        Livre l=iterator.next();
        Pret pretBackPlustot=pretService.pretRetourPlusProche(l.getNom());
        if(pretBackPlustot==null){
            l.setDateRetourPlusProche(null);
        }else{
        l.setDateRetourPlusProche(pretBackPlustot.getDateRetour());}
        List<Reservation> livreResas=reservationService.livreReservations(l.getNom());
        if(livreResas==null){
            l.setNbrReservations(0);
        } else {
            l.setNbrReservations(livreResas.size());
        }
            iterator.set(l);
        }


        return livresList;
    }

    @Override
    public Livre saveLivre(Livre leLivre) {
    return livreDAO.save(leLivre);
    }

    @Override
    public Livre get(int theId) {
        return livreDAO.findById(theId).orElse(null);
    }

    @Override
    public void delete(int theId) {
        livreDAO.deleteById(theId);

    }

    @Override
    public List<Livre> listeLivresParCategorie(String categorie) {
        return livreDAO.findByCategorie(categorie);
    }
}
