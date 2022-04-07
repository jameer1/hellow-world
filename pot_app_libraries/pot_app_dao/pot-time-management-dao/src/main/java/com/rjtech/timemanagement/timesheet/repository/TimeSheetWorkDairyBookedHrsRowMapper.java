package com.rjtech.timemanagement.timesheet.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class TimeSheetWorkDairyBookedHrsRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        labelKeyTO.setCode(rs.getString(2));
        Map<String, String> displayMap = new HashMap<String, String>();
        displayMap.put(CommonConstants.TOTAL_HRS, rs.getString(3));
        labelKeyTO.setDisplayNamesMap(displayMap);
        return labelKeyTO;
    }

}
