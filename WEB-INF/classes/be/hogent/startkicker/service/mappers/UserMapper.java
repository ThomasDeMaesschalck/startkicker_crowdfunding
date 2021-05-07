package be.hogent.startkicker.service.mappers;

import java.util.ArrayList;
import java.util.List;

import be.hogent.startkicker.business.User;
import be.hogent.startkicker.service.dto.UserDTO;

public class UserMapper implements IMapper<User, UserDTO> {

	public User mapDTOToObject(UserDTO pDTO) {
		if (pDTO == null) {
			return null;
		}
		User user = null;
		user = new User(pDTO.getFirstName(), pDTO.getName(),
				pDTO.getUserName(), pDTO.getPassword(), pDTO.getEmail());
		user.setId(pDTO.getId());
		user.setActif(pDTO.isActif());
		return user;
	}

	public UserDTO mapObjectToDTO(User p) {
		if (p == null) {
			return null;
		}
		UserDTO userDTO = null;
		userDTO = new UserDTO(p.getFirstName(), p.getName(),
				p.getUserName(), p.getPassword(), p.getEmail());
		userDTO.setId(p.getId());
		userDTO.setActif(p.isActif());
		userDTO.setAdmin(p.isAdmin());
		return userDTO;
	}

	public List<UserDTO> allObjectToDTO(List<User> listPs) {
		if (listPs == null)
			return null;
		List<UserDTO> list = new ArrayList<UserDTO>();
		for (User p : listPs) {
			list.add(mapObjectToDTO(p));
		}
		return list;

	}

	public List<User> allDTOToObject(List<UserDTO> listPs) {
		if (listPs == null)
			return null;
		List<User> list = new ArrayList<User>();
		for (UserDTO p : listPs) {
			list.add(mapDTOToObject(p));
		}
		return list;

	}

}
