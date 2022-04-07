package com.rjtech.notification.dto;

import com.rjtech.common.dto.ProjectTO;

public class WorkDairyNotificationsTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String date;
    private String code;
    private Long notifyRefId;
    private Long fromUserId;
    private Long toUserId;
    private String fromDate;
    private String toDate;
    private String crewName;
    private Long crewId;
    private String shift;
    private String workDairyNumber;
    private String type;
    private String reqComments;
    private String apprComments;
    private String notificationStatus;
    private String notificationMsg;
    private String notifyStatus;
    private Long wdmId;
    private Boolean originalType;
    private Boolean internalType;
    private Boolean externalType;
    private Long shiftId;
    private String reqUser;

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public Boolean getOriginalType() {
        return originalType;
    }

    public void setOriginalType(Boolean originalType) {
        this.originalType = originalType;
    }

    public Boolean getInternalType() {
        return internalType;
    }

    public void setInternalType(Boolean internalType) {
        this.internalType = internalType;
    }

    public Boolean getExternalType() {
        return externalType;
    }

    public void setExternalType(Boolean externalType) {
        this.externalType = externalType;
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

    public Long getNotifyRefId() {
        return notifyRefId;
    }

    public void setNotifyRefId(Long notifyRefId) {
        this.notifyRefId = notifyRefId;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
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

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getWorkDairyNumber() {
        return workDairyNumber;
    }

    public void setWorkDairyNumber(String workDairyNumber) {
        this.workDairyNumber = workDairyNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public Long getWdmId() {
        return wdmId;
    }

    public void setWdmId(Long wdmId) {
        this.wdmId = wdmId;
    }

    public String getReqUser() {
        return reqUser;
    }

    public void setReqUser(String reqUser) {
        this.reqUser = reqUser;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }
}
