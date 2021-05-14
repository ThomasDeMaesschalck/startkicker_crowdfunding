package be.hogent.startkicker.service;

import be.hogent.startkicker.business.Funding;
import be.hogent.startkicker.business.repositories.FundingJPARepo;
import be.hogent.startkicker.business.repositories.IFundingRepo;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.mappers.FundingMapper;
import be.hogent.startkicker.service.mappers.IMapper;

import java.util.List;

/**
 * Funding service. Communicates with the frontend
 */
public class FundingService {

    /**
     * The Funding repository
     */
    private IFundingRepo fundingRepo;

    /**
     * Mapping class, Map funding Object to Funding DTO and other way around
     */
    private IMapper<Funding, FundingDTO> fundingMapper;

    /**
     * Singleton instance
     */
    private static FundingService instance;


    /**
     * Singleton pattern
     * @return Instance of FundingService
     */
    public static FundingService getInstance() {
        if (instance == null) {
            instance = new FundingService();
        }
        return instance;
    }

    /**
     * Default constructor
     */
    private FundingService() {
        fundingRepo = new FundingJPARepo();
        fundingMapper = new FundingMapper();
    }

    /**
     * Save a funding to the database
     * @param fDTO FundingDTO that needs to be persisted
     * @return String with success or error code
     */
    public String saveFunding(FundingDTO fDTO) {
        return fundingRepo.saveFunding(fundingMapper.mapDTOToObject(fDTO));
    }

}
