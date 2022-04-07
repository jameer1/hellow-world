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
public class ProjSettingsWorkDairyProcRepository extends AbstractJDBCRepository {

    private static final String PROJ_SETTINGS_CHECK_FOR_WORK_DAIRY = "PROJ_SETTINGS_CHECK_FOR_WORK_DAIRY";

    private static final String PROJ_ID = "PROJ_ID";
    private static final String TIME_SHEET_ID = "TIME_SHEET_ID";
    private static final String SUBMIT_TYPE = "SUBMIT_TYPE";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getProSettingsWorkDairyCheck(Long projId, Long timeSheetId, String submitType) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                PROJ_SETTINGS_CHECK_FOR_WORK_DAIRY);
        Map map = proc.getProSettingsWorkDairyCheck(projId, timeSheetId, submitType);
        return (List<LabelKeyTO>) map.get(PROJ_SETTINGS_CHECK_FOR_WORK_DAIRY);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);

            if (PROJ_SETTINGS_CHECK_FOR_WORK_DAIRY.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(TIME_SHEET_ID, Types.INTEGER));
                declareParameter(new SqlParameter(SUBMIT_TYPE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(PROJ_SETTINGS_CHECK_FOR_WORK_DAIRY,
                        new ProjSettingsWorkDairyRowMapper() {
                        }));

            }
            compile();
        }

        public Map getProSettingsWorkDairyCheck(Long projId, Long timeSheetId, String submitType) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(TIME_SHEET_ID, timeSheetId);
            inputs.put(SUBMIT_TYPE, submitType);
            return super.execute(inputs);
        }

    }

}
