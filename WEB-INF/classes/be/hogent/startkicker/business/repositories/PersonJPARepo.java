package be.hogent.startkicker.business.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import be.hogent.startkicker.business.Person;
import be.hogent.startkicker.persistence.jpa.entities.PersonEntity;
import be.hogent.startkicker.persistence.jpa.mapper.PersonMapper;

public class PersonJPARepo implements IPersonRepo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String NAME_PERSISTENCEUNIT = "DemoLayered2.2";

	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private PersonMapper pm = new PersonMapper();

	public PersonJPARepo() {
		System.out.println("PersonJPARepo created");
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
	public String savePerson(Person person) {
		try {
			createEM();
			PersonEntity personInDB = em.find(PersonEntity.class, person.getId());

			em.getTransaction().begin();
			if (personInDB != null) {
				return updatePerson(pm.mapObjectToEntity(person), personInDB);
			} else {
				return saveNewPerson(pm.mapObjectToEntity(person));
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
	 * @param person
	 * @return
	 */
	private String saveNewPerson(PersonEntity person) {
		try {
			em.persist(person);
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
	private String updatePerson(PersonEntity person, PersonEntity personInDB) {
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
	public Person getPerson(String userName, String password) {
		try {
			createEM();
			System.out.println(em);
			TypedQuery<PersonEntity> q = em.createQuery(
					"select p from PersonEntity p where p.userName= :userName and p.password= :password and p.actif = '1' ",
					PersonEntity.class);
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
	public Person getPerson(long id) {
		try {
			createEM();
			return pm.mapEntityToObject(em.find(PersonEntity.class, id));
		} catch (Exception e) {
			return null;
		} finally {
			closeEM();
		}

	}

	@Override
	public List<Person> getAllPersons() {
		try {
			createEM();
			TypedQuery<PersonEntity> q = em.createQuery("select p from PersonEntity p where not p.userName = :initUser order by p.name ",
					PersonEntity.class);
			q.setParameter("initUser", "My_Admin");
			return pm.allEntityToObject(q.getResultList());
		} catch (Exception e) {
			return new ArrayList<Person>();
		} finally {
			closeEM();
		}
	}

}
