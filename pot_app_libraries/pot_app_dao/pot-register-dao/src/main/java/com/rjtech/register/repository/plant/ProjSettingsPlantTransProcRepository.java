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
public class ProjSettingsPlantTransProcRepository extends AbstractJDBCRepository {

    private static final String PROJ_SETTINGS_CHECK_FOR_PLANT_TRANSFER = "PROJ_SETTINGS_CHECK_FOR_PLANT_TRANSFER";

    private static final String PROJ_ID = "PROJ_ID";
    private static final String PLANT_ID = "PLANT_ID";
    private static final String SUBMIT_TYPE = "SUBMIT_TYPE";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getProSettingsForPlantTransCheck(Long projId, Long plantId, String submitType) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                PROJ_SETTINGS_CHECK_FOR_PLANT_TRANSFER);
        Map map = proc.getProSettingsForPlantTransCheck(projId, plantId, submitType);
        return (List<LabelKeyTO>) map.get(PROJ_SETTINGS_CHECK_FOR_PLANT_TRANSFER);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);

            if (PROJ_SETTINGS_CHECK_FOR_PLANT_TRANSFER.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PLANT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(SUBMIT_TYPE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(PROJ_SETTINGS_CHECK_FOR_PLANT_TRANSFER,
                        new ProjSettingsPlantTransRowMapper() {
                        }));

            }
            compile();
        }

        public Map getProSettingsForPlantTransCheck(Long projId, Long plantId, String submitType) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(PLANT_ID, plantId);
            inputs.put(SUBMIT_TYPE, submitType);
            return super.execute(inputs);
        }

    }

}
