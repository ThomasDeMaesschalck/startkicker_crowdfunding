package be.hogent.startkicker.persistence.jpa.mapper;

import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.persistence.jpa.entities.ProjectEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapping class to map Project Entity to Object or other way around
 */
public class ProjectMapper {

    /**
     * To use the User Mapper in Project mapping
     */
    UserMapper userMapper = new UserMapper();

    /**
     * To use the Funding Mapper in Project mapping
     */
    FundingMapper fundingMapper = new FundingMapper();

    /**
     * Map Project Entity to Object
     * @param pEntity Project entity that needs to be mapped
     * @return Mapped Project
     */
    public Project mapEntityToObject(ProjectEntity pEntity) {
        if (pEntity == null) {
            return null;
        }
        Project project = null;
        project = new Project(pEntity.getTitle(), pEntity.getDescription(), pEntity.getStartDate(), pEntity.getEndDate(),
                pEntity.getFundingTarget(), userMapper.mapEntityToObject(pEntity.getCreator()), pEntity.getStatus(), pEntity.getComment());
        project.setId(pEntity.getId());
        project.setComment(pEntity.getComment());
        project.setFunding(fundingMapper.allEntitySetToObject(pEntity.getFunding()));
        return project;
    }

    /**
     * Map Project Object to Project Entity
     * @param p Project that needs mapping
     * @return Mapped Project Entity
     */

    public ProjectEntity mapObjectToEntity(Project p) {
        if (p == null) {
            return null;
        }
        ProjectEntity projectEntity = null;
        projectEntity = new ProjectEntity(p.getTitle(), p.getDescription(), p.getStartDate(), p.getEndDate(), p.getFundingTarget(),
                userMapper.mapObjectToEntity(p.getCreator()), p.getStatus(), p.getComment());
        projectEntity.setId(p.getId());
         return projectEntity;
    }

    /**
     * Map a list of Project Entities to Objects
     * Uses the mapEntityToObject method
     * @param listPs List of Project Entities that needs mappping
     * @return List of mapped Project Objects
     */
    public List<Project> allEntityToObject(List<ProjectEntity> listPs) {
        if (listPs == null)
            return null;
        List<Project> list = new ArrayList<Project>();
        for (ProjectEntity p : listPs) {
            list.add(mapEntityToObject(p));
        }
        return list;
    }

}
