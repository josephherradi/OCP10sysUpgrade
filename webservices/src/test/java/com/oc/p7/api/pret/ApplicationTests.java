package com.oc.p7.api.pret;

import com.ocp7.webservices.Application;
import com.ocp7.webservices.DAO.LivreDAO;
import com.ocp7.webservices.Modele.Livre;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.List;


@SpringBootTest(classes={Application.class})
@ExtendWith(SpringExtension.class)


public class ApplicationTests {
	@Autowired
	private LivreDAO livreDAO;

	@Test
	public void checkdDAO() {
		List<Livre> livreList=livreDAO.findAll();
		Assert.isTrue(livreList.size()==5,"error nombre livre erroné");
	}

}