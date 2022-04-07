package com.rjtech.procurement.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class PurchaseOrderTO extends ProjectTO {

    private static final long serialVersionUID = 4782807246932099144L;
    private Long id;
    private Long parentId;
    private String poParentCode;
    private Long apprUserId;
    private String apprUserCode;
    private String apprUserName;
    private String startDate;
    private String finsihDate;
    private BigDecimal amount;
    private BigDecimal paymentInDays;
    private String procureType;
    private String completeProcureType;
    private PreContractCmpTO preContractCmpTO = new PreContractCmpTO();
    private PurchaseOrderDetailsTO poDetailsTO = new PurchaseOrderDetailsTO();
    private Date createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinsihDate() {
        return finsihDate;
    }

    public void setFinsihDate(String finsihDate) {
        this.finsihDate = finsihDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaymentInDays() {
        return paymentInDays;
    }

    public void setPaymentInDays(BigDecimal paymentInDays) {
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

    public String getApprUserCode() {
        return apprUserCode;
    }

    public void setApprUserCode(String apprUserCode) {
        this.apprUserCode = apprUserCode;
    }

    public String getApprUserName() {
        return apprUserName;
    }

    public void setApprUserName(String apprUserName) {
        this.apprUserName = apprUserName;
    }

    public String getPoParentCode() {
        return poParentCode;
    }

    public void setPoParentCode(String poParentCode) {
        this.poParentCode = poParentCode;
    }
}