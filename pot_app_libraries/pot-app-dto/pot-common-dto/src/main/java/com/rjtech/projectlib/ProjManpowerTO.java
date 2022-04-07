package com.rjtech.projectlib;

import java.util.Date;

import com.rjtech.centrallib.dto.EmpClassTO;
import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.dto.ProjectTO;

public class ProjManpowerTO extends ProjectTO {

    private static final long serialVersionUID = 725069871190599664L;
    private Long id;
    private Long empClassId;
    private Long empCatgId;
    private Long measureId;
    private Double originalQty;
    private Double revisedQty;
    private Double actualQty = Double.valueOf(0);
    private Double remainingQty;
    private Double estimateComplete;
    private Double estimateCompletion;
    private String startDate;
    private String finishDate;
    private Long resourceCurveId;
    private EmpClassTO empClassTO = new EmpClassTO();
    private MeasureUnitTO measureUnitTO = new MeasureUnitTO();
    private String estimateType;
    private String projEmpCategory;
    private Double budgetAtCompletion;
    private Double earnedValue;
    private Double productivityFactor;
    private Double schedulePerformanceIndex;
    private Date minStartDateOfBaseline;
    private Date maxFinishDateOfBaseline;
    private Double plannedValue;
    private String itemStatus;
    private Integer isItemReturned;
    private Long approverUserId;
    private String approverComments;
    private Long originatorUserId;
    private Long returnedByUserId;
    private String returnedComments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpClassId() {
        return empClassId;
    }

    public void setEmpClassId(Long empClassId) {
        this.empClassId = empClassId;
    }

    public Long getEmpCatgId() {
        return empCatgId;
    }

    public void setEmpCatgId(Long empCatgId) {
        this.empCatgId = empCatgId;
    }

    public Double getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(Double originalQty) {
        this.originalQty = originalQty;
    }

    public Double getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(Double revisedQty) {
        this.revisedQty = revisedQty;
    }

    public Double getActualQty() {
        return actualQty;
    }

    public void setActualQty(Double actualQty) {
        this.actualQty = actualQty;
    }

    public Double getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(Double remainingQty) {
        this.remainingQty = remainingQty;
    }

    public Double getEstimateComplete() {
        return estimateComplete;
    }

    public void setEstimateComplete(Double estimateComplete) {
        this.estimateComplete = estimateComplete;
    }

    public Double getEstimateCompletion() {
        return estimateCompletion;
    }

    public void setEstimateCompletion(Double estimateCompletion) {
        this.estimateCompletion = estimateCompletion;
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

    public EmpClassTO getEmpClassTO() {
        return empClassTO;
    }

    public void setEmpClassTO(EmpClassTO empClassTO) {
        this.empClassTO = empClassTO;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public Long getResourceCurveId() {
        return resourceCurveId;
    }

    public void setResourceCurveId(Long resourceCurveId) {
        this.resourceCurveId = resourceCurveId;
    }

    public MeasureUnitTO getMeasureUnitTO() {
        return measureUnitTO;
    }

    public void setMeasureUnitTO(MeasureUnitTO measureUnitTO) {
        this.measureUnitTO = measureUnitTO;
    }

    public String getEstimateType() {
        return estimateType;
    }

    public void setEstimateType(String estimateType) {
        this.estimateType = estimateType;
    }

    public String getProjEmpCategory() {
        return projEmpCategory;
    }

    public void setProjEmpCategory(String projEmpCategory) {
        this.projEmpCategory = projEmpCategory;
    }

    public Double getBudgetAtCompletion() {
        return budgetAtCompletion;
    }

    public void setBudgetAtCompletion(Double budgetAtCompletion) {
        this.budgetAtCompletion = budgetAtCompletion;
    }

    public Double getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(Double earnedValue) {
        this.earnedValue = earnedValue;
    }

    public Double getProductivityFactor() {
        return productivityFactor;
    }

    public void setProductivityFactor(Double productivityFactor) {
        this.productivityFactor = productivityFactor;
    }

    public Double getSchedulePerformanceIndex() {
        return schedulePerformanceIndex;
    }

    public void setSchedulePerformanceIndex(Double schedulePerformanceIndex) {
        this.schedulePerformanceIndex = schedulePerformanceIndex;
    }

	public Date getMinStartDateOfBaseline() {
		return minStartDateOfBaseline;
	}

	public void setMinStartDateOfBaseline(Date minStartDateOfBaseline) {
		this.minStartDateOfBaseline = minStartDateOfBaseline;
	}

	public Date getMaxFinishDateOfBaseline() {
		return maxFinishDateOfBaseline;
	}

	public void setMaxFinishDateOfBaseline(Date maxFinishDateOfBaseline) {
		this.maxFinishDateOfBaseline = maxFinishDateOfBaseline;
	}
	
	public Double getPlannedValue() {
        return plannedValue;
    }

    public void setPlannedValue(Double plannedValue) {
        this.plannedValue = plannedValue;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }
    
    public Integer getIsItemReturned() {
        return isItemReturned;
    }

    public void setIsItemReturned(Integer isItemReturned) {
        this.isItemReturned = isItemReturned;
    }
    
    public Long getApproverUserId() {
        return approverUserId;
    }

    public void setApproverUserId( Long approverUserId ) {
        this.approverUserId = approverUserId;
    }
    
    public String getApproverComments() {
        return approverComments;
    }

    public void setApproverComments( String approverComments ) {
        this.approverComments = approverComments;
    }
    
    public Long getOriginatorUserId() {
        return originatorUserId;
    }

    public void setOriginatorUserId( Long originatorUserId ) {
        this.originatorUserId = originatorUserId;
    }
    
    public String getReturnedComments() {
        return returnedComments;
    }

    public void setReturnedComments( String returnedComments ) {
        this.returnedComments = returnedComments;
    }
    
    public Long getReturnedByUserId() {
        return returnedByUserId;
    }

    public void setReturnedByUserId( Long returnedByUserId ) {
        this.returnedByUserId = returnedByUserId;
    }
}
