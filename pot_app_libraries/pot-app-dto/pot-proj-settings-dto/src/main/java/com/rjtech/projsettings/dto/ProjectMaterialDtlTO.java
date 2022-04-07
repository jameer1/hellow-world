package com.rjtech.projsettings.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.centrallib.dto.MaterialClassTO;
import com.rjtech.common.dto.ProjectTO;

public class ProjectMaterialDtlTO extends ProjectTO {

    private static final long serialVersionUID = 1210438992012491203L;
    private Long id;
    private BigDecimal actualQty;
    private BigDecimal estimateCompletion;
    private BigDecimal estimateComplete;
    private Long materialId;
    private Long materialClassId;
    private BigDecimal originalQty;
    private BigDecimal remainingQty;
    private BigDecimal revisedQty;
    private String finishDate;
    private String startDate;
    private Long parentId;
    private boolean item;
    private boolean expand = true;
    private Long resourceCurveId;
    private String estimateType;
    private Date minStartDateOfBaseline;
    private Date maxFinishDateOfBaseline;
    private String itemStatus;
    private Integer isItemReturned;
    private Long approverUserId;
    private String approverComments;
    private Long originatorUserId;
    private Long returnedByUserId;
    private String returnedComments;

    List<ProjectMaterialDtlTO> projectMaterialDtlTOs = new ArrayList<ProjectMaterialDtlTO>();
    private MaterialClassTO materialClassTO = new MaterialClassTO();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public BigDecimal getEstimateCompletion() {
        return estimateCompletion;
    }

    public void setEstimateCompletion(BigDecimal estimateCompletion) {
        this.estimateCompletion = estimateCompletion;
    }

    public BigDecimal getEstimateComplete() {
        return estimateComplete;
    }

    public void setEstimateComplete(BigDecimal estimateComplete) {
        this.estimateComplete = estimateComplete;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getMaterialClassId() {
        return materialClassId;
    }

    public void setMaterialClassId(Long materialClassId) {
        this.materialClassId = materialClassId;
    }

    public Long getResourceCurveId() {
        return resourceCurveId;
    }

    public void setResourceCurveId(Long resourceCurveId) {
        this.resourceCurveId = resourceCurveId;
    }

    public BigDecimal getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(BigDecimal originalQty) {
        this.originalQty = originalQty;
    }

    public BigDecimal getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(BigDecimal remainingQty) {
        this.remainingQty = remainingQty;
    }

    public BigDecimal getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(BigDecimal revisedQty) {
        this.revisedQty = revisedQty;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public List<ProjectMaterialDtlTO> getProjectMaterialDtlTOs() {
        return projectMaterialDtlTOs;
    }

    public void setProjectMaterialDtlTOs(List<ProjectMaterialDtlTO> projectMaterialDtlTOs) {
        this.projectMaterialDtlTOs = projectMaterialDtlTOs;
    }

    public MaterialClassTO getMaterialClassTO() {
        return materialClassTO;
    }

    public void setMaterialClassTO(MaterialClassTO materialClassTO) {
        this.materialClassTO = materialClassTO;
    }

    public String getEstimateType() {
        return estimateType;
    }

    public void setEstimateType(String estimateType) {
        this.estimateType = estimateType;
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

    public void setIsItemReturned( Integer isItemReturned ) {
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
