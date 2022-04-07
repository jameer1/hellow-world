package com.rjtech.register.repository.plant;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class PlantTransRequestRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PLANT_REG_NO, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PLANT_MANFACTURE, rs.getString(5));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PLANT_MODEL, rs.getString(6));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.EXPECTED_DATE, rs.getString(7));
        return labelKeyTO;
    }

}
