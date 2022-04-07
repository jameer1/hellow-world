package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjProgressClaimePeriodTO extends ProjectTO {

    private static final long serialVersionUID = 7166123162420346156L;
    private Long id;
    private String projType;
    private String startDay;
    private String finishDay;
    private String isDefault;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getFinishDay() {
        return finishDay;
    }

    public void setFinishDay(String finishDay) {
        this.finishDay = finishDay;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

}
