package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.LabelKeyTO;

public class EmployeeWiseCycleTO extends ClientTO {

    private static final long serialVersionUID = -8912185654428504435L;

    private Long id;
    private String taxCode;
    private String taxDesc;
    private Long procureMstrId;
    private Long payRollCycleMstrId;
    private String empCategory;
    private String comments;
    private Long codeTypeCountryProvisionId;

    private LabelKeyTO projEmpLabelKey = new LabelKeyTO();
    private LabelKeyTO procureCatgLabelKey = new LabelKeyTO();
    private LabelKeyTO payRollCycleLabelKeyTO = new LabelKeyTO();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxDesc() {
        return taxDesc;
    }

    public void setTaxDesc(String taxDesc) {
        this.taxDesc = taxDesc;
    }

    public Long getProcureMstrId() {
        return procureMstrId;
    }

    public void setProcureMstrId(Long procureMstrId) {
        this.procureMstrId = procureMstrId;
    }

    public Long getPayRollCycleMstrId() {
        return payRollCycleMstrId;
    }

    public void setPayRollCycleMstrId(Long payRollCycleMstrId) {
        this.payRollCycleMstrId = payRollCycleMstrId;
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

    public LabelKeyTO getProjEmpLabelKey() {
        return projEmpLabelKey;
    }

    public void setProjEmpLabelKey(LabelKeyTO projEmpLabelKey) {
        this.projEmpLabelKey = projEmpLabelKey;
    }

    public LabelKeyTO getProcureCatgLabelKey() {
        return procureCatgLabelKey;
    }

    public void setProcureCatgLabelKey(LabelKeyTO procureCatgLabelKey) {
        this.procureCatgLabelKey = procureCatgLabelKey;
    }

    public LabelKeyTO getPayRollCycleLabelKeyTO() {
        return payRollCycleLabelKeyTO;
    }

    public void setPayRollCycleLabelKeyTO(LabelKeyTO payRollCycleLabelKeyTO) {
        this.payRollCycleLabelKeyTO = payRollCycleLabelKeyTO;
    }

    public String getEmpCategory() {
        return empCategory;
    }

    public void setEmpCategory(String empCategory) {
        this.empCategory = empCategory;
    }

}
