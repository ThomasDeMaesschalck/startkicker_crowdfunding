package be.hogent.startkicker.persistence.jpa.mapper;

import java.util.ArrayList;
import java.util.List;

import be.hogent.startkicker.business.User;
import be.hogent.startkicker.persistence.jpa.entities.UserEntity;

/**
 * Mapper to convert User Entity to Object and the other way around
 */
public class UserMapper {

	/**
	 * Map User Entity to Object
	 * @param pEntity User Entity that needs to be mapped
	 * @return Mapped User Object
	 */
	public User mapEntityToObject(UserEntity pEntity) {
		if (pEntity == null) {
			return null;
		}
		User user = null;
		user = new User(pEntity.getFirstName(), pEntity.getName(), pEntity.getUserName(), pEntity.getPassword(),
				pEntity.getEmail());
		user.setId(pEntity.getId());
		user.setActif(pEntity.isActif());
		user.setAdmin(pEntity.isAdmin());
		return user;
	}

	/**
	 * Map User Object to Entity
	 * @param p User Object that needs to be mapped
	 * @return The mapped User Entity
	 */
	public UserEntity mapObjectToEntity(User p) {
		if (p == null) {
			return null;
		}
		UserEntity userEntity = null;
		userEntity = new UserEntity(p.getFirstName(), p.getName(), p.getUserName(), p.getPassword(), p.getEmail());
		userEntity.setId(p.getId());
		userEntity.setActif(p.isActif());
		userEntity.setAdmin(p.isAdmin());
		return userEntity;
	}

	/**
	 * Map a list of User Entities to User Objects
	 * Uses mapEntityToObject method
	 * @param listPs List of User Entities that needs mapping
	 * @return List of mapped User Objects
	 */
	public List<User> allEntityToObject(List<UserEntity> listPs) {
		if (listPs == null)
			return null;
		List<User> list = new ArrayList<User>();
		for (UserEntity p : listPs) {
			list.add(mapEntityToObject(p));
		}
		return list;

	}

}
