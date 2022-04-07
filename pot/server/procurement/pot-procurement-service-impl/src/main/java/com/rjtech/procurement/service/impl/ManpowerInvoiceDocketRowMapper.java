package com.rjtech.procurement.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class ManpowerInvoiceDocketRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_ITEM_CMP_ID, rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.COMMENTS, rs.getString(4));
        return labelKeyTO;
    }

}
