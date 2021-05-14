package be.hogent.startkicker.persistence.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Funding entity contains details about how Funding objects need to be persisted in the database
 * Datebase table name is set to Funding
 */
@Entity
@Table(name = "Funding")
public class FundingEntity implements Serializable {

    private static final long serialVersionUID = -754520177577852999L;

    /**
     * Id of the funding. Automatically generated.
     */
    @Id @GeneratedValue
    private long id;

    /**
     * Project entity that gets funded. Saved using project id in the project_id column.
     */
    @ManyToOne @JoinColumn(name = "project_id") @NotBlank
    private ProjectEntity project;

    /**
     * User entity, this is the user providing the funds. Saved using user id in the user_id column.
     */
    @ManyToOne @JoinColumn(name = "user_id") @NotBlank
    private UserEntity user;

    /**
     * BigDecimal value of how much money the user has pledged
     */
    @NotBlank
    private BigDecimal amount;

    /**
     * Empty constructor for Funding Entity
     */
    protected FundingEntity() {
    }

    /**
     * Constructor
     * @param project Project that gets funded
     * @param user User funding the project
     * @param amount How much the user is funding
     */
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
