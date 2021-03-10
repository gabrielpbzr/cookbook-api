package com.github.gabrielpbzr.cookbook.web;

import com.github.gabrielpbzr.cookbook.exceptions.RecordNotFoundException;
import com.github.gabrielpbzr.cookbook.recipes.Recipe;
import com.github.gabrielpbzr.cookbook.recipes.RecipeDAO;
import com.github.gabrielpbzr.cookbook.utils.Strings;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


/**
 * REST API Controller for Recipes
 * @author gabriel
 */
public class RecipeController implements CrudHandler {
    private final RecipeDAO recipeDao;
    private final Logger logger;
    private static final int RECORDS_PER_PAGE = 20;
    private static final String LOG_MSG_FORMAT = "Recipe '%s' was %s";

    public RecipeController(@NotNull RecipeDAO recipeDao, @NotNull Logger logger) {
        this.recipeDao = recipeDao;
        this.logger = logger;
    }

    @Override
    public void create(@NotNull Context ctx) {
        try {
            Recipe recipe = getRecipeFromRequest(ctx);
            recipeDao.save(recipe);
            ctx.status(HttpStatus.CREATED_201).header("Location", ctx.fullUrl()+"/"+recipe.getUuid());
            logger.info(String.format(LOG_MSG_FORMAT, recipe.getUuid(), "created"));
        } catch (IllegalArgumentException ile) {
            ctx.status(HttpStatus.BAD_REQUEST_400).result(ile.getLocalizedMessage());
        } catch (Exception e) {
            logger.error("Error during recipe creation", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
    }

    @Override
    public void delete(@NotNull Context ctx, @NotNull String id) {
        try {
            final Recipe recipe = getRecipeFromId(id);
            recipeDao.remove(recipe);
            ctx.status(HttpStatus.OK_200).result("Deleted successfully");
            logger.info(String.format(LOG_MSG_FORMAT, id, "deleted"));
        } catch(RecordNotFoundException rnfe) {
            ctx.status(HttpStatus.NOT_FOUND_404);
        }
    }

    @Override
    public void getAll(@NotNull Context ctx) {
        int page = ctx.queryParam("page", Integer.class, "0").get();
        List<Recipe> recipes = recipeDao.findAll(Collections.emptyMap(), page, RECORDS_PER_PAGE);
        long totalPages = recipeDao.getCount() / RECORDS_PER_PAGE;
       
        PaginatedResponse paginatedResponse = new PaginatedResponse(page, totalPages, recipes);
        ctx.status(200).json(paginatedResponse);
    }

    @Override
    public void getOne(@NotNull Context ctx, @NotNull String id) {
        try {
            final Recipe recipe = getRecipeFromId(id);
            ctx.status(200).json(recipe);
        } catch(RecordNotFoundException rnfe) {
            ctx.status(HttpStatus.NOT_FOUND_404);
        }
    }

    @Override
    public void update(@NotNull Context ctx, @NotNull String id) {
        try {
            Recipe recipe = getRecipeFromId(id);
            Recipe requestRecipe = getRecipeFromRequest(ctx);
            recipe.setTitle(requestRecipe.getTitle());
            recipe.setContent(requestRecipe.getContent());
            recipeDao.update(recipe);
            logger.info(String.format(LOG_MSG_FORMAT, id, "updated"));
            ctx.status(HttpStatus.OK_200);
        } catch(RecordNotFoundException rnfe) {
            ctx.status(HttpStatus.NOT_FOUND_404);
        }
    }

    private Recipe getRecipeFromId(String id) throws RecordNotFoundException {
        if (Strings.isNullOrEmpty(id)) {
            throw new RecordNotFoundException();
        }
        return recipeDao.findByUUID(UUID.fromString(id)).orElseThrow(RecordNotFoundException::new);
    }
    
    private Recipe getRecipeFromRequest(Context ctx) {
        String title = ctx.formParam("title");
        if (Strings.isNullOrEmpty(title)) {
            throw new IllegalArgumentException("Title is mandatory");
        }
        
        String content = ctx.formParam("content");
        if (Strings.isNullOrEmpty(content)) {
            throw new IllegalArgumentException("Content is mandatory");
        }
 
        String uuid = ctx.formParam("uuid");
        if (Strings.isNullOrEmpty(uuid)) {
            return new Recipe(title, content);
        }
        return new Recipe(title, content, UUID.fromString(uuid));
    }
}
