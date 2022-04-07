package com.rjtech.rjs.persistence.jdbc;

import com.rjtech.rjs.persistence.providers.ModuleCodeProvider;

import javax.sql.DataSource;
import java.util.Map;

public class RJSJDBCDataSourceRouter {

    private Map<String, DataSource> jdbcDataSourceMap;

    private DataSource defaultDataSource;

    public Map<String, DataSource> getDataSourceMap() {
        return jdbcDataSourceMap;
    }

    public void setDataSourceMap(Map<String, DataSource> dataSourceMap) {
        this.jdbcDataSourceMap = dataSourceMap;
    }

    public DataSource getDefaultDataSource() {
        return defaultDataSource;
    }

    public void setDefaultDataSource(DataSource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    public DataSource getDataSource() {
        String dataSourceKey = null;
        if (ModuleCodeProvider.getCode() != null && jdbcDataSourceMap != null
                && jdbcDataSourceMap.containsKey(ModuleCodeProvider.getCode())) {
            dataSourceKey = ModuleCodeProvider.getCode();
        }
        if (dataSourceKey != null) {
            return (DataSource) jdbcDataSourceMap.get(dataSourceKey);
        } else {
            return defaultDataSource;
        }
    }

}
