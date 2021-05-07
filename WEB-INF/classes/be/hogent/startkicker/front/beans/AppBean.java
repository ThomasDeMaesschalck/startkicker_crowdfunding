package be.hogent.startkicker.front.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import be.hogent.startkicker.service.UserService;
import be.hogent.startkicker.service.dto.UserDTO;

@ManagedBean(name = "myAppWideBean", eager = true)
@ApplicationScoped
public class AppBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UserDTO> allUsers;

	public AppBean() {
		System.out.println("AppBean -- constructor");
	}

	public List<UserDTO> getAllUsers() {

		List<UserDTO> allPersons = UserService.getInstance().getAllPersons();
		ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession currentSession = (HttpSession) ctx.getSession(true);
		AppBean appBean = (AppBean) currentSession.getServletContext().getAttribute("myAppWideBean");
		appBean.setAllUsers(allPersons);

		return Collections.unmodifiableList(allUsers);
	}

	public void setAllUsers(List<UserDTO> allUsers) {
		System.out.println("AppBean -- setting allUsers");
		this.allUsers = allUsers;
	}

}
