package be.hogent.startkicker.persistence.jpa.mapper;

import be.hogent.startkicker.business.Funding;
import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.persistence.jpa.entities.FundingEntity;
import be.hogent.startkicker.persistence.jpa.entities.ProjectEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mapping class. Map Objects to Entities and other way around.
 */
public class FundingMapper {

    /**
     * To use the UserMapper in our Funding Mapper class
     */
    UserMapper userMapper = new UserMapper();

    /**
     * Converts a Funding Entity to a Funding Object
     * Project gets mapped solely with project id
     * @param fEntity The Funding Entity that needs to be mapped
     * @return A Funding object
     */
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

    /**
     * Convert a Funding Object to a Funding entity
     * Project gets mapped solely with project id
     * @param f Funding object that needs mapping
     * @return A Funding Entity
     */
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

    /**
     * Map a list of Funding Entities to Funding Objects
     * Uses the mapEntityToObjects method.
     * @param listFs List of Funding Entities that needs mapping
     * @return List of Funding Objects
     */
    public List<Funding> allEntityToObject(List<FundingEntity> listFs) {
        if (listFs == null)
            return null;
        List<Funding> list = new ArrayList<Funding>();
        for (FundingEntity f : listFs) {
            list.add(mapEntityToObject(f));
        }
        return list;
    }

    /**
     * Map a Set of Funding Entities to Funding Objects
     * Uses the mapEntityToObjects method.
     * @param SetFs Set of Funding Entities that needs mapping
     * @return Set of Funding Objects
     */
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
