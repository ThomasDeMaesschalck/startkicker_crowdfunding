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
			UserEntity personInDB = em.find(UserEntity.class, user.getId());

			em.getTransaction().begin();
			if (personInDB != null) {
				return updateUser(pm.mapObjectToEntity(user), personInDB);
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
	 * @param person
	 * @param personInDB
	 * @return
	 */
	private String updateUser(UserEntity person, UserEntity personInDB) {
		try {
			personInDB.setFirstName(person.getFirstName());
			personInDB.setName(person.getName());
			personInDB.setEmail(person.getEmail());
			personInDB.setUserName(person.getUserName());
			personInDB.setPassword(person.getPassword());
			personInDB.setActif(person.isActif());
			em.persist(personInDB);
			em.getTransaction().commit();
			return SUCCESS;
		} catch (Exception e) {
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
			TypedQuery<UserEntity> q = em.createQuery("select p from UserEntity p where not p.userName = :initUser order by p.name ",
					UserEntity.class);
			q.setParameter("initUser", "My_Admin");
			return pm.allEntityToObject(q.getResultList());
		} catch (Exception e) {
			return new ArrayList<User>();
		} finally {
			closeEM();
		}
	}

}
