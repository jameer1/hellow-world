package com.rjtech.timemanagement.timesheet.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.dto.LabelKeyTO;

public class TimeSheetCostCodeRowMapper implements RowMapper<LabelKeyTO> {
    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        /*Map<String, String> displayMap = new LinkedHashMap<String, String>();
        
        displayMap.put(TimeSheetURLConstants.TIMESHEET_EMP_ID, rs.getString(1));
        displayMap.put(TimeSheetURLConstants.TIMESHEET_COSTCODE_ID, rs.getString(2));
        displayMap.put(TimeSheetURLConstants.TIMESHEET_WAGEID, rs.getString(3));
        displayMap.put(TimeSheetURLConstants.TIME_SHEET_STATUS, rs.getString(4));
        
        labelKeyTO.setDisplayNamesMap(displayMap);
        return labelKeyTO;*/
        labelKeyTO.setId(rs.getLong(4));
        labelKeyTO.setCode(rs.getString(1));
        labelKeyTO.setName(rs.getString(2));
        labelKeyTO.setEmail(rs.getString(3));
        return labelKeyTO;
    }
}
