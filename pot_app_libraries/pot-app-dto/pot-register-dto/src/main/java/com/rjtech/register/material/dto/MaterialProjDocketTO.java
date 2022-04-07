package com.rjtech.register.material.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;

public class MaterialProjDocketTO implements Serializable {

    private static final long serialVersionUID = 911119313997770330L;
    private Long id;
    private String projdocketNum;
    private String projdocketDate;
    private LabelKeyTO fromProjLabelkeyTO = new LabelKeyTO();
    private LabelKeyTO toProjLabelkeyTO = new LabelKeyTO();
    private LabelKeyTO notifyLabelKeyTO = new LabelKeyTO();
    private LabelKeyTO issuedByLabelkeyTO = new LabelKeyTO();
    private LabelKeyTO receivedByLabelkeyTO = new LabelKeyTO();
    private String locType;
    private String apprStatus;
    private Integer status;
    private String sourceType;
    private boolean exist;
    private List<MaterialProjSchItemTO> materialProjSchItemTOs = new ArrayList<MaterialProjSchItemTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MaterialProjSchItemTO> getMaterialProjSchItemTOs() {
        return materialProjSchItemTOs;
    }

    public void setMaterialProjSchItemTOs(List<MaterialProjSchItemTO> materialProjSchItemTOs) {
        this.materialProjSchItemTOs = materialProjSchItemTOs;
    }

    public LabelKeyTO getNotifyLabelKeyTO() {
        return notifyLabelKeyTO;
    }

    public void setNotifyLabelKeyTO(LabelKeyTO notifyLabelKeyTO) {
        this.notifyLabelKeyTO = notifyLabelKeyTO;
    }

    public LabelKeyTO getIssuedByLabelkeyTO() {
        return issuedByLabelkeyTO;
    }

    public void setIssuedByLabelkeyTO(LabelKeyTO issuedByLabelkeyTO) {
        this.issuedByLabelkeyTO = issuedByLabelkeyTO;
    }

    public LabelKeyTO getReceivedByLabelkeyTO() {
        return receivedByLabelkeyTO;
    }

    public void setReceivedByLabelkeyTO(LabelKeyTO receivedByLabelkeyTO) {
        this.receivedByLabelkeyTO = receivedByLabelkeyTO;
    }

    public String getProjdocketNum() {
        return projdocketNum;
    }

    public String getProjdocketDate() {
        return projdocketDate;
    }

    public LabelKeyTO getFromProjLabelkeyTO() {
        return fromProjLabelkeyTO;
    }

    public LabelKeyTO getToProjLabelkeyTO() {
        return toProjLabelkeyTO;
    }

    public void setProjdocketNum(String projdocketNum) {
        this.projdocketNum = projdocketNum;
    }

    public void setProjdocketDate(String projdocketDate) {
        this.projdocketDate = projdocketDate;
    }

    public void setFromProjLabelkeyTO(LabelKeyTO fromProjLabelkeyTO) {
        this.fromProjLabelkeyTO = fromProjLabelkeyTO;
    }

    public void setToProjLabelkeyTO(LabelKeyTO toProjLabelkeyTO) {
        this.toProjLabelkeyTO = toProjLabelkeyTO;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

}
