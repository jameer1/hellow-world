package com.rjtech.projectlib.repository;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.projectlib.dto.ProjSOWItemTO;
import com.rjtech.rjs.persistence.repository.AbstractJDBCRepository;

@Repository
public class ProjSowProcRepository extends AbstractJDBCRepository {

    private static final String GET_SOW_ITEMS_FOR_REPORT = "GET_SOW_ITEMS_FOR_REPORT";
    public static final String CLIENT_ID = "CLIENT_ID";
    private static final String PROJ_ID = "PROJ_ID";
    private static final String RESULT_DATA = "RESULT_DATA";

    private static final String PROJ_IDS = "PROJ_IDS";
    private static final String SOW_IDS = "SOW_IDS";

    @SuppressWarnings("unchecked")
    public List<ProjSOWItemTO> getProjSowForReport(final String projIds, final String sowIds) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_SOW_ITEMS_FOR_REPORT);
        return (List<ProjSOWItemTO>) proc.getProjSowForReport(projIds, sowIds).get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (GET_SOW_ITEMS_FOR_REPORT.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_IDS, Types.VARCHAR));
                declareParameter(new SqlParameter(SOW_IDS, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new ProjSowItemsResultSetExtractor() {
                }));

            }
            compile();
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public Map getProjSowWorkdairyMinDate(Long projId) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            return super.execute(inputs);
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public Map getProjSowForReport(final String projIds, final String sowIds) {
            Map<String, String> inputs = new HashMap();
            inputs.put(PROJ_IDS, projIds);
            inputs.put(SOW_IDS, sowIds);
            return super.execute(inputs);
        }
    }

}
