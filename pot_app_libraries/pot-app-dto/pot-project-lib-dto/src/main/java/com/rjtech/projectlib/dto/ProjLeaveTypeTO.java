package com.rjtech.projectlib.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;

public class ProjLeaveTypeTO extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;
    private Long id;
    private String code;
    private String name;
    private boolean priorApproval;
    private boolean medicalForm;
    private boolean evidenceForm;
    private Integer maxAllowYear;
    private Integer maxAllowEvent;
    private String countryCode;
    private String effectiveFrom;
    private List<ProjLeaveCategoryTO> leaveCategoryTOs = new ArrayList<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPriorApproval() {
        return priorApproval;
    }

    public void setPriorApproval(boolean priorApproval) {
        this.priorApproval = priorApproval;
    }

    public boolean isMedicalForm() {
        return medicalForm;
    }

    public void setMedicalForm(boolean medicalForm) {
        this.medicalForm = medicalForm;
    }

    public boolean isEvidenceForm() {
        return evidenceForm;
    }

    public void setEvidenceForm(boolean evidenceForm) {
        this.evidenceForm = evidenceForm;
    }

    public Integer getMaxAllowYear() {
        return maxAllowYear;
    }

    public void setMaxAllowYear(Integer maxAllowYear) {
        this.maxAllowYear = maxAllowYear;
    }

    public Integer getMaxAllowEvent() {
        return maxAllowEvent;
    }

    public void setMaxAllowEvent(Integer maxAllowEvent) {
        this.maxAllowEvent = maxAllowEvent;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public List<ProjLeaveCategoryTO> getLeaveCategoryTOs() {
        return leaveCategoryTOs;
    }

    public void setLeaveCategoryTOs(List<ProjLeaveCategoryTO> leaveCategoryTOs) {
        this.leaveCategoryTOs = leaveCategoryTOs;
    }

}
