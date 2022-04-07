package com.rjtech.procurement.dto;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

public class PreContractReqApprTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1547084587904500503L;

    private Long id;
    private String reqDate;
    private String apprDate;
    private String reqCode;
    private Integer apprStatus;
    private String reqComments;
    private LabelKeyTO reqUserLabelkeyTO = new LabelKeyTO();
    private LabelKeyTO apprUserLabelkeyTO = new LabelKeyTO();
    private String apprComments;
    private boolean latest;
    private Long preContractId;
    private String notes;
    private String apprCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LabelKeyTO getReqUserLabelkeyTO() {
        return reqUserLabelkeyTO;
    }

    public void setReqUserLabelkeyTO(LabelKeyTO reqUserLabelkeyTO) {
        this.reqUserLabelkeyTO = reqUserLabelkeyTO;
    }

    public LabelKeyTO getApprUserLabelkeyTO() {
        return apprUserLabelkeyTO;
    }

    public void setApprUserLabelkeyTO(LabelKeyTO apprUserLabelkeyTO) {
        this.apprUserLabelkeyTO = apprUserLabelkeyTO;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public Integer getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(Integer apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getReqComments() {
        return reqComments;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getApprCode() {
        return apprCode;
    }

    public void setApprCode(String apprCode) {
        this.apprCode = apprCode;
    }

}
