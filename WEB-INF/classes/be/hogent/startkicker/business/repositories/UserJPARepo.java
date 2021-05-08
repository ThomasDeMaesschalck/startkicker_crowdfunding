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

public class UserJPARepo implements IUserRepo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String NAME_PERSISTENCEUNIT = "startkicker";

	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private UserMapper pm = new UserMapper();

	public UserJPARepo() {
		System.out.println("UserJPARepo created");
		emf = Persistence.createEntityManagerFactory(NAME_PERSISTENCEUNIT);
	}

	private void createEM() {
//		emf = Persistence.createEntityManagerFactory(NAME_PERSISTENCEUNIT);
		em = emf.createEntityManager();
	}

	private void closeEM() {
		if (em != null)
			em.close();
//		if (emf != null)
//			emf.close();
	}

	@Override
	public String saveUser(User user) {
		try {
			createEM();
			UserEntity userInDB = em.find(UserEntity.class, user.getId());

			System.out.println(user.getId() + "is userID");

			em.getTransaction().begin();
			if (userInDB != null) {
				System.out.println("User found...");
				return updateUser(pm.mapObjectToEntity(user), userInDB);
			} else {
				System.out.println("Making new user...");
				return saveNewUser(pm.mapObjectToEntity(user));
			}
		} catch (Exception e) {
			System.out.println("not found...");
			return FAIL;
		} finally {
			closeEM();
		}
	}

	/**
	 * De nieuwe persoon proberen opslaan.<br>
	 * Username moet uniek zijn om succes te hebben.<br>
	 * see uniqueConstraints = @UniqueConstraint(columnNames = "userName")
	 * @param user
	 * @return
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
	 * De persoon proberen updaten.<br>
	 * Username, indien veranderd, moet uniek zijn om succes te hebben.<br>
	 * 
	 * @param user
	 * @param userInDB
	 * @return
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
			System.out.println("updating...");
			return SUCCESS;

		} catch (Exception e) {
			System.out.println(e.toString());

			return USERNAME_ALREADY_EXISTS;
		}
	}

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

	@Override
	public List<User> getAllUsers() {
		try {
			createEM();
			TypedQuery<UserEntity> q = em.createQuery("select p from UserEntity p where not p.userName = :initUser order by p.id DESC",
					UserEntity.class);
			q.setParameter("initUser", "My_Admin");
			return pm.allEntityToObject(q.getResultList());
		} catch (Exception e) {
			return new ArrayList<User>();
		} finally {
			closeEM();
		}
	}

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
