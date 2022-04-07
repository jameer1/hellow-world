package com.rjtech.register.repository.material;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.register.constans.RegisterConstants;

public class MateriaWorkairyConsumeRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        Map<String, String> displayMap = new HashMap<String, String>();
        displayMap.put(RegisterConstants.PROJ_ID, String.valueOf(rs.getInt(4)));
        displayMap.put(RegisterConstants.WORK_DAIRY_CODE, rs.getString(5));
        displayMap.put(RegisterConstants.REQ_USER, rs.getString(6));
        displayMap.put(RegisterConstants.STOCK_SOURCE_TYPE, rs.getString(7));
        displayMap.put(RegisterConstants.CLASS_TYPE, String.valueOf(rs.getInt(8)));
        displayMap.put(RegisterConstants.CMP_ID, String.valueOf(rs.getInt(9)));
        displayMap.put(RegisterConstants.DELIVERY_DOCKET_TYPE, rs.getString(10));
        displayMap.put(RegisterConstants.DELIVERY_DOCKET_NO, rs.getString(11));
        displayMap.put(RegisterConstants.DELIVERY_DOCKET_DATE, rs.getString(12));
        displayMap.put(RegisterConstants.UNIT_OF_RATE, rs.getString(13));
        displayMap.put(RegisterConstants.CONSUMPTION_DATE, rs.getString(14));
        displayMap.put(RegisterConstants.CONSUMPTION_QTY, rs.getString(15));
        displayMap.put(RegisterConstants.DELIVERY_DOCKET_ID, rs.getString(16));

        labelKeyTO.setDisplayNamesMap(displayMap);

        return labelKeyTO;
    }

}
