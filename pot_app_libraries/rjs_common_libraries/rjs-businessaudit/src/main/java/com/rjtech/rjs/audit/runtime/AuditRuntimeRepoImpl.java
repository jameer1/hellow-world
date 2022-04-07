package com.rjtech.rjs.audit.runtime;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rjtech.rjs.core.exception.RJSRuntimeException;
import com.rjtech.rjs.persistence.repository.AbstractJDBCRepository;

/**
 * @author Sreenivasa Rao Kollu
 * @description Repository Impl which uses jdbcTemplate to query DB.

 */

@Repository
public class AuditRuntimeRepoImpl extends AbstractJDBCRepository implements AuditRuntimeRepo {
    private static final Logger LOG = LoggerFactory.getLogger(AuditRuntimeRepoImpl.class);

    private static final String GET_SYS_PARAM_QUERY = "select sys_value from system_parameter where sys_key='auditStrategy' ";

    /**
     * <p>getRuntimeAuditFlagDB.</p>
     *
     * @return a {@link java.lang.String} object.
     * @throws java.lang.Exception if any.
     */
    public String getRuntimeAuditFlagDB() throws RJSRuntimeException {
        LOG.debug("getRuntimeAuditFlag() invoked...");
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        List<String> sysParmValList = null;
        String auditFlagDB = "true";
        try {
            if (jdbcTemplate != null) {
                sysParmValList = jdbcTemplate.queryForList(GET_SYS_PARAM_QUERY, String.class);

                if (sysParmValList != null && !sysParmValList.isEmpty()) {
                    auditFlagDB = sysParmValList.get(0);
                    LOG.debug("getRuntimeAuditFlag(): auditFlagDB : " + auditFlagDB);
                }
            } else {
                LOG.debug("getRuntimeAuditFlag(): jdbcTemplate is null...");
            }
        } catch (Exception e) {
            throw new RJSRuntimeException(e);
        }

        return auditFlagDB;
    }

}
