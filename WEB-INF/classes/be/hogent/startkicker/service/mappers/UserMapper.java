package be.hogent.startkicker.service.mappers;

import java.util.ArrayList;
import java.util.List;

import be.hogent.startkicker.business.User;
import be.hogent.startkicker.service.dto.UserDTO;

/**
 * Map a User Object to User DTO and the other way around
 */
public class UserMapper implements IMapper<User, UserDTO> {

	/**
	 * Map a UserDTO to User Objects
	 * @param pDTO UserDTO that needs to be mapped
	 * @return User Object
	 */
	public User mapDTOToObject(UserDTO pDTO) {
		if (pDTO == null) {
			return null;
		}
		User user = null;
		user = new User(pDTO.getFirstName(), pDTO.getName(),
				pDTO.getUserName(), pDTO.getPassword(), pDTO.getEmail());
		user.setId(pDTO.getId());
		user.setActif(pDTO.isActif());
		user.setAdmin(pDTO.isAdmin());
		return user;
	}

	/**
	 * Map a User Object to a User DTO
	 * @param p User Object that needs to be mapped
	 * @return The User DTO
	 */
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

	/**
	 * Map a List of User Objects to List of UserDTOs
	 * Uses the mapObjectToDTO method
	 * @param listPs List of User Objects that need mapping
	 * @return List of User DTOs
	 */
	public List<UserDTO> allObjectToDTO(List<User> listPs) {
		if (listPs == null)
			return null;
		List<UserDTO> list = new ArrayList<UserDTO>();
		for (User p : listPs) {
			list.add(mapObjectToDTO(p));
		}
		return list;

	}

	/**
	 * Map a List of UserDTOs to a List of User Objects
	 * Uses the mapDTOToObject method
	 * @param listPs List of UserDTOs that need to be mapped
	 * @return List of User Objects
	 */
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
