package be.hogent.startkicker.service;

import be.hogent.startkicker.business.repositories.IUserRepo;
import be.hogent.startkicker.business.repositories.UserJPARepo;
import be.hogent.startkicker.service.dto.UserDTO;
import be.hogent.startkicker.service.mappers.UserMapper;

/**
 * LoginService communicates with the frontend.
 */
public class LoginService {

	/**
	 * The user repository
	 */
	private IUserRepo userRepo;

	/**
	 * The LoginService instance
	 */
	private static LoginService instance;

	/**
	 * Default constructor
	 */
	private LoginService() {
		userRepo = new UserJPARepo();
	}

	/**
	 * Singleton pattern
	 * @return The LoginService instance
	 */
	public static LoginService getInstance() {
		if (instance == null) {
			instance = new LoginService();
		}
		return instance;
	}

	/**
	 * Used by the frontend to login a user
	 * @param userName Username of the user that wants to login
	 * @param password The user password
	 * @return Returns the UserDTO if user is activate and username/password matches
	 */
	public UserDTO doLogin(String userName, String password) {
		return new UserMapper().mapObjectToDTO(userRepo
				.getUser(userName, password));
	}



}
