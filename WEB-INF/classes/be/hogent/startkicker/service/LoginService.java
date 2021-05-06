package be.hogent.startkicker.service;

import be.hogent.startkicker.business.repositories.IPersonRepo;
import be.hogent.startkicker.business.repositories.PersonJPARepo;
import be.hogent.startkicker.service.dto.PersonDTO;
import be.hogent.startkicker.service.mappers.PersonMapper;

public class LoginService {

	private IPersonRepo personRepo;
	private static LoginService instance;

	private LoginService() {
		personRepo = new PersonJPARepo();
	}

	public static LoginService getInstance() {
		if (instance == null) {
			instance = new LoginService();
		}
		return instance;
	}

	public PersonDTO doLogin(String userName, String password) {
		System.out.println("doLogin");
		return new PersonMapper().mapObjectToDTO(personRepo
				.getPerson(userName, password));
	}



}
