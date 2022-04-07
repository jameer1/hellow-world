package com.rjtech.timemanagement.workdairy.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class WorkDairyProgressRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        Map<String, String> displayMap = new HashMap<String, String>();
        displayMap.put(CommonConstants.SOW_CODE, rs.getString(2));
        displayMap.put(CommonConstants.SOW_NAME, rs.getString(3));
        displayMap.put(CommonConstants.SOR_CODE, rs.getString(4));
        displayMap.put(CommonConstants.SOE_CODE, rs.getString(5));
        displayMap.put(CommonConstants.COST_CODE, rs.getString(6));
        labelKeyTO.setDisplayNamesMap(displayMap);
        labelKeyTO.setUnitOfMeasure(rs.getString(7));

        return labelKeyTO;
    }

}
