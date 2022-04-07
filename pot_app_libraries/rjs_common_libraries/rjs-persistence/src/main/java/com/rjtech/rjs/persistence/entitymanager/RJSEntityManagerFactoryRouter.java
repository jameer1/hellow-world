package com.rjtech.rjs.persistence.entitymanager;

import com.rjtech.rjs.persistence.providers.ModuleCodeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.SharedEntityManagerCreator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;

public class RJSEntityManagerFactoryRouter implements RJSEntityManagerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(RJSEntityManagerFactoryRouter.class);

    private void log(String message) {
        LOGGER.info(message);
    }

    private Map<String, EntityManagerFactory> entityManagerFactoryMap;

    public void setEntityManagerFactoryMap(Map<String, EntityManagerFactory> entityManagerFactoryMap) {
        this.entityManagerFactoryMap = entityManagerFactoryMap;
    }

    private EntityManagerFactory defaultEntityManagerFactory;

    public void setDefaultEntityManagerFactory(EntityManagerFactory defaultEntityManagerFactory) {
        this.defaultEntityManagerFactory = defaultEntityManagerFactory;
    }

    public EntityManager getEntityManager() {
        String emfKey = null;
        if (ModuleCodeProvider.getCode() != null && entityManagerFactoryMap != null
                && entityManagerFactoryMap.containsKey(ModuleCodeProvider.getCode())) {
            emfKey = ModuleCodeProvider.getCode();
        }

        if (emfKey != null) {
            log("######### Injecting the entity maanger factory based on the key :" + emfKey
                    + " ######################");
            return SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactoryMap.get(emfKey));
        } else {
            log("######### Injecting the default entity maanger factory ######################");
            return SharedEntityManagerCreator.createSharedEntityManager(defaultEntityManagerFactory);
        }
    }
}
