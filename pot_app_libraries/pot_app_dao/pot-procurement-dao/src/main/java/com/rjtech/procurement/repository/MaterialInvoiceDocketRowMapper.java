package com.rjtech.procurement.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class MaterialInvoiceDocketRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.DELIVERY_DOCKET_NO, rs.getString(2));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.DELIVERY_DOCKET_DATE, rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.QTY, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.RECEIVED_BY, rs.getString(5));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.DEFECT_COMMENTS, rs.getString(6));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.SUPPLY_DELIVRY_DATE, rs.getString(7));
        return labelKeyTO;
    }

}
