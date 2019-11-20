package com.ocp7.webservices.DAO;

import com.ocp7.webservices.Modele.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationDAO extends JpaRepository<Reservation,Integer> {
    @Query("select r from Reservation r where r.utilisateur= :user")
    Optional<List<Reservation>> findByUtilisateur(@Param("user") String utilisateur);

    @Query("select r from Reservation r where r.livre= :bookName")
    Optional<List<Reservation>> findByLivre(@Param("bookName") String livre);

    @Query("select r from Reservation r where r.livre= :bookName and r.statut= :statut")
    Optional<List<Reservation>> findByLivreAndStatut(@Param("bookName") String livre,@Param("statut") String statut);

    @Query("select r from Reservation r where r.livre= :bookName and r.utilisateur= :utilisateur and statut= :statut")
    Optional<List<Reservation>> findByLivreAndUserAndStatut(@Param("bookName") String livre,@Param("utilisateur") String utilisateur,@Param("statut") String statut);



}
