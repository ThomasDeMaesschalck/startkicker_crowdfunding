package be.hogent.startkicker.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Funding DTO which is used by service layer and frontend
 */
public class FundingDTO implements Comparable<FundingDTO>, Serializable {

    private static final long serialVersionUID = 4545078937387808563L;

    /**
     * Funding id
     */
    private long id;

    /**
     * Funded project
     */
    private ProjectDTO project;

    /**
     * User that funded the project
     */
    private UserDTO user;

    /**
     * How much user funded
     */
    private BigDecimal amount;

    /**
     * Empty constructor
     */
    public FundingDTO() {
        super();
    }

    /**
     * Overloaded constructor
     * @param project Project that got funded
     * @param user User that funded the project
     * @param amount How much the user funded
     */
    public FundingDTO(ProjectDTO project, UserDTO user, BigDecimal amount) {
        super();
        this.project = project;
        this.user = user;
        this.amount = amount;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(FundingDTO f) {
        return this.toString().compareTo(f.toString());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FundingDTO other = (FundingDTO) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
