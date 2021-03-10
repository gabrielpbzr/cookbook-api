package com.github.gabrielpbzr.cookbook.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * EntityManager provider
 * @author gabriel
 */
public class EntityManagerProvider {
    private final EntityManagerFactory factory;
    private static final String UNIT_NAME = "cookbookPU";
    
    
    public EntityManagerProvider(Configuration configuration) {
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.driver", configuration.getValue("jdbc.driver"));
        properties.put("javax.persistence.jdbc.url", configuration.getValue("jdbc.url"));
        properties.put("javax.persistence.jdbc.user", configuration.getValue("jdbc.user"));
        properties.put("javax.persistence.jdbc.password", configuration.getValue("jdbc.password"));
        factory = Persistence.createEntityManagerFactory(UNIT_NAME, properties);
    }
    public EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
