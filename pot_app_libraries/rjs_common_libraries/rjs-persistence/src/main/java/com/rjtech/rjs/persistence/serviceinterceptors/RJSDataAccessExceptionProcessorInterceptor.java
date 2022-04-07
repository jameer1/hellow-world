package com.rjtech.rjs.persistence.serviceinterceptors;

import com.rjtech.rjs.core.exception.RJSRuntimeException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

@Aspect
public class RJSDataAccessExceptionProcessorInterceptor extends RJSAbstractInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RJSDataAccessExceptionProcessorInterceptor.class);

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionService() {
    }

    @Pointcut("@annotation(com.rjtech.rjs.core.annotations.RJSServiceMethod)")
    public void rjsServiceMethod() {
    }

    @Pointcut("@annotation(com.rjtech.rjs.core.annotations.RJSService)")
    public void rjsService() {
    }

    @AfterThrowing(pointcut = "transactionService() || rjsServiceMethod() || rjsService()", throwing = "dataAccessException")
    public void convertException(DataAccessException dataAccessException) {
        LOGGER.error("convertException() : dataAccessException.getRootCause() : "
                + dataAccessException.getRootCause().toString());
        throw new RJSRuntimeException(
                "Unexpected Error Occured while accessing the DataAccessLayer.Please check the server log for more details");

    }

}
