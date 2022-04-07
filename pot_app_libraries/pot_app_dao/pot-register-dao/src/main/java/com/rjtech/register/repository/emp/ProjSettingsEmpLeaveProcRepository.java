package com.rjtech.register.repository.emp;

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
public class ProjSettingsEmpLeaveProcRepository extends AbstractJDBCRepository {

    private static final String PROJ_SETTINGS_CHECK_FOR_EMP_LEAVE_REQUEST = "PROJ_SETTINGS_CHECK_FOR_EMP_LEAVE_REQUEST";

    private static final String PROJ_ID = "PROJ_ID";
    private static final String EMP_LEAVE_ID = "EMP_LEAVE_ID";
    private static final String SUBMIT_TYPE = "SUBMIT_TYPE";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getProSettingsForEmpLeaveCheck(Long projId, Long empLeaveId, String submitType) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                PROJ_SETTINGS_CHECK_FOR_EMP_LEAVE_REQUEST);
        Map map = proc.getProSettingsForEmpLeaveCheck(projId, empLeaveId, submitType);
        return (List<LabelKeyTO>) map.get(PROJ_SETTINGS_CHECK_FOR_EMP_LEAVE_REQUEST);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);

            if (PROJ_SETTINGS_CHECK_FOR_EMP_LEAVE_REQUEST.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(EMP_LEAVE_ID, Types.INTEGER));
                declareParameter(new SqlParameter(SUBMIT_TYPE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(PROJ_SETTINGS_CHECK_FOR_EMP_LEAVE_REQUEST,
                        new ProjSettingsEmpLeaveRowMapper() {
                        }));

            }
            compile();
        }

        public Map getProSettingsForEmpLeaveCheck(Long projId, Long empLeaveId, String submitType) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(EMP_LEAVE_ID, empLeaveId);
            inputs.put(SUBMIT_TYPE, submitType);
            return super.execute(inputs);
        }

    }

}
