package com.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ReservationDAO extends JpaRepository<Reservation,Integer> {

    @Query(value = "select c.reservation_id AS reservation_id," +
            " c.statut AS statut, a.nom AS nom, c.utilisateur AS utilisateur, c.num_liste_attente AS num_liste_attente, b.mail AS mail " +
            "from Livre a, Reservation c, Utilisateur b  where a.nom = c.livre and b.identifiant = c.utilisateur and disponibilite >= 1 and notified = false and statut = 'en attente'"+
            " order by c.num_liste_attente ASC limit 1",nativeQuery = true)
    List<ReservationProjection> customQuery();

    @Query("select r from Reservation r where r.notified = true")
    Optional <List<Reservation>> findNotified();
}
