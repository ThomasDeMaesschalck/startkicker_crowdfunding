package be.hogent.startkicker.front.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import be.hogent.startkicker.service.dto.PersonDTO;

@ManagedBean(name = "myAppWideBean", eager = true)
@ApplicationScoped
public class AppBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<PersonDTO> allUsers;

	public AppBean() {
		System.out.println("AppBean -- constructor");
	}

	public List<PersonDTO> getAllUsers() {
		return Collections.unmodifiableList(allUsers);
	}

	public void setAllUsers(List<PersonDTO> allUsers) {
		System.out.println("AppBean -- setting allUsers");
		this.allUsers = allUsers;
	}
}
