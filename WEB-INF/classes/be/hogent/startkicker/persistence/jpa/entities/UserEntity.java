package be.hogent.startkicker.persistence.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


/**
 * Entity class for users of the appliccation. Stored in Users database table.
 * Sets a constraint, all usernames need to be unique
 */
@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
public class UserEntity implements Serializable {


	private static final long serialVersionUID = 4798929679301916147L;

	/**
	 * Auto generated user id
	 */
	@Id
	@GeneratedValue
	private long id;

	/**
	 * First name of user
	 */
	@Column(nullable = false) @NotBlank(message = "Firstname can't be blank")
	protected String firstName;

	/**
	 * Last name of user
	 */
	@Column(nullable = false) @NotBlank(message = "Name can't be blank")
	protected String name;

	/**
	 * Username. Needs to be unique (DB constraint)
	 */
	@Column(nullable = false) @NotBlank(message = "Username can't be blank")
	protected String userName;

	/**
	 * User password
	 */
	@Column(nullable = false) @NotBlank(message = "Password can't be blank")
	protected String password;

	/**
	 * User mail address
	 */
	@Column(nullable = false) @NotBlank(message = "Enter a valid email address")
	protected String email;

	/**
	 *  Boolean that indicates whether an account is active (true) or needs admin activation (false)
	 */
	protected boolean actif;

	/**
	 * Boolean that indicates whether a user has admin rights.
	 */
	protected boolean admin;

	/**
	 * Empty constructor
	 */
	protected UserEntity() {
	}

	/**
	 * Overloaded constructor
	 * @param firstName User first name
	 * @param name Last name
	 * @param userName Unique username
	 * @param password Password
	 * @param email E-mail address
	 */
	public UserEntity(String firstName, String name, String userName,
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

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (actif ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		UserEntity other = (UserEntity) obj;
		if (actif != other.actif)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
}
