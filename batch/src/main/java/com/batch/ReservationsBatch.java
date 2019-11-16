package com.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.List;


@Component
public class ReservationsBatch {

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;
    private String attachment;

    @Scheduled(cron = "*/10 * * * * *")
    public void bookingBatch() throws Exception{

        ReservationBean laReservation=new ReservationBean();
        try{
            laReservation.setReservation_Id(reservationDAO.customQuery().getReservation_Id());
            laReservation.setNum_Liste_Attente(reservationDAO.customQuery().getNum_Liste_Attente());
            laReservation.setNom(reservationDAO.customQuery().getNom());
            laReservation.setUtilisateur(reservationDAO.customQuery().getUtilisateur());
            laReservation.setMail(reservationDAO.customQuery().getMail());
            laReservation.setStatut(reservationDAO.customQuery().getStatut());


            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(sender);
            helper.setTo(laReservation.getMail());
            helper.setSubject("Votre réservation du livre " + laReservation.getNom());
            helper.setText("Bonjour " + laReservation.getUtilisateur() + ". Le livre " + laReservation.getNom() +
                    " est disponible pendant 48H pour l'emprunt. Passé ce délai, votre réservation sera annulée. Cordialement");


            mailSender.send(message);

            Reservation resa = reservationDAO.findById(reservationDAO.customQuery().getReservation_Id()).orElse(null);
            resa.setNotified(Boolean.TRUE);
            resa.setStatut("Annule");
            reservationDAO.save(resa);

        } catch (NullPointerException e){
            System.out.println("pas de mail à envoyer");
        }





    }

}
