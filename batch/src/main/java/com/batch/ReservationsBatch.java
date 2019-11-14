package com.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ReservationsBatch {

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private JavaMailSender mailSender;


    @Scheduled(cron = "*/10 * * * * *")
    public void bookingBatch(){

        System.out.println(reservationDAO.customQuery().getNum_Liste_Attente());




    }

}
