package com.rjtech.user.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class UserProjRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_PARENT_EPSCODE_KEY, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_PARENT_NAME_KEY, rs.getString(5));
        return labelKeyTO;
    }

}
