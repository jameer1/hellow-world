package com.rjtech.rjs.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LovField {

    enum FieldType {
        TEXT_BOX, DATE, CHECK_BOX
    }

    ;

    enum CompareType {

        EQUALS, LIKE
    }

    String fieldName();

    String fieldLabel();

    FieldType fieldType();

    String defaultValue() default "";

    String toolTip();

    String messageKey();

    CompareType compareType() default CompareType.LIKE;

}
