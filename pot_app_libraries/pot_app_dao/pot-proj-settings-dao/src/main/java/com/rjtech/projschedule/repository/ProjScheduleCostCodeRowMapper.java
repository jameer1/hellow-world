package com.rjtech.projschedule.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;

public class ProjScheduleCostCodeRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_START_DATE, rs.getString(2));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_FINISH_DATE, rs.getString(3));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.ACTUAL_BUDGET, rs.getString(4));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.REVISED_BUDGET, rs.getString(5));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.ESTIMATE_TO_COMPLETE, rs.getString(6));
        labelKeyTO.getDisplayNamesMap().put(CommonConstants.ESTIMATION_TO_COMPLETTION, rs.getString(7));

        return labelKeyTO;
    }

}
