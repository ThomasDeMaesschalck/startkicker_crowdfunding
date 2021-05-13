package be.hogent.startkicker.service;

import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.business.ProjectStatus;
import be.hogent.startkicker.business.User;
import be.hogent.startkicker.business.repositories.IProjectRepo;
import be.hogent.startkicker.business.repositories.IUserRepo;
import be.hogent.startkicker.business.repositories.ProjectJPARepo;
import be.hogent.startkicker.business.repositories.UserJPARepo;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.dto.UserDTO;
import be.hogent.startkicker.service.mappers.IMapper;
import be.hogent.startkicker.service.mappers.ProjectMapper;
import be.hogent.startkicker.service.mappers.UserMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {
        private IProjectRepo projectRepo;
        private IMapper<Project, ProjectDTO> projectMapper;

        private static ProjectService instance;
        public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);


        public static ProjectService getInstance() {
            if (instance == null) {
                instance = new ProjectService();
            }
            return instance;
        }

        private ProjectService() {
            projectRepo = new ProjectJPARepo();
            projectMapper = new ProjectMapper();
        }

        public String saveProject(ProjectDTO pDTO) {
            return projectRepo.saveProject(projectMapper.mapDTOToObject(pDTO));
        }

        public String deleteProject(ProjectDTO pDTO)
        {
            return projectRepo.deleteProject(projectMapper.mapDTOToObject(pDTO));
        }

        public ProjectDTO getProject(long id)
        {
           return projectMapper.mapObjectToDTO(projectRepo.getProject(id));
        }

        public BigDecimal funded(ProjectDTO pDTO) {
            BigDecimal amount;
                amount = pDTO.getFunding().stream().map(FundingDTO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                return amount;
        }

        public List<ProjectDTO> getAllProjects() {
            List<ProjectDTO> allProjects = projectMapper.allObjectToDTO(projectRepo.getAllProjects());
            for (ProjectDTO p: allProjects) {
                BigDecimal funded = funded(p);
                p.setFunded(funded);
                if (p.getStartDate().isBefore(LocalDate.now()) && p.getStatus().equals(ProjectStatus.Created))
                {
                    switchProjectStatus(p, ProjectStatus.Active);
                }
                if(p.getEndDate().isBefore(LocalDate.now()) || p.getEndDate().equals(LocalDate.now()))
                {
                    switchFundingOff(p);
                }
            }
            return allProjects;
        }

        public List<ProjectDTO> getAllEndedButNotFinalizedProjects()
        {
            List<ProjectDTO> allProjects = getAllProjects();
            List<ProjectDTO> allEndedButNotFinalizedProjects = allProjects.stream().filter(o -> o.isProjectEndDateReached()).filter(o -> o.getStatus() == ProjectStatus.Active).collect(Collectors.toList());
            return  allEndedButNotFinalizedProjects;
        }

    public List<ProjectDTO> getAllProjects(UserDTO user) {
        List<ProjectDTO> allProjectsWithUserInfo = projectMapper.allObjectToDTO(projectRepo.getAllProjects());

        for (ProjectDTO p: allProjectsWithUserInfo) {
            BigDecimal funded = funded(p);
            if (user != null) {
                for (FundingDTO f : p.getFunding()) {
                    if (f.getUser().getId() == user.getId()) {
                        p.setUserHasFunded(true);
                    }
                    if(p.getEndDate().isBefore(LocalDate.now()) || p.getEndDate().equals(LocalDate.now()))
                    {
                        switchFundingOff(p);
                    }
                }
            }
            p.setFunded(funded);
        }
        return allProjectsWithUserInfo;
    }

    public List<ProjectDTO> getAllProjectsFundedByUser(UserDTO user)
    {
        List<ProjectDTO> listOfAllProjectsFunded = getAllProjects(user).stream().filter(p -> p.isUserHasFunded()).collect(Collectors.toList());
        return listOfAllProjectsFunded;
    }

    public List<ProjectDTO> getAllProjectsCreatedByUser(UserDTO user)
    {
        List<ProjectDTO> listOfCreatedProjects = getAllProjects(user).stream().filter(p -> p.getCreator().getId() == user.getId()).collect(Collectors.toList());
        return listOfCreatedProjects;
    }

    public int getPercentageFunded(ProjectDTO pDTO)
    {
        BigDecimal calculation = pDTO.getFunded().multiply(ONE_HUNDRED).divide(pDTO.getFundingTarget(), 0, RoundingMode.HALF_UP);
        int percent = calculation.toBigInteger().intValueExact();
        return percent;
    }

    public void switchProjectStatus(ProjectDTO project, ProjectStatus status) {
        ProjectDTO p = projectMapper.mapObjectToDTO(projectRepo.getProject(project.getId()));
        p.setStatus(status);
        projectRepo.saveProject(projectMapper.mapDTOToObject(p));
    }

    public void setProjectEndDateToNow(ProjectDTO project) {
        ProjectDTO p = projectMapper.mapObjectToDTO(projectRepo.getProject(project.getId()));
        p.setEndDate(LocalDate.now());
        projectRepo.saveProject(projectMapper.mapDTOToObject(p));
    }


    public void switchFundingOff(ProjectDTO project) {
        project.setProjectEndDateReached(true);
    }

}
