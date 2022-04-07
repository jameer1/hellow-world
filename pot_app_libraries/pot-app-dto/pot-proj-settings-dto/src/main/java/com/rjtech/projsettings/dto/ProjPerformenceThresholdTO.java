package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjPerformenceThresholdTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String category;
    private String svLowerLimit;
    private String svUpperLimit;
    private String cvLowerLimit;
    private String cvUpperLimit;
    private String spiLowerLimit;
    private String spiUpperLimit;
    private String cpiLowerLimit;
    private String cpiUpperLimit;
    private String tcpiLowerLimit;
    private String tcpiUpperLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSvLowerLimit() {
        return svLowerLimit;
    }

    public void setSvLowerLimit(String svLowerLimit) {
        this.svLowerLimit = svLowerLimit;
    }

    public String getSvUpperLimit() {
        return svUpperLimit;
    }

    public void setSvUpperLimit(String svUpperLimit) {
        this.svUpperLimit = svUpperLimit;
    }

    public String getCvLowerLimit() {
        return cvLowerLimit;
    }

    public void setCvLowerLimit(String cvLowerLimit) {
        this.cvLowerLimit = cvLowerLimit;
    }

    public String getCvUpperLimit() {
        return cvUpperLimit;
    }

    public void setCvUpperLimit(String cvUpperLimit) {
        this.cvUpperLimit = cvUpperLimit;
    }

    public String getSpiLowerLimit() {
        return spiLowerLimit;
    }

    public void setSpiLowerLimit(String spiLowerLimit) {
        this.spiLowerLimit = spiLowerLimit;
    }

    public String getSpiUpperLimit() {
        return spiUpperLimit;
    }

    public void setSpiUpperLimit(String spiUpperLimit) {
        this.spiUpperLimit = spiUpperLimit;
    }

    public String getCpiLowerLimit() {
        return cpiLowerLimit;
    }

    public void setCpiLowerLimit(String cpiLowerLimit) {
        this.cpiLowerLimit = cpiLowerLimit;
    }

    public String getCpiUpperLimit() {
        return cpiUpperLimit;
    }

    public void setCpiUpperLimit(String cpiUpperLimit) {
        this.cpiUpperLimit = cpiUpperLimit;
    }

    public String getTcpiLowerLimit() {
        return tcpiLowerLimit;
    }

    public void setTcpiLowerLimit(String tcpiLowerLimit) {
        this.tcpiLowerLimit = tcpiLowerLimit;
    }

    public String getTcpiUpperLimit() {
        return tcpiUpperLimit;
    }

    public void setTcpiUpperLimit(String tcpiUpperLimit) {
        this.tcpiUpperLimit = tcpiUpperLimit;
    }

}
