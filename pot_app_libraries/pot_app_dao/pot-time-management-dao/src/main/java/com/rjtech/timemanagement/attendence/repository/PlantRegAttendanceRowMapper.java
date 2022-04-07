package com.rjtech.timemanagement.attendence.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class PlantRegAttendanceRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        labelKeyTO.setUnitOfMeasure(rs.getString(10));
        Map<String, String> displayMap = new HashMap<String, String>();
        displayMap.put(CommonConstants.PLANT_REG_NO, rs.getString(4));
        displayMap.put(CommonConstants.PLANT_MANFACTURE, rs.getString(5));
        displayMap.put(CommonConstants.PLANT_MODEL, rs.getString(6));
        displayMap.put(CommonConstants.CLASS_TYPE, rs.getString(7));
        displayMap.put(CommonConstants.PROCURE_CATG, rs.getString(8));
        displayMap.put(CommonConstants.COMPANY_CATG_NAME, rs.getString(9));
        labelKeyTO.setDisplayNamesMap(displayMap);
        return labelKeyTO;
    }

}
