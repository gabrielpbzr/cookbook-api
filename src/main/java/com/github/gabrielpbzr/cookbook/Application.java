package com.github.gabrielpbzr.cookbook;

import com.github.gabrielpbzr.cookbook.recipes.RecipeController;
import com.github.gabrielpbzr.cookbook.recipes.RecipeDAO;
import com.github.gabrielpbzr.cookbook.recipes.RecipeDAOJPA;
import com.github.gabrielpbzr.cookbook.utils.Configuration;
import com.github.gabrielpbzr.cookbook.utils.EntityManagerProvider;
import io.javalin.Javalin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 *
 * @author gabriel
 */
public final class Application {
    private final Logger logger;
    private final EntityManagerProvider entityManagerProvider;
    private Javalin javalinApp;

    public Application(@NotNull Configuration config) {
        this.logger = LoggerFactory.getLogger(config.getValue("app.name"));
        this.entityManagerProvider = new EntityManagerProvider(config);
    }

    private void init() {
        logger.debug("Initializing app...");
        RecipeDAO recipeDao = new RecipeDAOJPA(entityManagerProvider.getEntityManager());
        RecipeController recipeController = new RecipeController(recipeDao, logger);
        this.javalinApp = Javalin.create().routes(() -> {
            crud("/api/recipes/:id", recipeController);
        });
        logger.debug("App initialized!");
    }

    public void start(int port) {
        this.init();
        javalinApp.start(port);
        logger.debug("Application started on port "+ port);
    }

    public void stop() {
        logger.info("Application is stopping...");
        javalinApp.stop();
        logger.info("Application stopped");
    }
}
