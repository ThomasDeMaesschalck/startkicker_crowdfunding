package be.hogent.startkicker.service;

import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.business.ProjectStatus;
import be.hogent.startkicker.business.repositories.IProjectRepo;
import be.hogent.startkicker.business.repositories.ProjectJPARepo;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.dto.UserDTO;
import be.hogent.startkicker.service.mappers.IMapper;
import be.hogent.startkicker.service.mappers.ProjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProjectService communicaties with the frontend
 */
public class ProjectService {

    /**
     * Repository of Projects
     */
    private IProjectRepo projectRepo;

    /**
     * Mapper to map Project to ProjectDTO and the other way around
     */
    private IMapper<Project, ProjectDTO> projectMapper;

    /**
     * The ProjectService instance
     */
        private static ProjectService instance;

    /**
     * Singleton pattern
     * @return Returns the ProjectService instance
     */
        public static ProjectService getInstance() {
            if (instance == null) {
                instance = new ProjectService();
            }
            return instance;
        }

    /**
     * Private constructor for the singleton pattern
     */

    private ProjectService() {
            projectRepo = new ProjectJPARepo();
            projectMapper = new ProjectMapper();
        }

    /**
     * Try to save a project to the database
     * @param pDTO ProjectDTO that needs to be persisted
     * @return String with success or error code
     */
    public String saveProject(ProjectDTO pDTO) {
            return projectRepo.saveProject(projectMapper.mapDTOToObject(pDTO));
        }

    /**
     * Try to delete a project from the database
     * @param pDTO ProjectDTO that needs to be deleted from DB
     * @return String with success or error code
     */
    public String deleteProject(ProjectDTO pDTO)
        {
            return projectRepo.deleteProject(projectMapper.mapDTOToObject(pDTO));
        }


    /**
     * Get a list of all Projects that have been persisted.
    * Iterates over all projects and uses adjustProjectAndFundingStatus method to set the correct Project Status and funding details
     * @return List of all ProjectDTO
     */
    public List<ProjectDTO> getAllProjects() {
            List<ProjectDTO> allProjects = projectMapper.allObjectToDTO(projectRepo.getAllProjects());
            for (ProjectDTO p: allProjects) {
                adjustProjectAndFundingStatus(p, null);
            }
            return allProjects;
        }

    /**
     This method will call the funded method to add the amount that has been funded to each ProjectDTO
     * Set ProjectStatus.Created to Active in case project start date is before LocalDate.now()
     * If the project end date is before LocalDate.now, the ProjectDTO projectEndDateReached will be set to true to disable funding in the frontend.
     * @param p The ProjectDTO that needs project and funding status adjusted
     * @return The adjusted ProjectDTO
     */
    public ProjectDTO adjustProjectAndFundingStatus(ProjectDTO p, UserDTO user) {
        BigDecimal funded = funded(p);
        p.setFunded(funded);
        if (user != null) {
            for (FundingDTO f : p.getFunding()) {
                if (f.getUser().getId() == user.getId()) {
                    p.setUserHasFunded(true);
                }
            }
            if (p.getStartDate().isBefore(LocalDate.now()) && p.getStatus().equals(ProjectStatus.Created)) {
                switchProjectStatus(p, ProjectStatus.Active);
            }
            if (p.getEndDate().isBefore(LocalDate.now()) || p.getEndDate().equals(LocalDate.now())) {
                switchFundingOff(p);
            }
        }
            return p;
        }


    /**
     * Retrieve a list of all persisted projects with some extra information for the logged in user.
     * Compared with the regular getAllProjects(), this method will add whether a user has already funded a project to prevent the double funding of a project.
     * @param user The logged in user
     * @return Returns a list of Project DTOs
     */
    public List<ProjectDTO> getAllProjects(UserDTO user) {
        List<ProjectDTO> allProjectsWithUserInfo = projectMapper.allObjectToDTO(projectRepo.getAllProjects());

        for (ProjectDTO p: allProjectsWithUserInfo) {
           adjustProjectAndFundingStatus(p, user);
        }
        return allProjectsWithUserInfo;
    }

    /**
     * Calculate the total amount of funding a project has received from project backers
     * @param pDTO Project that needs funds calculation
     * @return BigDecimal of total amount of funding project has received
     */
    public BigDecimal funded(ProjectDTO pDTO) {
        BigDecimal amount;
        amount = pDTO.getFunding().stream().map(FundingDTO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount;
    }

    /**
     * Makes a list of all projects that have hit the project end date and need an admin review to adjust the status code to Successful or Fail
     * @return List of all projects that need administrative review
     */
    public List<ProjectDTO> getAllEndedButNotFinalizedProjects()
        {
            List<ProjectDTO> allProjects = getAllProjects();
            List<ProjectDTO> allEndedButNotFinalizedProjects = allProjects.stream().filter(o -> o.isProjectEndDateReached()).filter(o -> o.getStatus() == ProjectStatus.Active).collect(Collectors.toList());
            return  allEndedButNotFinalizedProjects;
        }

    /**
     * Retrieve a list of all the Projects that have received funding from the logged in user.
     * @param user The logged in user
     * @return A List of ProjectDTOs
     */
    public List<ProjectDTO> getAllProjectsFundedByUser(UserDTO user)
    {
        List<ProjectDTO> listOfAllProjectsFunded = getAllProjects(user).stream().filter(p -> p.isUserHasFunded()).collect(Collectors.toList());
        return listOfAllProjectsFunded;
    }

    /**
     * Retrieve a list of all the Projects that have been created by the logged in user.
     * @param user The logged in user.
     * @return List of ProjectDTOs created by the user
     */

    public List<ProjectDTO> getAllProjectsCreatedByUser(UserDTO user)
    {
        List<ProjectDTO> listOfCreatedProjects = getAllProjects(user).stream().filter(p -> p.getCreator().getId() == user.getId()).collect(Collectors.toList());
        return listOfCreatedProjects;
    }

    /**
     * Calculates a percentage of how much of the funding target of a project has already been funded by backers.
     * If the project is overfunded, the percentage remains 100 percent in order not to break UI components
     * @param pDTO ProjectDTO that needs calculation
     * @return An int value that represents the percentage of the funding target that has been achieved
     */
    public int getPercentageFunded(ProjectDTO pDTO)
    {

        BigDecimal ONE_HUNDRED = new BigDecimal(100);
        BigDecimal calculation = pDTO.getFunded().multiply(ONE_HUNDRED).divide(pDTO.getFundingTarget(), 0, RoundingMode.HALF_UP);
        int percent = calculation.toBigInteger().intValueExact();
        if (percent > 100)
        {
            percent = 100;
        }
        return percent;
    }

    /**
     * Method to switch a status of a Project to a different ProjectStatus value.
     * @param project Project that needs status update
     * @param status The new project status
     */
    public void switchProjectStatus(ProjectDTO project, ProjectStatus status) {
        ProjectDTO p = projectMapper.mapObjectToDTO(projectRepo.getProject(project.getId()));
        p.setStatus(status);
        projectRepo.saveProject(projectMapper.mapDTOToObject(p));
    }

    /**
     * Method that allows project creator to end the project immediately.
     * The project end date gets adjusted to LocalDate.now()
     * @param project The project that needs to be ended
     */
    public void setProjectEndDateToNow(ProjectDTO project) {
        ProjectDTO p = projectMapper.mapObjectToDTO(projectRepo.getProject(project.getId()));
        p.setEndDate(LocalDate.now());
        projectRepo.saveProject(projectMapper.mapDTOToObject(p));
    }

    /**
     * Called when project has hit its end date. Sets boolean projectEndDateReached to true.
     * Used by frontend to make it impossible to fund a project.
     * @param project The project that needs funding switched to off
     */
    public void switchFundingOff(ProjectDTO project) {
        project.setProjectEndDateReached(true);
    }

}
