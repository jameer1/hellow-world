package com.rjtech.register.repository.material;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.rjs.persistence.repository.AbstractJDBCRepository;

@Repository
public class MaterialConsumptionProcRepository extends AbstractJDBCRepository {

    private static final String GET_WORKDAIRY_MATERIAL_CONSUMPTION = "GET_WORKDAIRY_MATERIAL_CONSUMPTION";
    private static final String GET_MATERIAL_STORE_INTRANSIT_CONSUMPTION = "GET_MATERIAL_STORE_INTRANSIT_CONSUMPTION";
    private static final String GET_MATERIAL_SROCK_PILED_CONSUMPTION = "GET_MATERIAL_SROCK_PILED_CONSUMPTION";
    private static final String GET_MATERIAL_DAILY_ISSUE_SCH_ITEMS = "GET_MATERIAL_DAILY_ISSUE_SCH_ITEMS";
    private static final String GET_MATERIAL_LEDGER_DETAILS = "GET_MATERIAL_LEDGER_DETAILS";

    private static final String PROJ_ID = "PROJ_ID";
    private static final String FROM_DATE = "FROM_DATE";
    private static final String TO_DATE = "TO_DATE";
    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getWorkdairyMaterialConsumption(List<Long> projIds, Date fromDate, Date toDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_WORKDAIRY_MATERIAL_CONSUMPTION);
        Map result = proc.getMaterialConsumption(StringUtils.join(projIds, ","), fromDate, toDate);
        return (List<LabelKeyTO>) result.get(RESULT_DATA);

    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getMaterialStoreTransitConsumption(List<Long> projIds, Date fromDate, Date toDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_MATERIAL_STORE_INTRANSIT_CONSUMPTION);
        Map result = proc.getMaterialConsumption(StringUtils.join(projIds, ","), fromDate, toDate);
        return (List<LabelKeyTO>) result.get(RESULT_DATA);

    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getMaterialStockPiledConsumption(List<Long> projIds, Date fromDate, Date toDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_MATERIAL_SROCK_PILED_CONSUMPTION);
        Map result = proc.getMaterialConsumption(StringUtils.join(projIds, ","), fromDate, toDate);
        return (List<LabelKeyTO>) result.get(RESULT_DATA);

    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getMaterialDailyIssueSchItems(List<Long> projIds, Date fromDate, Date toDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_MATERIAL_DAILY_ISSUE_SCH_ITEMS);
        Map result = proc.getMaterialConsumption(StringUtils.join(projIds, ","), fromDate, toDate);
        return (List<LabelKeyTO>) result.get(RESULT_DATA);

    }

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getMaterialProjLedgers(List<Long> projIds, Date fromDate, Date toDate) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                GET_MATERIAL_LEDGER_DETAILS);
        Map result = proc.getMaterialConsumption(StringUtils.join(projIds, ","), fromDate, toDate);
        return (List<LabelKeyTO>) result.get(RESULT_DATA);

    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (GET_WORKDAIRY_MATERIAL_CONSUMPTION.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.VARCHAR));
                declareParameter(new SqlParameter(FROM_DATE, Types.DATE));
                declareParameter(new SqlParameter(TO_DATE, Types.DATE));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MateriaWorkairyConsumeRowMapper() {
                }));
            } else if (GET_MATERIAL_STORE_INTRANSIT_CONSUMPTION.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.VARCHAR));
                declareParameter(new SqlParameter(FROM_DATE, Types.DATE));
                declareParameter(new SqlParameter(TO_DATE, Types.DATE));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MateriaStoreTansitConsumeRowMapper() {
                }));
            } else if (GET_MATERIAL_SROCK_PILED_CONSUMPTION.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.VARCHAR));
                declareParameter(new SqlParameter(FROM_DATE, Types.DATE));
                declareParameter(new SqlParameter(TO_DATE, Types.DATE));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MateriaStrockPiledConsumeRowMapper() {
                }));
            } else if (GET_MATERIAL_DAILY_ISSUE_SCH_ITEMS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.VARCHAR));
                declareParameter(new SqlParameter(FROM_DATE, Types.DATE));
                declareParameter(new SqlParameter(TO_DATE, Types.DATE));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MateriaDailyIssueSchItemsRowMapper() {
                }));
            } else if (GET_MATERIAL_LEDGER_DETAILS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(PROJ_ID, Types.VARCHAR));
                declareParameter(new SqlParameter(FROM_DATE, Types.DATE));
                declareParameter(new SqlParameter(TO_DATE, Types.DATE));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new MateriaLedgerDetailsRowMapper() {
                }));
            }
            compile();
        }

        public Map getMaterialConsumption(String projIds, Date fromDate, Date toDate) {
            Map inputs = new HashMap();
            inputs.put(PROJ_ID, projIds);
            inputs.put(FROM_DATE, fromDate);
            inputs.put(TO_DATE, toDate);
            return super.execute(inputs);
        }

    }

}
