package com.rjtech.timemanagement.workdairy.repository;

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
public class WorkDairyProgressProcRepository extends AbstractJDBCRepository {

    private static final String GET_WORK_DAIRY_SUBMITTED_PROGRESS = "GET_WORK_DAIRY_SUBMITTED_PROGRESS";
    private static final String PROJ_ID = "PROJ_ID";
    private static final String WORK_DAIRY_ID = "WORK_DAIRY_ID";
    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getWorkDairyProgress(Long projId, Long workdairyId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_WORK_DAIRY_SUBMITTED_PROGRESS);
        Map map = proc.getWorkDairyProgress(projId, workdairyId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (GET_WORK_DAIRY_SUBMITTED_PROGRESS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(WORK_DAIRY_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new WorkDairyEmpRegRowMapper() {
                }));
            }
            compile();
        }

        public Map getWorkDairyProgress(Long clientId, Long projId) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, clientId);
            inputs.put(WORK_DAIRY_ID, projId);
            return super.execute(inputs);
        }

    }

}
