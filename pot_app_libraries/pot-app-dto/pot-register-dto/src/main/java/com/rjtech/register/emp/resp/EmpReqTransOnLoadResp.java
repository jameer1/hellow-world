package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class EmpReqTransOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -7563997038832932625L;
    private Map<Long, LabelKeyTO> userMap = null;
    private List<String> statusType = null;
    private Map<Long, LabelKeyTO> empClassMap = null;
    private Map<Long, LabelKeyTO> empCompanyMap = null;
    private Map<Long, LabelKeyTO> userProjMap = null;

    public EmpReqTransOnLoadResp() {
        empClassMap = new HashMap<Long, LabelKeyTO>();
        empCompanyMap = new HashMap<Long, LabelKeyTO>();
        userMap = new HashMap<Long, LabelKeyTO>();
        statusType = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        userProjMap = new HashMap<Long, LabelKeyTO>();
    }

    public Map<Long, LabelKeyTO> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Long, LabelKeyTO> userMap) {
        this.userMap = userMap;
    }

    public List<String> getStatusType() {
        return statusType;
    }

    public void setStatusType(List<String> statusType) {
        this.statusType = statusType;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public Map<Long, LabelKeyTO> getEmpClassMap() {
        return empClassMap;
    }

    public void setEmpClassMap(Map<Long, LabelKeyTO> empClassMap) {
        this.empClassMap = empClassMap;
    }

    public Map<Long, LabelKeyTO> getEmpCompanyMap() {
        return empCompanyMap;
    }

    public void setEmpCompanyMap(Map<Long, LabelKeyTO> empCompanyMap) {
        this.empCompanyMap = empCompanyMap;
    }

}
