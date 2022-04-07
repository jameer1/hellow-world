package com.rjtech.register.proc.emp;

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
public class EmpTransferReqApprProcRepository extends AbstractJDBCRepository {

    private static final String GET_EMP_TRASNFER_REQUEST_DETAILS = "GET_EMP_TRASNFER_REQUEST_DETAILS";

    private static final String EMP_TRANS_ID = "EMP_TRANS_ID";
    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getEmpTransferReqDetails(Long empTransId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_EMP_TRASNFER_REQUEST_DETAILS);
        Map map = proc.getEmpTransferReqDetails(empTransId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {
        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (GET_EMP_TRASNFER_REQUEST_DETAILS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(EMP_TRANS_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new EmpTransferReqApprRowMapper()));
            }
            compile();
        }

        public Map getEmpTransferReqDetails(Long empTransId) {
            Map inputs = new HashMap();
            inputs.put(EMP_TRANS_ID, empTransId);
            return super.execute(inputs);
        }

    }
}
