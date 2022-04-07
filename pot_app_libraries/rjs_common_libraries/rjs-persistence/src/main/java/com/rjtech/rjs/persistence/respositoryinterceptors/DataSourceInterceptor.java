package com.rjtech.rjs.persistence.respositoryinterceptors;

import com.rjtech.rjs.persistence.providers.DatasourceProvider;
import com.rjtech.rjs.persistence.providers.TransactionTypeProvider;
import com.rjtech.rjs.persistence.providers.TransactionTypes;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

@Aspect
public class DataSourceInterceptor implements Ordered {

    private int order = Integer.MAX_VALUE;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceInterceptor.class);

    private void log(String message) {
        LOGGER.debug(message);
    }

    private String getMethodDetails(JoinPoint joinPoint) {
        String targetClass = joinPoint.getSignature().getDeclaringTypeName();
        String targetMethod = joinPoint.getSignature().getName();
        return targetClass + "." + targetMethod + "-";
    }

    @Pointcut("target(com.rjtech.rjs.persistence.repository.GenericRepository) && @target(org.springframework.stereotype.Repository)")
    public void repositoryPointcut() {
        // No Implementation
    }

    @Before("repositoryPointcut()")
    public void setDefaultDataSource(JoinPoint joinPoint) throws Throwable {
        String methodSignatureDetails = getMethodDetails(joinPoint);
        DatasourceProvider.setDatasource(DatasourceProvider.DEFAULT_RO_DATASOURCE);
        if (TransactionTypes.RW.equals(TransactionTypeProvider.getTransactionType())) {
            DatasourceProvider.setDatasource(DatasourceProvider.DEFAULT_RW_DATASOURCE);
        } else if (TransactionTypes.XA.equals(TransactionTypeProvider.getTransactionType())) {
            DatasourceProvider.setDatasource(DatasourceProvider.DEFAULT_XA_DATASOURCE);
        }
        log(methodSignatureDetails + " Setting  DataSource : " + DatasourceProvider.getDatasource());

    }

    @After("repositoryPointcut()")
    public void clearDataSource(JoinPoint joinPoint) throws Throwable {
        String methodSignatureDetails = getMethodDetails(joinPoint);
        log(methodSignatureDetails + " Clearing DataSource : " + DatasourceProvider.getDatasource());
        DatasourceProvider.clearDatasource();

    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
