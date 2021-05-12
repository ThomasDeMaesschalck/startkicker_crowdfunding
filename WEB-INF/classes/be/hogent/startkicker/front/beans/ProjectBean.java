package be.hogent.startkicker.front.beans;


import be.hogent.startkicker.business.ProjectStatus;
import be.hogent.startkicker.business.User;
import be.hogent.startkicker.service.ProjectService;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.dto.UserDTO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

@ManagedBean(name = "projectBean", eager = true)
@SessionScoped
public class ProjectBean implements Serializable {

    private ProjectDTO projectToSave = new ProjectDTO();
    private ProjectDTO selectedProject = new ProjectDTO();
    private LocalDate today;
    private LocalDate minEndDate;
    private int statusInt;
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public ProjectBean() {
    }


    public String makeAProject() {
        String pathToFollow = null;

        UserDTO creator =  (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
        projectToSave.setCreator(creator);
        projectToSave.setStatus(ProjectStatus.Created);

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

    public int getPercentageFunded()
    {
      BigDecimal calculation = selectedProject.getFunded().multiply(ONE_HUNDRED).divide(selectedProject.getFundingTarget(), 2, RoundingMode.HALF_UP);
      int percent = calculation.toBigInteger().intValueExact();
      return percent;
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


    public LocalDate getMinEditEndDate() {
        try {
            LocalDate endDate = selectedProject.getEndDate();

            if (endDate.isBefore(selectedProject.getStartDate()) || endDate.isEqual(selectedProject.getStartDate()))
            {
                selectedProject.setEndDate(selectedProject.getStartDate().plusDays(1));
            }
             return selectedProject.getStartDate().plusDays(1);

        }
        catch (Exception e)
        {
            return getToday().plusDays(1);
        }
    }

    public void setMinEditEndDate(LocalDate minEndDate) {
        this.minEndDate = minEndDate;
    }

    public int getEnumIntPosition(ProjectStatus status)
    {
        statusInt = 0;
for (ProjectStatus s : ProjectStatus.values())
        {
            if (s == status)
            {
                statusInt = s.ordinal();
            }
        }
        return statusInt;
    }

    public int getStatusInt() {
        return statusInt;
    }
}
