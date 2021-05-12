package be.hogent.startkicker.persistence.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "Funding")
public class FundingEntity implements Serializable {

    private static final long serialVersionUID = -754520177577852999L;

    @Id @GeneratedValue
    private long id;

    @ManyToOne @JoinColumn(name = "project_id") @NotBlank
    private ProjectEntity project;

    @ManyToOne @JoinColumn(name = "user_id") @NotBlank
    private UserEntity user;

    @NotBlank
    private BigDecimal amount;

    protected FundingEntity() {
    }

    public FundingEntity(ProjectEntity project, UserEntity user, BigDecimal amount) {
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

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FundingEntity that = (FundingEntity) o;
        return project.equals(that.project) && user.equals(that.user) && amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, user, amount);
    }
}
