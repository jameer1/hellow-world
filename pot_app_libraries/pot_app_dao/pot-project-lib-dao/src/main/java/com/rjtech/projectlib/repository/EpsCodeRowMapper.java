package com.rjtech.projectlib.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rjtech.common.dto.LabelKeyTO;

@Repository
public class EpsCodeRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(rs.getLong(1));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        return labelKeyTO;
    }

}
