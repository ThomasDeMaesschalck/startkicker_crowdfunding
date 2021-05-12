package be.hogent.startkicker.business;

import be.hogent.startkicker.persistence.jpa.entities.ProjectEntity;
import be.hogent.startkicker.persistence.jpa.entities.UserEntity;

import java.math.BigDecimal;

public class Funding {

    private long id;
    private ProjectEntity project;
    private UserEntity user;
    private BigDecimal amount;

    public Funding(ProjectEntity project, UserEntity user, BigDecimal amount) {
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
}
