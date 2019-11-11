package com.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class ReservationItemProcessor implements ItemProcessor<Reservation, MimeMessage> {
    @Autowired
    private JavaMailSender mailSender;

    private String sender;
    private String attachment;

    public ReservationItemProcessor(String sender, String attachment) {
        this.sender = sender;
        this.attachment = attachment;
    }

    @Override
    public MimeMessage process(Reservation reservation) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(sender);
        helper.setTo(reservation.getMail());
        helper.setSubject("disponibilité du livré réservé"+reservation.getNomLivre());
        helper.setText("Bonjour "+reservation.getUtilisateur()+","+" Le livre réservé est disponible pendant 48h pour emprunt");

        return message;
    }
}
