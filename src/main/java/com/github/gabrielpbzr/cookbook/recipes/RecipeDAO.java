package com.github.gabrielpbzr.cookbook.recipes;

import com.github.gabrielpbzr.cookbook.domain.Recipe;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Definition of a RecipeDAO
 * @author gabriel
 */
public interface RecipeDAO {
    /**
     * Persists a recipe
     * @param recipe
     * @return true if operation succeeds, false otherwise
     */
    boolean save(Recipe recipe);
    
    /**
     * Updates a recipe
     * @param recipe
     * @return true if operation succeeds, false otherwise
     */
    boolean update(Recipe recipe);
    
    /**
     * Delete a recipe
     * @param recipe
     * @return true if operation succeeds, false otherwise
     */
    boolean remove(Recipe recipe);
    
    /**
     * Find a recipe by its id
     * @param id recipe id
     * @return 
     */
    Optional<Recipe> find(Long id);
    
    /**
     * Find a recipe by its uuid
     * @param uuid
     * @return 
     */
    Optional<Recipe> findByUUID(UUID uuid);
    
    /**
     * List all recipes which corresponds to given filters or all records if <em>filters</em> is empty
     * @param filters record filters
     * @param start index of first record
     * @param max maximum number of records to retrieve
     * @return 
     */
    List<Recipe> findAll(Map<String, Object> filters, int start, int max);
    
    /**
     * Return total of records
     * @return 
     */
    long getCount();
}
