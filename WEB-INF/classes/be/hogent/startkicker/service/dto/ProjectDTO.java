package be.hogent.startkicker.service.dto;

import be.hogent.startkicker.business.ProjectStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Project DTO is used by the service layer and the frontend
 */
public class ProjectDTO implements Comparable<ProjectDTO>, Serializable {

    private static final long serialVersionUID = -1462565234313719335L;

    /**
     * Project id
     */
    private long id;

    /**
     * Project title
     */
    private String title;

    /**
     * Project description
     */
    private String description;

    /**
     * Project comment (can be added after project went live)
     */
    private String comment;

    /**
     * Project start date
     */
    private LocalDate startDate;

    /**
     * Project end date
     */
    private LocalDate endDate;

    /**
     * How much money the project creator needs
     */
    private BigDecimal fundingTarget;

    /**
     * User that created the project
     */
    private UserDTO creator;

    /**
     * Status code of the project (enum)
     */
    private ProjectStatus status;

    /**
     * A set that contains the funds that have been pledged to the project and users who provided these funds
     */
    private Set<FundingDTO> funding;

    /**
     * Value calculated by the service layer.
     * Allows frontend to show total amounts of funds received for this project
     */
    private BigDecimal funded;

    /**
     * Set by the service layer. Indicates whether logged in user who views this project has already funded it.
     * Used to ensure a user can't fund a project twice
     */
    private boolean userHasFunded;

    /**
     * Set by the service layer. If project has hit its end date this boolean is true.
     * Admin then needs to adjust status code manually to failed or succesful
     */
    private boolean ProjectEndDateReached;

    /**
     * Constructor for Project DTO
     * @param title Project title
     * @param description Project description
     * @param startDate Project start date
     * @param endDate Project end date
     * @param fundingTarget How much money does the project need
     * @param creator User who created the project
     * @param status Project status code (enum)
     * @param comment Comment from the project creator after project went active
     */
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
