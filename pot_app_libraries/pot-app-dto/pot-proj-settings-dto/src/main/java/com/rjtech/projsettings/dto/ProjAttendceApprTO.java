package com.rjtech.projsettings.dto;

import java.sql.Time;

import com.rjtech.common.dto.ProjectTO;

public class ProjAttendceApprTO extends ProjectTO {

    private static final long serialVersionUID = 7151424804018360178L;
    private Long id;
    private Long projCrewId;
    private Integer projAttenId;
    private Integer cutOffDays;
    private Integer cutOffHours;
    private String notification;
    private Integer cutOffMinutes;
    private Time cutOffTime;
    private String fromDate;
    private String toDate;
    private String type;
    private String crewName;
    private Long notificationId;

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

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

    public Long getProjCrewId() {
        return projCrewId;
    }

    public void setProjCrewId(Long projCrewId) {
        this.projCrewId = projCrewId;
    }

    public Integer getProjAttenId() {
        return projAttenId;
    }

    public void setProjAttenId(Integer projAttenId) {
        this.projAttenId = projAttenId;
    }

    public Integer getCutOffDays() {
        return cutOffDays;
    }

    public void setCutOffDays(Integer cutOffDays) {
        this.cutOffDays = cutOffDays;
    }

    public Integer getCutOffHours() {
        return cutOffHours;
    }

    public void setCutOffHours(Integer cutOffHours) {
        this.cutOffHours = cutOffHours;
    }

    public Integer getCutOffMinutes() {
        return cutOffMinutes;
    }

    public void setCutOffMinutes(Integer cutOffMinutes) {
        this.cutOffMinutes = cutOffMinutes;
    }

    public Time getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(Time cutOffTime) {
        this.cutOffTime = cutOffTime;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
