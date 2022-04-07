package com.rjtech.rjs.persistence.providers;

public final class DatasourceProvider {

    public static final String DEFAULT_RO_DATASOURCE = "RODS";
    public static final String DEFAULT_RW_DATASOURCE = "RWDS";
    public static final String DEFAULT_XA_DATASOURCE = "XADS";

    private static final ThreadLocal<String> DATASOURCE_HOLDER = new ThreadLocal<String>();

    private DatasourceProvider() {
    }

    public static void setDatasource(String datasourceType) {
        DATASOURCE_HOLDER.set(datasourceType);
    }

    public static String getDatasource() {
        return (String) DATASOURCE_HOLDER.get();
    }

    public static void clearDatasource() {
        DATASOURCE_HOLDER.remove();
    }
}
