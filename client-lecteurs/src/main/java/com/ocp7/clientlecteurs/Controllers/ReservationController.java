package com.ocp7.clientlecteurs.Controllers;

import com.ocp7.clientlecteurs.beans.ReservationBean;
import com.ocp7.clientlecteurs.proxies.ReservationMicroserviceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReservationController {
    @Autowired
    private ReservationMicroserviceProxy reservationMicroserviceProxy;


    @RequestMapping(value = "livre/{livreId}/reservation/showFormResa", method = RequestMethod.GET)
    public String showFormAdd(@PathVariable("livreId") int pretId, Model model) {
        ReservationBean laReservationBean = new ReservationBean();
        model.addAttribute("laReservationBean", laReservationBean);
        return "reservation-form";

    }

    @RequestMapping(value = "livre/{livreId}/reservation/saveFormResa", method = RequestMethod.POST)
    public String saveReservation(@PathVariable("livreId") int livreId, @ModelAttribute ReservationBean laReservationBean, BindingResult result, HttpSession session, HttpServletRequest request) {
        String utilisateur = (String) request.getSession().getAttribute("user");
        laReservationBean.setUtilisateur(utilisateur);
        reservationMicroserviceProxy.saveReservation(livreId, laReservationBean);
        return "redirect:/livres";}


    @RequestMapping(value = "userReservations", method = RequestMethod.GET)
    public String userReservations (HttpSession session, HttpServletRequest request, Model model){
        String utilisateur= (String) request.getSession().getAttribute("user");
        List<ReservationBean> userReservations= reservationMicroserviceProxy.userReservations(utilisateur);
        model.addAttribute("userReservations",userReservations);
        return "user-reservations";

    }

}




