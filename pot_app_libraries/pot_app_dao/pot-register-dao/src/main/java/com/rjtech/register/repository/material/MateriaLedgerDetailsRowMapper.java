package com.rjtech.register.repository.material;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.register.constans.RegisterConstants;

public class MateriaLedgerDetailsRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        Map<String, String> displayMap = new HashMap<String, String>();
        displayMap.put(RegisterConstants.PROJ_ID, String.valueOf(rs.getInt(4)));
        displayMap.put(RegisterConstants.FROM_LOCATION, rs.getString(5));
        displayMap.put(RegisterConstants.CLASS_TYPE, String.valueOf(rs.getInt(6)));
        displayMap.put(RegisterConstants.CMP_ID, String.valueOf(rs.getInt(7)));
        displayMap.put(RegisterConstants.DELIVERY_DOCKET_NO, rs.getString(8));
        displayMap.put(RegisterConstants.DELIVERY_DOCKET_TYPE, rs.getString(9));
        displayMap.put(RegisterConstants.DELIVERY_DOCKET_DATE, rs.getString(10));
        displayMap.put(RegisterConstants.UNIT_OF_RATE, rs.getString(11));
        displayMap.put(RegisterConstants.OPEN_QTY, rs.getString(12));
        displayMap.put(RegisterConstants.RECEIVED_QTY, rs.getString(13));
        displayMap.put(RegisterConstants.ISSUED_QTY, rs.getString(14));
        displayMap.put(RegisterConstants.CONSUMPTION_QTY, rs.getString(15));
        displayMap.put(RegisterConstants.TRANSIT_QTY, rs.getString(16));
        displayMap.put(RegisterConstants.TRANSFER_QTY, rs.getString(17));
        displayMap.put(RegisterConstants.CLOSE_QTY, rs.getString(18));
        //		displayMap.put(RegisterConstants.CURRENCY, rs.getString(20));
        labelKeyTO.setDisplayNamesMap(displayMap);

        return labelKeyTO;
    }
}
