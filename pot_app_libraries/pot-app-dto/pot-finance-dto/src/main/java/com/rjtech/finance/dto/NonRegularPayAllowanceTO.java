package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.LabelKeyTO;

public class NonRegularPayAllowanceTO extends ClientTO {

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
    private String taxType;

    private LabelKeyTO nonRegularLabelKeyTO = new LabelKeyTO();

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

    public LabelKeyTO getNonRegularLabelKeyTO() {
        return nonRegularLabelKeyTO;
    }

    public void setNonRegularLabelKeyTO(LabelKeyTO nonRegularLabelKeyTO) {
        this.nonRegularLabelKeyTO = nonRegularLabelKeyTO;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

}
