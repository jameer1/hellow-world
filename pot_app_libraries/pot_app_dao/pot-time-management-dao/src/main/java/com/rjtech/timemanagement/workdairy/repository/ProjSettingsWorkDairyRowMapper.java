package com.rjtech.timemanagement.workdairy.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.dto.LabelKeyTO;

public class ProjSettingsWorkDairyRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(rs.getLong((1)));
        return labelKeyTO;
    }

}
