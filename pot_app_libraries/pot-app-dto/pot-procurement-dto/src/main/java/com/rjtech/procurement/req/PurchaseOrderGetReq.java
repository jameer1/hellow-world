package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class PurchaseOrderGetReq extends ProjectTO {

    private static final long serialVersionUID = -2563991588756018264L;
    private Long purchaseId;
    private Long preContractId;
    private Long contractCmpId;
    private String preContractCode;
    private List<Long> projIds = new ArrayList<Long>();
    private String itemType;
    private String approveStatus;
    private String paymentStatus;
    private boolean loginUser;
    private List<Long> precontractCmpIds;
    private boolean isRepeatPO=true;
    private String preContractType;

    public String getPreContractType() {
		return preContractType;
	}

	public void setPreContractType(String preContractType) {
		this.preContractType = preContractType;
	}

	public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

    public Long getContractCmpId() {
        return contractCmpId;
    }

    public void setContractCmpId(Long contractCmpId) {
        this.contractCmpId = contractCmpId;
    }

    public String getPreContractCode() {
        return preContractCode;
    }

    public void setPreContractCode(String preContractCode) {
        this.preContractCode = preContractCode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public boolean isLoginUser() {
        return loginUser;
    }

    public void setLoginUser(boolean loginUser) {
        this.loginUser = loginUser;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<Long> getPrecontractCmpIds() {
        return precontractCmpIds;
    }

    public void setPrecontractCmpIds(List<Long> precontractCmpIds) {
        this.precontractCmpIds = precontractCmpIds;
    }

    public boolean isRepeatPO() {
        return isRepeatPO;
    }

    public void setRepeatPO(boolean repeatPO) {
        isRepeatPO = repeatPO;
    }
}
