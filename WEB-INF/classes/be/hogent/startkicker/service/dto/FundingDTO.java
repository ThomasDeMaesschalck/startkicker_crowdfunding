package be.hogent.startkicker.service.dto;

import be.hogent.startkicker.business.Funding;
import be.hogent.startkicker.persistence.jpa.entities.ProjectEntity;
import be.hogent.startkicker.persistence.jpa.entities.UserEntity;

import java.io.Serializable;
import java.math.BigDecimal;

public class FundingDTO implements Comparable<FundingDTO>, Serializable {

    private static final long serialVersionUID = 4545078937387808563L;

    private long id;
    private ProjectDTO project;
    private UserDTO user;
    private BigDecimal amount;


    public FundingDTO(ProjectDTO project, UserDTO user, BigDecimal amount) {
        super();
        this.project = project;
        this.user = user;
        this.amount = amount;
    }

    public FundingDTO() {
        super();
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
