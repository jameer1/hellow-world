package com.rjtech.register.repository.plant;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.register.constans.RegisterConstants;

public class PlantRepairMaterialProjDocketRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.PROJ_DOCKET_ID, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.PROJ_DOCKET_NO, rs.getString(5));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.PROJ_DOCKET_DATE, rs.getString(6));
        labelKeyTO.getDisplayNamesMap().put(RegisterConstants.QTY, rs.getString(7));

        return labelKeyTO;
    }

}
