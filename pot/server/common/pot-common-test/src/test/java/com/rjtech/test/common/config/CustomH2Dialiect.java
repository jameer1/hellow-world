package com.rjtech.test.common.config;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * This dialect is for registering unsupported methods of {@link H2Dialect}
 *
 */
public class CustomH2Dialiect extends H2Dialect {

    public CustomH2Dialiect() {
        super();
        registerFunction("trunc", new StandardSQLFunction("trunc", StandardBasicTypes.TIMESTAMP));
        registerFunction("to_char", new StandardSQLFunction("to_char", StandardBasicTypes.TIMESTAMP));
    }

}
