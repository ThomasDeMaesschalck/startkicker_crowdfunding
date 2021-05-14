package be.hogent.startkicker.business.repositories;

import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.business.User;

import java.io.Serializable;
import java.util.List;

/**
 * Class providing the interface for the Project repository
 */
public interface IProjectRepo extends Serializable {
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    /**
     * Get all projects from database
     * @return A list of Project objects
     */
    public List<Project> getAllProjects();

    /**
     * Save a project to the DB
     * @param p Project object that needs to be persisted
     * @return A String representing the success or error code
     */
    public String saveProject(Project p);

    /**
     * Retrieve a Project from DB based on project id.
     * @param id The id of the Project object that needs to be retrieved
     * @return Returns Project object
     */
    public Project getProject(long id);

    /**
     * Delet a project object from the DB.
     * @param p Project that needs to be deleted
     * @return A String representing the success or error code.
     */
    public String deleteProject(Project p);

}
