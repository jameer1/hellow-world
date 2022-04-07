package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjEstimateTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String resourceType;
    private String formulaType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(String formulaType) {
        this.formulaType = formulaType;
    }

}
