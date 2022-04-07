package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjResourceCurveTO extends ProjectTO {

    private static final long serialVersionUID = -4929619665133609726L;
    private Long id;
    private String curveType;
    private Double records;

    private ProjResourceCurveTO projResourceCurveTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurveType() {
        return curveType;
    }

    public void setCurveType(String curveType) {
        this.curveType = curveType;
    }

    public Double getRecords() {
        return records;
    }

    public void setRecords(Double records) {
        this.records = records;
    }

    public ProjResourceCurveTO getProjResourceCurveTO() {
        return projResourceCurveTO;
    }

    public void setProjResourceCurveTO(ProjResourceCurveTO projResourceCurveTO) {
        this.projResourceCurveTO = projResourceCurveTO;
    }

}
