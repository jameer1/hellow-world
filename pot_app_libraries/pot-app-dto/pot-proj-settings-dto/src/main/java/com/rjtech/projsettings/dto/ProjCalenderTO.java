package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjCalenderTO extends ProjectTO {
    /**
     * 
     */
    private static final long serialVersionUID = 2625256943854586539L;
    private Long id;
    private String calName;
    private ProjCalenderTO projCalenderTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalName() {
        return calName;
    }

    public void setCalName(String calName) {
        this.calName = calName;
    }

    public ProjCalenderTO getProjCalenderTO() {
        return projCalenderTO;
    }

    public void setProjCalenderTO(ProjCalenderTO projCalenderTO) {
        this.projCalenderTO = projCalenderTO;
    }

}
