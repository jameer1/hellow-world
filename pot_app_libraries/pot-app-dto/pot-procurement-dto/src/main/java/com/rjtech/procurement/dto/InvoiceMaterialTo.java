package com.rjtech.procurement.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class InvoiceMaterialTo extends ProjectTO {

    private static final long serialVersionUID = 4782807246932099144L;
    private String docketNum;
    private String matReceiveDate;
    private String docketDate;
    private String schId;
    private String schDesc;
    private String matClassItem;
    private String untiOfMeasure;
    private Integer contractQty;
    private Integer processQty;
    private Integer claimedQty; 
    private Integer progressQty;
    private String receiverComments;
    private Integer vendorRate;
    private BigDecimal amount;
    private Integer paymentInDays;
    private String procureType;
    private String completeProcureType;
    private PreContractCmpTO preContractCmpTO = new PreContractCmpTO();
    private PurchaseOrderDetailsTO poDetailsTO = new PurchaseOrderDetailsTO();
    private Date createdOn;

    public String getDocketNum() {
        return docketNum;
    }

    public void setDocketNum(String docketNum) {
        this.docketNum = docketNum;
    }
    
    public Integer getContractQty() {
    	return contractQty;
    }
    
    public void setContractQty(Integer contractQty) {
    	this.contractQty = contractQty;
    }
    
    public Integer getProcessQty() {
    	return processQty;
    }
    
    public void setProcessQty(Integer processQty) {
    	this.processQty = processQty;
    }
    
    public Integer getClaimedQty() {
    	return claimedQty;
    }
    
    public void setClaimedQty(Integer claimedQty) {
    	this.claimedQty = claimedQty;
    }
    
    public Integer getProgressQty() {
    	return progressQty;
    }
    
    public void setProgressQty(Integer progressQty) {
    	this.progressQty = progressQty;
    }
    
    public Integer getVendorRate() {
    	return vendorRate;
    }
    
    public void setVendorRate(Integer vendorRate) {
    	this.vendorRate = vendorRate;
    }
    
    public String getReceiverComments() {
        return receiverComments;
    }

    public void setReceiverComments(String receiverComments) {
        this.receiverComments = receiverComments;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getPaymentInDays() {
        return paymentInDays;
    }

    public void setPaymentInDays(Integer paymentInDays) {
        this.paymentInDays = paymentInDays;
    }

    public PreContractCmpTO getPreContractCmpTO() {
        return preContractCmpTO;
    }

    public void setPreContractCmpTO(PreContractCmpTO preContractCmpTO) {
        this.preContractCmpTO = preContractCmpTO;
    }

    public String getProcureType() {
        return procureType;
    }

    public void setProcureType(String procureType) {
        this.procureType = procureType;
    }

    public String getCompleteProcureType() {
        return completeProcureType;
    }

    public void setCompleteProcureType(String completeProcureType) {
        this.completeProcureType = completeProcureType;
    }

    public PurchaseOrderDetailsTO getPoDetailsTO() {
        return poDetailsTO;
    }

    public void setPoDetailsTO(PurchaseOrderDetailsTO poDetailsTO) {
        this.poDetailsTO = poDetailsTO;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    
    public void setMatReceiveDate(String matReceiveDate) {
        this.matReceiveDate = matReceiveDate;
    }

    public String getMatReceiveDate() {
        return matReceiveDate;
    }
    
    public void setDocketDate(String docketDate) {
        this.docketDate = docketDate;
    }

    public String getDocketDate() {
        return docketDate;
    }
    
    public void setSchId(String schId) {
        this.schId = schId;
    }

    public String getSchId() {
        return schId;
    }
    
    public void setSchDesc(String schDesc) {
        this.schDesc = schDesc;
    }

    public String getSchDesc() {
        return schDesc;
    }
    
    public void setMatClassItem(String matClassItem) {
        this.matClassItem = matClassItem;
    }

    public String getMatClassItem() {
        return matClassItem;
    }
    
    public void setUntiOfMeasure(String untiOfMeasure) {
        this.untiOfMeasure = untiOfMeasure;
    }

    public String getUntiOfMeasure() {
        return untiOfMeasure;
    }
}