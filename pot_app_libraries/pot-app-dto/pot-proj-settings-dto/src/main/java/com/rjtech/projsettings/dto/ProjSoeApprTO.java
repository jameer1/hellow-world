package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjSoeApprTO extends ProjectTO {

    private static final long serialVersionUID = -7306746413648006462L;
    private Long id;
    private Long workDairyId;
    private Long apprUserId;
    private String fromDate;
    private String toDate;
    private Long notificationId;
    private String notification;
    private Long projCrewId;
    private String crewName;

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

    public Long getProjCrewId() {
        return projCrewId;
    }

    public void setProjCrewId(Long projCrewId) {
        this.projCrewId = projCrewId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
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

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

}
