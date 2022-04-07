package com.rjtech.rjs.persistence.serviceinterceptors;

import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.rjs.persistence.providers.ModuleCodeProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Aspect
public class ModuleManagerInterceptor extends RJSAbstractInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleManagerInterceptor.class);

    @Pointcut("within(com.rjtech..*) && @target(rjsService)")
    public void rjServicePointcut(RJSService rjsService) {
    }

    @Pointcut("@annotation(transactional)")
    public void transactionService(Transactional transactional) {
        // No Implementation
    }

    @Around("transactionService(transactional) && rjServicePointcut(rjsService)")
    public Object checkRJServiceAnnotation(ProceedingJoinPoint pjp, Transactional transactional, RJSService rjsService)
            throws Throwable {
        boolean isParentModule = ModuleCodeProvider.isParent();
        ModuleCodeProvider.setModuleCode(rjsService.modulecode());
        log(getMethodDetails(pjp) + "checkRJSServiceAnnotation(): Setting Module Code :" + rjsService.modulecode());
        try {
            return pjp.proceed();
        } catch (Exception e) {
            LOGGER.error("Exception in checkRJSServiceAnnotation() :" + e.getMessage());
            throw e;
        } finally {
            if (isParentModule) {
                ModuleCodeProvider.clearAllModuleCodes();
            } else {
                ModuleCodeProvider.clearModuleCode();
            }
        }
    }

    private void log(String message) {
        LOGGER.info(message);
    }

}
