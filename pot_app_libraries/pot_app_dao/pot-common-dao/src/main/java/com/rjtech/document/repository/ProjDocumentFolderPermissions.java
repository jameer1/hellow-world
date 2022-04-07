package com.rjtech.document.repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.rjs.persistence.repository.AbstractJDBCRepository;
import com.rjtech.user.repository.UserProjRowMapper;

@Repository
public class ProjDocumentFolderPermissions extends AbstractJDBCRepository {

    private static final String DOCUMENT_FOLDER_PERMISSIONS = "DOCUMENT_FOLDER_PERMISSIONS";

    public static final String USER_ID = "USER_ID";
    public static final String READ_TYPE = "READ_TYPE";
    public static final String WRITE_TYPE = "WRITE_TYPE";

    private static final String RESULT_DATA = "RESULT_DATA";

    @SuppressWarnings("unchecked")
    public List<LabelKeyTO> getFolderPermissions(Long userId) {
        CustomerStoredProcedure proc = new CustomerStoredProcedure(this.getJdbcTemplate().getDataSource(),
                DOCUMENT_FOLDER_PERMISSIONS);
        Map map = proc.getFolderPermissions(userId);
        return (List<LabelKeyTO>) map.get(RESULT_DATA);
    }

    class CustomerStoredProcedure extends StoredProcedure {

        public CustomerStoredProcedure(DataSource dataSource, String sprocName) {
            super(dataSource, sprocName);
            if (DOCUMENT_FOLDER_PERMISSIONS.equalsIgnoreCase(sprocName)) {
                declareParameter(new SqlParameter(USER_ID, Types.INTEGER));
                declareParameter(new SqlReturnResultSet(RESULT_DATA, new FolderPermissionsRowMapper() {
                }));

            }
            compile();
        }

        public Map getFolderPermissions(Long userId) {
            Map inputs = new HashMap();
            inputs.put(USER_ID, userId);
            return super.execute(inputs);
        }
    }

}
