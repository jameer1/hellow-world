package com.rjtech.register.repository.material;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.register.constans.RegisterConstants;

public class MaterialTransReqApprRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.PUR_CODE, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.CLASS_TYPE, rs.getString(5));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.CLASS_NAME, rs.getString(6));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.CLASS_PARENT_NAME, rs.getString(7));
        labelKeyTO.setUnitOfMeasure(rs.getString(8));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.QTY, rs.getString(9));
        return labelKeyTO;
    }

}
