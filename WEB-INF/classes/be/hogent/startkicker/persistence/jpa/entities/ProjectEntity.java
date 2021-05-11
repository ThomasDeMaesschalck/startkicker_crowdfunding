package be.hogent.startkicker.persistence.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Projects")
public class ProjectEntity implements Serializable {

    private static final long serialVersionUID = 7752145391102604669L;


    @Id @GeneratedValue
    private long id;

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String comment;
    @NotBlank @FutureOrPresent
    private LocalDate startDate;
    @NotBlank @Future
    private LocalDate endDate;
    @NotBlank
    private BigDecimal fundingTarget;

    //project status en funds nog te implementen

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity creator;

    public ProjectEntity() {
    }

    public ProjectEntity(String title, String description, LocalDate startDate, LocalDate endDate, BigDecimal fundingTarget, UserEntity creator) {
        super();
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fundingTarget = fundingTarget;
        this.creator = creator;
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
