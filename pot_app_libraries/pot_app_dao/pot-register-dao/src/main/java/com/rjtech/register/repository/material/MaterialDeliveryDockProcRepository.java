package com.rjtech.register.repository.material;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.rjs.persistence.repository.AbstractJDBCRepository;

@Repository
public class MaterialDeliveryDockProcRepository extends AbstractJDBCRepository {

    private static final String GET_MATERIAL_DELIVERY_DOCKET_DETAILS = "GET_MATERIAL_DELIVERY_DOCKET_DETAILS";
    private static final String GET_MATERIAL_SCH_ITEM_DELIVERY_DOCKETS = "GET_MATERIAL_SCH_ITEM_DELIVERY_DOCKETS";

    private static final String PROJ_ID = "PROJ_ID";
    private static final String PROJ_IDS = "PROJ_IDS";
    private static final String FROM_DATE = "FROM_DATE";
    private static final String TO_DATE = "TO_DATE";
    private static final String RESULT_DATA = "RESULT_DATA";
    private static final String V_DOCKET_TYPE = "V_DOCKET_TYPE";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getMaterialDeliveryDockDetails(Long projId, char type) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_MATERIAL_DELIVERY_DOCKET_DETAILS);
        Map map = proc.getMaterialDeliveryDockDetails(projId, type);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getMaterialSchItemDeliveryDockets(List<Long> projIds, Date fromDate, Date toDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_MATERIAL_SCH_ITEM_DELIVERY_DOCKETS);
        Map map = proc.getMaterialSchItemDeliveryDockets(StringUtils.join(projIds, ","), fromDate, toDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (GET_MATERIAL_DELIVERY_DOCKET_DETAILS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(V_DOCKET_TYPE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MaterialDeliveryDockRowMapper() {
                }));
            } else if (GET_MATERIAL_SCH_ITEM_DELIVERY_DOCKETS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_IDS, Types.VARCHAR));
                declareParameter(new SqlParameter(FROM_DATE, Types.DATE));
                declareParameter(new SqlParameter(TO_DATE, Types.DATE));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MaterialSchItemDeliveryDocketRowMapper() {
                }));
            }
            compile();
        }

        public Map getMaterialSchItemDeliveryDockets(String projIds, Date fromDate, Date toDate) {
            Map inputs = new HashMap();
            inputs.put(PROJ_IDS, projIds);
            inputs.put(FROM_DATE, fromDate);
            inputs.put(TO_DATE, toDate);
            return super.execute(inputs);
        }

        public Map getMaterialDeliveryDockDetails(Long projId, char type) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(V_DOCKET_TYPE, Character.valueOf(type).toString());
            return super.execute(inputs);
        }
    }

}
