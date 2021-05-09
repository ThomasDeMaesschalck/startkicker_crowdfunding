package be.hogent.startkicker.business.repositories;

import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.business.User;
import be.hogent.startkicker.persistence.jpa.entities.ProjectEntity;
import be.hogent.startkicker.persistence.jpa.entities.UserEntity;
import be.hogent.startkicker.persistence.jpa.mapper.ProjectMapper;
import be.hogent.startkicker.persistence.jpa.mapper.UserMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectJPARepo implements IProjectRepo {

    private static final long serialVersionUID = -7589151905453692488L;
    private static final String NAME_PERSISTENCEUNIT = "startkicker";

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private ProjectMapper pm = new ProjectMapper();

    public ProjectJPARepo() {
        System.out.println("ProjectJPARepo created");
        emf = Persistence.createEntityManagerFactory(NAME_PERSISTENCEUNIT);
    }

    private void createEM() {
//		emf = Persistence.createEntityManagerFactory(NAME_PERSISTENCEUNIT);
        em = emf.createEntityManager();
    }

    private void closeEM() {
        if (em != null)
            em.close();
//		if (emf != null)
//			emf.close();
    }

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


    @Override
    public String saveProject(Project p) {
        try {
            createEM();
            ProjectEntity projectInDB = em.find(ProjectEntity.class, p.getId());

            System.out.println(p.getId() + "is userID");

            em.getTransaction().begin();
            if (projectInDB != null) {
                System.out.println("Project found...");
                return updateProject(pm.mapObjectToEntity(p), projectInDB);
            } else {
                System.out.println("Making new Project...");
                return saveNewProject(pm.mapObjectToEntity(p));
            }
        } catch (Exception e) {
            System.out.println("not found...");
            return FAIL;
        } finally {
            closeEM();
        }
    }

    /**
     * De nieuwe persoon proberen opslaan.<br>
     * Username moet uniek zijn om succes te hebben.<br>
     * see uniqueConstraints = @UniqueConstraint(columnNames = "userName")
     * @param p
     * @return
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
     * De persoon proberen updaten.<br>
     * Username, indien veranderd, moet uniek zijn om succes te hebben.<br>
     *
     * @param p
     * @param projectInDB
     * @return
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
            em.persist(projectInDB);
            em.getTransaction().commit();
            System.out.println("updating...");
            return SUCCESS;

        } catch (Exception e) {
            System.out.println(e.toString());

            return FAIL;
        }
    }


    }
