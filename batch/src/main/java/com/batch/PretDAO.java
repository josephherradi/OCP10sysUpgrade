package com.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PretDAO extends JpaRepository<PretBean,Integer> {
    @Query(value="select p from Pret p where p.utilisateur= :userId",nativeQuery = true)
    List<Pret> findByUtilisateur(@Param("userId") String utilisateur);

    @Query(value="select p from Pret p where p.nomLivre= :nom",nativeQuery = true)
    Optional <List<Pret>> findByLivre(@Param("nom") String livre);
}
