package com.rjtech.rjs.persistence.serviceinterceptors;

import com.rjtech.rjs.core.annotations.RJSServiceMethod;
import com.rjtech.rjs.persistence.providers.TransactionTypeProvider;
import com.rjtech.rjs.persistence.providers.TransactionTypes;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Aspect
public class RJSTransactionInterceptor extends RJSAbstractInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RJSTransactionInterceptor.class);

    private void log(String message) {
        LOGGER.debug(message);
    }

    @Pointcut("@annotation(transactional)")
    public void transactionService(Transactional transactional) {
        // No implementation
    }

    @Pointcut("@annotation(rjsServiceMethod)")
    public void rjsServiceMethod(RJSServiceMethod rjsServiceMethod) {
    }

    @Around("@annotation(transactional) && !@annotation(com.rjtech.rjs.core.annotations.RJSServiceMethod)")
    public Object transactionWithoutRJSServiceMethod(ProceedingJoinPoint pjp, Transactional transactional)
            throws Throwable {
        return executeService(pjp, transactional, false);
    }

    @Around("transactionService(transactional) && rjsServiceMethod(rjsServiceMethod)")
    public Object transactionWithRJServiceMethod(ProceedingJoinPoint pjp, Transactional transactional,
            RJSServiceMethod rjsServiceMethod) throws Throwable {
        return executeService(pjp, transactional, rjsServiceMethod.txXAEnabled());
    }

    public Object executeService(ProceedingJoinPoint pjp, Transactional transactional, boolean isXAEnabled)
            throws Throwable {

        Object retVal = null;
        boolean isTransactionTypeAvilable = true;
        String methodSignatureDetails = getMethodDetails(pjp);
        if (TransactionTypeProvider.getTransactionType() == null) {
            isTransactionTypeAvilable = false;
            // Setting default to RO.
            TransactionTypeProvider.setTransactionType(TransactionTypes.RO);
            if (!transactional.readOnly()) {
                TransactionTypeProvider.setTransactionType(TransactionTypes.RW);
            }
            // Overriding the decision based on @Transactional with
            // @RJServiceMethod
            if (isXAEnabled) {
                TransactionTypeProvider.setTransactionType(TransactionTypes.XA);
            }
            log(methodSignatureDetails + " Setting Transaction type to "
                    + TransactionTypeProvider.getTransactionType());
        } else {
            log(methodSignatureDetails + " Not Setting Transaction type as it is already set");
        }

        try {
            retVal = pjp.proceed();
        } catch (Exception e) {
            if (!TransactionTypes.RO.equals(TransactionTypeProvider.getTransactionType())) {
                log(methodSignatureDetails + " Setting the transaction to RollBack");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            throw e;
        } finally {
            if (!isTransactionTypeAvilable) {
                log(methodSignatureDetails + "-" + "Clearing Transaction type");
                TransactionTypeProvider.clearTransactionType();
            }
        }
        return retVal;

    }

}
