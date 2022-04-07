package com.rjtech.timemanagement.workdairy.repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import com.rjtech.rjs.persistence.repository.AbstractJDBCRepository;

@Repository
public class WorkDairyMaterialProcRepository extends AbstractJDBCRepository {

    private static final String UPDATE_DELIVERY_DOCKET_CONSUMPTION_QTY = "UPDATE_DELIVERY_DOCKET_CONSUMPTION_QTY";
    private static final String UPDATE_PROJ_DOCKET_CONSUMPTION_QTY = "UPDATE_PROJ_DOCKET_CONSUMPTION_QTY";
    private static final String PROJ_ID = "PROJ_ID";
    private static final String DOCKET_ID = "DOCKET_ID";
    private static final String SCH_ITEM_ID = "SCH_ITEM_ID";
    private static final String RECEIVED_QTY = "RECEIVED_QTY";
    private static final String WORK_DAIRY_DATE = "WORK_DAIRY_DATE";

    public void updateDeliveryDocketConsumptionQty(Long projId, Long docketId, Long schItemId, double qty,
            String workDairyDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                UPDATE_DELIVERY_DOCKET_CONSUMPTION_QTY);
        proc.updateDocketConsumptionQty(projId, docketId, schItemId, qty, workDairyDate);
    }

    public void updateProjDocketConsumptionQty(Long projId, Long docketId, Long schItemId, double qty,
            String workDairyDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                UPDATE_PROJ_DOCKET_CONSUMPTION_QTY);
        proc.updateDocketConsumptionQty(projId, docketId, schItemId, qty, workDairyDate);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
            declareParameter(new SqlParameter(DOCKET_ID, Types.INTEGER));
            declareParameter(new SqlParameter(SCH_ITEM_ID, Types.INTEGER));
            declareParameter(new SqlParameter(RECEIVED_QTY, Types.DOUBLE));
            declareParameter(new SqlParameter(WORK_DAIRY_DATE, Types.VARCHAR));
            compile();
        }

        public Map updateDocketConsumptionQty(Long projId, Long docketId, Long schItemId, double qty,
                String workDairyDate) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(DOCKET_ID, docketId);
            inputs.put(SCH_ITEM_ID, schItemId);
            inputs.put(RECEIVED_QTY, qty);
            inputs.put(WORK_DAIRY_DATE, workDairyDate);
            return super.execute(inputs);
        }

    }

}
