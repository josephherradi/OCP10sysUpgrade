package com.oc.p7.api.pret;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ocp7.webservices.Application;
import com.ocp7.webservices.Controller.exceptions.FunctionalException;
import com.ocp7.webservices.DAO.LivreDAO;
import com.ocp7.webservices.Modele.Livre;

import com.ocp7.webservices.Modele.Pret;
import com.ocp7.webservices.Service.PretService;
import com.ocp7.webservices.Service.PretServiceImpl;
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

	@Test
	public void checkBookDispo1() throws FunctionalException {
		Pret lePret= new Pret();
		lePret.setIdLivre(17);
		lePret.setTagForUpdate(Boolean.FALSE);
		Assertions.assertThrows(FunctionalException.class,(() -> {
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
	public void nouveauPret(){
		Pret lePret=new Pret();
		Livre leLivre=new Livre();
		leLivre.setDisponibilite(4);
		pretServiceimpl.nouveauPretProcessing(lePret,leLivre);
		Assert.isTrue(leLivre.getDisponibilite()==3,"erreur maj disponibilite");
	}

	@Test
	public void retourPret(){
		Pret lePret=new Pret();
		Livre leLivre=new Livre();
		leLivre.setDisponibilite(4);
		pretServiceimpl.retourPretProcessing(lePret,leLivre);
		Assert.isTrue(leLivre.getDisponibilite()==5,"erreur maj disponibilite");
	}





}