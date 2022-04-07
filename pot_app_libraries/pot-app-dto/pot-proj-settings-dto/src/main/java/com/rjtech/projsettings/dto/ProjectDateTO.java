package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjectDateTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -7971778750072058836L;
    private Long id;
    private String code;
    private String startDate;
    private String finishDate;
    private String foreCastDate;
    private String actualDate;
    private Long varience;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getForeCastDate() {
        return foreCastDate;
    }

    public void setForeCastDate(String foreCastDate) {
        this.foreCastDate = foreCastDate;
    }

    public String getActualDate() {
        return actualDate;
    }

    public void setActualDate(String actualDate) {
        this.actualDate = actualDate;
    }

    public Long getVarience() {
        return varience;
    }

    public void setVarience(Long varience) {
        this.varience = varience;
    }

}
