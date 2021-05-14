package be.hogent.startkicker.persistence.jpa.mapper;

import java.util.ArrayList;
import java.util.List;

import be.hogent.startkicker.business.User;
import be.hogent.startkicker.persistence.jpa.entities.UserEntity;

public class UserMapper {

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
