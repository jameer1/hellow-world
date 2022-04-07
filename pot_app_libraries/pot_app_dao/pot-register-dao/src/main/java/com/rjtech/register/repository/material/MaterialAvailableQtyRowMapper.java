package com.rjtech.register.repository.material;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.register.constans.RegisterConstants;

public class MaterialAvailableQtyRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.QTY, rs.getString(2));
        return labelKeyTO;
    }

}
