package com.rjtech.register.proc.emp;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class EmpTransferReqApprRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.FIRST_NAME, rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.LAST_NAME, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.CLASS_TYPE, rs.getString(5));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_NAME, rs.getString(6));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.EXPECTED_DATE, rs.getString(7));

        return labelKeyTO;
    }

}
