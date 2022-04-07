package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjTimeSheetApprTO extends ProjectTO {

    private static final long serialVersionUID = 941303995012898157L;
    private Long id;
    private Long projCrewId;
    private Long notificationId;
    private Long timeSheetId;
    private String timeSheetDate;
    private String timeSheetEndDate;
    private Integer grantHrs;
    private String timeSheetNo;
    private Long apprUserId;
    private String notification;
    private String crewType;
    private String crewName;

    private ProjTimeSheetTO projTimeSheetTO = new ProjTimeSheetTO();

    public Long getId() {
        return id;
    }

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjCrewId() {
        return projCrewId;
    }

    public void setProjCrewId(Long projCrewId) {
        this.projCrewId = projCrewId;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getTimeSheetDate() {
        return timeSheetDate;
    }

    public void setTimeSheetDate(String timeSheetDate) {
        this.timeSheetDate = timeSheetDate;
    }

    public Integer getGrantHrs() {
        return grantHrs;
    }

    public void setGrantHrs(Integer grantHrs) {
        this.grantHrs = grantHrs;
    }

    public String getTimeSheetNo() {
        return timeSheetNo;
    }

    public void setTimeSheetNo(String timeSheetNo) {
        this.timeSheetNo = timeSheetNo;
    }

    public String getTimeSheetEndDate() {
        return timeSheetEndDate;
    }

    public void setTimeSheetEndDate(String timeSheetEndDate) {
        this.timeSheetEndDate = timeSheetEndDate;
    }

    public ProjTimeSheetTO getProjTimeSheetTO() {
        return projTimeSheetTO;
    }

    public void setProjTimeSheetTO(ProjTimeSheetTO projTimeSheetTO) {
        this.projTimeSheetTO = projTimeSheetTO;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getCrewType() {
        return crewType;
    }

    public void setCrewType(String crewType) {
        this.crewType = crewType;
    }

}
