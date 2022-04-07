package com.rjtech.notification.dto;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

public class TimeSheetNotificationsTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String date;
    private String code;
    private String moduleCode;
    private Long notifyRefId;
    private String fromUserName;
    private String toUserName;
    private Long fromUserId;
    private Long toUserId;
    private String fromDate;
    private String toDate;
    private String weeakCommencingDay;
    private String timeSheetNumber;
    private String notificationStatus;
    private String notificationMsg;
    private String reqComments;
    private String apprComments;
    private String type;
    private String crewName;
    private Long crewId;
    private Long timeSheetId;
    private String crewType;
    private String projName;
    private String epsName;
    private String notificationNumber;

    private LabelKeyTO userProjLabelKeyTO = new LabelKeyTO();
    private LabelKeyTO usersLabelKeyTO = new LabelKeyTO();

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

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
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

    public String getTimeSheetNumber() {
        return timeSheetNumber;
    }

    public void setTimeSheetNumber(String timeSheetNumber) {
        this.timeSheetNumber = timeSheetNumber;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getWeeakCommencingDay() {
        return weeakCommencingDay;
    }

    public void setWeeakCommencingDay(String weeakCommencingDay) {
        this.weeakCommencingDay = weeakCommencingDay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LabelKeyTO getUserProjLabelKeyTO() {
        return userProjLabelKeyTO;
    }

    public void setUserProjLabelKeyTO(LabelKeyTO userProjLabelKeyTO) {
        this.userProjLabelKeyTO = userProjLabelKeyTO;
    }

    public LabelKeyTO getUsersLabelKeyTO() {
        return usersLabelKeyTO;
    }

    public void setUsersLabelKeyTO(LabelKeyTO usersLabelKeyTO) {
        this.usersLabelKeyTO = usersLabelKeyTO;
    }

    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public String getCrewType() {
        return crewType;
    }

    public void setCrewType(String crewType) {
        this.crewType = crewType;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getEpsName() {
        return epsName;
    }

    public void setEpsName(String epsName) {
        this.epsName = epsName;
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

    public String getNotificationNumber() {
        return notificationNumber;
    }

    public void setNotificationNumber(String notificationNumber) {
        this.notificationNumber = notificationNumber;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }
}
