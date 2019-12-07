package com.ocp7.webservices.Service;

import com.ocp7.webservices.Modele.Prolongation;

import java.util.List;

public interface ProlongationService {
    public List<Prolongation> ProlongationList();
    public void saveProlongation(Integer preId, Prolongation laProlongation);
    public  Prolongation getProlongation(int id);
    public  void delete(int id);
    public List<Prolongation> userProlongation(String utilisateur);
}
