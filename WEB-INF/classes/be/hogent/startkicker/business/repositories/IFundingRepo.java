package be.hogent.startkicker.business.repositories;

import be.hogent.startkicker.business.Funding;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for the Funding JPA repository
 */
public interface IFundingRepo extends Serializable {
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    /**
     * Get all funding objects from DB
     * @return List of Funding objects
     */
    public List<Funding> getAllFunding();

    /**
     * Save Funding object to DB
     * @param f Funding object that needs persisting
     * @return A string representing the success or error code
     */
    public String saveFunding(Funding f);

    /**
     * Retrieve funding details based on funding id.
     * @param id The id of the Funding object.
     * @return Returns a Funding object.
     */
    public Funding getFunding(long id);

}
