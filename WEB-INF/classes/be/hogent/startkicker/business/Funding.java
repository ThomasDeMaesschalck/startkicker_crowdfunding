package be.hogent.startkicker.business;

import be.hogent.startkicker.persistence.jpa.entities.ProjectEntity;
import be.hogent.startkicker.persistence.jpa.entities.UserEntity;

import java.math.BigDecimal;

public class Funding {

    private long id;
    private Project project;
    private User user;
    private BigDecimal amount;

    public Funding(Project project, User user, BigDecimal amount) {
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
