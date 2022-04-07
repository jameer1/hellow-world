package com.rjtech.config;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

public class AtomikosJtaPlatform  extends AbstractJtaPlatform{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6211791375719435102L;
	private static TransactionManager transactionManager;
    private static UserTransaction userTransaction;
    
    public static void factoryInit(UserTransactionManager transactionManager,UserTransactionImp userTransaction) {
        AtomikosJtaPlatform.transactionManager = transactionManager;
        AtomikosJtaPlatform.userTransaction = userTransaction;
    }

    @Override
    protected TransactionManager locateTransactionManager() {
        return transactionManager;
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return userTransaction;
    }
}


