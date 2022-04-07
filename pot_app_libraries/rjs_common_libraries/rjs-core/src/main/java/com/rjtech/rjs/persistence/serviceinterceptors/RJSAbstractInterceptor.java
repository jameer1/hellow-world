package com.rjtech.rjs.persistence.serviceinterceptors;

import org.aspectj.lang.JoinPoint;
import org.springframework.core.Ordered;

public abstract class RJSAbstractInterceptor implements Ordered {

    private int order = Integer.MAX_VALUE;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    protected String getMethodDetails(JoinPoint joinPoint) {
        String targetClass = joinPoint.getSignature().getDeclaringTypeName();
        String targetMethod = joinPoint.getSignature().getName();
        return targetClass + "." + targetMethod + "-";
    }

}
