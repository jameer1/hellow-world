package com.rjtech.timemanagement.timesheet.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class TimeSheetOtherCrewBookedHrsRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        Map<String, String> displayMap = new HashMap<String, String>();
        displayMap.put(CommonConstants.DAY1_BOOKED_HRS, rs.getString(2));
        displayMap.put(CommonConstants.DAY2_BOOKED_HRS, rs.getString(3));
        displayMap.put(CommonConstants.DAY3_BOOKED_HRS, rs.getString(4));
        displayMap.put(CommonConstants.DAY4_BOOKED_HRS, rs.getString(5));
        displayMap.put(CommonConstants.DAY5_BOOKED_HRS, rs.getString(6));
        displayMap.put(CommonConstants.DAY6_BOOKED_HRS, rs.getString(7));
        displayMap.put(CommonConstants.DAY7_BOOKED_HRS, rs.getString(8));
        labelKeyTO.setDisplayNamesMap(displayMap);

        return labelKeyTO;
    }

}
