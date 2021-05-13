package be.hogent.startkicker.service.dto;

import be.hogent.startkicker.business.ProjectStatus;
import be.hogent.startkicker.business.User;
import be.hogent.startkicker.persistence.jpa.entities.FundingEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class ProjectDTO implements Comparable<ProjectDTO>, Serializable {

    private static final long serialVersionUID = -1462565234313719335L;

    private long id;
    private String title;
    private String description;
    private String comment;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal fundingTarget;
    private UserDTO creator;
    private ProjectStatus status;
    private Set<FundingDTO> funding;
    private BigDecimal funded;
    private boolean userHasFunded;
    private boolean ProjectEndDateReached;


    public ProjectDTO(String title, String description, LocalDate startDate, LocalDate endDate, BigDecimal fundingTarget, UserDTO creator, ProjectStatus status, String comment) {
        super();
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fundingTarget = fundingTarget;
        this.creator = creator;
        this.status = status;
        this.comment = comment;
    }

    public ProjectDTO() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getFundingTarget() {
        return fundingTarget;
    }

    public void setFundingTarget(BigDecimal fundingTarget) {
        this.fundingTarget = fundingTarget;
    }

    public UserDTO getCreator() {
        return creator;
    }

    public void setCreator(UserDTO creator) {
        this.creator = creator;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public BigDecimal getFunded() {
        return funded;
    }

    public void setFunded(BigDecimal funded) {
        this.funded = funded;
    }

    public Set<FundingDTO> getFunding() {
        return funding;
    }

    public void setFunding(Set<FundingDTO> funding) {
        this.funding = funding;
    }

    public boolean isUserHasFunded() {
        return userHasFunded;
    }

    public void setUserHasFunded(boolean userHasFunded) {
        this.userHasFunded = userHasFunded;
    }

    public boolean isProjectEndDateReached() {
        return ProjectEndDateReached;
    }

    public void setProjectEndDateReached(boolean projectEndDateReached) {
        ProjectEndDateReached = projectEndDateReached;
    }

    @Override
    public int compareTo(ProjectDTO p) {
        return this.toString().compareTo(p.toString());
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
        ProjectDTO other = (ProjectDTO) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
