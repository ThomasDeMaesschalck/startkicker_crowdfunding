package be.hogent.startkicker.business.repositories;

import be.hogent.startkicker.business.Funding;
import be.hogent.startkicker.business.Project;

import java.io.Serializable;
import java.util.List;

public interface IFundingRepo extends Serializable {
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    public List<Funding> getAllFunding();

    public String saveFunding(Funding f);

    public Funding getFunding(long id);

}
