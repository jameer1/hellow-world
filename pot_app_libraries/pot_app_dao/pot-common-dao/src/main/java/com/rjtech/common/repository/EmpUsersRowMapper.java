package com.rjtech.common.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class EmpUsersRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.setName(rs.getString(2));
        labelKeyTO.setCode(rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.FIRST_NAME, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.LAST_NAME, rs.getString(5));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.CLASS_TYPE, rs.getString(6));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PHONE, rs.getString(7));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_NAME, rs.getString(8));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.USR_EMAIL, rs.getString(9));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJ_ID, rs.getString(10));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_NAME, rs.getString(11));

        return labelKeyTO;
    }

}
