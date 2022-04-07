package com.rjtech.test.common.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = "com.rjtech")
public class DataTestConfig {

    private static final Logger log = LoggerFactory.getLogger(DataTestConfig.class);

    @Bean
    public HibernateJpaVendorAdapter getHibernateJpaAdapter() {
        HibernateJpaVendorAdapter adpter = new HibernateJpaVendorAdapter();
        adpter.setShowSql(true);
        adpter.setGenerateDdl(true);
        adpter.setDatabase(Database.H2);
        adpter.setDatabasePlatform("com.rjtech.test.common.config.CustomH2Dialiect");

        return adpter;
    }

    @Bean(name = "entityManagerFactory") // same name "entityManagerFactory" required if u change name
                                         // then @EnableJpaRepositories(entityManagerFactoryRef="name"
                                         // ,basePackages="com.data.repositories")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() throws IOException {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(getDataSource());
        factory.setJpaVendorAdapter(getHibernateJpaAdapter());
        factory.setPackagesToScan("com.rjtech"); // packages where you wrote @Entity classes.
                                                 // (Domain classes)
        return factory;
    }

    @Bean
    public DataSource getDataSource() {
        log.info("Embedded data source created");
        return new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2).build();
    }

}