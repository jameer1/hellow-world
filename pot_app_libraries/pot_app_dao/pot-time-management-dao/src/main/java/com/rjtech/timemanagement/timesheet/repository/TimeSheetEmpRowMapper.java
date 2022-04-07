package com.rjtech.timemanagement.timesheet.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class TimeSheetEmpRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getLong(1)));
        Map<String, String> displayMap = new HashMap<String, String>();
        displayMap.put(CommonConstants.EMP_CODE, rs.getString(2));
        displayMap.put(CommonConstants.FIRST_NAME, rs.getString(3));
        displayMap.put(CommonConstants.LAST_NAME, rs.getString(4));
        displayMap.put(CommonConstants.GENDER, rs.getString(5));
        displayMap.put(CommonConstants.ECM_NAME, rs.getString(6));
        displayMap.put(CommonConstants.PEC_DESC, rs.getString(7));
        displayMap.put(CommonConstants.CMP_NAME, rs.getString(8));
        labelKeyTO.setDisplayNamesMap(displayMap);

        return labelKeyTO;
    }

}
