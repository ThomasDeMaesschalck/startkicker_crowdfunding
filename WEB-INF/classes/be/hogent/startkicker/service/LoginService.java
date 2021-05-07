package be.hogent.startkicker.service;

import be.hogent.startkicker.business.repositories.IUserRepo;
import be.hogent.startkicker.business.repositories.UserJPARepo;
import be.hogent.startkicker.service.dto.UserDTO;
import be.hogent.startkicker.service.mappers.UserMapper;

public class LoginService {

	private IUserRepo userRepo;
	private static LoginService instance;

	private LoginService() {
		userRepo = new UserJPARepo();
	}

	public static LoginService getInstance() {
		if (instance == null) {
			instance = new LoginService();
		}
		return instance;
	}

	public UserDTO doLogin(String userName, String password) {
		System.out.println("doLogin");
		return new UserMapper().mapObjectToDTO(userRepo
				.getUser(userName, password));
	}



}
