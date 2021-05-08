package be.hogent.startkicker.front.beans;

import be.hogent.startkicker.service.UserService;
import be.hogent.startkicker.service.dto.UserDTO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name = "userBean", eager = true)
@RequestScoped
public class UserBean implements Serializable {

    private UserDTO userToSave = new UserDTO();

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
        String outcome = UserService.getInstance().savePerson(userToSave);

        if (outcome == "success") {
            if (admin)
            {
                pathToFollow = "admin.jsf?faces-redirect=true";
            }
            else {
                pathToFollow = "index.jsf?faces-redirect=true";
            }
            return pathToFollow;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", outcome));
        }
        return pathToFollow;
    }


    public String userEdit() {
        String pathToFollow = null;
        String outcome = UserService.getInstance().savePerson(selectedUser);
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
        UserService.getInstance().switchPersonActif(userId);
        return pathToFollow;
    }

    public String deleteUser(UserDTO userDTO) {
        String pathToFollow = null;
        UserService.getInstance().deleteUser(userDTO);
        return pathToFollow;
    }

}