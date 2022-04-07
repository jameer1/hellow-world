package com.rjtech.procurement.service.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class POScheduledItemsRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(String.valueOf(rs.getInt(1))));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));

        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_CLASS_ID, String.valueOf(rs.getInt(4)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.COST_ID, String.valueOf(rs.getInt(5)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.STOCK_ID, String.valueOf(rs.getInt(6)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJ_STOCK_ID, String.valueOf(rs.getInt(7)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.CURRENCY, String.valueOf(rs.getInt(8)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_START_DATE, String.valueOf(rs.getDate(9)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_FINISH_DATE, String.valueOf(rs.getDate(10)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.QTY, String.valueOf(rs.getInt(11)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.UNIT_OF_RATE, String.valueOf(rs.getBigDecimal(12)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_ITEM_CMP_ID, String.valueOf(rs.getInt(13)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_CMP_ID, String.valueOf(rs.getInt(14)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_ID, String.valueOf(rs.getInt(15)));
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        if (columnsNumber >= 16) {
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.DELIVERY_PLACE, rs.getString(16));
        }
        if (columnsNumber >= 17) {
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.DELIVERY_CATAGORY, rs.getString(17));
        }

        if (columnsNumber == 18 && rsmd.getColumnLabel(18).equals("CMC_RECEIVED_QTY")) {
            labelKeyTO.getDisplayNamesMap().put("recievedQty", String.valueOf(rs.getInt(18)));
        }

        if (columnsNumber >= 18 && !rsmd.getColumnLabel(18).equals("CMC_RECEIVED_QTY")) {
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_ID, String.valueOf(rs.getInt(18)));
        }
        if (columnsNumber >= 19) {
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_CODE, rs.getString(19));
        }
        return labelKeyTO;
    }
}
