package be.hogent.startkicker.front.beans;

import be.hogent.startkicker.service.UserService;
import be.hogent.startkicker.service.dto.UserDTO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Frontend bean used for user registration and administrative management of users.
 * Known in the view as userBean
 */
@ManagedBean(name = "userBean", eager = true)
@RequestScoped
public class UserBean implements Serializable {

    private UserDTO userToSave = new UserDTO();
    private UserDTO selectedUser = new UserDTO();


    /**
     * BigDecimal that stores the total amount of money a logged in user has committed to projects
     */
    protected BigDecimal totalFunded;

    /**
     * Constructor for the UserBean
     */
    public UserBean() {
    }

    /**
     * Method to make a new user. Can be used by a non-logged in user or by an administrator.
     * @param admin If set to true, JSF redirects to the admin page instead of index page.
     * @return A String used by JSF for navigation.
     */
    public String register(boolean admin) {

        String pathToFollow = null;
        String outcome = UserService.getInstance().saveUser(userToSave);

        if (outcome == "success") {
            if (admin)
            {
                pathToFollow = "admin.jsf?faces-redirect=true";
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account created. It will be activated after approval by an administrator.", outcome));
            }
            return pathToFollow;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", outcome));
        }
        return pathToFollow;
    }


    /**
     * Edit details of selected user.
     * @return A String used by JSF for navigation
     */
    public String userEdit() {
        String pathToFollow = null;
        String outcome = UserService.getInstance().saveUser(selectedUser);
        if (outcome == "success") {
            pathToFollow = "admin.jsf?faces-redirect=true";
            return pathToFollow;
        }

         else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", outcome));
        }
        return pathToFollow;
    }

    /**
     * A user account is inactive until activated by an admin. This method sets a user status to active.
     * @param userId Long value of the user id of the User that needs to be activated
     * @return String used by JSF for navigation.
     */
    public String activateUser(long userId) {
        String pathToFollow = null;
        UserService.getInstance().switchUserActive(userId);
        return pathToFollow;
    }

    /**
     * Delete an existing user from the DB.
     * @param userDTO The user that needs to be deleted
     * @return String used by JSF for navigation
     */
    public String deleteUser(UserDTO userDTO) {
        String pathToFollow = null;
        UserService.getInstance().deleteUser(userDTO);
        return pathToFollow;
    }

    /**
     * Retrieve the total amount of money a logged in user has pledged to crowdfunding projects
     * @return BigDecimal of the total amount a user has pledged to projects
     */
    public BigDecimal getTotalFunded(){
        UserDTO thisUser =  (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
        setTotalFunded(UserService.getInstance().userTotalFunded(thisUser));
        return 	totalFunded;
    }

    public void setTotalFunded(BigDecimal totalFunded) {
        this.totalFunded = totalFunded;
    }

    public UserDTO getUserToSave() {
        return userToSave;
    }

    public void setUserToSave(UserDTO userToSave) {
        this.userToSave = userToSave;
    }

    public UserDTO getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDTO selectedUser) {
        this.selectedUser = selectedUser;
    }
}
