package com.rjtech.register.repository.material;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class MaterialDeliveryDockRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        labelKeyTO.setUnitOfMeasure(rs.getString(4));
        Map<String, String> displayMap = new HashMap<String, String>();
        displayMap.put(CommonConstants.CLASS_TYPE, rs.getString(5));
        displayMap.put(CommonConstants.COMPANY_CATG_NAME, rs.getString(6));
        displayMap.put(CommonConstants.PUR_CODE, rs.getString(7));
        displayMap.put(CommonConstants.DELIVERY_DOCKET_NO, rs.getString(8));
        displayMap.put(CommonConstants.DELIVERY_DOCKET_DATE, rs.getString(9));
        displayMap.put(CommonConstants.LOCATION, rs.getString(10));
        displayMap.put(CommonConstants.TRANSIT_QTY, rs.getString(11));
        displayMap.put(CommonConstants.DEFECT_COMMENTS, rs.getString(12));
        displayMap.put(CommonConstants.COMMENTS, rs.getString(13));
        displayMap.put(CommonConstants.DELIVERY_DOCKET_ID, rs.getString(14));
        displayMap.put("calculatedReceivedQty", rs.getString(15));
        labelKeyTO.setDisplayNamesMap(displayMap);

        return labelKeyTO;
    }

}
