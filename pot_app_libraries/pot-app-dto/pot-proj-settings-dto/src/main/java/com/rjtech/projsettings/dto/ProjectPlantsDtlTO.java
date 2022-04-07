package com.rjtech.projsettings.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.centrallib.dto.PlantClassTO;
import com.rjtech.common.dto.ProjectTO;

public class ProjectPlantsDtlTO extends ProjectTO {

    private static final long serialVersionUID = 5274406519780996315L;
    private Long id;
    private Long plantId;
    private Long plantClassId;
    private Long measureId;
    private BigDecimal originalQty;
    private BigDecimal revisedQty;
    private BigDecimal actualQty;
    private BigDecimal remainingQty;
    private BigDecimal estimateComplete;
    private String estimateType;
    private BigDecimal estimateCompletion;
    private String startDate;
    private String finishDate;
    private Long resourceCurveId;
    private Date minStartDateOfBaseline;
    private Date maxFinishDateOfBaseline;
    private PlantClassTO plantClassTO;
    private MeasureUnitTO measureUnitTO = new MeasureUnitTO();
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

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getPlantClassId() {
        return plantClassId;
    }

    public void setPlantClassId(Long plantClassId) {
        this.plantClassId = plantClassId;
    }

    public BigDecimal getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(BigDecimal originalQty) {
        this.originalQty = originalQty;
    }

    public BigDecimal getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(BigDecimal revisedQty) {
        this.revisedQty = revisedQty;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public BigDecimal getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(BigDecimal remainingQty) {
        this.remainingQty = remainingQty;
    }

    public BigDecimal getEstimateComplete() {
        return estimateComplete;
    }

    public void setEstimateComplete(BigDecimal estimateComplete) {
        this.estimateComplete = estimateComplete;
    }

    public String getEstimateType() {
        return estimateType;
    }

    public void setEstimateType(String estimateType) {
        this.estimateType = estimateType;
    }

    public BigDecimal getEstimateCompletion() {
        return estimateCompletion;
    }

    public void setEstimateCompletion(BigDecimal estimateCompletion) {
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

    public PlantClassTO getPlantClassTO() {
        return plantClassTO;
    }

    public void setPlantClassTO(PlantClassTO plantClassTO) {
        this.plantClassTO = plantClassTO;
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
