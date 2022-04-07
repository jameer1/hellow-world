package com.rjtech.rjs.persistence.postprocessors;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

import javax.persistence.spi.PersistenceUnitTransactionType;

public class JtaPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {

    private boolean jtaMode = true;
    private PersistenceUnitTransactionType transacType = PersistenceUnitTransactionType.RESOURCE_LOCAL;

    /**
     * {@inheritDoc}
     */
    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo mutablePersistenceUnitInfo) {

        if (jtaMode) {
            transacType = PersistenceUnitTransactionType.JTA;
        }

        mutablePersistenceUnitInfo.setTransactionType(transacType);

    }

    /**
     * <p>
     * isJtaMode.
     * </p>
     *
     * @return a boolean.
     */
    public boolean isJtaMode() {
        return jtaMode;
    }

    /**
     * <p>
     * Setter for the field <code>jtaMode</code>.
     * </p>
     *
     * @param jtaMode a boolean.
     */
    public void setJtaMode(boolean jtaMode) {
        this.jtaMode = jtaMode;
    }
}
