package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.rjtech.common.dto.ProjectTO;

public class ProcurementGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3754852758992336728L;
    /**
     * 
     */

    private Long contractId = null;
    private boolean version;
    private boolean fromVendor;
    private boolean external;
    private Long preContractCmpId;
    private List<Long> projIds = new ArrayList<Long>();
    private boolean loginUser;
    private String contractType;
    private Set<Long> costIds;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public boolean isFromVendor() {
        return fromVendor;
    }

    public void setFromVendor(boolean fromVendor) {
        this.fromVendor = fromVendor;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    public Long getPreContractCmpId() {
        return preContractCmpId;
    }

    public void setPreContractCmpId(Long preContractCmpId) {
        this.preContractCmpId = preContractCmpId;
    }
    
    public Set<Long> getCostIds() {
        return costIds;
    }

    public void setCostIds(Set<Long> costIds) {
        this.costIds = costIds;
    }

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public boolean isLoginUser() {
        return loginUser;
    }

    public void setLoginUser(boolean loginUser) {
        this.loginUser = loginUser;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
    
    public String toString() {
    	return this.contractType+"->"+this.contractId;
    }
}
