package com.rjtech.finance.repository;

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
import com.rjtech.rjs.persistence.repository.AbstractJDBCRepository;

@Repository
public class FinanceTaxCodesRepository extends AbstractJDBCRepository {

    private static final String PROJ_ID = "PROJ_ID";
    private static final String START_DATE = "START_DATE";
    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String FINANCE_TYPE = "FINANCE_TYPE";
    private static final String RESULT_DATA = "RESULT_DATA";
    private static final String FINANCE_TAX_CODES = "finanaceTaxCodes";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getFinanceTaxCodes(Long projId, Long clientId, Date startDate, String financeType) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                FINANCE_TAX_CODES);
        Map map = proc.getFinanceTaxCodes(projId, clientId, startDate, financeType);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {
        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
            declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
            declareParameter(new SqlParameter(START_DATE, Types.DATE));
            declareParameter(new SqlParameter(FINANCE_TYPE, Types.VARCHAR));
            declareParameter(new SqlReturnResultSet(RESULT_DATA, new FinanceTaxCodesRowMapper() {
            }));
            compile();
        }

        public Map getFinanceTaxCodes(Long projId, Long clientId, Date startDate, String financeType) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(CLIENT_ID, clientId);
            inputs.put(START_DATE, startDate);
            inputs.put(FINANCE_TYPE, financeType);
            return super.execute(inputs);
        }
    }
}
