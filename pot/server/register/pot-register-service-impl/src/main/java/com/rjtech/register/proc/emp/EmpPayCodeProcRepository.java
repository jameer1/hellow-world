
package com.rjtech.register.proc.emp;

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
public class EmpPayCodeProcRepository extends AbstractJDBCRepository {

    private static final String GET_EMP_REGULAR_PAY_CODES = "GET_EMP_REGULAR_PAY_CODES";
    private static final String GET_EMP_NON_REGULAR_PAY_CODES = "GET_EMP_NON_REGULAR_PAY_CODES";
    private static final String GET_EMP_PAY_DEDUCTION_CODES = "GET_EMP_PAY_DEDUCTION_CODES";
    private static final String GET_EMP_PROVIDENT_FUND_CODES = "GET_EMP_PROVIDENT_FUND_CODES";

    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String COUNTRY_ID = "COUNTRY_ID";
    private static final String PROVINCE_ID = "PROVINCE_ID";
    private static final String TYPE_ID = "TYPE_ID";
    private static final String EFFECTIVE_DATE = "EFFECTIVE_DATE";
    private static final String CURRENT_DATE = "CURRENT_DATE";
    private static final String RESULT_DATA = "RESULT_DATA";
    private static final String FROM_DATE = "WEEK_START_DATE";
    private static final String TO_DATE = "FROM_WEEK_START_DATE";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getEmpRegularPaycodes(Long clientId, String countryId, String provinceId, Integer typeId,
            String date) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_EMP_REGULAR_PAY_CODES);
        Map map = proc.getEmpFinancecodes(clientId, countryId, provinceId, typeId, date);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getEmpNonRegularPaycodes(Long clientId, String countryId, String provinceId, Integer typeId,
            String date) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_EMP_NON_REGULAR_PAY_CODES);
        Map map = proc.getEmpFinancecodes(clientId, countryId, provinceId, typeId, date);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getEmpPaydeductions(Long clientId, String countryId, String provinceId, Integer typeId,
            String date) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_EMP_PAY_DEDUCTION_CODES);
        Map map = proc.getEmpFinancecodes(clientId, countryId, provinceId, typeId, date);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getEmpProvidentFunds(Long clientId, String countryId, String provinceId, Integer typeId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_EMP_PROVIDENT_FUND_CODES);
        Map map = proc.getEmpPaycodes(clientId, countryId, provinceId, typeId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (GET_EMP_PROVIDENT_FUND_CODES.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(COUNTRY_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROVINCE_ID, Types.INTEGER));
                declareParameter(new SqlParameter(TYPE_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new EmpPayCodeRowMapper() {
                }));
            } else {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(COUNTRY_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROVINCE_ID, Types.INTEGER));
                declareParameter(new SqlParameter(TYPE_ID, Types.INTEGER));
                declareParameter(new SqlParameter(EFFECTIVE_DATE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new EmpPayCodeRowMapper() {
                }));
            }
            compile();
        }

        public Map getEmpPaycodes(Long clientId, String countryId, String provinceId, Integer typeId) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(COUNTRY_ID, countryId);
            inputs.put(PROVINCE_ID, provinceId);
            inputs.put(TYPE_ID, typeId);
            return super.execute(inputs);
        }

        public Map getEmpFinancecodes(Long clientId, String countryId, String provinceId, Integer typeId, String date) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(COUNTRY_ID, countryId);
            inputs.put(PROVINCE_ID, provinceId);
            inputs.put(TYPE_ID, typeId);
            inputs.put(EFFECTIVE_DATE, date);
            return super.execute(inputs);
        }

    }

}
