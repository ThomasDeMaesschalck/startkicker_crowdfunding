package be.hogent.startkicker.business.repositories;

import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.persistence.jpa.entities.ProjectEntity;
import be.hogent.startkicker.persistence.jpa.mapper.ProjectMapper;
import be.hogent.startkicker.persistence.jpa.mapper.UserMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used for persisting and retrieving Projects from the database.
 */

public class ProjectJPARepo implements IProjectRepo {

    private static final long serialVersionUID = -7589151905453692488L;
    private static final String NAME_PERSISTENCEUNIT = "startkicker";

    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    /**
     * ProjectMapper is used to map Project objects to entities and the other way around.
     */
    private ProjectMapper pm = new ProjectMapper();

    /**
     * UserMapper is used to map User objects to entities and the other way around.
     */
    private UserMapper um = new UserMapper();

    /**
     * Initialization of the Project JPA repository
     */
    public ProjectJPARepo() {
        emf = Persistence.createEntityManagerFactory(NAME_PERSISTENCEUNIT);
    }

    /**
     * Open the entity manager resource
     */
    private void createEM() {
        em = emf.createEntityManager();
    }

    /**
     * Close the entity manager connection
     */
    private void closeEM() {
        if (em != null)
            em.close();
    }

    /**
     * Get a list of all projects persisted in the database.
     * @return A list with all Project objects, ordered by DESC.
     */
    @Override
    public List<Project> getAllProjects() {
        {
            try {
                createEM();
                TypedQuery<ProjectEntity> q = em.createQuery("select p from ProjectEntity p order by p.id DESC",
                        ProjectEntity.class);
                return pm.allEntityToObject(q.getResultList());
            } catch (Exception e) {
                return new ArrayList<Project>();
            } finally {
                closeEM();
            }
        }    }


    /**
     * Retrieve a project based on the project id stored in the database.
     * @param id A long value representing the project's id.
     * @return A Project object.
     */
    @Override
    public Project getProject(long id) {
        try {
            createEM();
            return pm.mapEntityToObject(em.find(ProjectEntity.class, id));
        } catch (Exception e) {
            return null;
        } finally {
            closeEM();
        }
    }

    /**
     * Method for deleting a project from the database. Searched for persisted project based on Project id and tries to delete it.
     * @param p A Project object that should be present in the database.
     * @return A String representing the success or error code.
     */
    @Override
    public String deleteProject(Project p) {
        {
            String outcome = "";
            try {
                createEM();
                ProjectEntity projectInDB = em.find(ProjectEntity.class, p.getId());
                if (projectInDB != null)
                {
                    em.getTransaction().begin();
                    em.remove(projectInDB);
                    em.getTransaction().commit();
                    outcome = SUCCESS;
                }
                else  {
                    outcome = FAIL;
                }
            } catch (Exception e) {
                outcome = FAIL;
            } finally {
                closeEM();
                return outcome;
            }
        }
    }

    /**
     * Try to persist a Project object in the database. Method searched if the object is present in the database.
     * If found, it will try to update the existing record. If not found, a new record will be created.
     * @param p This is the Project object that needs to be persisted.
     * @return A String representing the success or error code.
     */
    @Override
    public String saveProject(Project p) {
        try {
            createEM();
            ProjectEntity projectInDB = em.find(ProjectEntity.class, p.getId());

            em.getTransaction().begin();
            if (projectInDB != null) {
                p.setCreator(um.mapEntityToObject(projectInDB.getCreator()));
                return updateProject(pm.mapObjectToEntity(p), projectInDB);
            } else {
                return saveNewProject(pm.mapObjectToEntity(p));
            }
        } catch (Exception e) {
            return FAIL;
        } finally {
            closeEM();
        }
    }

    /**
     * Try to save a project entity to the database. Creates a new record.
     * @param p The ProjectEntity that needs to be persisted.
     * @return A String representing the success or error code.
     */
    private String saveNewProject(ProjectEntity p) {
        try {
            em.persist(p);
            em.getTransaction().commit();
            return SUCCESS;
        } catch (Exception ex) {
            return FAIL;
        }
    }

    /**
     * Try to update a Project that was fouind in the database by the saveProject method<br>
     *
     * @param p The ProjectEntity that needs to be persisted
     * @param projectInDB The record that was retrieved from the database.
     * @return A String representing the success or error code.
     */
    private String updateProject(ProjectEntity p, ProjectEntity projectInDB) {
        try {
            projectInDB.setTitle(p.getTitle());
            projectInDB.setDescription(p.getDescription());
            projectInDB.setComment(p.getComment());
            projectInDB.setStartDate(p.getStartDate());
            projectInDB.setEndDate(p.getEndDate());
            projectInDB.setFundingTarget(p.getFundingTarget());
            projectInDB.setCreator(p.getCreator());
            projectInDB.setStatus(p.getStatus());
            em.persist(projectInDB);
            em.getTransaction().commit();
            return SUCCESS;

        } catch (Exception e) {
            return FAIL;
        }
    }


    }
