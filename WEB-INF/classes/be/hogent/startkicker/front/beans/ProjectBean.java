package be.hogent.startkicker.front.beans;


import be.hogent.startkicker.business.User;
import be.hogent.startkicker.service.ProjectService;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.dto.UserDTO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Map;

@ManagedBean(name = "projectBean", eager = true)
@RequestScoped
public class ProjectBean implements Serializable {

    private ProjectDTO projectToSave = new ProjectDTO();
    private ProjectDTO selectedProject = new ProjectDTO();
    private LocalDate today;
    private LocalDate minEndDate;

    public ProjectBean() {
    }


    public String makeAProject() {
        String pathToFollow = null;

        UserDTO creator =  (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
        projectToSave.setCreator(creator);

        String outcome = ProjectService.getInstance().saveProject(projectToSave);
        if (outcome == "success") {
                         pathToFollow = "index.jsf?faces-redirect=true";
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", outcome));
        }
        return pathToFollow;
    }


    public String projectEdit() {
        String pathToFollow = null;
        String outcome = ProjectService.getInstance().saveProject(selectedProject);
        if (outcome == "success") {
            pathToFollow = "index.jsf?faces-redirect=true";
            return pathToFollow;
        }

        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", outcome));
        }
        return pathToFollow;
    }

    public String deleteProject(ProjectDTO projectDTO) {
        String pathToFollow = null;
        ProjectService.getInstance().deleteProject(projectDTO);
        return pathToFollow;
    }

    public String view(){
        return "project";
    }

    public ProjectDTO getProjectToSave() {
        return projectToSave;
    }

    public void setProjectToSave(ProjectDTO projectToSave) {
        this.projectToSave = projectToSave;
    }

    public ProjectDTO getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(ProjectDTO selectedProject) {
        this.selectedProject = selectedProject;
    }

    public LocalDate getToday(){
        return LocalDate.now();
    }

    public void setToday(LocalDate today){
        this.today = today;
    }

    public LocalDate getMinEndDate() {
      try {
          return projectToSave.getStartDate().plusDays(1);
      }
      catch (Exception e)
      {
          return getToday().plusDays(1);
      }
    }

    public void setMinEndDate(LocalDate minEndDate) {
        this.minEndDate = minEndDate;
    }

}
