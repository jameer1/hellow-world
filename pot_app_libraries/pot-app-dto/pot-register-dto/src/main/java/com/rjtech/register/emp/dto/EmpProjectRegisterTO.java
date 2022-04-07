package com.rjtech.register.emp.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class EmpProjectRegisterTO extends ProjectTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long projEmpId;
    private String mobilaizationDate;
    private String deMobilaizationDate;
    private String latest;
    private String taxFileNum;

    private LabelKeyTO empLabelKeyTO = new LabelKeyTO();

    public Long getProjEmpId() {
        return projEmpId;
    }

    public String getMobilaizationDate() {
        return mobilaizationDate;
    }

    public String getDeMobilaizationDate() {
        return deMobilaizationDate;
    }

    public String getLatest() {
        return latest;
    }

    public void setProjEmpId(Long projEmpId) {
        this.projEmpId = projEmpId;
    }

    public void setMobilaizationDate(String mobilaizationDate) {
        this.mobilaizationDate = mobilaizationDate;
    }

    public void setDeMobilaizationDate(String deMobilaizationDate) {
        this.deMobilaizationDate = deMobilaizationDate;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public LabelKeyTO getEmpLabelKeyTO() {
        return empLabelKeyTO;
    }

    public void setEmpLabelKeyTO(LabelKeyTO empLabelKeyTO) {
        this.empLabelKeyTO = empLabelKeyTO;
    }

    public String getTaxFileNum() {
        return taxFileNum;
    }

    public void setTaxFileNum(String taxFileNum) {
        this.taxFileNum = taxFileNum;
    }

}
