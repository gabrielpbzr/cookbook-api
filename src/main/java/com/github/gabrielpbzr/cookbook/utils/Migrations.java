package com.github.gabrielpbzr.cookbook.utils;

import com.github.gabrielpbzr.cookbook.exceptions.DatabaseMigrationException;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

/**
 * Database migrations class
 *
 * @author gabriel
 */
public class Migrations {

    private Migrations() {
    }

    /**
     * Apply database migrations
     * @param config application settings
     * @throws DatabaseMigrationException if something goes wrong during migration
     */
    public static void apply(Configuration config) throws DatabaseMigrationException {
        try {
            String url = config.getValue("jdbc.url");
            String user = config.getValue("jdbc.user");
            String password = config.getValue("jdbc.password");
            Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
            flyway.migrate();
        } catch (FlywayException fe) {
            throw new DatabaseMigrationException(fe);
        }
    }
}
