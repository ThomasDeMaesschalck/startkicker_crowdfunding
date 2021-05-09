package be.hogent.startkicker.business.repositories;

import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.business.User;

import java.io.Serializable;
import java.util.List;

public interface IProjectRepo extends Serializable {
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    public List<Project> getAllProjects();

    public String saveProject(Project p);

    public User getProject(long id);

    public String deleteProject(Project p);

}
