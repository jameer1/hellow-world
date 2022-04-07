package com.rjtech.register.proc.emp;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.register.constans.RegisterConstants;

public class EmpPayCodeRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.TAX_APPLICABLE, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.COMMENTS, rs.getString(5));
        return labelKeyTO;
    }

}
