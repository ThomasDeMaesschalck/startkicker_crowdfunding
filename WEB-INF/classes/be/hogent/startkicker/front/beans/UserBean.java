package be.hogent.startkicker.front.beans;

import be.hogent.startkicker.service.UserService;
import be.hogent.startkicker.service.dto.UserDTO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigDecimal;

@ManagedBean(name = "userBean", eager = true)
@RequestScoped
public class UserBean implements Serializable {

    private UserDTO userToSave = new UserDTO();
    protected BigDecimal totalFunded;


    public UserBean() {
    }

    public UserDTO getUserToSave() {
        return userToSave;
    }

    public void setUserToSave(UserDTO userToSave) {
        this.userToSave = userToSave;
    }

    private UserDTO selectedUser = new UserDTO();

    public UserDTO getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDTO selectedUser) {
        this.selectedUser = selectedUser;
    }

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

    public String activateUser(long userId) {
        String pathToFollow = null;
        UserService.getInstance().switchUserActive(userId);
        return pathToFollow;
    }

    public String deleteUser(UserDTO userDTO) {
        String pathToFollow = null;
        UserService.getInstance().deleteUser(userDTO);
        return pathToFollow;
    }

    public BigDecimal getTotalFunded(){
        UserDTO thisUser =  (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
        setTotalFunded(UserService.getInstance().userTotalFunded(thisUser));
        return 	totalFunded;
    }

    public void setTotalFunded(BigDecimal totalFunded) {
        this.totalFunded = totalFunded;
    }
}
