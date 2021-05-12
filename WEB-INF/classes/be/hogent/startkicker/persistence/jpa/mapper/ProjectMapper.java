package be.hogent.startkicker.persistence.jpa.mapper;

import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.business.User;
import be.hogent.startkicker.persistence.jpa.entities.ProjectEntity;
import be.hogent.startkicker.persistence.jpa.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class ProjectMapper {

    UserMapper userMapper = new UserMapper();
    FundingMapper fundingMapper = new FundingMapper();

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
