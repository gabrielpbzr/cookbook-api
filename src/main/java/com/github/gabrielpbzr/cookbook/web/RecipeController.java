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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * REST API Controller for Recipes
 * @author gabriel
 */
public class RecipeController implements CrudHandler {
    private final RecipeDAO recipeDao;
    private final Logger logger;

    public RecipeController(@NotNull RecipeDAO recipeDao, @NotNull Logger logger) {
        this.recipeDao = recipeDao;
        this.logger = logger;
    }

    @Override
    public void create(@NotNull Context ctx) {
        //TODO Implement
        ctx.result("Creating new recipe");
    }

    @Override
    public void delete(@NotNull Context ctx, @NotNull String s) {
        //TODO Implement
        try {
            final Recipe recipe = getRecipeFromId(s);
            recipeDao.remove(recipe);
            ctx.status(HttpStatus.OK_200).result("Deleted successfully");
            logger.info("Recipe '%s' was deleted");
        } catch(RecordNotFoundException rnfe) {
            ctx.status(HttpStatus.NOT_FOUND_404);
        }

    }

    @Override
    public void getAll(@NotNull Context ctx) {
        //TODO Implement
        ctx.result("Listing all recipes");
    }

    @Override
    public void getOne(@NotNull Context ctx, @NotNull String s) {
        //TODO Implement
        try {
            final Recipe recipe = getRecipeFromId(s);
            ctx.result(String.format("Listing recipe %s", s));
        } catch(RecordNotFoundException rnfe) {
            ctx.status(HttpStatus.NOT_FOUND_404);
        }
    }

    @Override
    public void update(@NotNull Context ctx, @NotNull String s) {
        //TODO Implement
        try {
            final Recipe recipe = getRecipeFromId(s);
            ctx.result(String.format("Updating recipe %s", s));
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
        String content = ctx.formParam("content");
        String uuid = ctx.formParam("uuid");
        return new Recipe(title, content, UUID.fromString(uuid));
    }
}
