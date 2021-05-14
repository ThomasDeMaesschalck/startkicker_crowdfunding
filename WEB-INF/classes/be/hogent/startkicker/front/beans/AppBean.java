package be.hogent.startkicker.front.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import be.hogent.startkicker.service.ProjectService;
import be.hogent.startkicker.service.UserService;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.dto.UserDTO;

/**
 * Frontend bean that retrieves all users and projects stored in the database. Known in view as myAppWideBean
 */
@ManagedBean(name = "myAppWideBean", eager = true)
@ApplicationScoped
public class AppBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UserDTO> allUsers;
	private List<ProjectDTO> allProjects;

	/**
	 * Constructor of the AppBean.
	 */
	public AppBean() {
	}

	/**
	 * Get all users stored in the database.
	 * @return Returns a list of type UserDTO that contains all user details.
	 */
	public List<UserDTO> getAllUsers() {
		List<UserDTO> allUsers = UserService.getInstance().getAllUsers();
		ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession currentSession = (HttpSession) ctx.getSession(true);
		AppBean appBean = (AppBean) currentSession.getServletContext().getAttribute("myAppWideBean");
		appBean.setAllUsers(allUsers);
		return Collections.unmodifiableList(allUsers);
	}

	/**
	 * Setter for all user list.
	 * @param allUsers List containing UserDTO objects.
	 */
	public void setAllUsers(List<UserDTO> allUsers) {
		this.allUsers = allUsers;
	}

	/**
	 * Get a list of all projects stored in the database.
	 * If the user is logged in, the method will also retrieve which projects have already been funded by the logged in user
	 * @return Returns list of all requested projects, type is UserDTO.
	 */
	public List<ProjectDTO> getAllProjects() {

		ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession currentSession = (HttpSession) ctx.getSession(true);
		AppBean appBean = (AppBean) currentSession.getServletContext().getAttribute("myAppWideBean");
		UserDTO thisUser =  (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");

		List<ProjectDTO> allProjects;
		if (thisUser == null)
		{
			allProjects = ProjectService.getInstance().getAllProjects();
		}
		else
		{
			allProjects = ProjectService.getInstance().getAllProjects(thisUser);
		}

		appBean.setAllProjects(allProjects);
		return Collections.unmodifiableList(allProjects);
	}

	/**
	 * Setter for all projects list
	 * @param allProjects Returns list containing ProjectDTO objects.
	 */
	public void setAllProjects(List<ProjectDTO> allProjects) {
		this.allProjects = allProjects;
	}

}
