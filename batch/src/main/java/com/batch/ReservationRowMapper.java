package com.batch;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ReservationRowMapper implements RowMapper<Reservation> {
    @Override
    public Reservation mapRow(ResultSet resultSet, int i) throws SQLException {

       Date dateRetour=resultSet.getDate("date_retour");





        Reservation reservation=new Reservation();
        reservation.setId(resultSet.getInt("reservation_id"));
        reservation.setDateRetour(dateRetour);
        reservation.setNomLivre(resultSet.getString("nom_livre"));
        reservation.setMail(resultSet.getString("mail"));
        reservation.setNumListeAttente(resultSet.getInt("num_liste_attente"));
        reservation.setUtilisateur(resultSet.getString("utilisateur"));
        reservation.setStatut(resultSet.getString("statut"));
        return reservation;
    }
}
