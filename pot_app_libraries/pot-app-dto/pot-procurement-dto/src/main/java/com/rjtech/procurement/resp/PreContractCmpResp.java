package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.ProcurementCompanyTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractCmpTO;

public class PreContractCmpResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PreContractCmpTO> preContractCmpTOs = null;
    private Long preContractId;
    private Map<Long, LabelKeyTO> userProjMap = null;
    private Map<Long, ProcurementCompanyTO> preContractCompanyMap = null;
    private Map<Long, LabelKeyTO> companyMap = null;

    private Map<Long, LabelKeyTO> precontractReqApprMap = null;

    public PreContractCmpResp() {
        userProjMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        preContractCmpTOs = new ArrayList<PreContractCmpTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        preContractCompanyMap = new HashMap<Long, ProcurementCompanyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        precontractReqApprMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        companyMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public List<PreContractCmpTO> getPreContractCmpTOs() {
        return preContractCmpTOs;
    }

    public void setPreContractCmpTOs(List<PreContractCmpTO> preContractCmpTOs) {
        this.preContractCmpTOs = preContractCmpTOs;
    }

    public Map<Long, ProcurementCompanyTO> getPreContractCompanyMap() {
        return preContractCompanyMap;
    }

    public void setPreContractCompanyMap(Map<Long, ProcurementCompanyTO> preContractCompanyMap) {
        this.preContractCompanyMap = preContractCompanyMap;
    }

    public Map<Long, LabelKeyTO> getCompanyMap() {
        return companyMap;
    }

    public void setCompanyMap(Map<Long, LabelKeyTO> companyMap) {
        this.companyMap = companyMap;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

    public Map<Long, LabelKeyTO> getPrecontractReqApprMap() {
        return precontractReqApprMap;
    }

    public void setPrecontractReqApprMap(Map<Long, LabelKeyTO> precontractReqApprMap) {
        this.precontractReqApprMap = precontractReqApprMap;
    }

}
