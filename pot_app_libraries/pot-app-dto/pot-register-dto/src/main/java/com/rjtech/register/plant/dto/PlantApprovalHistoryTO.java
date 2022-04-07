package com.rjtech.register.plant.dto;

import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class PlantApprovalHistoryTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1972176348492426620L;
    private Long id;
    private Long ppjdId;
    private Long userId;
    private Long prftId;
    private Date approvalDate;
    private String approvalNotifyId;
    private String approvalDecision;
    private Date dateOfTrans;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPpjdId() {
        return ppjdId;
    }

    public void setPpjdId(Long ppjdId) {
        this.ppjdId = ppjdId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPrftId() {
        return prftId;
    }

    public void setPrftId(Long prftId) {
        this.prftId = prftId;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalNotifyId() {
        return approvalNotifyId;
    }

    public void setApprovalNotifyId(String approvalNotifyId) {
        this.approvalNotifyId = approvalNotifyId;
    }

    public String getApprovalDecision() {
        return approvalDecision;
    }

    public void setApprovalDecision(String approvalDecision) {
        this.approvalDecision = approvalDecision;
    }

    public Date getDateOfTrans() {
        return dateOfTrans;
    }

    public void setDateOfTrans(Date dateOfTrans) {
        this.dateOfTrans = dateOfTrans;
    }

}
