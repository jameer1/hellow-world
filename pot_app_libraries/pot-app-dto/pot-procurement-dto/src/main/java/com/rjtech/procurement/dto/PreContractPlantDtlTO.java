package com.rjtech.procurement.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

public class PreContractPlantDtlTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -2777060962200425837L;

    private Long id;
    private Long preContractId;
    private String itemCode;
    private String itemDesc;
    private String startDate;
    private String finishDate;
    private Integer quantity;
    private BigDecimal estimateCost;
    private BigDecimal balanceBudget;
    private Boolean latest;
    private String deliveryPlace;
    private BigDecimal estimatePlantBudget;
    private Long procureSubCatgId;
    private Integer actualQty;
    private String costCode;
    private String costCodeDesc;
    private String unitMeasure; 

    private LabelKeyTO projPlantLabelKey = new LabelKeyTO();
    private LabelKeyTO projCostLabelKey = new LabelKeyTO();
    private PreContractTO precontractTO = new PreContractTO();

    private PreContractPlantCmpTO preContractPlantCmpTO = new PreContractPlantCmpTO();
    private List<PreContractPlantCmpTO> preContractPlantCmpTOs = new ArrayList<PreContractPlantCmpTO>();
    
    private LabelKeyTO storeLabelKey = new LabelKeyTO();
    private LabelKeyTO projStoreLabelKey = new LabelKeyTO();

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

    public BigDecimal getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(BigDecimal estimateCost) {
        this.estimateCost = estimateCost;
    }
    
    public BigDecimal getEstimatePlantBudget() {
        return estimatePlantBudget;
    }

    public void setEstimatePlantBudget(BigDecimal estimatePlantBudget) {
        this.estimatePlantBudget = estimatePlantBudget;
    }
    
    public BigDecimal getBalanceBudget() {
        return balanceBudget;
    }

    public void setBalanceBudget(BigDecimal balanceBudget) {
        this.balanceBudget = balanceBudget;
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

    public LabelKeyTO getProjPlantLabelKey() {
        return projPlantLabelKey;
    }

    public void setProjPlantLabelKey(LabelKeyTO projPlantLabelKey) {
        this.projPlantLabelKey = projPlantLabelKey;
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

    public PreContractPlantCmpTO getPreContractPlantCmpTO() {
        return preContractPlantCmpTO;
    }

    public void setPreContractPlantCmpTO(PreContractPlantCmpTO preContractPlantCmpTO) {
        this.preContractPlantCmpTO = preContractPlantCmpTO;
    }

    public List<PreContractPlantCmpTO> getPreContractPlantCmpTOs() {
        return preContractPlantCmpTOs;
    }

    public void setPreContractPlantCmpTOs(List<PreContractPlantCmpTO> preContractPlantCmpTOs) {
        this.preContractPlantCmpTOs = preContractPlantCmpTOs;
    }

    public Long getProcureSubCatgId() {
        return procureSubCatgId;
    }

    public void setProcureSubCatgId(Long procureSubCatgId) {
        this.procureSubCatgId = procureSubCatgId;
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
