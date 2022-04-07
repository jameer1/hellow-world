package com.rjtech.rjs.persistence.sequence.idgenerator.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.rjs.persistence.sequence.idgenerator.IDGenerator;

public class SequenceNotSupportedIDGenerator implements IDGenerator {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object generate(EntityManager entityManager, String tableName) {
        Long result = Long.valueOf(((BigInteger) entityManager
                .createNativeQuery("SELECT id FROM " + tableName + " for update").getSingleResult()).longValue());
        entityManager.createNativeQuery("UPDATE " + tableName + " SET ID = ? WHERE ID = ? ").setParameter(1, result + 1)
                .setParameter(2, result).executeUpdate();
        return new BigDecimal(result);
    }

}
