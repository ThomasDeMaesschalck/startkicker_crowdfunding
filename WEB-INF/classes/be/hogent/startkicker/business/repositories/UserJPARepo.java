package be.hogent.startkicker.business.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import be.hogent.startkicker.business.User;
import be.hogent.startkicker.persistence.jpa.entities.UserEntity;
import be.hogent.startkicker.persistence.jpa.mapper.UserMapper;

/**
 *  Class used for persisting and retrieving users in the database.
 *
 */
public class UserJPARepo implements IUserRepo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private static final String NAME_PERSISTENCEUNIT = "startkicker";

	private EntityManagerFactory emf = null;
	private EntityManager em = null;

	/**
	 * UserMapper is a class to map objects to entities and the other way around.
	 */
	private UserMapper pm = new UserMapper();


	/**
	 * Initialize the user repository
	 */
	public UserJPARepo() {
		emf = Persistence.createEntityManagerFactory(NAME_PERSISTENCEUNIT);
	}

	/**
	 * Create entity manager for DB operations
	 */
	private void createEM() {
		em = emf.createEntityManager();
	}

	/**
	 * Closing the entity manager resource
	 */
	private void closeEM() {
		if (em != null)
			em.close();
	}

	/**
	 * Tries to persist a User object in the database. Checks if user is present in the databased, based on the user id,
	 * and performs logic to check whether a new user needs to be created, or an existing user record needs to be updated.
	 * @param user User object that needs to be persisted in the DB.
	 * @return Returns a string representing the success or error code.
	 */
	@Override
	public String saveUser(User user) {
		try {
			createEM();
			UserEntity userInDB = em.find(UserEntity.class, user.getId());


			em.getTransaction().begin();
			if (userInDB != null) {
				return updateUser(pm.mapObjectToEntity(user), userInDB);
			} else {
				return saveNewUser(pm.mapObjectToEntity(user));
			}
		} catch (Exception e) {
			return FAIL;
		} finally {
			closeEM();
		}
	}

	/**
	 * Try to save a new user.<br>
	 * Success returned if persistence succeeds.<br>
	 * see uniqueConstraints = @UniqueConstraint(columnNames = "userName")
	 * @param user The user to save
	 * @return A string representing the success or error code
	 */
	private String saveNewUser(UserEntity user) {
		try {
			em.persist(user);
			em.getTransaction().commit();
			return SUCCESS;
		} catch (Exception ex) {
			return USERNAME_ALREADY_EXISTS;
		}
	}

	/**
	 * Try to update a user record.<br>
	 * Usernames need to be unique (database constraint). Error code is returned if username is not unique.<br>
	 * 
	 * @param user A UserEntity to persist in DB
	 * @param userInDB The current User object stored in the DB
	 * @return A String representing the success or error code
	 */
	private String updateUser(UserEntity user, UserEntity userInDB) {
		try {
			userInDB.setFirstName(user.getFirstName());
			userInDB.setName(user.getName());
			userInDB.setEmail(user.getEmail());
			userInDB.setUserName(user.getUserName());
			userInDB.setPassword(user.getPassword());
			userInDB.setActif(user.isActif());
			userInDB.setAdmin(user.isAdmin());
			em.persist(userInDB);
			em.getTransaction().commit();
			return SUCCESS;

		} catch (Exception e) {
			return USERNAME_ALREADY_EXISTS;
		}
	}

	/**
	 * Retrieve a user record from the database. This method is also used to perform the login functionality.
	 * Method only returns user if username and password is correct, and if the user account has been activated by the admin. User actif boolean needs to be true.
	 * @param userName String containing name of the user
	 * @param password String containing user password
	 * @return A string representing a success or error code
	 */
	@Override
	public User getUser(String userName, String password) {
		try {
			createEM();
			System.out.println(em);
			TypedQuery<UserEntity> q = em.createQuery(
					"select p from UserEntity p where p.userName= :userName and p.password= :password and p.actif = '1' ",
					UserEntity.class);
			q.setParameter("userName", userName);
			q.setParameter("password", password);
			return pm.mapEntityToObject(q.getSingleResult());
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		} finally {
			closeEM();
		}

	}

	/**
	 * Retrieve a user from the database based on the user id.
	 * @param id The id that identifies the user.
	 * @return Returns a User object
	 */
	@Override
	public User getUser(long id) {
		try {
			createEM();
			return pm.mapEntityToObject(em.find(UserEntity.class, id));
		} catch (Exception e) {
			return null;
		} finally {
			closeEM();
		}

	}

	/**
	 * Retrieves all users from the database.
	 * @return A list with User objects.
	 */

	@Override
	public List<User> getAllUsers() {
		try {
			createEM();
			TypedQuery<UserEntity> q = em.createQuery("select p from UserEntity p order by p.id DESC",
					UserEntity.class);
			return pm.allEntityToObject(q.getResultList());
		} catch (Exception e) {
			return new ArrayList<User>();
		} finally {
			closeEM();
		}
	}

	/**
	 * Delete a user record from the database. Method searches if user id is present in the database and tries to delete this record.
	 * @param user A user object with the user's id and other information.
	 * @return A String representing the success or error code.
	 */

	@Override
	public String deleteUser(User user) {
		String outcome = "";
		try {
			createEM();
			UserEntity userInDB = em.find(UserEntity.class, user.getId());
			if (userInDB != null)
			{
				em.getTransaction().begin();
				em.remove(userInDB);
				em.getTransaction().commit();
				outcome = SUCCESS;
			}
			else  {
				outcome = FAIL;
			}
		} catch (Exception e) {
			outcome = FAIL;
		} finally {
			closeEM();
			return outcome;
		}
	}


}
