package be.hogent.startkicker.business.repositories;

import be.hogent.startkicker.business.Funding;
import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.persistence.jpa.entities.FundingEntity;
import be.hogent.startkicker.persistence.jpa.entities.ProjectEntity;
import be.hogent.startkicker.persistence.jpa.mapper.FundingMapper;
import be.hogent.startkicker.persistence.jpa.mapper.ProjectMapper;
import be.hogent.startkicker.persistence.jpa.mapper.UserMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used for persisting and retrieving Funding objects from the database.
 */

public class FundingJPARepo implements IFundingRepo {

    private static final long serialVersionUID = 4805531531908225999L;
    private static final String NAME_PERSISTENCEUNIT = "startkicker";

    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    /**
     * Mapper used to map Project objects to entities, and the other way around.
     */
    private ProjectMapper pm = new ProjectMapper();

    /**
     * Mapper used to map User objects to entities, and the other way around.
     */
    private UserMapper um = new UserMapper();

    /**
     * Mapper used to map Funding objects to entities, and the other way around.
     */
    private FundingMapper fm = new FundingMapper();


    /**
     * Initialize the Funding JPA repository
     */
    public FundingJPARepo() {
        emf = Persistence.createEntityManagerFactory(NAME_PERSISTENCEUNIT);
    }

    /**
     * Open the entity manager resource connection
     */
    private void createEM() {
        em = emf.createEntityManager();
    }

    /**
     * Close the entity manager resource connection
     */
    private void closeEM() {
        if (em != null)
            em.close();
    }

    /**
     * Method for retrieving all Funding objects from the database.
     * @return A List with all Funding objects.
     */
    @Override
    public List<Funding> getAllFunding() {
        {
            try {
                createEM();
                TypedQuery<FundingEntity> q = em.createQuery("select f from FundingEntity f order by f.id DESC",
                        FundingEntity.class);
                return fm.allEntityToObject(q.getResultList());
            } catch (Exception e) {
                return new ArrayList<Funding>();
            } finally {
                closeEM();
            }
        }    }

    /**
     * Get a Funding object based on the funding id.
     * @param id The funding id.
     * @return Returns a Funding object.
     */
    @Override
    public Funding getFunding(long id) {
        try {
            createEM();
            return fm.mapEntityToObject(em.find(FundingEntity.class, id));
        } catch (Exception e) {
            return null;
        } finally {
            closeEM();
        }
    }

    /**
     * Try to save a Funding object in the database.
     * Checks if the Funding object is present.
     * If Funding is found in database it returns FAIL. Duplicates are not possible.
     * @param f The Funding object that needs to be persisted.
     * @return A String representing the success or error code.
     */
    @Override
    public String saveFunding(Funding f) {
        try {
            createEM();
            FundingEntity fundingInDB = em.find(FundingEntity.class, f.getId());

            em.getTransaction().begin();
            if (fundingInDB != null) {
                return FAIL;
            } else {
                return saveNewFunding(fm.mapObjectToEntity(f));
            }
        } catch (Exception e) {
            return FAIL;
        } finally {
            closeEM();
        }
    }

    /**
     * Try to persist new funding record to the database
     * @param f FundingEntity that needs to be persisted.
     * @return A String representing the success or error code
     */
    private String saveNewFunding(FundingEntity f) {
        try {
            em.persist(f);
            em.getTransaction().commit();
            return SUCCESS;
        } catch (Exception ex) {
            return FAIL;
        }
    }




}
