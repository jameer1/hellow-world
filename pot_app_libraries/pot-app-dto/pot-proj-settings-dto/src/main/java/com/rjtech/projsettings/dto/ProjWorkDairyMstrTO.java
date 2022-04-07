package com.rjtech.projsettings.dto;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjWorkDairyMstrTO extends ProjectTO {

    private static final long serialVersionUID = -4572795933001992462L;
    private Long id;
    private Integer cutOffDays;
    private Time cutOffTime;
    private String apprType;
    private String defaultStatus;
    private Integer cutOffHours;
    private Integer cutOffMinutes;
    private Long apprTypeId;

    private List<ProjWorkDairyApprTO> projWorkDairyApprTOs = new ArrayList<ProjWorkDairyApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCutOffDays() {
        return cutOffDays;
    }

    public void setCutOffDays(Integer cutOffDays) {
        this.cutOffDays = cutOffDays;
    }

    public Time getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(Time cutOffTime) {
        this.cutOffTime = cutOffTime;
    }

    public String getApprType() {
        return apprType;
    }

    public void setApprType(String apprType) {
        this.apprType = apprType;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
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

    public List<ProjWorkDairyApprTO> getProjWorkDairyApprTOs() {
        return projWorkDairyApprTOs;
    }

    public void setProjWorkDairyApprTOs(List<ProjWorkDairyApprTO> projWorkDairyApprTOs) {
        this.projWorkDairyApprTOs = projWorkDairyApprTOs;
    }

    public Long getApprTypeId() {
        return apprTypeId;
    }

    public void setApprTypeId(Long apprTypeId) {
        this.apprTypeId = apprTypeId;
    }

}
