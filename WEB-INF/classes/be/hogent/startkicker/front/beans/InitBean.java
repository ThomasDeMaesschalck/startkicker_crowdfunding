package be.hogent.startkicker.front.beans;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import be.hogent.startkicker.service.dto.PersonDTO;
import be.hogent.startkicker.service.LoginService;
import be.hogent.startkicker.service.PersonService;

@ManagedBean(eager = true) // eager loading of bean @startup
@ApplicationScoped
public class InitBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InitBean() {
		System.out.println("InitBean created");

		PersonDTO person = LoginService.getInstance().doLogin("My_Admin", "AbC123");
		if (person == null) {
			PersonService pManager = PersonService.getInstance();
			PersonDTO p = new PersonDTO("Voornaam_Admin", "Achternaam_Admin", "My_Admin", "AbC123", "noreply@test.be");
			p.setActif(true);
			pManager.savePerson(p);
			for (int i = 0; i < 10; i++) {
				PersonDTO pFor = new PersonDTO("Voornaam_"+i, "Achternaam_"+i, "UName_"+i, "AbC123"+i, "user_"+i+"@test.be");
				pFor.setActif(true);
				pManager.savePerson(pFor);
			}
			
		}
	}

}
