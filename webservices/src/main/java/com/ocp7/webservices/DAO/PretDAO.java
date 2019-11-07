package com.ocp7.webservices.DAO;

import com.ocp7.webservices.Modele.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface PretDAO extends JpaRepository<Pret,Integer> {
    @Query("select p from Pret p where p.utilisateur= :userId")
    List<Pret> findByUtilisateur(@Param("userId") String utilisateur);

    @Query("select p from Pret p where p.nomLivre= :nom")
    Optional <List<Pret>> findByLivre(@Param("nom") String livre);
}
