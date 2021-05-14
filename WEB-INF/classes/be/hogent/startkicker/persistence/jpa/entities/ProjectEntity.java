package be.hogent.startkicker.persistence.jpa.entities;

import be.hogent.startkicker.business.ProjectStatus;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * Entity class for the crowdfunding projects. Saved in the Projects database table.
 */
@Entity
@Table(name = "Projects")
public class ProjectEntity implements Serializable {

    private static final long serialVersionUID = 7752145391102604669L;

    /**
     * Automatically generated project id
     */
    @Id @GeneratedValue
    private long id;

    /**
     * Project title
     */
    @NotBlank
    private String title;

    /**
     * Project description
     */
    @NotBlank
    private String description;

    /**
     * Comments can be added after a project goes live
     */
    @NotBlank
    private String comment;

    /**
     * Start date of the project
     */
    @NotBlank @FutureOrPresent
    private LocalDate startDate;

    /**
     * End date of the project
     */
    @NotBlank @Future
    private LocalDate endDate;

    /**
     * How much project creator wants to raise
     */
    @NotBlank
    private BigDecimal fundingTarget;

    /**
     * Enumeration representing the project status code
     */
    @Enumerated
    private ProjectStatus status;

    /**
     * The user that created the project. Stored in user_id column
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity creator;

    /**
     * A set of FundingEntity objects. These are the fundings provided by project backers.
     */
    @OneToMany(mappedBy = "project")
    private Set<FundingEntity> funding;

    /**
     * empty constructor
     */
    public ProjectEntity() {
    }

    /**
     * Constructor for ProjectEntity
     * @param title Project title
     * @param description Project description
     * @param startDate Project start date
     * @param endDate Project end date
     * @param fundingTarget Project funding target
     * @param creator Who created the project
     * @param status Status code of the project (enum)
     * @param comment Comments added by creator after project status changed to active
     */
    public ProjectEntity(String title, String description, LocalDate startDate, LocalDate endDate, BigDecimal fundingTarget, UserEntity creator, ProjectStatus status, String comment) {
        super();
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fundingTarget = fundingTarget;
        this.creator = creator;
        this.comment = comment;
        this.status = status;
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

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Set<FundingEntity> getFunding() {
        return funding;
    }

    public void setFunding(Set<FundingEntity> funding) {
        this.funding = funding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity that = (ProjectEntity) o;
        return description.equals(that.description) && Objects.equals(comment, that.comment) && startDate.equals(that.startDate) && endDate.equals(that.endDate) && fundingTarget.equals(that.fundingTarget) && creator.equals(that.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, comment, startDate, endDate, fundingTarget, creator);
    }
}
