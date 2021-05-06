package be.hogent.startkicker.service.mappers;

import java.util.ArrayList;
import java.util.List;

import be.hogent.startkicker.business.Person;
import be.hogent.startkicker.service.dto.PersonDTO;

public class PersonMapper implements IMapper<Person, PersonDTO> {

	public Person mapDTOToObject(PersonDTO pDTO) {
		if (pDTO == null) {
			return null;
		}
		Person person = null;
		person = new Person(pDTO.getFirstName(), pDTO.getName(),
				pDTO.getUserName(), pDTO.getPassword(), pDTO.getEmail());
		person.setId(pDTO.getId());
		person.setActif(pDTO.isActif());
		return person;
	}

	public PersonDTO mapObjectToDTO(Person p) {
		if (p == null) {
			return null;
		}
		PersonDTO personDTO = null;
		personDTO = new PersonDTO(p.getFirstName(), p.getName(),
				p.getUserName(), p.getPassword(), p.getEmail());
		personDTO.setId(p.getId());
		personDTO.setActif(p.isActif());
		return personDTO;
	}

	public List<PersonDTO> allObjectToDTO(List<Person> listPs) {
		if (listPs == null)
			return null;
		List<PersonDTO> list = new ArrayList<PersonDTO>();
		for (Person p : listPs) {
			list.add(mapObjectToDTO(p));
		}
		return list;

	}

	public List<Person> allDTOToObject(List<PersonDTO> listPs) {
		if (listPs == null)
			return null;
		List<Person> list = new ArrayList<Person>();
		for (PersonDTO p : listPs) {
			list.add(mapDTOToObject(p));
		}
		return list;

	}

}
