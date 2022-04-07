package com.rjtech.rjs.persistence.datasources;

import com.rjtech.rjs.persistence.providers.DatasourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DatasourceRouter extends AbstractRoutingDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourceRouter.class);

    public DatasourceRouter() {
        super();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        // LOGGER.debug("DataSource : {}", DatasourceProvider.getDatasource());
        return DatasourceProvider.getDatasource();
    }

}
