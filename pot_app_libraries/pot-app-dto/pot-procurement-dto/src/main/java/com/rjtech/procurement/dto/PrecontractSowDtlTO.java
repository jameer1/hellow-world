package com.rjtech.procurement.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class PrecontractSowDtlTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long preContractId;
    private Long sowId;
    private String itemCode;
    private String itemDesc;
    private Long quantity;
    private String apprStatus;
    private Boolean latest;
    private BigDecimal estimateCost;
    private String startDate;
    private String finishDate;
    private String responseDate;
    private BigDecimal estimateSowCost;
    private String deliveryPlace;
    private Integer actualQty;
    private String costCode;
    private String costCodeDesc;

    private PreContractTO precontractTO = new PreContractTO();
    private PrecontractSowCmpTO precontractSowCmpTO = new PrecontractSowCmpTO();

    List<PrecontractSowCmpTO> precontractSowCmpTOs = new ArrayList<PrecontractSowCmpTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

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

    public Long getSowId() {
        return sowId;
    }

    public void setSowId(Long sowId) {
        this.sowId = sowId;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Boolean getLatest() {
        return latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    public BigDecimal getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(BigDecimal estimateCost) {
        this.estimateCost = estimateCost;
    }
    
    public BigDecimal getEstimateSowCost() {
        return estimateSowCost;
    }

    public void setEstimateSowCost(BigDecimal estimateSowCost) {
        this.estimateSowCost = estimateSowCost;
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

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

    public List<PrecontractSowCmpTO> getPrecontractSowCmpTOs() {
        return precontractSowCmpTOs;
    }

    public void setPrecontractSowCmpTOs(List<PrecontractSowCmpTO> precontractSowCmpTOs) {
        this.precontractSowCmpTOs = precontractSowCmpTOs;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public PreContractTO getPrecontractTO() {
        return precontractTO;
    }

    public void setPrecontractTO(PreContractTO precontractTO) {
        this.precontractTO = precontractTO;
    }

    public PrecontractSowCmpTO getPrecontractSowCmpTO() {
        return precontractSowCmpTO;
    }

    public void setPrecontractSowCmpTO(PrecontractSowCmpTO precontractSowCmpTO) {
        this.precontractSowCmpTO = precontractSowCmpTO;
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
    
    public Integer getActualQty() {
        return actualQty;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
    }
}
