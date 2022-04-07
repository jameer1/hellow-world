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
public class EmpAttendanceProcRepository extends AbstractJDBCRepository {

    private static final String EMP_REG_DTL_INVIDUAL = "EMP_REG_DTL_INVIDUAL";
    private static final String COPY_ATTENDANCE_EMP_REG = "COPY_ATTENDANCE_EMP_REG";
    private static final String EMP_ATTENDANCE_SHEETS = "EMP_ATTENDANCE_SHEETS";
    private static final String CHECK_ATTENDANCE_PERIOD = "CHECK_ATTENDANCE_PERIOD";

    private static final String PROJ_ID = "PROJ_ID";
    private static final String CREW_ID = "CREW_ID";
    private static final String START_DATE = "START_DATE";
    private static final String END_DATE = "END_DATE";
    private static final String RESULT_DATA = "RESULT_DATA";
    private static final String ATTENDANCE_ID = "ATTENDANCE_ID";
    private static final String ATTENDANCE_TYPE = "ATTENDANCE_TYPE";
    public static final String ATTENDANCE_MONTH = "ATTENDANCE_MONTH";
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String USER_ID = "USER_ID";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> findEmpRegDetailsInvidiual(Long projId, Date startDate, Date endDate, Long clientId,
            Long userId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                EMP_REG_DTL_INVIDUAL);
        Map map = proc.getEmpRegDetailsInvidiual(projId, startDate, endDate, clientId, userId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> copyAttendanceEmpDetails(Long projId, Long crewId, Long attendanceId, Date startDate,
            Date endDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                COPY_ATTENDANCE_EMP_REG);
        Map map = proc.copyAttendanceEmpDetails(projId, crewId, attendanceId, startDate, endDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> findAttendanceSheets(Long projId, Long crewId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                EMP_ATTENDANCE_SHEETS);
        Map map = proc.getAttendanceSheets(projId, crewId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> enableAttendanceDays(Long projId, Long crewId, String attendance_type) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                CHECK_ATTENDANCE_PERIOD);
        Map map = proc.enableAttendanceDays(projId, crewId, attendance_type);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (EMP_REG_DTL_INVIDUAL.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(START_DATE, Types.DATE));
                declareParameter(new SqlParameter(END_DATE, Types.DATE));
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(USER_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new EmpRegAttendanceRowMapper() {
                }));
            } else if (COPY_ATTENDANCE_EMP_REG.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(ATTENDANCE_ID, Types.INTEGER));
                declareParameter(new SqlParameter(START_DATE, Types.DATE));
                declareParameter(new SqlParameter(END_DATE, Types.DATE));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new EmpRegAttendanceRowMapper() {
                }));
            } else if (EMP_ATTENDANCE_SHEETS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new AttendanceRowMapper() {
                }));
            } else if (CHECK_ATTENDANCE_PERIOD.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(ATTENDANCE_TYPE, Types.VARCHAR));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new AttendanceDayRowMapper() {
                }));
            }
            compile();
        }

        public Map getEmpRegDetails(Long projId, Date startDate, Date endDate) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(START_DATE, startDate);
            inputs.put(END_DATE, endDate);
            return super.execute(inputs);
        }

        public Map getEmpRegDetailsInvidiual(Long projId, Date startDate, Date endDate, Long clientId, Long userId) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(START_DATE, startDate);
            inputs.put(END_DATE, endDate);
            inputs.put(CLIENT_ID, clientId);
            inputs.put(USER_ID, userId);
            return super.execute(inputs);
        }

        public Map copyAttendanceEmpDetails(Long projId, Long crewId, Long attendanceId, Date startDate, Date endDate) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(ATTENDANCE_ID, attendanceId);
            inputs.put(START_DATE, startDate);
            inputs.put(END_DATE, endDate);
            return super.execute(inputs);
        }

        public Map getAttendanceSheets(Long projId, Long crewId) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            return super.execute(inputs);
        }

        public Map enableAttendanceDays(Long projId, Long crewId, String attendance_type) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(ATTENDANCE_TYPE, attendance_type);
            return super.execute(inputs);
        }

    }

}
