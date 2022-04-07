package com.rjtech.test.common.runner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rjtech.test.common.config.DataTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DataTestConfig.class })
public class TestRunner {

    private static final Logger log = LoggerFactory.getLogger(TestRunner.class);

    @Test
    public void testHqlQueries() {
        log.info("Test HQL Queries with H2 DB");
    }

}
