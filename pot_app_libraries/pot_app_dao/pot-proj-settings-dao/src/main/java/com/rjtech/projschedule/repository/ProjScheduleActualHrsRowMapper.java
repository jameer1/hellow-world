package com.rjtech.projschedule.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class ProjScheduleActualHrsRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_DATE, rs.getString(2));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_HRS, rs.getString(3));
        return labelKeyTO;
    }

}
