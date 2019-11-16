package com.batch;

public interface ReservationProjection {
    Integer getReservation_Id();
    String getNom();
    String getUtilisateur();
    Integer getNum_Liste_Attente();
    String getMail();
    String getStatut();
}
