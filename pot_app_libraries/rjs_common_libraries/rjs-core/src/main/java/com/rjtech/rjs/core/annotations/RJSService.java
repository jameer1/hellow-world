
package com.rjtech.rjs.core.annotations;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used for identifying that the target class is an
 * RJSService. The code specifies the module code. The module code is mandatory
 * and adding proper module code will help in reducing the portability of
 * application from database perspective. Also with the module code, it is easy
 * to modularise the application from the database perspective( for eg:- one
 * module can access one database(or schema ) and another module can access
 * another database(or schema). Usually one module can have more than one
 * service class and all service in the same module should have the same code.
 *
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface RJSService {

    /**
     * Used for specifying the module code of the target interface.
     *
     * @return string depicting the module code
     */
    String modulecode();

}
