package be.hogent.startkicker.front.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import be.hogent.startkicker.service.dto.UserDTO;
import be.hogent.startkicker.service.LoginService;
import be.hogent.startkicker.service.UserService;

@ManagedBean(name = "loginBean", eager = true)
@RequestScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6955508471291131930L;
	private String userName, password, errorMsg;

	public LoginBean() {
		// TODO remove this in production as you expose Admin credentials :)
		userName = "ThomasDM";
		password = "AbC123"; //overkill, as <h:inputSecret> does not pre-populate the field
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) { this.errorMsg = errorMsg;	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		String pathToFollow = null;
		UserDTO foundPerson = LoginService.getInstance().doLogin(userName, password);
		if (foundPerson == null) {
			//be carefull with "exposing" error messages that are too detailed
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unknown Username/Password"));
		} else {
			ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
			HttpSession currentSession = (HttpSession) ctx.getSession(true);
			currentSession.setAttribute("loggedInUser", foundPerson);
			AppBean appBean = (AppBean) currentSession.getServletContext().getAttribute("myAppWideBean");
			System.out.println("appBean.getAllUsers().size() -- "+appBean.getAllUsers().size());
			currentSession.getServletContext().setAttribute("myAppWideBean",appBean);
			pathToFollow = "index.jsf";
		}
		return pathToFollow;
	}

}
