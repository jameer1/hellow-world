package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.WorkFlowStatusTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractEmpDtlTO;
import com.rjtech.procurement.dto.PreContractReqApprTO;
import com.rjtech.procurement.dto.PreContractTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PreContractOnLoadResp extends AppResp {

    private static final long serialVersionUID = 6429699786381580619L;
    private List<PreContractReqApprTO> preContractReqApprTOs = null;
    private List<WorkFlowStatusTO> workFlowStatusTOs = null;
    private PreContractTO preContractTO = null;
    private List<String> currencyList = null;
    private List<PreContractEmpDtlTO> preContractEmpDtlTOs = null;
    private Map<Long, LabelKeyTO> projEmpClassMap = null;
    private Map<Long, LabelKeyTO> projPlantMap = null;
    private Map<Long, LabelKeyTO> projMaterialClassMap = null;
    private Map<Long, LabelKeyTO> projServiceMap = null;
    private Map<Long, LabelKeyTO> projStoreMap = null;
    private Map<Long, LabelKeyTO> storeMap = null;
    private Map<Long, LabelKeyTO> projCostItemMap = null;
    private Map<Long, LabelKeyTO> companyMap = null;
    private Map<Long, LabelKeyTO> usersMap = null;
    private Map<Long, LabelKeyTO> procureCategoryMap = null;
    private Map<Long, LabelKeyTO> addressMap = null;
    private Map<Long, LabelKeyTO> contactsMap = null;
    private Map<Long, LabelKeyTO> userProjMap = null;
    private Map<Long, LabelKeyTO> projSOWMap = null;

    public PreContractOnLoadResp() {
        currencyList = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        preContractTO = new PreContractTO();
        preContractReqApprTOs = new ArrayList<PreContractReqApprTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        workFlowStatusTOs = new ArrayList<WorkFlowStatusTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        preContractEmpDtlTOs = new ArrayList<PreContractEmpDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projEmpClassMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projPlantMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projMaterialClassMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projServiceMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projStoreMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        storeMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projCostItemMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        companyMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        usersMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        procureCategoryMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        addressMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        contactsMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        userProjMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projSOWMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public PreContractTO getPreContractTO() {
        return preContractTO;
    }

    public void setPreContractTO(PreContractTO preContractTO) {
        this.preContractTO = preContractTO;
    }

    public List<PreContractReqApprTO> getPreContractReqApprTOs() {
        return preContractReqApprTOs;
    }

    public void setPreContractReqApprTOs(List<PreContractReqApprTO> preContractReqApprTOs) {
        this.preContractReqApprTOs = preContractReqApprTOs;
    }

    public List<WorkFlowStatusTO> getWorkFlowStatusTOs() {
        return workFlowStatusTOs;
    }

    public void setWorkFlowStatusTOs(List<WorkFlowStatusTO> workFlowStatusTOs) {
        this.workFlowStatusTOs = workFlowStatusTOs;
    }

    public List<PreContractEmpDtlTO> getPreContractEmpDtlTOs() {
        return preContractEmpDtlTOs;
    }

    public void setPreContractEmpDtlTOs(List<PreContractEmpDtlTO> preContractEmpDtlTOs) {
        this.preContractEmpDtlTOs = preContractEmpDtlTOs;
    }

    public Map<Long, LabelKeyTO> getProjEmpClassMap() {
        return projEmpClassMap;
    }

    public void setProjEmpClassMap(Map<Long, LabelKeyTO> projEmpClassMap) {
        this.projEmpClassMap = projEmpClassMap;
    }

    public Map<Long, LabelKeyTO> getProjPlantMap() {
        return projPlantMap;
    }

    public void setProjPlantMap(Map<Long, LabelKeyTO> projPlantMap) {
        this.projPlantMap = projPlantMap;
    }

    public Map<Long, LabelKeyTO> getProjMaterialClassMap() {
        return projMaterialClassMap;
    }

    public void setProjMaterialClassMap(Map<Long, LabelKeyTO> projMaterialClassMap) {
        this.projMaterialClassMap = projMaterialClassMap;
    }

    public Map<Long, LabelKeyTO> getProjServiceMap() {
        return projServiceMap;
    }

    public void setProjServiceMap(Map<Long, LabelKeyTO> projServiceMap) {
        this.projServiceMap = projServiceMap;
    }

    public Map<Long, LabelKeyTO> getProjStoreMap() {
        return projStoreMap;
    }

    public void setProjStoreMap(Map<Long, LabelKeyTO> projStoreMap) {
        this.projStoreMap = projStoreMap;
    }

    public Map<Long, LabelKeyTO> getStoreMap() {
        return storeMap;
    }

    public void setStoreMap(Map<Long, LabelKeyTO> storeMap) {
        this.storeMap = storeMap;
    }

    public Map<Long, LabelKeyTO> getProjCostItemMap() {
        return projCostItemMap;
    }

    public void setProjCostItemMap(Map<Long, LabelKeyTO> projCostItemMap) {
        this.projCostItemMap = projCostItemMap;
    }

    public Map<Long, LabelKeyTO> getCompanyMap() {
        return companyMap;
    }

    public void setCompanyMap(Map<Long, LabelKeyTO> companyMap) {
        this.companyMap = companyMap;
    }

    public Map<Long, LabelKeyTO> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Long, LabelKeyTO> usersMap) {
        this.usersMap = usersMap;
    }

    public Map<Long, LabelKeyTO> getProcureCategoryMap() {
        return procureCategoryMap;
    }

    public void setProcureCategoryMap(Map<Long, LabelKeyTO> procureCategoryMap) {
        this.procureCategoryMap = procureCategoryMap;
    }

    public Map<Long, LabelKeyTO> getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(Map<Long, LabelKeyTO> addressMap) {
        this.addressMap = addressMap;
    }

    public Map<Long, LabelKeyTO> getContactsMap() {
        return contactsMap;
    }

    public void setContactsMap(Map<Long, LabelKeyTO> contactsMap) {
        this.contactsMap = contactsMap;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public Map<Long, LabelKeyTO> getProjSOWMap() {
        return projSOWMap;
    }

    public void setProjSOWMap(Map<Long, LabelKeyTO> projSOWMap) {
        this.projSOWMap = projSOWMap;
    }

    public List<String> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<String> currencyList) {
        this.currencyList = currencyList;
    }
}
