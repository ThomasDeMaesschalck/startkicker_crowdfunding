package be.hogent.startkicker.persistence.jpa.mapper;

import java.util.ArrayList;
import java.util.List;

import be.hogent.startkicker.business.Person;
import be.hogent.startkicker.persistence.jpa.entities.PersonEntity;

public class PersonMapper {

	public Person mapEntityToObject(PersonEntity pEntity) {
		if (pEntity == null) {
			return null;
		}
		Person person = null;
		person = new Person(pEntity.getFirstName(), pEntity.getName(), pEntity.getUserName(), pEntity.getPassword(),
				pEntity.getEmail());
		person.setId(pEntity.getId());
		person.setActif(pEntity.isActif());
		return person;
	}

	public PersonEntity mapObjectToEntity(Person p) {
		if (p == null) {
			return null;
		}
		PersonEntity personEntity = null;
		personEntity = new PersonEntity(p.getFirstName(), p.getName(), p.getUserName(), p.getPassword(), p.getEmail());
		personEntity.setId(p.getId());
		personEntity.setActif(p.isActif());
		return personEntity;
	}

//	public List<PersonEntity> allObjectToEntity(List<Person> listPs) {
//		if (listPs == null)
//			return null;
//		List<PersonEntity> list = new ArrayList<PersonEntity>();
//		for (Person p : listPs) {
//			list.add(mapObjectToEntity(p));
//		}
//		return list;
//
//	}

	public List<Person> allEntityToObject(List<PersonEntity> listPs) {
		if (listPs == null)
			return null;
		List<Person> list = new ArrayList<Person>();
		for (PersonEntity p : listPs) {
			list.add(mapEntityToObject(p));
		}
		return list;

	}

}
