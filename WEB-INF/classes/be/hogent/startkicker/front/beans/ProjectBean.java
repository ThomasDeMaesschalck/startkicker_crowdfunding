package be.hogent.startkicker.front.beans;

import be.hogent.startkicker.business.ProjectStatus;
import be.hogent.startkicker.service.FundingService;
import be.hogent.startkicker.service.ProjectService;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.dto.UserDTO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Frontend bean for project functionality. Known in view as projectBean.
 */
@ManagedBean(name = "projectBean", eager = true)
@SessionScoped
public class ProjectBean implements Serializable {

    private ProjectDTO projectToSave = new ProjectDTO();
    private ProjectDTO selectedProject = new ProjectDTO();
    private FundingDTO fundingToSave = new FundingDTO();
    private LocalDate today;
    private LocalDate minEndDate;
    private int statusInt;
    private BigDecimal fundingAmount;

    /**
     * List of ProjectDTOs created by the logged in user
     */
    private List<ProjectDTO> projectsCreatedByUser;

    /**
     * List of ProjectDTOs that have received funding from the logged in user
     */
    private List<ProjectDTO> projectsFundedByUser;

    /**
     * List for admin panel, containing ProjectDTOs that have hit the project end date and that need administrative review.
     */
    private List<ProjectDTO> projectsEndedButNotFinalized;

    /**
     * Constructor for the ProjectBean
     */
    public ProjectBean() {
    }

    /**
     * Allows a logged in user to create a new project.
     * Project is send to the backend, which tries to persist it into the DB.
     * Project status is set to Created
     * Project creator is set to the logged in user.
     * @return Returns String used by JSF to redirect to correct page
     */
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

    /**
     * Method for updating project details in the database
     * @return Returns String used by JSF to redirect to correct page
     */
    public String projectEdit() {
        String pathToFollow = null;
        String outcome = ProjectService.getInstance().saveProject(selectedProject);
        if (outcome == "success") {
            pathToFollow = "myprojects.jsf?faces-redirect=true";
            return pathToFollow;
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", outcome));
        }
        return pathToFollow;
    }

    /**
     * Delete a project from the DB.
     * @param projectDTO The project that needs to be deleted
     * @return Returns String used by JSF to redirect to correct page
     */
    public String deleteProject(ProjectDTO projectDTO) {
        String pathToFollow = null;
        ProjectService.getInstance().deleteProject(projectDTO);
        return pathToFollow;
    }

    /**
     * Method that allows a logged in user to pledge funds to an active project.
     * @param user The logged in user
     * @param amount How much money the user is pledging
     * @return Returns String used by JSF to redirect to correct page
     */
    public String fundProject(UserDTO user, BigDecimal amount) {
        String pathToFollow = null;
        fundingToSave.setProject(selectedProject);
        fundingToSave.setUser(user);
        fundingToSave.setAmount(amount);
    String outcome = FundingService.getInstance().saveFunding(fundingToSave);
        if (outcome == "success") {
        pathToFollow = "index.jsf?faces-redirect=true";
        return pathToFollow;
    }
        else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Funding could not be added", outcome));
    }
        return pathToFollow;
}

    /**
     * Redirect the viewer to the project page. Shows the selected project.
     * @return String with value project to go to the project page.
     */
    public String view(){
        return "project";
    }


    /**
     * Get today's date.
     * @return LocalDate with value today.
     */
    public LocalDate getToday(){
        return LocalDate.now();
    }

    /**
     * Setter to set today's day
     * @param today is today's date
     */

    public void setToday(LocalDate today){
        this.today = today;
    }


    /**
     * Retrieve a percentage of how much of the project's funding target has been achieved
     * @return Int value that indicates how much of the selected project's funding targte has been achieved
     */
    public int getPercentageFunded()
    {
      int percent = ProjectService.getInstance().getPercentageFunded(selectedProject);
      return percent;
    }

    /**
     * Used by the view to set a minimum date for the Primefaces Datepicker for the project end date.
     * Dynamically changes if the project start date is changed.
     * If no start date is set it returns today's date plus one day
     * @return LocalDate used as minimum date value by Primefaces Datepicker
     */
    public LocalDate getMinEndDate() {
      try {
          return projectToSave.getStartDate().plusDays(1);
      }
      catch (Exception e)
      {
          return getToday().plusDays(1);
      }
    }

    /**
     * Setter for the minimum date used by the Primefaces Datepicker
     * Used by end date for project
     * @param minEndDate LocalDate that specifies the minimum allowed end date
     */
    public void setMinEndDate(LocalDate minEndDate) {
        this.minEndDate = minEndDate;
    }

    /**
     * Used by the view to set a minimum date for the Primefaces Datepicker for the project end date.
     * Dynamically changes if the project start date is changed.
     * This method is used in the view to edit an existing project.
     *  * @return LocalDate used as minimum date value by Primefaces Datepicker
     */
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

    /**
     *  Setter for the minimum date used by the Primefaces Datepicker
     *  Used in edit project view
     * @param minEndDate The minimum allowed end date
     */
    public void setMinEditEndDate(LocalDate minEndDate) {
        this.minEndDate = minEndDate;
    }

    /**
     * Returns int value that represents the position of the ProjectStatus value in the enumeration.
     * Used to simplify expression language in the view
     * @param status Is the status code of the selected project
     * @return Returns int value that represents position in the ProjectStatus enumeration
     */
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

    /**
     * Retrieve a ProjectDTO list of all projects that were created by the logged in user
     * @return A List of ProjectDTO objects that were created by the logged in user
     */

    public List<ProjectDTO> getProjectsCreatedByUser() {
        UserDTO thisUser =  (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
        projectsCreatedByUser = ProjectService.getInstance().getAllProjectsCreatedByUser(thisUser);

        return Collections.unmodifiableList(projectsCreatedByUser);
    }

    /**
     * Sets list of projects created by logged in user
     * @param projectsCreatedByUser List of ProjectDTOs created by logged in user
     */
    public void setProjectsCreatedByUser(List<ProjectDTO> projectsCreatedByUser) {
        this.projectsCreatedByUser = projectsCreatedByUser;
    }

    /**
     * Get a list of all the ProjectDTOs that have received funding from the logged in user
     * @return List of ProjectDTOs funded by the logged in user
     */
    public List<ProjectDTO> getProjectsFundedByUser() {
        UserDTO thisUser =  (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
        projectsFundedByUser = ProjectService.getInstance().getAllProjectsFundedByUser(thisUser);

        return Collections.unmodifiableList(projectsFundedByUser);
    }

    /**
     * Setter for the projects funded by user list
     * @param projectsFundedByUser List of all projects that received funding from the logged in user
     */
    public void setProjectsFundedByUser(List<ProjectDTO> projectsFundedByUser) {
        this.projectsFundedByUser = projectsFundedByUser;
    }

    /**
     *  Get a list of all the projects that have hit the projects end date and that have not been closed yet by the administrator. Admin needs to manually change project status code.
     * @return List of ProjectDTOs that need admin review
     */
    public List<ProjectDTO> getProjectsEndedButNotFinalized() {
        projectsEndedButNotFinalized = ProjectService.getInstance().getAllEndedButNotFinalizedProjects();
        return Collections.unmodifiableList(projectsEndedButNotFinalized);
    }

    /**
     * Setter for all the ended projects that need admin review
     * @param projectsEndedButNotFinalized List of ProjecDTOs that need admin review
     */
    public void setProjectsEndedButNotFinalized(List<ProjectDTO> projectsEndedButNotFinalized) {
        this.projectsEndedButNotFinalized = projectsEndedButNotFinalized;
    }

    /**
     * Method that allows an administrator to set an ended project to Failed or Succesful
     * @param project Project that needs a status code update
     * @param status Long that represents the new ProjectStatus code
     * @return Returns String used for JSF navigation
     */
    public String switchProjectStatus(ProjectDTO project, long status) {
        String pathToFollow = null;
        if (status == 2) {
            ProjectService.getInstance().switchProjectStatus(project, ProjectStatus.Failed);
        }
        else
        {
            ProjectService.getInstance().switchProjectStatus(project, ProjectStatus.Success);
        }
        return pathToFollow;
    }

    /**
     * Allows project creator to immediately end an active project
     * @param project The project that needs to be ended
     * @return A String used by JSF for navigation
     */
    public String endProjectNow(ProjectDTO project)
    {
        String pathToFollow = null;
        ProjectService.getInstance().setProjectEndDateToNow(project);
        return pathToFollow;
    }

    public int getStatusInt() {
        return statusInt;
    }

    public FundingDTO getFundingToSave() {
        return fundingToSave;
    }

    public void setFundingToSave(FundingDTO fundingToSave) {
        this.fundingToSave = fundingToSave;
    }

    public BigDecimal getFundingAmount() {
        return fundingAmount;
    }

    public void setFundingAmount(BigDecimal fundingAmount) {
        this.fundingAmount = fundingAmount;
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

}
