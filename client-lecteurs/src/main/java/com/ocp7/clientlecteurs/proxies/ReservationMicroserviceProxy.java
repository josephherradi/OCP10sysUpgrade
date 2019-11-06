package com.ocp7.clientlecteurs.proxies;

import com.ocp7.clientlecteurs.beans.ReservationBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservice-prets", url = "localhost:9092")

public interface ReservationMicroserviceProxy {
@GetMapping(value = "livre/{livreId}/reservation/showFormResa")
public ReservationBean showFormForAdd(@PathVariable("livreId") int pretId);

@PostMapping(value = "livre/{livreId}/reservation/saveFormResa")
public void saveReservation(@PathVariable("livreId") int livreId, ReservationBean laReservation);

@GetMapping(value="userReservations")
public List<ReservationBean> userReservations(@RequestParam("utilisateur") String utilisateur);

@GetMapping(value="livreReservations")
public List<ReservationBean> livreReservations(@RequestParam("livre") String livre);



}
