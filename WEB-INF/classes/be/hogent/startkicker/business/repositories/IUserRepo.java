package be.hogent.startkicker.business.repositories;

import java.io.Serializable;
import java.util.List;

import be.hogent.startkicker.business.User;

public interface IUserRepo extends Serializable {
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String USERNAME_ALREADY_EXISTS = "Username already in use";

	public List<User> getAllUsers();

	public String saveUser(User p);

	public User getUser(String userName, String password);

	public User getUser(long id);

}
