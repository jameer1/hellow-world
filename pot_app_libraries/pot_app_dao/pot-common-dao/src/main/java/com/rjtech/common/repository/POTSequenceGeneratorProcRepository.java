package com.rjtech.common.repository;

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
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.persistence.repository.AbstractJDBCRepository;

@Repository
public class POTSequenceGeneratorProcRepository extends AbstractJDBCRepository {

    private static final String GENERATE_POT_SEQ_CODE = "GENERATE_POT_SEQ_CODE";
    private static final String GENERATE_CLIENT_POT_CODE = "GENERATE_CLIENT_POT_CODE";

    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String PROJ_ID = "PROJ_ID";
    private static final String MODULE_PREFIX = "MODULE_PREFIX";
    private static final String MODULE_CODE = "MODULE_CODE";
    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public String generatePOTSeqCode(Long clientId, Long projId, String modulePrefix, String moduleCode) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GENERATE_POT_SEQ_CODE);
        Map map = proc.generatePOTSeqCode(clientId, projId, modulePrefix, moduleCode);
        List<LabelKeyTO> labelKeyTOs = (List<LabelKeyTO>) map.get(RESULT_DATA);
        if (CommonUtil.isListHasData(labelKeyTOs)) {
            return labelKeyTOs.get(0).getCode();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public String generateClientPOTSeqCode(Long clientId, String modulePrefix, String moduleCode) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GENERATE_CLIENT_POT_CODE);
        Map map = proc.generateClientPOTSeqCode(clientId, modulePrefix, moduleCode);
        List<LabelKeyTO> labelKeyTOs = (List<LabelKeyTO>) map.get(RESULT_DATA);
        if (CommonUtil.isListHasData(labelKeyTOs)) {
            return labelKeyTOs.get(0).getCode();
        }
        return null;
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);

            if (GENERATE_POT_SEQ_CODE.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(MODULE_PREFIX, Types.VARCHAR));
                declareParameter(new SqlParameter(MODULE_CODE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new POTSequenceGeneratorRowMapper() {
                }));
            } else if (GENERATE_CLIENT_POT_CODE.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(MODULE_PREFIX, Types.VARCHAR));
                declareParameter(new SqlParameter(MODULE_CODE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new POTSequenceGeneratorRowMapper() {
                }));
            }
            compile();
        }

        public Map generatePOTSeqCode(Long clientId, Long projId, String modulePrefix, String moduleCode) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(MODULE_PREFIX, modulePrefix);
            inputs.put(MODULE_CODE, moduleCode);
            return super.execute(inputs);
        }

        public Map generateClientPOTSeqCode(Long clientId, String modulePrefix, String moduleCode) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(MODULE_PREFIX, modulePrefix);
            inputs.put(MODULE_CODE, moduleCode);
            return super.execute(inputs);
        }

    }

}
