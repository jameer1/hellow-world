package com.rjtech.rjs.validation.aspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.rjs.core.exception.RJSRuntimeException;
import com.rjtech.rjs.validation.exception.RJSValidationException;

@Aspect
public class RJSValidationAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RJSValidationAspect.class);
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @Pointcut("@annotation(com.rjtech.rjs.core.annotations.RJSValidate)")
    public void exceptionPoincut() {
    }

    @SuppressWarnings("deprecation")
    @AfterThrowing(pointcut = "exceptionPoincut()", throwing = "exception")
    public void exceptionCatching(Exception exception) {
        LOGGER.error("Unexpected Error Occurred", exception.getMessage());
        if (exception instanceof RJSValidationException) {
            throw (RJSValidationException) exception;
        } /*else if (exception instanceof MethodConstraintViolationException) {
          List<ConstraintViolation<?>> violationList = new ArrayList<ConstraintViolation<?>>();
          for (ConstraintViolation<?> violation : ((MethodConstraintViolationException) exception)
          		.getConstraintViolations()) {
          	violationList.add(violation);
          }
          if (!violationList.isEmpty()) {
          	throw new RJSValidationException("Validation Failed", violationList);
          }
          }*/
        throw new RJSRuntimeException("Unexpected Error Occurred", exception);
    }

    @Around(value = "@annotation(com.rjtech.rjs.core.annotations.RJSValidate)")
    public Object serviceMethodValidation(ProceedingJoinPoint joinPoint) throws Throwable {
        Validator validator = validatorFactory.getValidator();
        LOGGER.debug("Inside Validation");

        List<ConstraintViolation<?>> violationList = new ArrayList<ConstraintViolation<?>>();

        for (Object object : joinPoint.getArgs()) {
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
            for (ConstraintViolation<?> violation : constraintViolations) {
                violationList.add(violation);
            }
            LOGGER.info("POJO Message {}", constraintViolations);
        }

        if (!violationList.isEmpty()) {
            throw new RJSValidationException("Validation Failed", violationList);
        }
        Object o = joinPoint.proceed();
        return o;
    }
}
