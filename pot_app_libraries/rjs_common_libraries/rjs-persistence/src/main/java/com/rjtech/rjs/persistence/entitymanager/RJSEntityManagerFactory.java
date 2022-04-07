package com.rjtech.rjs.persistence.entitymanager;

import javax.persistence.EntityManager;

public interface RJSEntityManagerFactory {

    EntityManager getEntityManager();
}
