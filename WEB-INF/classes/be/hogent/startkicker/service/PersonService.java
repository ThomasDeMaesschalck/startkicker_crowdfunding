package be.hogent.startkicker.service;

import java.util.List;

import be.hogent.startkicker.business.Person;
import be.hogent.startkicker.business.repositories.IPersonRepo;
import be.hogent.startkicker.business.repositories.PersonJPARepo;
import be.hogent.startkicker.service.dto.PersonDTO;
import be.hogent.startkicker.service.mappers.IMapper;
import be.hogent.startkicker.service.mappers.PersonMapper;

public class PersonService {

	private IPersonRepo personRepo;
	private IMapper<Person, PersonDTO> personMapper;
	
	private static PersonService instance;

	

	public static PersonService getInstance() {
		if (instance == null) {
			instance = new PersonService();
		}
		return instance;
	}

	private PersonService() {
		personRepo = new PersonJPARepo();
		personMapper = new PersonMapper();
	}

	public String savePerson(PersonDTO pDTO) {
		return personRepo.savePerson(personMapper.mapDTOToObject(pDTO));
	}

	public List<PersonDTO> getAllPersons() {
		return personMapper.allObjectToDTO(personRepo.getAllPersons());

	}

	public void switchPersonActif(long idPerson) {
		PersonDTO p = personMapper.mapObjectToDTO(personRepo.getPerson(idPerson));
		p.setActif(!p.isActif());
		personRepo.savePerson(personMapper.mapDTOToObject(p));

	}

}
