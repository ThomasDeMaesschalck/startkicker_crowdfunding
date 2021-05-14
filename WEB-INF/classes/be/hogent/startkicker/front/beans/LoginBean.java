package be.hogent.startkicker.front.beans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import be.hogent.startkicker.service.dto.UserDTO;
import be.hogent.startkicker.service.LoginService;

/**
 * Frontend bean with login functionality. Known in view as loginBean.
 */
@ManagedBean(name = "loginBean", eager = true)
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 6955508471291131930L;
	private String userName, password;

	/**
	 * Constructor for the login bean
	 */
	public LoginBean() {
	}

	/**
	 * A user logs into the application using a username and password.
	 * If successful, the backend returns a UserDTO object that gets stored in the session.
	 * @return String value used in JSF to redirect the user to the correct page.
	 */
	public String login() {
		String pathToFollow = null;
		UserDTO foundUser = LoginService.getInstance().doLogin(userName, password);
		if (foundUser == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unknown Username/Password. Or User not actived yet."));
		} else {
			ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
			HttpSession currentSession = (HttpSession) ctx.getSession(true);
			currentSession.setAttribute("loggedInUser", foundUser);
			AppBean appBean = (AppBean) currentSession.getServletContext().getAttribute("myAppWideBean");
			currentSession.getServletContext().setAttribute("myAppWideBean",appBean);
			pathToFollow = "index.jsf?faces-redirect=true";
		}
		return pathToFollow;
	}

	/**
	 * Invalidate the current session, which logs out the user.
	 * @return String value used by JSF to redirect the user to the correct page.
	 */
	public String logout(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(false);
		httpSession.invalidate();
		String pathToFollow = "index.jsf?faces-redirect=true";
		return pathToFollow;
	}

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


}
