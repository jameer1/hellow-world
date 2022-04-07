
package com.rjtech.rjs.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LovResult {

    String colHeader();

    String colName();

}
