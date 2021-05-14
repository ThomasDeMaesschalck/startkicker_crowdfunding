package be.hogent.startkicker.business;

import be.hogent.startkicker.persistence.jpa.entities.FundingEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Project objects are crowdfunding projects. Each project has a number of properties including title, description, start and end date, and funding target.
 */
public class Project {

    private long id;
    private String title;
    private String description;
    private String comment;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * The target amount of money the creator of the project hopes to raise
     */
    private BigDecimal fundingTarget;

    /**
     * User who created the project
     */
    private User creator;

    /**
     * Status code of the project
     */
    private ProjectStatus status;

    /**
     * Set containing the Funding pledged to this project by various users.
     */
    private Set<Funding> funding;


    /**
     * Constructor to create a Project object.
     * @param title Title of the project
     * @param description Project description
     * @param startDate When the Project needs to start
     * @param endDate The end date of the Project
     * @param fundingTarget Funding target of the project
     * @param creator User who created the project
     * @param status The status code of the project (created, activate, ...)
     * @param comment Comments added after the project went live.
     */
    public Project(String title, String description, LocalDate startDate, LocalDate endDate, BigDecimal fundingTarget, User creator, ProjectStatus status, String comment) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fundingTarget = fundingTarget;
        this.creator = creator;
        this.status = status;
        this.comment = comment;
    }

    /**
     * Default constructor
     */
    public Project() {
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Set<Funding> getFunding() {
        return funding;
    }

    public void setFunding(Set<Funding> funding) {
        this.funding = funding;
    }
}
