package com.rjtech.rjs.persistence.serviceinterceptors;

import com.rjtech.rjs.persistence.exception.RJSServiceAnnotationNotDefinedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class ServiceValidatorInterceptor extends RJSAbstractInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceValidatorInterceptor.class);

    private void log(String message) {
        LOGGER.error(message);
    }

    @Pointcut("within(com.rjtech..*) && @target(com.rjtech.rjs.core.annotations.RJSService)")
    public void rjsServicePointcut() {

    }

    /**
     * <p>
     * sequenceGenerator.
     * </p>
     */
    @Pointcut("target(com.rjtech.rjs.persistence.sequence.idgenerator.IDGenerator)")
    public void sequenceGenerator() {

    }

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional) || @annotation(com.rjtech.rjs.core.annotations.RJSServiceMethod)")
    public void transactionService() {

    }

    @Before("transactionService() && !rjsServicePointcut() && !sequenceGenerator()")
    public void checkRJSServiceAnnotation(JoinPoint joinPoint) throws Throwable {
        StringBuffer reason = new StringBuffer();
        reason.append(getMethodDetails(joinPoint)).append(
                "############################### ERROR : @Transactional or @RJServiceMethod annotation is defined  method level without @RJSService annotation in the class level.\r\n")
                .append("Please correct this as this may lead to unexpected behaviour############");
        log(reason.toString());
        throw new RJSServiceAnnotationNotDefinedException(reason.toString());
    }

}
