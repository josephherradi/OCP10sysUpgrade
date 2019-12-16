package com.oc.p7.api.pret;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ocp7.webservices.Application;
import com.ocp7.webservices.Controller.exceptions.FunctionalException;
import com.ocp7.webservices.Controller.exceptions.ImpossibleAjouterProlongationException;
import com.ocp7.webservices.DAO.LivreDAO;
import com.ocp7.webservices.DAO.PretDAO;
import com.ocp7.webservices.DAO.ReservationDAO;
import com.ocp7.webservices.Modele.Livre;

import com.ocp7.webservices.Modele.Pret;
import com.ocp7.webservices.Modele.Prolongation;
import com.ocp7.webservices.Modele.Reservation;
import com.ocp7.webservices.Service.PretService;
import com.ocp7.webservices.Service.PretServiceImpl;
import com.ocp7.webservices.Service.ProlongationServiceImpl;
import com.ocp7.webservices.Service.ReservationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@DatabaseSetup("/data.xml")
@SpringBootTest(classes={Application.class})
@ExtendWith(SpringExtension.class)

@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
@TestPropertySource(locations="/application.properties")


public class ApplicationTests {
    @Autowired
    private PretServiceImpl pretServiceimpl;
    @Autowired
    private ProlongationServiceImpl prolongationServiceImpl;
    @Autowired
    private ReservationServiceImpl reservationServiceImpl;
    @Autowired
    private LivreDAO livreDAO;
    @Autowired
    private ReservationDAO reservationDAO;
    @Autowired
    private PretDAO pretDAO;

    @Test
    public void checkBookDispo1() throws FunctionalException {
        Pret lePret = new Pret();
        lePret.setIdLivre(17);
        lePret.setTagForUpdate(Boolean.FALSE);
        Assertions.assertThrows(FunctionalException.class, (() -> {
            pretServiceimpl.checkDispoLivre(lePret);
        }));
    }

    @Test
    public void checkBookDispo2() throws FunctionalException {
        Pret lePret = new Pret();
        lePret.setIdLivre(1);
        lePret.setTagForUpdate(Boolean.FALSE);
        Assertions.assertDoesNotThrow(() -> {
            pretServiceimpl.checkDispoLivre(lePret);
        });
    }

    @Test
    public void nouveauPret() {
        Pret lePret = new Pret();
        Livre leLivre = new Livre();
        leLivre.setDisponibilite(4);
        pretServiceimpl.nouveauPretProcessing(lePret, leLivre);
        Assert.isTrue(leLivre.getDisponibilite() == 3, "erreur maj disponibilite");
    }

    @Test
    public void retourPret() {
        Pret lePret = new Pret();
        Livre leLivre = new Livre();
        leLivre.setDisponibilite(4);
        pretServiceimpl.retourPretProcessing(lePret, leLivre);
        Assert.isTrue(leLivre.getDisponibilite() == 5, "erreur maj disponibilite");
    }

    @Test
    public void listPrets() {
        List<Pret> listPrets = pretServiceimpl.listePrets();
        Assert.isTrue(listPrets.size() == 3, "erreur nombre de prets");
    }

    @Test
    public void checkPretProlongation() {
        Pret lePret = new Pret();
        Prolongation laProlongation = new Prolongation();
        lePret.setRendu(Boolean.TRUE);
        lePret.setPretProlonge(Boolean.TRUE);

        Assertions.assertThrows(FunctionalException.class, (() -> {
            prolongationServiceImpl.checkPretAndProlongation(lePret, laProlongation);
        }));
        Assertions.assertThrows(ImpossibleAjouterProlongationException.class, (() -> {
            prolongationServiceImpl.checkPretAndProlongation(lePret, null);
        }));

    }

    @Test
    public void checkDateProlongation1() throws ParseException {
        Pret lePret = new Pret();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date d = sdf.parse("2019/09/01");
        lePret.setDateRetour(d);
        Assertions.assertThrows(FunctionalException.class, (() -> {
            prolongationServiceImpl.checkDateProlongationPret(lePret);
        }));
    }

    @Test
    public void checkDateProlongation2() throws ParseException {
        Pret lePret = new Pret();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date d = sdf.parse("2035/09/01");
        lePret.setDateRetour(d);
        Assertions.assertDoesNotThrow(() -> {
            prolongationServiceImpl.checkDateProlongationPret(lePret);
        });
    }

    @Test
    public void prolongationProcessing() throws ParseException {
        Pret lePret = new Pret();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date d1 = sdf.parse("2019/11/15");
        Date d2 = sdf.parse("2019/12/13");
        lePret.setDateRetour(d1);
        prolongationServiceImpl.prolongationProcessing(lePret);
        Assert.isTrue(lePret.getPretProlonge() == Boolean.TRUE, "Flag non maj");
        Assert.isTrue(lePret.getDateRetour().compareTo(d2)==0, "erreur maj date retour");

    }

    @Test
    public void userProlongations() {
        List<Prolongation> userprolongations = prolongationServiceImpl.userProlongation("test1");
        Assert.isTrue(userprolongations.size() == 2, "erreur nombre de prolongations");
    }


    @Test
    public void checkSizeQueueList1() {
    Livre leLivre=livreDAO.findById(17).orElse(null);
    List<Reservation> bookReservations=reservationDAO.findByLivre(leLivre.getNom()).orElse(null);
        Assertions.assertDoesNotThrow(() -> {
            reservationServiceImpl.checkSizeQueueList(leLivre.getQuantite(),bookReservations);
        });

    }

    @Test
    public void checkSizeQueueList2() {
        Livre leLivre=livreDAO.findById(17).orElse(null);
        List<Reservation> bookReservations=reservationDAO.findByLivre(leLivre.getNom()).orElse(null);
        leLivre.setQuantite(1);
        Assertions.assertThrows(FunctionalException.class,() -> {
            reservationServiceImpl.checkSizeQueueList(leLivre.getQuantite(),bookReservations);
        });

    }

    @Test
    public void checkReservationQueuePosition1(){
    Reservation laReservation=new Reservation();
    List<Reservation> bookReservations=reservationDAO.findByLivre("test2").orElse(null);
    reservationServiceImpl.checkReservationQueuePosition(laReservation,bookReservations);
    Assert.isTrue(laReservation.getNumListeAttente()==4,"erreur position liste attente");
    }

    @Test
    public void checkReservationQueuePosition2(){
        Reservation laReservation=new Reservation();
        List<Reservation> bookReservations=null;
        reservationServiceImpl.checkReservationQueuePosition(laReservation,bookReservations);
        Assert.isTrue(laReservation.getNumListeAttente()==1,"erreur position liste attente");
    }

    @Test
    public void checkUserPret() {
        List<Pret> pretsFoundBeforeResa = pretDAO.FindByLivreAndUser(livreDAO.findById(17).orElse(null).getNom(), "Test5").orElse(null);
        Assertions.assertThrows(FunctionalException.class, () -> {
            reservationServiceImpl.checkUserPret(pretsFoundBeforeResa);
        });
    }

     @Test
     public  void userReservations(){
         List<Reservation> userResas=reservationServiceImpl.utilisateurReservations("Test3");
         Assert.isTrue(userResas.size()==2,"erreur nombre de réservations pour cet utilisateur");
        }


    }






