package com.rjtech.user.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class UsersByClientRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        labelKeyTO.setCode(rs.getString(2));
        Map<String, String> displayMap = new HashMap<String, String>();
        displayMap.put(CommonConstants.FIRST_NAME, rs.getString(3));
        displayMap.put(CommonConstants.LAST_NAME, rs.getString(4));
        displayMap.put(CommonConstants.GENDER, rs.getString(5));
        displayMap.put(CommonConstants.DESIGNATION, rs.getString(6));
        displayMap.put(CommonConstants.COMPANY_CATG_NAME, rs.getString(7));
        displayMap.put(CommonConstants.DISPLAY_NAME, rs.getString(3) + " " + rs.getString(4));
        labelKeyTO.setDisplayNamesMap(displayMap);
        return labelKeyTO;
    }

}
