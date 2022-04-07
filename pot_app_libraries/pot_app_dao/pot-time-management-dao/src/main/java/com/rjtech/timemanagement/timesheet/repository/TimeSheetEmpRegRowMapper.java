package com.rjtech.timemanagement.timesheet.repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class TimeSheetEmpRegRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        labelKeyTO.setCode(rs.getString(2));
        Map<String, String> displayMap = new HashMap<String, String>();
        displayMap.put(CommonConstants.FIRST_NAME, rs.getString(3));
        displayMap.put(CommonConstants.LAST_NAME, rs.getString(4));
        displayMap.put(CommonConstants.GENDER, rs.getString(5));
        displayMap.put(CommonConstants.CLASS_TYPE, rs.getString(6));
        displayMap.put(CommonConstants.PROCURE_CATG, rs.getString(6));
        displayMap.put(CommonConstants.COMPANY_CATG_NAME, rs.getString(8));
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        if (columnsNumber >= 9) {
            displayMap.put(CommonConstants.DAY1_BOOKED_HRS, String.valueOf(rs.getInt(9)));
        }
        if (columnsNumber >= 10) {
            displayMap.put(CommonConstants.DAY2_BOOKED_HRS, String.valueOf(rs.getInt(10)));
        }
        if (columnsNumber >= 11) {
            displayMap.put(CommonConstants.DAY3_BOOKED_HRS, String.valueOf(rs.getInt(11)));
        }
        if (columnsNumber >= 12) {
            displayMap.put(CommonConstants.DAY4_BOOKED_HRS, String.valueOf(rs.getInt(12)));
        }
        if (columnsNumber >= 13) {
            displayMap.put(CommonConstants.DAY5_BOOKED_HRS, String.valueOf(rs.getInt(13)));
        }
        if (columnsNumber >= 14) {
            displayMap.put(CommonConstants.DAY6_BOOKED_HRS, String.valueOf(rs.getInt(14)));
        }
        if (columnsNumber >= 15) {
            displayMap.put(CommonConstants.DAY7_BOOKED_HRS, String.valueOf(rs.getInt(15)));
        }
        if (columnsNumber >= 16) {
            displayMap.put(CommonConstants.TOTAL_HRS, rs.getString(16));
        }
        labelKeyTO.setDisplayNamesMap(displayMap);

        return labelKeyTO;
    }

}
