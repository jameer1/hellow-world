package com.rjtech.procurement.repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class ProcurementCostCodeRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.ESTIMATE_AMOUNT, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.BUDGET_AMOUNT, rs.getString(5));
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        if (columnsNumber > 5) {
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_ID, String.valueOf(rs.getInt(6)));
        }
        return labelKeyTO;
    }

}
