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
public class WorkDairyProcRepository extends AbstractJDBCRepository {

    private static final String WORK_DAIRY_EMP_REG_MAP = "WORK_DAIRY_EMP_REG_MAP";
    private static final String WORK_DAIRY_PLANT_REG_MAP = "WORK_DAIRY_PLANT_REG_MAP";
    private static final String WORK_DAIRY_EMP_BOOKED_HRS_FOR_OTHER_CREW = "WORK_DAIRY_EMP_BOOKED_HRS_FOR_OTHER_CREW";
    private static final String PLANT_BOOKED_HRS_FOR_OTHER_CREW = "PLANT_BOOKED_HRS_FOR_OTHER_CREW";
    private static final String GET_TIME_BOOKED_HRS_FOR_WORK_DAIRY = "GET_TIME_BOOKED_HRS_FOR_WORK_DAIRY";
    private static final String WORK_DAIRY_EMP_REG = "WORK_DAIRY_EMP_REG";
    private static final String WORK_DAIRY_PLANT_REG = "WORK_DAIRY_PLANT_REG";
    private static final String GENERATE_WORK_DAIRY_CODE = "GENERATE_WORK_DAIRY_CODE";
    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String PROJ_ID = "PROJ_ID";
    private static final String CREW_ID = "CREW_ID";
    private static final String WORK_DAIRY_DATE = "WORK_DAIRY_DATE";
    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getEmpRegMap(Long clientId, Long projId, Long crewId, String currentDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                WORK_DAIRY_EMP_REG_MAP);
        Map map = proc.getEmpRegDetails(clientId, projId, crewId, currentDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getEmpRegDetails(Long clientId, Long projId, Long crewId, String currentDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                WORK_DAIRY_EMP_REG);
        Map map = proc.getEmpRegDetails(clientId, projId, crewId, currentDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getPlantRegMap(Long clientId, Long projId, Long crewId, String currentDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                WORK_DAIRY_PLANT_REG_MAP);
        Map map = proc.getPlantRegDetails(clientId, projId, crewId, currentDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getPlantRegDetails(Long clientId, Long projId, Long crewId, String currentDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                WORK_DAIRY_PLANT_REG);
        Map map = proc.getPlantRegDetails(clientId, projId, crewId, currentDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> generateWorkDairyCode(Long clientId, Long projId, Long crewId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GENERATE_WORK_DAIRY_CODE);
        Map map = proc.generateWorkDairyCode(clientId, projId, crewId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getTimeSheetBookedHrs(Long projId, String workDairyDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_TIME_BOOKED_HRS_FOR_WORK_DAIRY);
        Map map = proc.getTimeSheetBookedHrs(projId, workDairyDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getWorkdairyBookedHrsForOtherCrew(Long clientId, Long projId, Long crewId,
            String workDairyDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                WORK_DAIRY_EMP_BOOKED_HRS_FOR_OTHER_CREW);
        Map map = proc.getWorkdairyBookedHrsForOtherCrew(clientId, projId, crewId, workDairyDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getPlantBookedHrsForOtherCrew(Long clientId, Long projId, Long crewId,
            String workDairyDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                PLANT_BOOKED_HRS_FOR_OTHER_CREW);
        Map map = proc.getPlantBookedHrsForOtherCrew(clientId, projId, crewId, workDairyDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (WORK_DAIRY_EMP_REG_MAP.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new WorkDairyEmpRegRowMapper() {
                }));
            } else if (WORK_DAIRY_EMP_REG.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new EmpRegAttendanceRowMapper() {
                }));
            } else if (WORK_DAIRY_PLANT_REG.equalsIgnoreCase(sprocName)
                    || WORK_DAIRY_PLANT_REG_MAP.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new PlantRegAttendanceRowMapper() {
                }));
            } else if (GENERATE_WORK_DAIRY_CODE.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new WorkDairyCodeRowMapper() {
                }));
            } else if (GET_TIME_BOOKED_HRS_FOR_WORK_DAIRY.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new WorkDairyBookedHrsMapper() {
                }));
            } else if (WORK_DAIRY_EMP_BOOKED_HRS_FOR_OTHER_CREW.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new WorkDairyBookedHrsMapper() {
                }));
            } else if (PLANT_BOOKED_HRS_FOR_OTHER_CREW.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(WORK_DAIRY_DATE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new WorkDairyBookedHrsMapper() {
                }));
            }
            compile();
        }

        public Map getworkDairyBookedHrsForOtherCrew(Long clientId, Long projId) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            return super.execute(inputs);
        }

        public Map getWorkdairyBookedHrsForOtherCrew(Long clientId, Long projId, Long crewId, String workDairyDate) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(WORK_DAIRY_DATE, workDairyDate);
            return super.execute(inputs);
        }

        public Map getPlantBookedHrsForOtherCrew(Long clientId, Long projId, Long crewId, String workDairyDate) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(WORK_DAIRY_DATE, workDairyDate);
            return super.execute(inputs);
        }

        public Map getTimeSheetBookedHrs(Long projId, String workDairyDate) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(WORK_DAIRY_DATE, workDairyDate);
            return super.execute(inputs);
        }

        public Map getEmpRegDetails(Long clientId, Long projId, Long crewId, String workDairyDate) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(WORK_DAIRY_DATE, workDairyDate);
            return super.execute(inputs);
        }

        public Map getPlantRegDetails(Long clientId, Long projId, Long crewId, String workDairyDate) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(WORK_DAIRY_DATE, workDairyDate);
            return super.execute(inputs);
        }

        public Map generateWorkDairyCode(Long clientId, Long projId, Long crewId) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            return super.execute(inputs);
        }
    }

}
