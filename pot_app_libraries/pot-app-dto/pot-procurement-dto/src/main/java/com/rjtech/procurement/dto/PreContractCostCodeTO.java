package com.rjtech.procurement.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.math.MathContext;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.common.dto.WorkFlowStatusTO;

public class PreContractCostCodeTO extends ProjectTO {

    private static final long serialVersionUID = -6748335769511761914L;

    private Long id;
    private String desc;
    private String projCode;
    private String preContractType;
    private String contractStageStatus;
    private String costCodeId;
    private String costCodeDesc;
    private Integer estimateBudget;
    

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getProjCode() {
        return projCode;
    }
    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }
    
    public String getPreContractType() {
        return preContractType;
    }
    public void setPreContractType(String preContractType) {
        this.preContractType = preContractType;
    }
    
    public String getContractStageStatus() {
        return contractStageStatus;
    }
    public void setContractStageStatus(String contractStageStatus) {
        this.contractStageStatus = contractStageStatus;
    }
   
    public String getCostCodeId() {
        return costCodeId;
    }
    public void setCostCodeId(String costCodeId) {
        this.costCodeId = costCodeId;
    }
    
    public String getCostCodeDesc() {
        return costCodeDesc;
    }
    public void setCostCodeDesc(String costCodeDesc) {
        this.costCodeDesc = costCodeDesc;
    }
    
    public Integer getEstimateBudget() {
        return estimateBudget;
    }

    public void setEstimateBudget(Integer estimateBudget) {
        this.estimateBudget = estimateBudget;
    }
    
}