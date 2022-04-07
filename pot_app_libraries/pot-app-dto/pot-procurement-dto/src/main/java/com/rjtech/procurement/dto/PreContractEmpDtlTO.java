package com.rjtech.procurement.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

public class PreContractEmpDtlTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -921618770262167494L;

    private Long id;
    private Long preContractId;
    private String itemCode;
    private String itemDesc;
    private String startDate;
    private String finishDate;
    private Integer quantity;
    private BigDecimal estimateCost;
    private BigDecimal balanceBudget;
    private BigDecimal estimateEmpBudget;
    private Boolean latest;
    private String deliveryPlace;
    private Long empCatgId;
    private Long procureSubCatgId;
    private LabelKeyTO projEmpLabelKey = new LabelKeyTO();
    private LabelKeyTO projCostLabelKey = new LabelKeyTO();
    private PreContractTO precontractTO = new PreContractTO();
    private LabelKeyTO projEmpCatgLabelKey = new LabelKeyTO();
    private LabelKeyTO storeLabelKey = new LabelKeyTO();
    private LabelKeyTO projStoreLabelKey = new LabelKeyTO();
    private Integer actualQty;
    private String costCode;
    private String costCodeDesc;
    private String unitMeasure;

    private PreContractsEmpCmpTO preContractsEmpCmpTO = new PreContractsEmpCmpTO();
    private List<PreContractsEmpCmpTO> preContractsEmpCmpTOs = new ArrayList<PreContractsEmpCmpTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public BigDecimal getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(BigDecimal estimateCost) {
        this.estimateCost = estimateCost;
    }
    
    public BigDecimal getEstimateEmpBudget() {
        return estimateEmpBudget;
    }

    public void setEstimateEmpBudget(BigDecimal estimateEmpBudget) {
        this.estimateEmpBudget = estimateEmpBudget;
    }
    
    public BigDecimal getBalanceBudget() {
        return balanceBudget;
    }

    public void setBalanceBudget(BigDecimal balanceBudget) {
        this.balanceBudget = balanceBudget;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getLatest() {
        return latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    public PreContractTO getPrecontractTO() {
        return precontractTO;
    }

    public void setPrecontractTO(PreContractTO precontractTO) {
        this.precontractTO = precontractTO;
    }

    public Long getProcureSubCatgId() {
        return procureSubCatgId;
    }

    public void setProcureSubCatgId(Long procureSubCatgId) {
        this.procureSubCatgId = procureSubCatgId;
    }

    public Long getEmpCatgId() {
        return empCatgId;
    }

    public void setEmpCatgId(Long empCatgId) {
        this.empCatgId = empCatgId;
    }

    public LabelKeyTO getProjEmpLabelKey() {
        return projEmpLabelKey;
    }

    public void setProjEmpLabelKey(LabelKeyTO projEmpLabelKey) {
        this.projEmpLabelKey = projEmpLabelKey;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public LabelKeyTO getProjCostLabelKey() {
        return projCostLabelKey;
    }

    public void setProjCostLabelKey(LabelKeyTO projCostLabelKey) {
        this.projCostLabelKey = projCostLabelKey;
    }

    public PreContractsEmpCmpTO getPreContractsEmpCmpTO() {
        return preContractsEmpCmpTO;
    }

    public void setPreContractsEmpCmpTO(PreContractsEmpCmpTO preContractsEmpCmpTO) {
        this.preContractsEmpCmpTO = preContractsEmpCmpTO;
    }

    public List<PreContractsEmpCmpTO> getPreContractsEmpCmpTOs() {
        return preContractsEmpCmpTOs;
    }

    public void setPreContractsEmpCmpTOs(List<PreContractsEmpCmpTO> preContractsEmpCmpTOs) {
        this.preContractsEmpCmpTOs = preContractsEmpCmpTOs;
    }

    public LabelKeyTO getProjEmpCatgLabelKey() {
        return projEmpCatgLabelKey;
    }

    public void setProjEmpCatgLabelKey(LabelKeyTO projEmpCatgLabelKey) {
        this.projEmpCatgLabelKey = projEmpCatgLabelKey;
    }

	public LabelKeyTO getStoreLabelKey() {
		return storeLabelKey;
	}

	public void setStoreLabelKey(LabelKeyTO storeLabelKey) {
		this.storeLabelKey = storeLabelKey;
	}

	public LabelKeyTO getProjStoreLabelKey() {
		return projStoreLabelKey;
	}

	public void setProjStoreLabelKey(LabelKeyTO projStoreLabelKey) {
		this.projStoreLabelKey = projStoreLabelKey;
	}
    
	public Integer getActualQty() {
        return actualQty;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }
    
    public String getCostCodeDesc() {
        return costCodeDesc;
    }

    public void setCostCodeDesc(String costCodeDesc) {
        this.costCodeDesc = costCodeDesc;
    }
    
    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

}
