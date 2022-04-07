package com.rjtech.timemanagement.timesheet.repository;

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
public class TimeSheetProcRepository extends AbstractJDBCRepository {

    private static final String CREW_ATTENDACNE_FOR_INDIVIDUAL_TIME_SHEET = "CREW_ATTENDACNE_FOR_INDIVIDUAL_TIME_SHEET";
    private static final String COPY_TIME_SHEET_EMP_REG = "COPY_TIME_SHEET_EMP_REG";
    private static final String TIME_SHEET_COSTCODE_WAGE_MAP = "TIME_SHEET_COSTCODE_WAGE_MAP";

    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String PROJ_ID = "PROJ_ID";
    private static final String CREW_ID = "CREW_ID";
    private static final String EMP_ID = "EMP_ID";
    private static final String FROM_CREW_ID = "FROM_CREW_ID";
    private static final String RESULT_DATA = "RESULT_DATA";
    private static final String TIME_SHEET_ID = "TIME_SHEET_ID";
    private static final String WEEK_START_DATE = "WEEK_START_DATE";
    private static final String FROM_WEEK_START_DATE = "FROM_WEEK_START_DATE";
    private static final String WEEK_END_DATE = "WEEK_END_DATE";
    private static final String TYPE_ID = "TYPE_ID";
    private static final String USRNAME = "USR_NAME";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getAttendanceForIndividuals(Long clientId, Long projId, Long crewId, Date weekStartDate,
            Date weekEndDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                CREW_ATTENDACNE_FOR_INDIVIDUAL_TIME_SHEET);
        Map map = proc.getAttendanceForIndividuals(clientId, projId, crewId, weekStartDate, weekEndDate);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> copyTimeSheetEmpDetails(Long projId, Long crewId, Long fromCrewId, Date weekStartDate,
            Date fromWeekStartDate, Integer additional) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                COPY_TIME_SHEET_EMP_REG);
        Map map = proc.copyTimeSheetEmpDetails(projId, crewId, fromCrewId, weekStartDate, fromWeekStartDate,
                additional);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getAllCostCodeWageMap(Long projId, Date weekStartDate, Long timeSheetId, Long crewId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                TIME_SHEET_COSTCODE_WAGE_MAP);
        Map map = proc.getAllCostCodeWageMap(projId, weekStartDate, timeSheetId, crewId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);

            if (CREW_ATTENDACNE_FOR_INDIVIDUAL_TIME_SHEET.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(CLIENT_ID, Types.INTEGER));
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(WEEK_START_DATE, Types.DATE));
                declareParameter(new SqlParameter(WEEK_END_DATE, Types.DATE));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new TimeSheetEmpRegRowMapper() {
                }));
            } else if (COPY_TIME_SHEET_EMP_REG.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(FROM_CREW_ID, Types.INTEGER));
                declareParameter(new SqlParameter(WEEK_START_DATE, Types.DATE));
                declareParameter(new SqlParameter(FROM_WEEK_START_DATE, Types.DATE));
                declareParameter(new SqlParameter(TYPE_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new TimeSheetEmpRegRowMapper() {
                }));
            } else if (TIME_SHEET_COSTCODE_WAGE_MAP.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.INTEGER));
                declareParameter(new SqlParameter(WEEK_START_DATE, Types.DATE));
                declareParameter(new SqlParameter(TIME_SHEET_ID, Types.INTEGER));
                declareParameter(new SqlParameter(CREW_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new TimeSheetCostCodeRowMapper() {
                }));
            }
            compile();
        }

        public Map getAllIndividualsFromTimeSheetEmp(Long projId, String weekStartDate, String weekEndDate,
                String usrName, Long clientId) {

            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(WEEK_START_DATE, weekStartDate);
            inputs.put(WEEK_END_DATE, weekEndDate);
            inputs.put(USRNAME, usrName);
            inputs.put(CLIENT_ID, clientId);

            return super.execute(inputs);
        }

        public Map getProSettingsForTimeSheet(Long clientId, Long projId) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            return super.execute(inputs);
        }

        public Map generateCrewTimeSheet(Long clientId, Long projId, Long crewId, Integer additional) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(TYPE_ID, additional);
            return super.execute(inputs);
        }

        public Map generateIndividualTimeSheet(Long clientId, Long projId, Long empId) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(EMP_ID, empId);
            return super.execute(inputs);
        }

        public Map createTimeSheet(Long timSheetId) {
            Map inputs = new HashMap();
            inputs.put(TIME_SHEET_ID, timSheetId);
            return super.execute(inputs);
        }

        public Map getEmpRegDetails(Long clientId, Long projId, Long crewId, Integer additional, Date weekStartDate,
                Date weekEndDate) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(TYPE_ID, additional);
            inputs.put(WEEK_START_DATE, weekStartDate);
            inputs.put(WEEK_END_DATE, weekEndDate);
            return super.execute(inputs);
        }

        public Map getOtherCrewEmpAttendance(Long clientId, Long projId, Long crewId, Long fromCrewId,
                Date weekStartDate, Date weekEndDate) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(FROM_CREW_ID, fromCrewId);
            inputs.put(WEEK_START_DATE, weekStartDate);
            inputs.put(WEEK_END_DATE, weekEndDate);
            return super.execute(inputs);
        }

        public Map getAttendanceForIndividuals(Long clientId, Long projId, Long crewId, Date weekStartDate,
                Date weekEndDate) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(WEEK_START_DATE, weekStartDate);
            inputs.put(WEEK_END_DATE, weekEndDate);
            return super.execute(inputs);
        }

        public Map getAttendanceForTimeSheeet(Long clientId, Long projId, Date weekStartDate, Date weekEndDate) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(WEEK_START_DATE, weekStartDate);
            inputs.put(WEEK_END_DATE, weekEndDate);
            return super.execute(inputs);
        }

        public Map getTimeSheetBookedHrsForOtherCrew(Long clientId, Long projId, Long timeSheetId, Date weekStartDate) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(TIME_SHEET_ID, timeSheetId);
            inputs.put(WEEK_START_DATE, weekStartDate);
            return super.execute(inputs);
        }

        public Map getTimeSheetBookedHrsForSameCrew(Long clientId, Long projId, Long crewId, Date weekStartDate) {
            Map inputs = new HashMap();
            inputs.put(CLIENT_ID, clientId);
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(WEEK_START_DATE, weekStartDate);
            return super.execute(inputs);
        }

        public Map getWorkDairyBookedHrs(Long projId, Date weekStartDate, Date weekEndDate) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(WEEK_START_DATE, weekStartDate);
            inputs.put(WEEK_END_DATE, weekEndDate);
            return super.execute(inputs);
        }

        public Map enableCrewTimeSheet(Long projId, Long crewId, Long timeSheetId, Integer typeId) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(TIME_SHEET_ID, timeSheetId);
            inputs.put(TYPE_ID, typeId);
            return super.execute(inputs);
        }

        public Map enableIndividualTimeSheet(Long projId, Long empId, Long timeSheetId, Integer typeId) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(EMP_ID, empId);
            inputs.put(TIME_SHEET_ID, timeSheetId);
            inputs.put(TYPE_ID, typeId);
            return super.execute(inputs);
        }

        public Map copyTimeSheetEmpDetails(Long projId, Long crewId, Long fromCrewId, Date weekStartDate,
                Date fromWeekStartDate, Integer additional) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(CREW_ID, crewId);
            inputs.put(FROM_CREW_ID, fromCrewId);
            inputs.put(WEEK_START_DATE, weekStartDate);
            inputs.put(FROM_WEEK_START_DATE, fromWeekStartDate);
            inputs.put(TYPE_ID, additional);
            return super.execute(inputs);
        }

        public Map getIndividualsFromTimeSheet(Long projId, Date weekStartDate, Date weekEndDate, Integer additional) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(WEEK_START_DATE, weekStartDate);
            inputs.put(WEEK_END_DATE, weekEndDate);
            inputs.put(TYPE_ID, additional);
            return super.execute(inputs);
        }

        public Map getAllIndividualsFromTimeSheet(Long projId, Date weekStartDate, Date weekEndDate) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(WEEK_START_DATE, weekStartDate);
            inputs.put(WEEK_END_DATE, weekEndDate);
            return super.execute(inputs);
        }

        public Map getAllCostCodeWageMap(Long projId, Date weekStartDate, Long timeSheetId, Long crewId) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projId);
            inputs.put(WEEK_START_DATE, weekStartDate);
            inputs.put(TIME_SHEET_ID, timeSheetId);
            inputs.put(CREW_ID, crewId);
            return super.execute(inputs);
        }

    }

}
