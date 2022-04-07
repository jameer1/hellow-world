package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class ProcurementFilterReq extends ProjectTO {

    private static final long serialVersionUID = 6526217036270683215L;
    private Long id;
    private String code;
    private String name;
    private Long preContractCmpId;
    private Integer approveStatus;
    private String contarctStageStatus;
    private boolean loginUser;
    private List<Long> projIds = new ArrayList<Long>();
    private String fromDate;
    private String toDate;
    private String biddingStatus;
    private String quoteRefCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContarctStageStatus() {
        return contarctStageStatus;
    }

    public void setContarctStageStatus(String contarctStageStatus) {
        this.contarctStageStatus = contarctStageStatus;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public boolean isLoginUser() {
        return loginUser;
    }

    public void setLoginUser(boolean loginUser) {
        this.loginUser = loginUser;
    }

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public String getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public Long getPreContractCmpId() {
        return preContractCmpId;
    }

    public void setPreContractCmpId(Long preContractCmpId) {
        this.preContractCmpId = preContractCmpId;
    }

    public String getQuoteRefCode() {
        return quoteRefCode;
    }

    public void setQuoteRefCode(String quoteRefCode) {
        this.quoteRefCode = quoteRefCode;
    }

}
