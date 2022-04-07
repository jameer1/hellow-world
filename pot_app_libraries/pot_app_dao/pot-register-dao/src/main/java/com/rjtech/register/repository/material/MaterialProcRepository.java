package com.rjtech.register.repository.material;

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
public class MaterialProcRepository extends AbstractJDBCRepository {

    private static final String GET_WORKDAIRY_MATERIAL_CONSUMPTION = "GET_WORKDAIRY_MATERIAL_CONSUMPTION";
    private static final String PROJ_ID = "PROJ_ID";
    private static final String FROM_DATE = "FROM_DATE";
    private static final String TO_DATE = "TO_DATE";
    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getWorkdairyMaterialConsumptionQty(Long projId, Date fromDate, Date toDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_WORKDAIRY_MATERIAL_CONSUMPTION);
        Map result = proc.getWorkdairyMaterialConsumptionQty(projId, fromDate, toDate);
        return (List<LabelKeyTO>) result.get(RESULT_DATA);

    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
            declareParameter(new SqlParameter(FROM_DATE, Types.DATE));
            declareParameter(new SqlParameter(TO_DATE, Types.DATE));
            declareParameter(new SqlReturnResultSet(RESULT_DATA, new MateriaWorkairyConsumeRowMapper() {
            }));
            compile();
        }

        public Map getWorkdairyMaterialConsumptionQty(Long projId, Date fromDate, Date toDate) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(FROM_DATE, fromDate);
            inputs.put(TO_DATE, toDate);
            return super.execute(inputs);
        }

    }

}
