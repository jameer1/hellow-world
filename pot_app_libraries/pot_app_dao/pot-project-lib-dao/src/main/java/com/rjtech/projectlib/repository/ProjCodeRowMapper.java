package com.rjtech.projectlib.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.projectlib.util.ProjLibMapConstants;

@Repository
public class ProjCodeRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        int count = ((ResultSet) rs).getMetaData().getColumnCount();
        labelKeyTO.setId(rs.getLong(1));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.SUB_GROUP_CODE, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.SUB_GROUP_NAME, rs.getString(5));
        if (count > 5) {
            labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.UNIT_OF_MEASURE, rs.getString(6));
        }
        if (count > 6) {
            labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.UNIT_RATE, rs.getString(7));
        }
        if (count > 7) {
            labelKeyTO.getDisplayNamesMap().put(ProjLibMapConstants.QTY, rs.getString(8));
        }
        return labelKeyTO;
    }

}
