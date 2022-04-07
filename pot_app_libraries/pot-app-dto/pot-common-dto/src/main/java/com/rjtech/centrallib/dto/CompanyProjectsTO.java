package com.rjtech.centrallib.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.rjtech.common.dto.ClientTO;

public class CompanyProjectsTO extends ClientTO {
    /**
     * 
     */
    private static final long serialVersionUID = 2950084862079755848L;
    private Long id;
    private Long projId;
    private String projCode;
    private String projName;
    private Long purchaseId;
    private String purchaseName;
    private Long cmpId;
    private String contractValue;
    private String startDate;
    private String finishDate;
    private String performance;
    private boolean closed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public Long getCmpId() {
        return cmpId;
    }

    public void setCmpId(Long cmpId) {
        this.cmpId = cmpId;
    }

    public String getContractValue() {
        return contractValue;
    }

    public void setContractValue(String contractValue) {
        this.contractValue = contractValue;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

}