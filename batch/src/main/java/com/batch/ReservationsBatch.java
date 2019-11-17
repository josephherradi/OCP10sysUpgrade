package com.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.ListIterator;


@Component
public class ReservationsBatch {

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PretDAO pretDAO;

    @Value("${spring.mail.username}")
    private String sender;
    private String attachment;

    @Scheduled(cron = "*/10 * * * * *")
    public void bookingBatch() throws Exception{

        List<ReservationProjection> resasList=reservationDAO.customQuery();
        ReservationBean laReservation=new ReservationBean();
        ListIterator<ReservationProjection> iterator=resasList.listIterator();

        while (iterator.hasNext()) {
            ReservationProjection r=iterator.next();
            laReservation.setReservation_Id(r.getReservation_Id());
            laReservation.setNum_Liste_Attente(r.getNum_Liste_Attente());
            laReservation.setNom(r.getNom());
            laReservation.setUtilisateur(r.getUtilisateur());
            laReservation.setMail(r.getMail());
            laReservation.setStatut(r.getStatut());


            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(sender);
            helper.setTo(laReservation.getMail());
            helper.setSubject("Votre réservation du livre " + laReservation.getNom());
            helper.setText("Bonjour " + laReservation.getUtilisateur() + ". Le livre " + laReservation.getNom() +
                    " est disponible pendant 48H pour l'emprunt. Passé ce délai, votre réservation sera annulée. Cordialement");


            mailSender.send(message);

            Reservation resa = reservationDAO.findById(r.getReservation_Id()).orElse(null);
            resa.setNotified(Boolean.TRUE);
            resa.setStatut("Annule");
            reservationDAO.save(resa);
        }



        List<Integer> resaToStatutUpdate=pretDAO.resaToUpdate();
        ListIterator<Integer> iterator2=resaToStatutUpdate.listIterator();
        while (iterator.hasNext()){
            Integer i= iterator2.next();
            Reservation reservationToUpdate=reservationDAO.findById(i).orElse(null);
            reservationToUpdate.setStatut("Accepte");
            reservationDAO.save(reservationToUpdate);

        }





    }

}
