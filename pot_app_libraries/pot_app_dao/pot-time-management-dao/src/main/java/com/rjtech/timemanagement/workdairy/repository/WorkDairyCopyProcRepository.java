package com.rjtech.timemanagement.workdairy.repository;

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
import com.rjtech.timemanagement.attendence.repository.EmpRegAttendanceRowMapper;
import com.rjtech.timemanagement.attendence.repository.PlantRegAttendanceRowMapper;

@Repository
public class WorkDairyCopyProcRepository extends AbstractJDBCRepository {

    private static final String COPY_WORKDAIRY_MANPOWER = "COPY_WORKDAIRY_MANPOWER";
    private static final String COPY_WORKDAIRY_PLANT = "COPY_WORKDAIRY_PLANT";
    private static final String COPY_WORKDAIRY_MATERIAL = "COPY_WORKDAIRY_MATERIAL";
    private static final String COPY_WORKDAIRY_PROGRESS = "COPY_WORKDAIRY_PROGRESS";

    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String PROJ_ID = "PROJ_ID";
    private static final String CREW_ID = "CREW_ID";
    private static final String FROM_WORK_DAIRY_DATE = "FROM_WORK_DAIRY_DATE";
    private static final String TO_WORK_DAIRY_DATE = "TO_WORK_DAIRY_DATE";

    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> copyWorkDairyEmps(Long client, Long projId, Long crewId, String fromWorkDairyDate,
            String toWorkDairyDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                COPY_WORKDAIRY_MANPOWER);
        Map map = proc.copyWorkDairy(client, projId, crewId, fromWorkDairyDate, toWorkDairyDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> copyWorkDairyPlants(Long client, Long projId, Long crewId, String fromWorkDairyDate,
            String toWorkDairyDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                COPY_WORKDAIRY_PLANT);
        Map map = proc.copyWorkDairy(client, projId, crewId, fromWorkDairyDate, toWorkDairyDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> copyWorkDairyMaterials(Long client, Long projId, Long crewId, String fromWorkDairyDate,
            String toWorkDairyDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                COPY_WORKDAIRY_MATERIAL);
        Map map = proc.copyWorkDairy(client, projId, crewId, fromWorkDairyDate, toWorkDairyDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> copyWorkDairyProgress(Long client, Long projId, Long crewId, String fromWorkDairyDate,
            String toWorkDairyDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                COPY_WORKDAIRY_PROGRESS);
        Map map = proc.copyWorkDairy(client, projId, crewId, fromWorkDairyDate, toWorkDairyDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (COPY_WORKDAIRY_MANPOWER.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(FROM_WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlParameter(TO_WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new WorkDairyEmpRegRowMapper() {
                }));
            } else if (COPY_WORKDAIRY_PLANT.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(FROM_WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlParameter(TO_WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new PlantRegAttendanceRowMapper() {
                }));
            } else if (COPY_WORKDAIRY_MATERIAL.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(FROM_WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlParameter(TO_WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new WorkDairyMaterialRowMapper() {
                }));
            } else if (COPY_WORKDAIRY_PROGRESS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(FROM_WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlParameter(TO_WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new WorkDairyProgressRowMapper() {
                }));
            }
            compile();
        }

        public Map copyWorkDairy(Long client, Long projId, Long crewId, String fromWorkDairyDate,
                String toWorkDairyDate) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, client);
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(FROM_WORK_DAIRY_DATE, fromWorkDairyDate);
            inputs.put(TO_WORK_DAIRY_DATE, toWorkDairyDate);
            return super.execute(inputs);
        }

    }

}
