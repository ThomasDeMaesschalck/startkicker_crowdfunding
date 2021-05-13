package be.hogent.startkicker.persistence.jpa.mapper;

import be.hogent.startkicker.business.Funding;
import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.persistence.jpa.entities.FundingEntity;
import be.hogent.startkicker.persistence.jpa.entities.ProjectEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FundingMapper {

    UserMapper userMapper = new UserMapper();

    public Funding mapEntityToObject(FundingEntity fEntity) {
        if (fEntity == null) {
            return null;
        }
        Funding funding = null;
        Project project = new Project();
        project.setId(fEntity.getProject().getId());
        funding = new Funding(project, userMapper.mapEntityToObject(fEntity.getUser()), fEntity.getAmount());
        funding.setId(fEntity.getId());
        return funding;
    }

    public FundingEntity mapObjectToEntity(Funding f) {
        if (f == null) {
            return null;
        }
        FundingEntity fundingEntity = null;
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(f.getProject().getId());

       fundingEntity = new FundingEntity(projectEntity, userMapper.mapObjectToEntity(f.getUser()), f.getAmount());
        fundingEntity.setId(f.getId());
        return fundingEntity;
    }

    public List<Funding> allEntityToObject(List<FundingEntity> listFs) {
        if (listFs == null)
            return null;
        List<Funding> list = new ArrayList<Funding>();
        for (FundingEntity f : listFs) {
            list.add(mapEntityToObject(f));
        }
        return list;
    }

    public Set<Funding> allEntitySetToObject(Set<FundingEntity> SetFs) {
        if (SetFs == null)
            return null;
        Set<Funding> set = new HashSet<>();
        for (FundingEntity f : SetFs) {
            set.add(mapEntityToObject(f));
        }
        return set;
    }


}
