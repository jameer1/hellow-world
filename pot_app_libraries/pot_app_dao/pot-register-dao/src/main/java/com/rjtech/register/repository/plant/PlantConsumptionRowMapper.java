package com.rjtech.register.repository.plant;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class PlantConsumptionRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJ_ID, rs.getString(2));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.DEPLOYMENT_ID, rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.MOB_DATE, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.DEMOB_DATE, rs.getString(5));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.MOB_ODO_METER, rs.getString(6));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.DEMOB_ODO_METER, rs.getString(7));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.USED_TIME, rs.getString(8));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.IDLE_TIME, rs.getString(9));
        return labelKeyTO;
    }
}
