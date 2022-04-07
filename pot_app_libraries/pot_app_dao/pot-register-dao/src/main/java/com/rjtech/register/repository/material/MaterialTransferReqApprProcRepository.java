package com.rjtech.register.repository.material;

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
public class MaterialTransferReqApprProcRepository extends AbstractJDBCRepository {

    private static final String GET_MATRIAL_DETAILS_FOR_TRANSFER = "GET_MATRIAL_DETAILS_FOR_TRANSFER";
    private static final String GET_MATERIAL_TRANSFER_REQUEST_DETAILS = "GET_MATERIAL_TRANSFER_REQUEST_DETAILS";
    private static final String GET_MATERIAL_AVIALBLE_QTY_BY_PROJ_LOC = "GET_MATERIAL_AVIALBLE_QTY_BY_PROJ_LOC";
    private static final String GET_MATERIAL_NOTIFY_TRANSFER_REQ_DETAILS = "GET_MATERIAL_NOTIFY_TRANSFER_REQ_DETAILS";

    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String MATERIAL_TRANS_ID = "MATERIAL_TRANS_ID";
    private static final String PROJ_ID = "PROJ_ID";
    private static final String TO_PROJ_ID = "TO_PROJ_ID";
    private static final String SM_ID = "SM_ID";
    private static final String NOTIFY_ID = "NOTIFY_ID";
    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getMaterialDetailsForTransfer(Long clientId, Long projId, Long toProjId, Long storeId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_MATRIAL_DETAILS_FOR_TRANSFER);
        Map map = proc.getMaterialDetailsForTransfer(clientId, projId, toProjId, storeId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getMaterialTransferReqDetails(Long plantTransId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_MATERIAL_TRANSFER_REQUEST_DETAILS);
        Map map = proc.getMaterialTransferReqDetails(plantTransId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getMaterialAvlQtyByProjLoc(Long projId, Long storeId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_MATERIAL_AVIALBLE_QTY_BY_PROJ_LOC);
        Map map = proc.getMaterialAvlQtyByProjLoc(projId, storeId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getMaterialSchDetailsForTransfer(Long notifyId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_MATERIAL_NOTIFY_TRANSFER_REQ_DETAILS);
        Map map = proc.getMaterialSchDetailsForTransfer(notifyId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {
        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (GET_MATRIAL_DETAILS_FOR_TRANSFER.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(TO_PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(SM_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MaterialTransReqApprRowMapper()));
            } else if (GET_MATERIAL_TRANSFER_REQUEST_DETAILS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(MATERIAL_TRANS_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MaterialTransReqApprRowMapper()));
            } else if (GET_MATERIAL_AVIALBLE_QTY_BY_PROJ_LOC.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(SM_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MaterialAvailableQtyRowMapper()));
            } else if (GET_MATERIAL_NOTIFY_TRANSFER_REQ_DETAILS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(NOTIFY_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MaterialTransReqApprRowMapper()));
            }
            compile();
        }

        public Map getMaterialSchDetailsForTransfer(Long notifyId) {
            Map inputs = new HashMap();
            inputs.put(NOTIFY_ID, notifyId);
            return super.execute(inputs);

        }

        public Map getMaterialDetailsForTransfer(Long clientId, Long projId, Long toProjId, Long storeId) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(TO_PROJ_ID, toProjId);
            inputs.put(SM_ID, storeId);
            return super.execute(inputs);
        }

        public Map getMaterialTransferReqDetails(Long plantTransId) {
            Map inputs = new HashMap();
            inputs.put(MATERIAL_TRANS_ID, plantTransId);
            return super.execute(inputs);
        }

        public Map getMaterialAvlQtyByProjLoc(Long projId, Long storeId) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(SM_ID, storeId);
            return super.execute(inputs);
        }
    }
}
