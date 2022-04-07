package com.rjtech.rjs.persistence.sequence.idgenerator;

import javax.persistence.EntityManager;

public interface IDGenerator {

    Object generate(EntityManager entityManager, String dataBaseObjectName);

}
