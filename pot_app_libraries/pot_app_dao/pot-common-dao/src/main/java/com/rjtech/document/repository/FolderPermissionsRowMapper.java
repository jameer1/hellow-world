package com.rjtech.document.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class FolderPermissionsRowMapper implements RowMapper<LabelKeyTO> {
    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.READ_VALUE, rs.getString(2));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.WRITE_VALUE, rs.getString(3));

        return labelKeyTO;
    }

}
