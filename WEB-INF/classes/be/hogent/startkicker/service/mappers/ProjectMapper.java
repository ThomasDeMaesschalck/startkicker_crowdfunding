package be.hogent.startkicker.service.mappers;

import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.service.dto.ProjectDTO;

import java.util.ArrayList;
import java.util.List;

public class ProjectMapper implements IMapper<Project, ProjectDTO> {

       be.hogent.startkicker.service.mappers.UserMapper userMapper = new UserMapper();
    be.hogent.startkicker.service.mappers.FundingMapper fundingMapper = new FundingMapper();


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

    public List<ProjectDTO> allObjectToDTO(List<Project> listPs) {
        if (listPs == null)
            return null;
        List<ProjectDTO> list = new ArrayList<ProjectDTO>();
        for (Project p : listPs) {
            list.add(mapObjectToDTO(p));
        }
        return list;

    }

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
