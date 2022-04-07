package com.rjtech.projsettings.dto;

import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class ProjScheduledSummaryTO extends ProjectTO {

    private static final long serialVersionUID = 3999765855229318972L;
    private Long id;
    private String dateType;
    private Date scheduledDeate;
    private Date ForeCastDeate;
    private Date actualDeate;
    private Long varience;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public Date getScheduledDeate() {
        return scheduledDeate;
    }

    public void setScheduledDeate(Date scheduledDeate) {
        this.scheduledDeate = scheduledDeate;
    }

    public Date getForeCastDeate() {
        return ForeCastDeate;
    }

    public void setForeCastDeate(Date foreCastDeate) {
        ForeCastDeate = foreCastDeate;
    }

    public Date getActualDeate() {
        return actualDeate;
    }

    public void setActualDeate(Date actualDeate) {
        this.actualDeate = actualDeate;
    }

    public Long getVarience() {
        return varience;
    }

    public void setVarience(Long varience) {
        this.varience = varience;
    }

}
