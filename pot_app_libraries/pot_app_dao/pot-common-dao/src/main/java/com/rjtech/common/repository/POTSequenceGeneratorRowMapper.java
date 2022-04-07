package com.rjtech.common.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.dto.LabelKeyTO;

public class POTSequenceGeneratorRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setCode(rs.getString(1));
        return labelKeyTO;
    }

}
