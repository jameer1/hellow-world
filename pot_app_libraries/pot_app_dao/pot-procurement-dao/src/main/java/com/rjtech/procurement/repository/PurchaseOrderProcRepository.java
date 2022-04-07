package com.rjtech.procurement.repository;

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

// TODO: Convert used procedures to HQL queries and then remove this repository
@Repository
public class PurchaseOrderProcRepository extends AbstractJDBCRepository {

    private static final String PURCHASE_ORDER_SCHEDULE_ITEMS_BY_PROJECT = "PURCHASE_ORDER_SCHEDULE_ITEMS_BY_PROJECT";
    private static final String PURCHASE_ORDER_SUMMARY = "PURCHASE_ORDER_SUMMARY";
    private static final String MAN_POWER_INVOICE_DOCKET_ITEMS = "MAN_POWER_INVOICE_DOCKET_ITEMS";
    private static final String PLANT_DELIVERY_DOCKETS = "PLANT_DELIVERY_DOCKETS";
    private static final String MATERIAL_DELIVERY_DOCKETS = "MATERIAL_DELIVERY_DOCKETS";

    public static final String PUR_ID = "PUR_ID";
    public static final String PUR_TYPE = "PUR_TYPE";
    public static final String PROJ_ID = "PROJ_ID";
    public static final String SCH_ID = "SCH_ID";
    public static final String PRE_CONTRACT_ID = "PRE_CONTRACT_ID";
    public static final String SCH_CMP_ID = "SCH_CMP_ID";

    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getPOScheudleItemsByProject(Long projId, String type) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                PURCHASE_ORDER_SCHEDULE_ITEMS_BY_PROJECT);
        Map map = proc.getPurChaseOrderItemsByProject(projId, type);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public void updatePurchaseOrderSummary(Long purId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                PURCHASE_ORDER_SUMMARY);
        proc.updatePurchaseOrderSummar(purId);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getManpowerInvoiceDockets(Long purId, Long projId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                MAN_POWER_INVOICE_DOCKET_ITEMS);
        Map map = proc.getManpowerInvoiceDockets(purId, projId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getPlantInvoiceDockets(Long purId, Long projId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                PLANT_DELIVERY_DOCKETS);
        Map map = proc.getPlantInvoiceDockets(purId, projId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getMaterialInvoiceDockets(Long purId, Long projId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                MATERIAL_DELIVERY_DOCKETS);
        Map map = proc.getMaterialInvoiceDockets(purId, projId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (PURCHASE_ORDER_SUMMARY.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PUR_ID, Types.INTEGER));
            } else if (MAN_POWER_INVOICE_DOCKET_ITEMS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PUR_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new ManpowerInvoiceDocketRowMapper()));
            } else if (PLANT_DELIVERY_DOCKETS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PUR_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MaterialInvoiceDocketRowMapper()));
            } else if (MATERIAL_DELIVERY_DOCKETS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PUR_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MaterialInvoiceDocketRowMapper()));
            }
            compile();
        }

        public Map getPurchaseOrdersByPrecontract(Long precontractId) {
            Map inputs = new HashMap();
            inputs.put(PRE_CONTRACT_ID, precontractId);
            return super.execute(inputs);
        }

        public Map getPurChaseOrders(Long projId, String type) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(PUR_TYPE, type);
            return super.execute(inputs);
        }

        public Map getPurChaseOrderItems(Long purId, String type) {
            Map inputs = new HashMap();
            inputs.put(PUR_ID, purId);
            inputs.put(PUR_TYPE, type);
            return super.execute(inputs);
        }

        public Map getPurChaseOrderItemsByProject(Long projId, String type) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(PUR_TYPE, type);
            return super.execute(inputs);
        }

        public Map getManpowerInvoiceDockets(Long purId, Long projId) {
            Map inputs = new HashMap();
            inputs.put(PUR_ID, purId);
            inputs.put(PROJ_ID, projId);
            return super.execute(inputs);
        }

        public Map getPlantInvoiceDockets(Long purId, Long projId) {
            Map inputs = new HashMap();
            inputs.put(PUR_ID, purId);
            inputs.put(PROJ_ID, projId);
            return super.execute(inputs);
        }

        public Map getMaterialInvoiceDockets(Long purId, Long projId) {
            Map inputs = new HashMap();
            inputs.put(PUR_ID, purId);
            inputs.put(PROJ_ID, projId);
            return super.execute(inputs);
        }

        public Map updatePurchaseOrderSummar(Long purId) {
            Map inputs = new HashMap();
            inputs.put(PUR_ID, purId);
            return super.execute(inputs);
        }
    }
}
