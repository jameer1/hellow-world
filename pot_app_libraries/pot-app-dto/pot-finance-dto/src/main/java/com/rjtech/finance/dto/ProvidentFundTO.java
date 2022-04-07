package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.LabelKeyTO;

public class ProvidentFundTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5361142571022885950L;

    private Long id;
    private String code;
    private String name;
    private String comments;
    private String taxStatus;
    private String creditCycle;
    private String creditDate;
    private Long codeTypeCountryProvisionId;
    private LabelKeyTO superFundLabelKeyTO = new LabelKeyTO();
    private LabelKeyTO creditCycleLabelKeyTO = new LabelKeyTO();

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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    public String getCreditCycle() {
        return creditCycle;
    }

    public void setCreditCycle(String creditCycle) {
        this.creditCycle = creditCycle;
    }

    public String getCreditDate() {
        return creditDate;
    }

    public void setCreditDate(String creditDate) {
        this.creditDate = creditDate;
    }

    public Long getCodeTypeCountryProvisionId() {
        return codeTypeCountryProvisionId;
    }

    public void setCodeTypeCountryProvisionId(Long codeTypeCountryProvisionId) {
        this.codeTypeCountryProvisionId = codeTypeCountryProvisionId;
    }

    public LabelKeyTO getSuperFundLabelKeyTO() {
        return superFundLabelKeyTO;
    }

    public void setSuperFundLabelKeyTO(LabelKeyTO superFundLabelKeyTO) {
        this.superFundLabelKeyTO = superFundLabelKeyTO;
    }

    public LabelKeyTO getCreditCycleLabelKeyTO() {
        return creditCycleLabelKeyTO;
    }

    public void setCreditCycleLabelKeyTO(LabelKeyTO creditCycleLabelKeyTO) {
        this.creditCycleLabelKeyTO = creditCycleLabelKeyTO;
    }

}
