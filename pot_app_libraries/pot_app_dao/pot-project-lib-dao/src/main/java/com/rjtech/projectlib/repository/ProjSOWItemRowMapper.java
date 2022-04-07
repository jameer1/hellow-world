package com.rjtech.projectlib.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.projectlib.util.ProjLibMapConstants;

@Repository
public class ProjSOWItemRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(rs.getLong(1));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.SOW_CODE, rs.getString(2));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.SOW_NAME, rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.SUB_GROUP_CODE, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.SUB_GROUP_NAME, rs.getString(5));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.SOE_CODE, rs.getString(6));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.SOE_NAME, rs.getString(7));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.UNIT_OF_MEASURE, rs.getString(8));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.SOR_CODE, rs.getString(9));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.SOR_NAME, rs.getString(10));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.COST_CODE, rs.getString(11));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.COST_NAME, rs.getString(12));
        return labelKeyTO;
    }

}
