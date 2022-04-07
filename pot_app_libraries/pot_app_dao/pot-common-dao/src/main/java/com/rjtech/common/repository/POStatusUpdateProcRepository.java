package com.rjtech.common.repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import com.rjtech.rjs.persistence.repository.AbstractJDBCRepository;

@Repository
public class POStatusUpdateProcRepository extends AbstractJDBCRepository {

    private static final String UPDATE_PURCHASE_ORDER_STATUS = "UPDATE_PURCHASE_ORDER_STATUS";

    public static final String PUR_ID = "PUR_ID";
    public static final String PUR_TYPE = "PUR_TYPE";
    public static final String SCH_ID = "SCH_ID";
    public static final String SCH_ITEM_CMP_ID = "SCH_CMP_ID";
    public static final String RECEIVED_QTY = "RECEIVED_QTY";

    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public void updatePurchaseOrderStatus(Long purId, Long schItemCmpId, String type, double receviedQty) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                UPDATE_PURCHASE_ORDER_STATUS);
        proc.updatePurchaseOrderStatus(purId, schItemCmpId, type, receviedQty);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (UPDATE_PURCHASE_ORDER_STATUS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PUR_ID, Types.INTEGER));
                declareParameter(new SqlParameter(SCH_ITEM_CMP_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PUR_TYPE, Types.VARCHAR));
                declareParameter(new SqlParameter(RECEIVED_QTY, Types.DECIMAL));

            }
            compile();
        }

        public Map updatePurchaseOrderStatus(Long purId, Long schItemCmpId, String type, double receviedQty) {
            Map inputs = new HashMap();
            inputs.put(PUR_ID, purId);
            inputs.put(SCH_ITEM_CMP_ID, schItemCmpId);
            inputs.put(PUR_TYPE, type);
            inputs.put(RECEIVED_QTY, receviedQty);
            return super.execute(inputs);
        }
    }
}
