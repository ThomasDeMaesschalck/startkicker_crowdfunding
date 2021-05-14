package be.hogent.startkicker.service.mappers;

import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.service.dto.ProjectDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Map Project DTO to Project Object and other way around
 */
public class ProjectMapper implements IMapper<Project, ProjectDTO> {

    /**
     * Use the UserMapper in the ProjectMapper
     */
       be.hogent.startkicker.service.mappers.UserMapper userMapper = new UserMapper();

    /**
     * Use the FundingMapper in the ProjectMapper
     */
    be.hogent.startkicker.service.mappers.FundingMapper fundingMapper = new FundingMapper();

    /**
     * Map a Project DTO to a Project Object
     * @param pDTO Project DTO that needs to be mapped
     * @return Project Object
     */
    public Project mapDTOToObject(ProjectDTO pDTO) {
        if (pDTO == null) {
            return null;
        }
        Project project = null;
        project = new Project(pDTO.getTitle(), pDTO.getDescription(),
                pDTO.getStartDate(), pDTO.getEndDate(), pDTO.getFundingTarget(), userMapper.mapDTOToObject(pDTO.getCreator()), pDTO.getStatus(), pDTO.getComment());
        project.setId(pDTO.getId());
         return project;
    }

    /**
     * Map an Project Object to a Project DTO
     * @param p Project Object that needs to be mapped
     * @return The ProjectDTO
     */
    public ProjectDTO mapObjectToDTO(Project p) {
        if (p == null) {
            return null;
        }
        ProjectDTO projectDTO = null;
        projectDTO = new ProjectDTO(p.getTitle(), p.getDescription(),
                p.getStartDate(), p.getEndDate(), p.getFundingTarget(), userMapper.mapObjectToDTO(p.getCreator()), p.getStatus(), p.getComment());
        projectDTO.setId(p.getId());
       projectDTO.setFunding(fundingMapper.allObjectSetToDTO(p.getFunding()));
        return projectDTO;
    }

    /**
     * Map a List of Project Objects to a List of Project DTOs
     * Uses mapObjectToDTO method
     * @param listPs List of Project Objects that need to be mapped
     * @return The List of Project DTOs
     */
    public List<ProjectDTO> allObjectToDTO(List<Project> listPs) {
        if (listPs == null)
            return null;
        List<ProjectDTO> list = new ArrayList<ProjectDTO>();
        for (Project p : listPs) {
            list.add(mapObjectToDTO(p));
        }
        return list;

    }

    /**
     * Map Project DTO List to List of Project Objects
     * Uses the mapDTOToObject method
     * @param listPs List of Project DTOs that need to be mapped
     * @return List of Project Objects
     */

    public List<Project> allDTOToObject(List<ProjectDTO> listPs) {
        if (listPs == null)
            return null;
        List<Project> list = new ArrayList<Project>();
        for (ProjectDTO p : listPs) {
            list.add(mapDTOToObject(p));
        }
        return list;

    }

}
