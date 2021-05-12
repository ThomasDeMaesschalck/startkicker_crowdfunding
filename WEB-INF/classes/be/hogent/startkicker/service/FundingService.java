package be.hogent.startkicker.service;

import be.hogent.startkicker.business.Funding;
import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.business.repositories.FundingJPARepo;
import be.hogent.startkicker.business.repositories.IFundingRepo;
import be.hogent.startkicker.business.repositories.IProjectRepo;
import be.hogent.startkicker.business.repositories.ProjectJPARepo;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.mappers.FundingMapper;
import be.hogent.startkicker.service.mappers.IMapper;
import be.hogent.startkicker.service.mappers.ProjectMapper;

import java.util.List;

public class FundingService {
    private IFundingRepo fundingRepo;
    private IMapper<Funding, FundingDTO> fundingMapper;

    private static FundingService instance;


    public static FundingService getInstance() {
        if (instance == null) {
            instance = new FundingService();
        }
        return instance;
    }

    private FundingService() {
        fundingRepo = new FundingJPARepo();
        fundingMapper = new FundingMapper();
    }

    public String saveFunding(FundingDTO fDTO) {
        return fundingRepo.saveFunding(fundingMapper.mapDTOToObject(fDTO));
    }

    public List<FundingDTO> getAllFundings() {
        return fundingMapper.allObjectToDTO(fundingRepo.getAllFunding());
    }


}
