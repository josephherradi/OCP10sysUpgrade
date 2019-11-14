package com.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReservationDAO extends JpaRepository<Reservation,Integer> {

    @Query(value = "select c.reservation_id AS reservation_id, a.nom_livre AS nom_livre, c.utilisateur AS utilisateur, c.num_liste_attente AS num_liste_attente, b.mail AS mail " +
            "from Pret a, Reservation c, Utilisateur b  where a.nom_livre = c.livre and b.identifiant = c.utilisateur and rendu = true and notified = false"+
            " order by c.num_liste_attente ASC limit 1",nativeQuery = true)
    ReservationProjection customQuery();

}
