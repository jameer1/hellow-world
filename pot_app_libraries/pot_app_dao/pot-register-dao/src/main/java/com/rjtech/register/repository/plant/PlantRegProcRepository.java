package com.rjtech.register.repository.plant;

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
public class PlantRegProcRepository extends AbstractJDBCRepository {

    private static final String WORK_DAIRY_PLANT_CONSUMPTION = "WORK_DAIRY_PLANT_CONSUMPTION";
    private static final String GET_PLANT_NEW_REQUEST_DETAILS = "GET_PLANT_NEW_REQUEST_DETAILS";
    private static final String GET_PLANT_MATERIAL_PROJ_DOCKET_DETAILS = "GET_PLANT_MATERIAL_PROJ_DOCKET_DETAILS";

    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String MATERIAL_CLASS_ID = "MATERIAL_CLASS_ID";
    private static final String PROJ_ID = "PROJ_ID";
    private static final String PLANT_ID = "PLANT_ID";
    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<LabelKeyTO> getPlantConsumption(Long plantId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                WORK_DAIRY_PLANT_CONSUMPTION);
        Map map = proc.execute(plantId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<LabelKeyTO> getPlantNewRequestTransferDetails(Long clientId, Long projId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_PLANT_NEW_REQUEST_DETAILS);
        Map map = proc.getPlantNewRequestTransferDetails(clientId, projId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<LabelKeyTO> getPlantMaterialProjDocketDetails(Long materialClassId, Long projId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_PLANT_MATERIAL_PROJ_DOCKET_DETAILS);
        Map map = proc.getPlantMaterialProjDocketDetails(materialClassId, projId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {
        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (WORK_DAIRY_PLANT_CONSUMPTION.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PLANT_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new PlantConsumptionRowMapper()));
            } else if (GET_PLANT_NEW_REQUEST_DETAILS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new PlantTransRequestRowMapper()));
            } else if (GET_PLANT_MATERIAL_PROJ_DOCKET_DETAILS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(MATERIAL_CLASS_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new PlantRepairMaterialProjDocketRowMapper()));
            }
            compile();
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public Map getPlantNewRequestTransferDetails(Long clientId, Long projId) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            return super.execute(inputs);
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public Map getPlantMaterialProjDocketDetails(Long materialClassId, Long projId) {
            Map inputs = new HashMap();
            inputs.put(MATERIAL_CLASS_ID, materialClassId);
            inputs.put(PROJ_ID, projId);
            return super.execute(inputs);
        }

    }
}
