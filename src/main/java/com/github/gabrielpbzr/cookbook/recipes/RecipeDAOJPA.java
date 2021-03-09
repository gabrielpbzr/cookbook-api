package com.github.gabrielpbzr.cookbook.recipes;

import com.github.gabrielpbzr.cookbook.domain.Recipe;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JPA implementation of RecipeDAO
 * @author gabriel
 */
public class RecipeDAOJPA implements RecipeDAO {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final Logger logger;
    
    public RecipeDAOJPA(EntityManager entityManager) {
        //Default constructor
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.logger = LoggerFactory.getLogger(RecipeDAO.class);
    }

    @Override
    public boolean save(Recipe recipe) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(recipe);
            entityManager.flush();
            tx.commit();
            return true;
        } catch (PersistenceException pe) {
            tx.rollback();
            logger.error("Error during recipe persistence", pe);
            return false;
        }
    }
    
    @Override
    public boolean update(Recipe recipe) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(recipe);
            entityManager.flush();
            tx.commit();
            
            return true;
        } catch (PersistenceException pe) {
            tx.rollback();
            logger.error(String.format("Error during recipe '%s' update", recipe.getUuid().toString()), pe);
            
            return false;
        }
    }

    @Override
    public boolean remove(Recipe recipe) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Recipe r = find(recipe.getId()).orElseThrow(PersistenceException::new);
            entityManager.remove(r);
            entityManager.flush();
            tx.commit();
            
            return true;
        } catch (PersistenceException pe) {
            tx.rollback();
            logger.error(String.format("Error during recipe uuid='%s' remove", recipe.getUuid().toString()), pe);
            
            return false;
        }
    }

    @Override
    public Optional<Recipe> find(Long id) {
        try {
            return Optional.of(entityManager.find(Recipe.class, id));
        } catch (PersistenceException pe) {
            logger.debug(String.format("Recipe with id='%d' was not found", id));
            return Optional.empty();
        }
    }

    @Override
    public Optional<Recipe> findByUUID(UUID uuid) {
        try {
            CriteriaQuery<Recipe> cq = getCriteriaQuery();
            Root<Recipe> root = cq.from(Recipe.class);
            cq.select(root)
                    .where(criteriaBuilder.equal(root.get("uuid").as(UUID.class), uuid));
            
            final Recipe result = entityManager.createQuery(cq)
                    .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException nre) {
            logger.debug(String.format("Recipe with uuid='%s' was not found", uuid.toString()));
            return Optional.empty();
        }
    }

    @Override
    public List<Recipe> findAll(Map<String, Object> filters, int start, int max) {
        CriteriaQuery<Recipe> cq = getCriteriaQuery();
        Root<Recipe> root = cq.from(Recipe.class);
        
        cq.select(root);
        
        return entityManager.createQuery(cq).setFirstResult(start)
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public long getCount() {
        return (long) entityManager.createQuery("SELECT COUNT(r) FROM Recipe r").getSingleResult();
    }
    
    private CriteriaQuery<Recipe> getCriteriaQuery() {
        return criteriaBuilder.createQuery(Recipe.class);
    }
}
