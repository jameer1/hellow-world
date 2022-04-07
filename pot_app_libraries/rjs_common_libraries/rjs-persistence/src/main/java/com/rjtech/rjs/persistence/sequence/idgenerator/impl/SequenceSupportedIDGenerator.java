package com.rjtech.rjs.persistence.sequence.idgenerator.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.rjs.core.exception.RJSRuntimeException;
import com.rjtech.rjs.persistence.sequence.idgenerator.IDGenerator;

public class SequenceSupportedIDGenerator implements IDGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceSupportedIDGenerator.class);

    private static final String ORACLE_DIALECT = "org.hibernate.dialect.Oracle10gDialect";
    private static final String MY_SQL = "org.hibernate.dialect.MySQLDialect";

    public BigDecimal generate(EntityManager entityManager, String sequenceName) {
        if (null == entityManager) {
            LOGGER.error(" Entity manager should'nt be null in ID generator.");
            throw new RJSRuntimeException(" Entity manager should'nt be null in ID generator.");

        }
        if (null == sequenceName || sequenceName.trim().isEmpty()) {
            LOGGER.error(" SequenceName should'nt be null or empty in ID generator.");
            throw new RJSRuntimeException(" SequenceName should'nt be null or empty in ID generator.");

        }

        StringBuffer sql = new StringBuffer("SELECT ").append(sequenceName).append(".nextval from dual");
        String dialect = (String) entityManager.getEntityManagerFactory().getProperties().get("hibernate.dialect");

        if (dialect.equalsIgnoreCase(MY_SQL)) {
            return new BigDecimal((BigInteger) (entityManager.createNativeQuery(sql.toString()).getSingleResult()));
        }
        if (dialect.equalsIgnoreCase(ORACLE_DIALECT)) {
            return (BigDecimal) (entityManager.createNativeQuery(sql.toString()).getSingleResult());
        } else {
            LOGGER.error("{} not supported in ID generator", dialect);
            throw new RJSRuntimeException(dialect + " not supported in ID generator");
        }

    }

}
