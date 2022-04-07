package com.rjtech.procurement.req;

import com.rjtech.common.dto.ProjectTO;

import java.util.ArrayList;
import java.util.List;

public class RepeatPurchaseOrderGetReq extends ProjectTO {

    private static final long serialVersionUID = 200L;

    Long purchaseOrderId;
    Long projId;
    Long repeatPurchaseOrderId;
    String contractItemType;
    Long contractId;
    Long contractCmpId;
    Long contractItemDetailId;
    Long contractItemId;
    private List<Long> projIds = new ArrayList<Long>();

    // from Purchase Order
    //private Long purchaseId;
    //private Long preContractId;
    //private Long contractCmpId;
    private String preContractCode;
    //private List<Long> projIds = new ArrayList<Long>();
    private String itemType;
    private String approveStatus;
    private String paymentStatus;
    private boolean loginUser;
    private List<Long> precontractCmpIds;


    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getRepeatPurchaseOrderId() {
        return repeatPurchaseOrderId;
    }

    public void setRepeatPurchaseOrderId(Long repeatPurchaseOrderId) {
        this.repeatPurchaseOrderId = repeatPurchaseOrderId;
    }

    public String getContractItemType() {
        return contractItemType;
    }

    public void setContractItemType(String contractItemType) {
        this.contractItemType = contractItemType;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getContractCmpId() {
        return contractCmpId;
    }

    public void setContractCmpId(Long contractCmpId) {
        this.contractCmpId = contractCmpId;
    }

    public Long getContractItemDetailId() {
        return contractItemDetailId;
    }

    public void setContractItemDetailId(Long contractItemDetailId) {
        this.contractItemDetailId = contractItemDetailId;
    }

    public Long getContractItemId() {
        return contractItemId;
    }

    public void setContractItemId(Long contractItemId) {
        this.contractItemId = contractItemId;
    }

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    @Override
    public Long getProjId() {
        return projId;
    }

    @Override
    public void setProjId(Long projId) {
        this.projId = projId;
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

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public boolean isLoginUser() {
        return loginUser;
    }

    public void setLoginUser(boolean loginUser) {
        this.loginUser = loginUser;
    }

    public List<Long> getPrecontractCmpIds() {
        return precontractCmpIds;
    }

    public void setPrecontractCmpIds(List<Long> precontractCmpIds) {
        this.precontractCmpIds = precontractCmpIds;
    }
}
