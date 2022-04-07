package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.LabelKeyTO;

public class RegularPayAllowanceTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1514947990233521806L;

    private Long id;
    private String code;
    private String name;
    private String taxStatus;
    private String comments;
    private Long codeTypeCountryProvisionId;

    private LabelKeyTO regularLabelKeyTO = new LabelKeyTO();

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

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getCodeTypeCountryProvisionId() {
        return codeTypeCountryProvisionId;
    }

    public void setCodeTypeCountryProvisionId(Long codeTypeCountryProvisionId) {
        this.codeTypeCountryProvisionId = codeTypeCountryProvisionId;
    }

    public LabelKeyTO getRegularLabelKeyTO() {
        return regularLabelKeyTO;
    }

    public void setRegularLabelKeyTO(LabelKeyTO regularLabelKeyTO) {
        this.regularLabelKeyTO = regularLabelKeyTO;
    }

}
