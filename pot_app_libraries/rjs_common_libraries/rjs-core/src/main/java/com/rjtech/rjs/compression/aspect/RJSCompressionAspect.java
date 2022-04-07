package com.rjtech.rjs.compression.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rjtech.rjs.compression.dto.RJSZippedObject;
import com.rjtech.rjs.compression.services.RJSCompressionService;
import com.rjtech.rjs.core.annotations.RJSCompression;
import com.rjtech.rjs.core.exception.RJSRuntimeException;

@Aspect
public class RJSCompressionAspect<E, R> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RJSCompressionAspect.class);

    @Pointcut("within(com.rjtech..*) && (@target(org.springframework.stereotype.Service) || @target(com.rjtech.rjs.core.annotations.RJSService)) ")
    public void servicePointCut() {
    }

    @Pointcut("servicePointCut() && @annotation(rjsMethodCompression)")
    public void compressionMethodPointCut(RJSCompression rjsMethodCompression) {
    }

    @Autowired
    RJSCompressionService<E, R> rJSCompressionService;

    @Around("compressionMethodPointCut(rjsMethodCompression)")
    public Object compressionMethodPointCutHandler1(ProceedingJoinPoint jointPoint, RJSCompression rjsMethodCompression)
            throws Throwable {
        LOGGER.debug(
                "@RJSCompression annotation found at Method level, compressionMethodPointCutHandler1() invoked ...");

        Object retObj = null;

        if (rjsMethodCompression.compressedArgument() || rjsMethodCompression.compressedReturn()) {
            retObj = handleCompression(jointPoint, rjsMethodCompression.compressedArgument(),
                    rjsMethodCompression.compressedReturn());
        } else {
            retObj = jointPoint.proceed();
        }
        return retObj;
    }

    @SuppressWarnings("unchecked")
    private R handleCompression(ProceedingJoinPoint joinPoint, boolean compressedArgument, boolean compressedReturnVal)
            throws Throwable {
        LOGGER.debug("handleCompression() invoked ...");

        boolean invokewithCompressed = false;
        boolean invokewithUnCompressed = false;

        Object[] compressedArgs = null;
        Object[] unCompressedArgs = null;

        E returnObj = null;
        R finalObj = null;

        try {

            Object[] args = joinPoint.getArgs();

            if (compressedArgument && args.length > 0) {

                unCompressedArgs = new Object[args.length];
                compressedArgs = new Object[args.length];

                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof RJSZippedObject) {
                        RJSZippedObject rjsZippedObjectIn = (RJSZippedObject) (args[i]);
                        unCompressedArgs[i] = rJSCompressionService.unCompressObject(rjsZippedObjectIn);
                        invokewithUnCompressed = true;
                    } else {
                        compressedArgs[i] = rJSCompressionService.compressObject((E) args[i]);
                        invokewithCompressed = true;
                    }
                }
                if (invokewithCompressed) {
                    LOGGER.debug("handleCompression():compressedArgs is true...compressedArgs.length  : "
                            + compressedArgs.length);
                    returnObj = (E) joinPoint.proceed(compressedArgs);
                } else if (invokewithUnCompressed) {
                    LOGGER.debug("handleCompression():unCompressedArgs is true...unCompressedArgs.length :  "
                            + unCompressedArgs.length);
                    returnObj = (E) joinPoint.proceed(unCompressedArgs);
                }
            } else {
                LOGGER.debug("handleCompression(): Compressed Argument is false... ");
                returnObj = (E) joinPoint.proceed(args);
            }

            if (compressedReturnVal && !(returnObj instanceof RJSZippedObject) && returnObj != null) {

                LOGGER.debug("handleCompression():Compressed Return is true....  ");
                finalObj = (R) rJSCompressionService.compressObject(returnObj);

            } else if (returnObj != null && compressedReturnVal && (returnObj instanceof RJSZippedObject)) {

                LOGGER.debug("handleCompression():UnCompress Return is true ...  ");

                RJSZippedObject rjsZippedReturnObject = (RJSZippedObject) returnObj;
                finalObj = rJSCompressionService.unCompressObject(rjsZippedReturnObject);

            } else {

                LOGGER.debug("handleCompression():Compressed Return is false ...  ");
                finalObj = (R) returnObj;
            }

            LOGGER.debug("handleCompression() : finalObj : " + finalObj);

        } catch (Exception e) {
            LOGGER.error(">> Exception in handleCompression() : e.getMessage() : " + e.getMessage(), e);
            throw new RJSRuntimeException("Error compressing Objects.Due to : " + e.getMessage());
        }
        LOGGER.debug("handleCompression() ends...");
        return finalObj;
    }
}
