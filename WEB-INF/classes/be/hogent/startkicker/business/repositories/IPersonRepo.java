package be.hogent.startkicker.business.repositories;

import java.io.Serializable;
import java.util.List;

import be.hogent.startkicker.business.Person;

public interface IPersonRepo extends Serializable {
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String USERNAME_ALREADY_EXISTS = "Username already in use";

	public List<Person> getAllPersons();

	public String savePerson(Person p);

	public Person getPerson(String userName, String password);

	public Person getPerson(long id);

}
