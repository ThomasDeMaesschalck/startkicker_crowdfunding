package be.hogent.startkicker.business;

import be.hogent.startkicker.persistence.jpa.entities.FundingEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class Project {

    private long id;
    private String title;
    private String description;
    private String comment;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal fundingTarget;
    private User creator;
    private ProjectStatus status;
    private Set<Funding> funding;


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
