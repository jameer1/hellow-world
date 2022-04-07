package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;

public class EmpMedicalTaxMstrTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6720804212836372255L;
    private Long id;
    private Long financeMedicalId;
    private String medicalStatus;
    private String medicalAmmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFinanceMedicalId() {
        return financeMedicalId;
    }

    public void setFinanceMedicalId(Long financeMedicalId) {
        this.financeMedicalId = financeMedicalId;
    }

    public String getMedicalStatus() {
        return medicalStatus;
    }

    public void setMedicalStatus(String medicalStatus) {
        this.medicalStatus = medicalStatus;
    }

    public String getMedicalAmmount() {
        return medicalAmmount;
    }

    public void setMedicalAmmount(String medicalAmmount) {
        this.medicalAmmount = medicalAmmount;
    }

}
