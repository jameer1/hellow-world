package com.rjtech.projectlib.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.dto.ProjSOWItemTO;

@Repository
public class ProjSowItemsResultSetExtractor implements ResultSetExtractor<List<ProjSOWItemTO>> {

    public List<ProjSOWItemTO> extractData(ResultSet rs) throws SQLException {

        List<ProjSOWItemTO> list = new ArrayList<ProjSOWItemTO>();
        while (rs.next()) {
            ProjSOWItemTO sowItemTO = new ProjSOWItemTO();
            sowItemTO.setSowId(Long.valueOf(rs.getString("SOW_ID")));
            sowItemTO.setId(sowItemTO.getSowId());
            sowItemTO.setSoeId(Long.valueOf(rs.getString("SOE_ID")));
            sowItemTO.setProjId(Long.valueOf(rs.getString("PROJ_ID")));
            sowItemTO.setStartDate(rs.getString("SOW_START_DATE"));
            sowItemTO.setFinishDate(rs.getString("SOW_FINISH_DATE"));
            sowItemTO.setOriginalQty(new BigDecimal(rs.getString("SOE_QUANTITY")));
            sowItemTO.setItem(true);
            // rs.getString("BASELINE_ID");
            list.add(sowItemTO);
        }

        return list;
    }

}
