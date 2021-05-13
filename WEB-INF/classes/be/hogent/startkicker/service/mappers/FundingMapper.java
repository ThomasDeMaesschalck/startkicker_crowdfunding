package be.hogent.startkicker.service.mappers;

import be.hogent.startkicker.business.Funding;
import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.dto.ProjectDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FundingMapper implements IMapper<Funding, FundingDTO>{

    be.hogent.startkicker.service.mappers.UserMapper userMapper = new UserMapper();


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

    public List<FundingDTO> allObjectToDTO(List<Funding> listFs) {
        if (listFs == null)
            return null;
        List<FundingDTO> list = new ArrayList<FundingDTO>();
        for (Funding f : listFs) {
            list.add(mapObjectToDTO(f));
        }
        return list;

    }

    public Set<FundingDTO> allObjectSetToDTO(Set<Funding> SetFs) {
        if (SetFs == null)
            return null;
        Set<FundingDTO> set = new HashSet<>();
        for (Funding f : SetFs) {
            set.add(mapObjectToDTO(f));
        }
        return set;

    }

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
