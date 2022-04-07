package com.rjtech.procurement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TermsAndConditionsTO implements Serializable {

    private static final long serialVersionUID = 5266645468802222013L;

    private Long id;

    private String conditionsText;

    private List<Long> projDocFileIds = new ArrayList<Long>();

    private List<Long> deletedTermsAndConditionsFileIds = new ArrayList<Long>();

    private List<TermsAndConditionsFileDetailsTo> termsAndConditionsFiles = new ArrayList<TermsAndConditionsFileDetailsTo>();

    private Long preContractId;

    private Long poId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConditionsText() {
        return conditionsText;
    }

    public void setConditionsText(String conditionsText) {
        this.conditionsText = conditionsText;
    }

    public List<Long> getProjDocFileIds() {
        return projDocFileIds;
    }

    public void setProjDocFileIds(List<Long> projDocFileIds) {
        this.projDocFileIds = projDocFileIds;
    }

    public List<TermsAndConditionsFileDetailsTo> getTermsAndConditionsFiles() {
        return termsAndConditionsFiles;
    }

    public void setTermsAndConditionsFiles(List<TermsAndConditionsFileDetailsTo> termsAndConditionsFiles) {
        this.termsAndConditionsFiles = termsAndConditionsFiles;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

    public Long getPoId() {
        return poId;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public List<Long> getDeletedTermsAndConditionsFileIds() {
        return deletedTermsAndConditionsFileIds;
    }

    public void setDeletedTermsAndConditionsFileIds(List<Long> deletedTermsAndConditionsFileIds) {
        this.deletedTermsAndConditionsFileIds = deletedTermsAndConditionsFileIds;
    }

}
