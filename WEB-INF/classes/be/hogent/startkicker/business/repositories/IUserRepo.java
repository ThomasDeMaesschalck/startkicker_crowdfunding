package be.hogent.startkicker.business.repositories;

import java.io.Serializable;
import java.util.List;

import be.hogent.startkicker.business.User;

/**
 * Interface for User JPA repository
 */
public interface IUserRepo extends Serializable {
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String USERNAME_ALREADY_EXISTS = "Username already in use";

	/**
	 * Get all user objects from DB
	 * @return A List of User objects
	 */
	public List<User> getAllUsers();

	/**
	 * Save User object to DB
	 * @param p User that needs to be persisted
	 * @return A String representing the success or error code
	 */
	public String saveUser(User p);

	/**
	 * Get a user from the DB. Used for login.
	 * @param userName The User's username
	 * @param password The User's password
	 * @return Returns a User object if an object with active code true has been found that matches the provided username and password.
	 */
	public User getUser(String userName, String password);

	/**
	 * Get a User from the DB based on the user id.
	 * @param id The id of the user
	 * @return Returns a User object
	 */
	public User getUser(long id);

	/**
	 * Delete a User from the DB
	 * @param u User that needs to be deleted
	 * @return Returns a String representing the success or error code
	 */
	public String deleteUser(User u);

}
