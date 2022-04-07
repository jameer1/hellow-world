package com.rjtech.timemanagement.attendence.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.common.dto.LabelKeyTO;

public class AttendanceRowMapper implements RowMapper<LabelKeyTO> {

    public LabelKeyTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(Long.valueOf(rs.getInt(1)));
        labelKeyTO.setCode(rs.getString(2));
        labelKeyTO.setName(rs.getString(3));
        return labelKeyTO;
    }

}
