package com.rjtech.projectlib.dto;

import com.rjtech.common.dto.ProjectTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProjPMCMItemTO extends ProjectTO {
	
    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String code;
    
    private String name;
    
    private boolean item;
    
    private boolean expand = false;
    
    private Long parentId;
    
    private String parentName;
    
    private BigDecimal quantity;
    
    private Long manPowerHrs;
    
    private BigDecimal total;
    
    private BigDecimal amount;
    
    private BigDecimal labourRate;
    
    private BigDecimal plantRate;
    
    private BigDecimal materialRate;
    
    private BigDecimal othersRate;
    
    private String comments;
    
    private List<ProjPMCMItemTO> childSORItemTOs = new ArrayList<ProjPMCMItemTO>();
    
    private String pmCostCodeId;

    private String pmCostCodeName;

    private String pmCurrency;

    private Long pmContractAmount;

    private String pmSchedFinishDate;

    private String pmProgressStatus;

    private String pmActualFinishDate;

    private Long pmPrevProgClaim;
    
    private String pmPreviousProgClaim;

    private Long pmClaimedAmount;

    private String pmStatusDate;

    // fro Report --
    private String projCode;
    private String projName;
    private Long projectId;
    private Long epsId;
    private String epsCode;
    private String epsName;

    private double prevValue = 0;
    private double currentValue = 0;

    private List<ReportPMCMValueTO> hrsList = new ArrayList<>();
    // for Report -------
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

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getLabourRate() {
        return labourRate;
    }

    public void setLabourRate(BigDecimal labourRate) {
        this.labourRate = labourRate;
    }

    public BigDecimal getPlantRate() {
        return plantRate;
    }

    public void setPlantRate(BigDecimal plantRate) {
        this.plantRate = plantRate;
    }

    public BigDecimal getMaterialRate() {
        return materialRate;
    }

    public void setMaterialRate(BigDecimal materialRate) {
        this.materialRate = materialRate;
    }

    public BigDecimal getOthersRate() {
        return othersRate;
    }

    public void setOthersRate(BigDecimal othersRate) {
        this.othersRate = othersRate;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public List<ProjPMCMItemTO> getChildSORItemTOs() {
        return childSORItemTOs;
    }

    public void setChildSORItemTOs(List<ProjPMCMItemTO> childSORItemTOs) {
        this.childSORItemTOs = childSORItemTOs;
    }

    public void addPrevHrs(ReportPMCMValueTO prevHrs) {
        if(prevHrs!=null && prevHrs.getValue()!=null)
            addPrevValue(prevHrs.getValue());
    }

    public void addCurrentHrs(ReportPMCMValueTO currentHrs) {
        if(currentHrs!=null && currentHrs.getValue()!=null)
            addCurrentValue(currentHrs.getValue());
    }

    public void addPrevValue(double prevValue) {
        this.prevValue = Double.sum(this.prevValue, prevValue);
    }

    public void addCurrentValue(double currentValue) {
        this.currentValue = Double.sum(this.currentValue, currentValue);
    }

    public String getPmCostCodeId() {
        return pmCostCodeId;
    }

    public void setPmCostCodeId(String pmCostCodeId) {
        this.pmCostCodeId = pmCostCodeId;
    }

    public String getPmCurrency() {
        return pmCurrency;
    }

    public void setPmCurrency(String pmCurrency) {
        this.pmCurrency = pmCurrency;
    }

    public Long getPmContractAmount() {
        return pmContractAmount;
    }

    public void setPmContractAmount(Long pmContractAmount) {
        this.pmContractAmount = pmContractAmount;
    }

    public String getPmSchedFinishDate() {
        return pmSchedFinishDate;
    }

    public void setPmSchedFinishDate(String pmSchedFinishDate) {
        this.pmSchedFinishDate = pmSchedFinishDate;
    }

    public String getPmActualFinishDate() {
        return pmActualFinishDate;
    }

    public void setPmActualFinishDate(String pmActualFinishDate) {
        this.pmActualFinishDate = pmActualFinishDate;
    }

    public Long getPmPrevProgClaim() {
        return pmPrevProgClaim;
    }

    public void setPmPrevProgClaim(Long pmPrevProgClaim) {
        this.pmPrevProgClaim = pmPrevProgClaim;
    }
    
    public String getPmPreviousProgClaim() {
        return pmPreviousProgClaim;
    }

    public void setPmPreviousProgClaim(String pmPreviousProgClaim) {
        this.pmPreviousProgClaim = pmPreviousProgClaim;
    }

    public Long getPmClaimedAmount() {
        return pmClaimedAmount;
    }

    public void setPmClaimedAmount(Long pmClaimedAmount) {
        this.pmClaimedAmount = pmClaimedAmount;
    }

    public Long getManPowerHrs() {
        return manPowerHrs;
    }

    public void setManPowerHrs(Long manPowerHrs) {
        this.manPowerHrs = manPowerHrs;
    }

    public String getPmProgressStatus() {
        return pmProgressStatus;
    }

    public void setPmProgressStatus(String pmProgressStatus) {
        this.pmProgressStatus = pmProgressStatus;
    }

    public String getPmStatusDate() {
        return pmStatusDate;
    }

    public void setPmStatusDate(String pmStatusDate) {
        this.pmStatusDate = pmStatusDate;
    }

    public String getPmCostCodeName() {
        return pmCostCodeName;
    }

    public void setPmCostCodeName(String pmCostCodeName) {
        this.pmCostCodeName = pmCostCodeName;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getEpsId() {
        return epsId;
    }

    public void setEpsId(Long epsId) {
        this.epsId = epsId;
    }

    public String getEpsCode() {
        return epsCode;
    }

    public void setEpsCode(String epsCode) {
        this.epsCode = epsCode;
    }

    public String getEpsName() {
        return epsName;
    }

    public void setEpsName(String epsName) {
        this.epsName = epsName;
    }

    public double getPrevValue() {
        return prevValue;
    }

    public void setPrevValue(double prevValue) {
        this.prevValue = prevValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public List<ReportPMCMValueTO> getHrsList() {
        return hrsList;
    }

    public void setHrsList(List<ReportPMCMValueTO> hrsList) {
        this.hrsList = hrsList;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProjPMCMItemTO other = (ProjPMCMItemTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}

