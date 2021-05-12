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

public class FundingJPARepo implements IFundingRepo {

    private static final long serialVersionUID = 4805531531908225999L;
    private static final String NAME_PERSISTENCEUNIT = "startkicker";

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private ProjectMapper pm = new ProjectMapper();
    private UserMapper um = new UserMapper();
    private FundingMapper fm = new FundingMapper();


    public FundingJPARepo() {
        System.out.println("FundingJPARepo created");
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


    @Override
    public String saveFunding(Funding f) {
        try {
            createEM();
            FundingEntity fundingInDB = em.find(FundingEntity.class, f.getId());

            em.getTransaction().begin();
            if (fundingInDB != null) {
                System.out.println("Funding found...");
                return FAIL;
            } else {
                System.out.println("Making new Funding...");
                return saveNewFunding(fm.mapObjectToEntity(f));
            }
        } catch (Exception e) {
            System.out.println("not found...");
            return FAIL;
        } finally {
            closeEM();
        }
    }

    /**
     * Funding proberen opslaan.<br>
     * @param f
     * @return
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
