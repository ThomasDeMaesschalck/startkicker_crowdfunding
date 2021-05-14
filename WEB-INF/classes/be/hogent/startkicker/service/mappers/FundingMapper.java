package be.hogent.startkicker.service.mappers;

import be.hogent.startkicker.business.Funding;
import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.dto.ProjectDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Map Funding Object to DTO or other way around
 */
public class FundingMapper implements IMapper<Funding, FundingDTO>{

    /**
     * To use UserMapper in FundingMapper
     */
    be.hogent.startkicker.service.mappers.UserMapper userMapper = new UserMapper();

    /**
     * Convert FundingDTO to Funding Object
     * @param fDTO The Funding DTO that needs to be mapped
     * @return The Funding Object
     */
    public Funding mapDTOToObject(FundingDTO fDTO) {
        if (fDTO == null) {
            return null;
        }
        Funding funding = null;
        Project project = new Project();
        project.setId(fDTO.getProject().getId());
       funding = new Funding(project, userMapper.mapDTOToObject(fDTO.getUser()), fDTO.getAmount());
        funding.setId(fDTO.getId());
        return funding;
    }

    /**
     * Map an Object to DTO
     * @param f Funding object that needs mapping
     * @return The Funding DTO
     */
    public FundingDTO mapObjectToDTO(Funding f) {
        if (f == null) {
            return null;
        }
        FundingDTO fundingDTO = null;
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(f.getProject().getId());
      fundingDTO = new FundingDTO(projectDTO, userMapper.mapObjectToDTO(f.getUser()), f.getAmount());
        fundingDTO.setId(f.getId());
        return fundingDTO;
    }

    /**
     * Map a List of Funding Objects to a List of Funding DTOs
     * Uses mapObjectToDTO method
     * @param listFs List of Funding Objects
     * @return List of Funding DTOs
     */
    public List<FundingDTO> allObjectToDTO(List<Funding> listFs) {
        if (listFs == null)
            return null;
        List<FundingDTO> list = new ArrayList<FundingDTO>();
        for (Funding f : listFs) {
            list.add(mapObjectToDTO(f));
        }
        return list;

    }

    /**
     * Map Set of Funding to Set of Funding DTOs
     * Uses mapObjectToDTO method
     * @param SetFs Set of Funding
     * @return Set of Funding DTOs
     */
    public Set<FundingDTO> allObjectSetToDTO(Set<Funding> SetFs) {
        if (SetFs == null)
            return null;
        Set<FundingDTO> set = new HashSet<>();
        for (Funding f : SetFs) {
            set.add(mapObjectToDTO(f));
        }
        return set;

    }

    /**
     * Map a List of Funding DTOs to a List of Funding Objects
     * Uses mapDTOToObjects method
     * @param listFs List of Funding DTOs
     * @return List of Funding Objects
     */
    public List<Funding> allDTOToObject(List<FundingDTO> listFs) {
        if (listFs == null)
            return null;
        List<Funding> list = new ArrayList<Funding>();
        for (FundingDTO f : listFs) {
            list.add(mapDTOToObject(f));
        }
        return list;

    }
}
