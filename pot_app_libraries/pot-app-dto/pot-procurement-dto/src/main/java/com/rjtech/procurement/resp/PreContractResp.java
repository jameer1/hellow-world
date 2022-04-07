package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractTO;
import com.rjtech.procurement.dto.PreContractCostCodeTO;

public class PreContractResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PreContractTO> preContractTOs = null;
    private Map<Long, LabelKeyTO> userProjMap = null;
    private Map<Long, LabelKeyTO> usersMap = null;
    private Map<Long, LabelKeyTO> precontractReqApprMap = null;
    private List<PurchaseOrderResp> purchaseOrderResps = null;
    private List<PreContractTO> repeatPOPreContractTOs = null;
    private List<PreContractCostCodeTO> preContractCostCodeTOs = null;

    public PreContractResp() {
        preContractTOs = new ArrayList<PreContractTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        userProjMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        usersMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        precontractReqApprMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        repeatPOPreContractTOs = new ArrayList<PreContractTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PreContractTO> getPreContractTOs() {
        return preContractTOs;
    }

    public void setPreContractTOs(List<PreContractTO> preContractTOs) {
        this.preContractTOs = preContractTOs;
    }

    public Map<Long, LabelKeyTO> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Long, LabelKeyTO> usersMap) {
        this.usersMap = usersMap;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public Map<Long, LabelKeyTO> getPrecontractReqApprMap() {
        return precontractReqApprMap;
    }

    public void setPrecontractReqApprMap(Map<Long, LabelKeyTO> precontractReqApprMap) {
        this.precontractReqApprMap = precontractReqApprMap;
    }

    public List<PurchaseOrderResp> getPurchaseOrderResps() {
        return purchaseOrderResps;
    }

    public void setPurchaseOrderResps(List<PurchaseOrderResp> purchaseOrderResps) {
        this.purchaseOrderResps = purchaseOrderResps;
    }

    public List<PreContractTO> getRepeatPOPreContractTOs() {
        return repeatPOPreContractTOs;
    }

    public void setRepeatPOPreContractTOs(List<PreContractTO> repeatPOPreContractTOs) {
        this.repeatPOPreContractTOs = repeatPOPreContractTOs;
    }
    
    public List<PreContractCostCodeTO> getPreContractCostCodeTOs() {
        return preContractCostCodeTOs;
    }
    public void setPreContractCostCodeTOs(List<PreContractCostCodeTO> preContractCostCodeTOs) {
        this.preContractCostCodeTOs = preContractCostCodeTOs;
    }

}
