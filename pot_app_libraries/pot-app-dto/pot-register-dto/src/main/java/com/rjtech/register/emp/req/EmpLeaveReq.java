package com.rjtech.register.emp.req;

import com.rjtech.common.dto.ProjectTO;

public class EmpLeaveReq extends ProjectTO {

    private static final long serialVersionUID = -7764588560956931548L;
    private boolean loginUser;
    private Long id;
    private boolean onload;
    private Long reqUsrId;
    private Long apprUsrId;
    private String apprStatus;
    private boolean reqType;
    private String fromDate;
    private String toDate;
    private boolean fromApproval;

    public boolean isFromApproval() {
        return fromApproval;
    }

    public void setFromApproval(boolean fromApproval) {
        this.fromApproval = fromApproval;
    }

    public boolean isReqType() {
        return reqType;
    }

    public void setReqType(boolean reqType) {
        this.reqType = reqType;
    }

    public boolean isLoginUser() {
        return loginUser;
    }

    public void setLoginUser(boolean loginUser) {
        this.loginUser = loginUser;
    }

    public boolean isOnload() {
        return onload;
    }

    public void setOnload(boolean onload) {
        this.onload = onload;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Long getReqUsrId() {
        return reqUsrId;
    }

    public void setReqUsrId(Long reqUsrId) {
        this.reqUsrId = reqUsrId;
    }

    public Long getApprUsrId() {
        return apprUsrId;
    }

    public void setApprUsrId(Long apprUsrId) {
        this.apprUsrId = apprUsrId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
