package com.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PretDAO extends JpaRepository<PretBean,Integer> {
    @Query(value = "select reservation_id AS reservation_id from Reservation r " +
            "inner join Pret p on r.livre = p.nom_livre and r.utilisateur = p.utilisateur " +
            "where p.date_pret>r.date_demande ",nativeQuery = true)
    List<Integer> resaToUpdate();

}
