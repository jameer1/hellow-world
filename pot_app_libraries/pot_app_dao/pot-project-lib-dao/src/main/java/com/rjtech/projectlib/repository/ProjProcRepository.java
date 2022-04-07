package com.rjtech.projectlib.repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.rjs.persistence.repository.AbstractJDBCRepository;

@Repository
public class ProjProcRepository extends AbstractJDBCRepository {

    public static final String GET_PROJECT_CODES = "GET_PROJECT_CODES";
    public static final String GET_EPS_PROJECT_CODES = "GET_EPS_PROJECT_CODES";
    public static final String GET_SOW_ITEMS_BY_PROJECTS = "GET_SOW_ITEMS_BY_PROJECTS";
    public static final String GET_SOR_DETAILS_BY_PROJECTS = "GET_SOR_DETAILS_BY_PROJECTS";
    public static final String GET_COST_CODES_BY_PROJECTS = "GET_COST_CODES_BY_PROJECTS";

    private static final String PROJ_IDS = "PROJ_IDS";
    private static final String RESULT_DATA = "RESULT_DATA";
    private static final String CLIENT_ID = "CLIENT_ID";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getProjCodes(String projIds) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_PROJECT_CODES);
        Map map = proc.execute(projIds);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getEPSProjCodes(Long clientId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_EPS_PROJECT_CODES);
        Map map = proc.eps(clientId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getProjSOWItems(String projIds) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_SOW_ITEMS_BY_PROJECTS);
        Map map = proc.execute(projIds);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getProjSORItems(String projIds) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_SOR_DETAILS_BY_PROJECTS);
        Map map = proc.execute(projIds);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getProjCostCodeItems(String projIds) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_COST_CODES_BY_PROJECTS);
        Map map = proc.execute(projIds);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (GET_PROJECT_CODES.equalsIgnoreCase(sprocName) || GET_COST_CODES_BY_PROJECTS.equalsIgnoreCase(sprocName)
                    || GET_SOR_DETAILS_BY_PROJECTS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_IDS, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new ProjCodeRowMapper() {
                }));
            } else if (GET_SOW_ITEMS_BY_PROJECTS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_IDS, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new ProjSOWItemRowMapper() {
                }));
            } else if (GET_EPS_PROJECT_CODES.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new EpsCodeRowMapper() {
                }));
            }

            compile();
        }

        public Map execute(String projIds) {
            Map inputs = new HashMap();
            inputs.put(PROJ_IDS, projIds);
            return super.execute(inputs);
        }

        public Map eps(Long clientId) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            return super.execute(inputs);
        }

    }

}
