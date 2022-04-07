package com.rjtech.pot.db.flyway;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class DbMigration {

    private static final Logger log = LoggerFactory.getLogger(DbMigration.class);

    /**
     * Default FlywayMigrationInitializer object for flyway initialization
     * 
     * @param flyway
     * @return
     */
    //@Bean
    public FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        log.info("Initializig flyway migration");
        return new FlywayMigrationInitializer(flyway, null);
    }

}
