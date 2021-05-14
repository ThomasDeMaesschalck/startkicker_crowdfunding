package be.hogent.startkicker.service.dto;

import java.io.Serializable;

/**
 * User DTO is used by the service layer and the frontend.
 */
public class UserDTO implements Comparable<UserDTO>, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * User id
	 */
	private long id;

	/**
	 * User first name
	 */
	protected String firstName;

	/**
	 * User last name
	 */
	protected String name;

	/**
	 * Username
	 */
	protected String userName;

	/**
	 * User password
	 */
	protected String password;

	/**
	 * User email address
	 */
	protected String email;

	/**
	 * Is the user account active (true) or inactive (false)
	 */
	protected boolean actif;

	/**
	 * Does the user have admin rights (true) or not (false)
	 */
	protected boolean admin;

	/**
	 * Empty constructor for UserDTO
	 */
	public UserDTO() {
		super();
	}

	/**
	 * Overloaded constructor
	 * @param firstName User first name
	 * @param name User last name
	 * @param userName Unique username
	 * @param password User password
	 * @param email User email address
	 */
	public UserDTO(String firstName, String name, String userName,
				   String password, String email) {
		super();
		this.firstName = firstName;
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		int aantal = this.getClass().getSimpleName().length() - 3;
		return this.getClass().getSimpleName().substring(0, aantal);
	}

	@Override
	public int compareTo(UserDTO o) {
		return this.toString().compareTo(o.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

}
