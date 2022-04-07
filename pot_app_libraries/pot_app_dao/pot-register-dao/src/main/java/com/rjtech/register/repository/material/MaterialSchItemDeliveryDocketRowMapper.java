package com.rjtech.register.repository.material;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class MaterialSchItemDeliveryDocketRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        labelKeyTO.setUnitOfMeasure(rs.getString(4));
        Map<String, String> displayMap = new HashMap<String, String>();
        displayMap.put(CommonConstants.PROJ_ID, rs.getString(5));
        displayMap.put(CommonConstants.PUR_CODE, rs.getString(6));
        displayMap.put(CommonConstants.CLASS_TYPE, rs.getString(7));
        displayMap.put(CommonConstants.CMP_NAME, rs.getString(8));
        displayMap.put(CommonConstants.DELIVERY_DOCKET_NO, rs.getString(9));
        displayMap.put(CommonConstants.DELIVERY_DOCKET_DATE, rs.getString(10));
        displayMap.put(CommonConstants.DELIVERY_PLACE, rs.getString(11));
        displayMap.put(CommonConstants.QTY, rs.getString(12));
        displayMap.put(CommonConstants.UNIT_OF_RATE, rs.getString(13));
        displayMap.put(CommonConstants.RECEIVED_BY, rs.getString(14));
        displayMap.put(CommonConstants.DEFECT_COMMENTS, rs.getString(15));
        displayMap.put(CommonConstants.COMMENTS, rs.getString(16));
        displayMap.put(CommonConstants.SUPPLY_DELIVRY_DATE, rs.getString(17));
        displayMap.put(CommonConstants.DELIVERY_CATAGORY, rs.getString(18));
        displayMap.put(CommonConstants.DELIVERY_DOCKET_ID, rs.getString(19));
        labelKeyTO.setDisplayNamesMap(displayMap);

        return labelKeyTO;
    }

}
