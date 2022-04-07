package com.rjtech.rjs.persistence.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.rjtech.rjs.persistence.jdbc.RJSJDBCDataSourceRouter;
import com.rjtech.rjs.persistence.providers.ModuleCodeProvider;
import javax.annotation.Resource;

public abstract class AbstractJDBCRepository implements GenericRepository {
    private static Map<String, NamedParameterJdbcTemplate> namedParameterJdbcTemplateMap = Collections
            .synchronizedMap(new HashMap<String, NamedParameterJdbcTemplate>());
    private static Map<String, JdbcTemplate> jdbcTemplateMap = Collections
            .synchronizedMap(new HashMap<String, JdbcTemplate>());
    private static NamedParameterJdbcTemplate defaultNamedParameterJdbcTemplate;
    private static JdbcTemplate defaultJdbcTemplate;

    @Resource(name = "rjsJDBCDataSourceRouter")
    private RJSJDBCDataSourceRouter rjsJDBCDataSourceRouter;

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        if (ModuleCodeProvider.getCode() == null) {
            if (defaultNamedParameterJdbcTemplate == null) {
                defaultNamedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                        rjsJDBCDataSourceRouter.getDataSource());
            }
            return defaultNamedParameterJdbcTemplate;
        } else {
            if (!namedParameterJdbcTemplateMap.containsKey(ModuleCodeProvider.getCode())) {
                namedParameterJdbcTemplateMap.put(ModuleCodeProvider.getCode(),
                        new NamedParameterJdbcTemplate(rjsJDBCDataSourceRouter.getDataSource()));
            }

            return namedParameterJdbcTemplateMap.get(ModuleCodeProvider.getCode());
        }
    }

    public JdbcTemplate getJdbcTemplate() {
        if (ModuleCodeProvider.getCode() == null) {
            if (defaultJdbcTemplate == null) {
                defaultJdbcTemplate = new JdbcTemplate(rjsJDBCDataSourceRouter.getDataSource());
            }
            return defaultJdbcTemplate;
        } else {
            if (!jdbcTemplateMap.containsKey(ModuleCodeProvider.getCode())) {
                jdbcTemplateMap.put(ModuleCodeProvider.getCode(),
                        new JdbcTemplate(rjsJDBCDataSourceRouter.getDataSource()));
            }

            return jdbcTemplateMap.get(ModuleCodeProvider.getCode());
        }
    }

}
