package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;

public class EmpNotificationsTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String date;
    private String code;
    private String moduleCode;
    private Long notifyRefId;
    private Long apprUserId;
    private Long reqUserId;
    private Long forProject;
    private String type;
    private String notificationStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Long getNotifyRefId() {
        return notifyRefId;
    }

    public void setNotifyRefId(Long notifyRefId) {
        this.notifyRefId = notifyRefId;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

    public Long getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(Long reqUserId) {
        this.reqUserId = reqUserId;
    }

    public Long getForProject() {
        return forProject;
    }

    public void setForProject(Long forProject) {
        this.forProject = forProject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
