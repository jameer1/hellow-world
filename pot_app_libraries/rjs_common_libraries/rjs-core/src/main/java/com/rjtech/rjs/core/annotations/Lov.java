package com.rjtech.rjs.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Lov {
    enum LogicalCondition {

        OR, AND
    }

    enum LovSourceType {

        M, AND
    }

    String lovDesc();

    String lovSourceType();

    String lovSource();

    LovField[] fields();

    LovResult[] results();

    LogicalCondition logicalCondition() default LogicalCondition.OR;

}
