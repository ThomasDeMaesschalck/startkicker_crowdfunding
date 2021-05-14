package be.hogent.startkicker.business;

/**
 * Registered users of the crowdfunding application. Each user has a unique username.
 */
public class User {

	private long id;
	protected String firstName;
	protected String name;
	protected String userName;
	protected String password;
	protected String email;

	/**
	 * Boolean actif indicates whether the user account has been activated by an administrator. False means account needs activation.
	 */
	protected boolean actif;

	/**
	 * Boolean admin. Specifies whether the user has administrator rights. True means the user have administrative rights.
	 */
	protected boolean admin;


	/**
	 * Constructor to make our User.
	 * @param firstName First name of user
	 * @param name Last name of user
	 * @param userName Unique username
	 * @param password Password of the user
	 * @param email E-mail adress of the user
	 */
	public User(String firstName, String name, String userName,
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
}

