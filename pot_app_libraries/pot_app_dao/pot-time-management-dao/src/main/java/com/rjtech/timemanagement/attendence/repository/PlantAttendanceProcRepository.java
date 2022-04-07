package com.rjtech.timemanagement.attendence.repository;

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
public class PlantAttendanceProcRepository extends AbstractJDBCRepository {

    private static final String COPY_ATTENDANCE_PLANT_REG = "COPY_ATTENDANCE_PLANT_REG";
    private static final String PLANT_ATTENDANCE_SHEETS = "PLANT_ATTENDANCE_SHEETS";
    private static final String PROJ_ID = "PROJ_ID";
    private static final String CREW_ID = "CREW_ID";
    private static final String START_DATE = "START_DATE";
    private static final String END_DATE = "END_DATE";
    private static final String ATTENDANCE_ID = "ATTENDANCE_ID";
    private static final String RESULT_DATA = "RESULT_DATA";
    public static final String ATTENDANCE_MONTH = "ATTENDANCE_MONTH";
    public static final String CLIENT_ID = "CLIENT_ID";
    private static final String GET_PLANT_PROJ_DEMOBILIZATION_DATE = "GET_PLANT_PROJ_DEMOBILIZATION_DATE";

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<LabelKeyTO> copyAttendancePlantDetails(Long projId, Long crewId, Long attendanceId, Date startDate,
            Date endDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                COPY_ATTENDANCE_PLANT_REG);
        Map map = proc.copyAttendancePlantDetails(projId, crewId, attendanceId, startDate, endDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<LabelKeyTO> getAttendanceSheets(Long projId, Long crewId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                PLANT_ATTENDANCE_SHEETS);
        Map map = proc.getAttendanceSheets(projId, crewId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<LabelKeyTO> getPlantProjDemobilizationDate(Long projId, Long attendanceId, Date startDate,
            Date endDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_PLANT_PROJ_DEMOBILIZATION_DATE);
        Map map = proc.getPlantProjDemobilizationDate(projId, attendanceId, startDate, endDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {
        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (COPY_ATTENDANCE_PLANT_REG.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(ATTENDANCE_ID, Types.INTEGER));
                declareParameter(new SqlParameter(START_DATE, Types.DATE));
                declareParameter(new SqlParameter(END_DATE, Types.DATE));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new PlantRegAttendanceRowMapper() {
                }));
            } else if (PLANT_ATTENDANCE_SHEETS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new AttendanceRowMapper() {
                }));
            } else if (GET_PLANT_PROJ_DEMOBILIZATION_DATE.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(ATTENDANCE_ID, Types.INTEGER));
                declareParameter(new SqlParameter(START_DATE, Types.DATE));
                declareParameter(new SqlParameter(END_DATE, Types.DATE));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new AttendanceRowMapper() {
                }));
            }

            compile();
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public Map copyAttendancePlantDetails(Long projId, Long crewId, Long attendanceId, Date startDate,
                Date endDate) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(ATTENDANCE_ID, attendanceId);
            inputs.put(START_DATE, startDate);
            inputs.put(END_DATE, endDate);
            return super.execute(inputs);
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public Map getAttendanceSheets(Long projId, Long crewId) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            return super.execute(inputs);
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public Map getPlantProjDemobilizationDate(Long projId, Long attendanceId, Date startDate, Date endDate) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(ATTENDANCE_ID, attendanceId);
            inputs.put(START_DATE, startDate);
            inputs.put(END_DATE, endDate);
            return super.execute(inputs);
        }
    }

}
