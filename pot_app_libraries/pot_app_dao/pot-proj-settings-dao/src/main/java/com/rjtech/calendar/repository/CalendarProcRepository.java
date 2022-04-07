package com.rjtech.calendar.repository;

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
import com.rjtech.common.repository.POTSequenceGeneratorRowMapper;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.persistence.repository.AbstractJDBCRepository;

@Repository
public class CalendarProcRepository extends AbstractJDBCRepository {

    private static final String GENERATE_GLOBAL_CALENDAR_CODE = "GENERATE_GLOBAL_CALENDAR_CODE";
    private static final String GENERATE_PROJ_CALENDAR_CODE = "GENERATE_PROJ_CALENDAR_CODE";
    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String PROJ_ID = "PROJ_ID";

    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public String generateGlobalCalCode(Long clientId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GENERATE_GLOBAL_CALENDAR_CODE);
        Map map = proc.generateGlobalCalCode(clientId);
        List<LabelKeyTO> labelKeyTOs = (List<LabelKeyTO>) map.get(RESULT_DATA);
        if (CommonUtil.isListHasData(labelKeyTOs)) {
            return labelKeyTOs.get(0).getCode();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public String generateProjCalCode(Long clientId, Long projId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GENERATE_PROJ_CALENDAR_CODE);
        Map map = proc.generateProjCalCode(clientId, projId);
        List<LabelKeyTO> labelKeyTOs = (List<LabelKeyTO>) map.get(RESULT_DATA);
        if (CommonUtil.isListHasData(labelKeyTOs)) {
            return labelKeyTOs.get(0).getCode();
        }
        return null;
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);

            if (GENERATE_GLOBAL_CALENDAR_CODE.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new POTSequenceGeneratorRowMapper() {
                }));
            } else if (GENERATE_PROJ_CALENDAR_CODE.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.VARCHAR));
                declareParameter(new SqlParameter(PROJ_ID, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new POTSequenceGeneratorRowMapper() {
                }));
            }
            compile();
        }

        public Map generateGlobalCalCode(Long clientId) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            return super.execute(inputs);
        }

        public Map generateProjCalCode(Long clientId, Long projId) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            return super.execute(inputs);
        }

    }
}
