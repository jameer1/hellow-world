package com.rjtech.projectlib.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjtech.projectlib.dto.TotalActualTO;

public class ProjSowTotalActualMapper implements RowMapper<TotalActualTO> {

    public TotalActualTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        TotalActualTO actualRevisedTO = new TotalActualTO();
        actualRevisedTO.setId(rs.getLong(1));
        actualRevisedTO.setActualQuantity(rs.getLong(2));
        return actualRevisedTO;
    }

}
