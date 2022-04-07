package com.rjtech.timemanagement.timesheet.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class TimeSheetProjSettingsRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.WEEK_START_DAY, String.valueOf(rs.getInt(2)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.WEEK_END_DAY, String.valueOf(rs.getInt(3)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.MAX_TOTAL_HRS, String.valueOf(rs.getInt(4)));
        return labelKeyTO;
    }

}
