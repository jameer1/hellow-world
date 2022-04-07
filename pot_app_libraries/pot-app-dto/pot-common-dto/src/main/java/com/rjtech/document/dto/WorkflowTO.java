
package com.rjtech.document.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class WorkflowTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long workflowId;
    private Long sampleTemplateId;
    private Long centralTemplateId;
    private Long projectTemplateId;
    private Long templateCategoryId;
    private Integer categoryMstrId;
    
    private String stage1Field;
    private String stage1Name;
    private String stage1Status;
    private Long stage1ApproverId;
    
    private String stage2Field;
    private String stage2Name;
    private String stage2Status;
    private Long stage2ApproverId;
    
    private String stage3Field;
    private String stage3Name;
    private String stage3Status;
    private Long stage3ApproverId;
    
    private String stage4Field;
    private String stage4Name;
    private String stage4Status;
    private Long stage4ApproverId;
    
    private String stage5Field;    
    private String stage5Name;
    private String stage5Status;
    private Long stage5ApproverId;
    
    private String stage6Field;
    private String stage6Name;
    private String stage6Status;
    private Long stage6ApproverId;
    private Integer stagesCount;
    
    private String approvalStageName;
    private String approvalStatus;
    private Boolean isFinalApproval;
    private Long approverUserId;
    private String stageFieldJson;
    private String approverComments;
    private String workflowStatus;
    
    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }
     
    public void setSampleTemplateId( Long sampleTemplateId ) {
        this.sampleTemplateId = sampleTemplateId;
    }

    public Long getSampleTemplateId() {
        return sampleTemplateId;
    }
    
    public void setCentralTemplateId( Long centralTemplateId ) {
        this.centralTemplateId = centralTemplateId;
    }

    public Long getCentralTemplateId() {
        return centralTemplateId;
    }
    
    public void setProjectTemplateId( Long projectTemplateId ) {
        this.projectTemplateId = projectTemplateId;
    }

    public Long getProjectTemplateId() {
        return projectTemplateId;
    }
    
    public void setTemplateCategoryId( Long templateCategoryId ) {
        this.templateCategoryId = templateCategoryId;
    }

    public Long getTemplateCategoryId() {
        return templateCategoryId;
    }    
    
    public Integer getCategoryMstrId() {
    	return categoryMstrId;
    }
    
    public void setCategoryMstrId( Integer categoryMstrId ) {
    	this.categoryMstrId = categoryMstrId;
    }
    
    public String getStage1Field() {
        return stage1Field;
    }

    public void setStage1Field(String stage1Field) {
        this.stage1Field = stage1Field;
    }
    
    public String getStage1Name() {
        return stage1Name;
    }

    public void setStage1Name(String stage1Name) {
        this.stage1Name = stage1Name;
    }
    
    public String getStage1Status() {
        return stage1Status;
    }

    public void setStage1Status(String stage1Status) {
        this.stage1Status = stage1Status;
    }
    
    public String getStage2Field() {
        return stage2Field;
    }

    public void setStage2Field(String stage2Field) {
        this.stage2Field = stage2Field;
    }
    
    public String getStage2Name() {
        return stage2Name;
    }

    public void setStage2Name(String stage2Name) {
        this.stage2Name = stage2Name;
    }
    
    public String getStage2Status() {
        return stage2Status;
    }

    public void setStage2Status(String stage2Status) {
        this.stage2Status = stage2Status;
    }
    
    public String getStage3Field() {
        return stage3Field;
    }

    public void setStage3Field(String stage3Field) {
        this.stage3Field = stage3Field;
    }
    
    public String getStage3Name() {
        return stage3Name;
    }

    public void setStage3Name(String stage3Name) {
        this.stage3Name = stage3Name;
    }
    
    public String getStage3Status() {
        return stage3Status;
    }

    public void setStage3Status(String stage3Status) {
        this.stage3Status = stage3Status;
    }
    
    public String getStage4Field() {
        return stage4Field;
    }

    public void setStage4Field(String stage4Field) {
        this.stage4Field = stage4Field;
    }
    
    public String getStage4Name() {
        return stage4Name;
    }

    public void setStage4Name(String stage4Name) {
        this.stage4Name = stage4Name;
    }
    
    public String getStage4Status() {
        return stage4Status;
    }

    public void setStage4Status(String stage4Status) {
        this.stage4Status = stage4Status;
    }
    
    public String getStage5Field() {
        return stage5Field;
    }

    public void setStage5Field(String stage5Field) {
        this.stage5Field = stage5Field;
    }
    
    public String getStage5Name() {
        return stage5Name;
    }

    public void setStage5Name(String stage5Name) {
        this.stage5Name = stage5Name;
    }
    
    public String getStage5Status() {
        return stage5Status;
    }

    public void setStage5Status(String stage5Status) {
        this.stage5Status = stage5Status;
    }
    
    public String getStage6Field() {
        return stage6Field;
    }

    public void setStage6Field(String stage6Field) {
        this.stage6Field = stage6Field;
    }
    
    public String getStage6Name() {
        return stage6Name;
    }

    public void setStage6Name(String stage6Name) {
        this.stage6Name = stage6Name;
    }
    
    public String getStage6Status() {
        return stage6Status;
    }

    public void setStage6Status(String stage6Status) {
        this.stage6Status = stage6Status;
    }
    
    public Integer getStagesCount() {
    	return stagesCount;
    }
    
    public void setStagesCount( Integer stagesCount ) {
    	this.stagesCount = stagesCount;
    }
        
    public String getApprovalStageName() {
        return approvalStageName;
    }

    public void setApprovalStageName( String approvalStageName ) {
        this.approvalStageName = approvalStageName;
    }
    
    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus( String approvalStatus ) {
        this.approvalStatus = approvalStatus;
    }
    
    public void setApproverUserId( Long approverUserId ) {
        this.approverUserId = approverUserId;
    }

    public Long getApproverUserId() {
        return approverUserId;
    }
    
    public Boolean getIsFinalApproval() {
    	return isFinalApproval;
    } 
    
    public void setIsFinalApproval( Boolean isFinalApproval ) {
    	this.isFinalApproval = isFinalApproval;
    }
    
    public String getStageFieldJson() {
        return stageFieldJson;
    }

    public void setStageFieldJson( String stageFieldJson ) {
        this.stageFieldJson = stageFieldJson;
    }
    
    public String getApproverComments() {
        return approverComments;
    }

    public void setApproverComments( String approverComments ) {
        this.approverComments = approverComments;
    }
    
    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus( String workflowStatus ) {
        this.workflowStatus = workflowStatus;
    }
    
    public void setStage1ApproverId( Long stage1ApproverId ) {
        this.stage1ApproverId = stage1ApproverId;
    }

    public Long getStage1ApproverId() {
        return stage1ApproverId;
    }
    
    public void setStage2ApproverId( Long stage2ApproverId ) {
        this.stage2ApproverId = stage2ApproverId;
    }

    public Long getStage2ApproverId() {
        return stage2ApproverId;
    }
    
    public void setStage3ApproverId( Long stage3ApproverId ) {
        this.stage3ApproverId = stage3ApproverId;
    }

    public Long getStage3ApproverId() {
        return stage3ApproverId;
    }
    
    public void setStage4ApproverId( Long stage4ApproverId ) {
        this.stage4ApproverId = stage4ApproverId;
    }

    public Long getStage4ApproverId() {
        return stage4ApproverId;
    }
    
    public void setStage5ApproverId( Long stage5ApproverId ) {
        this.stage5ApproverId = stage5ApproverId;
    }

    public Long getStage5ApproverId() {
        return stage5ApproverId;
    }
    
    public void setStage6ApproverId( Long stage6ApproverId ) {
        this.stage6ApproverId = stage6ApproverId;
    }

    public Long getStage6ApproverId() {
        return stage6ApproverId;
    }
    
    public String toString()
    {
    	return "From WorkFlowStagesEntity Template Id: sampleTemplateId:"+sampleTemplateId+" centralTemplateId:"+centralTemplateId+" projectTemplateId:"+projectTemplateId+" stage1Field:"+stage1Field+" stage1Status:"+stage1Status+" stage2Field:"+stage2Field+" stage2Status:"+stage2Status+" stage3Field:"+stage3Field+" stage3Status:"+stage3Status+" stage4Field:"+stage4Field+" stage4Status:"+stage4Status+" stage5Field:"+stage5Field+" stage5Status:"+stage5Status+" stage6Field:"+stage6Field+" stage6Status:"+stage6Status+" isFinalApproval:"+isFinalApproval+" approvalStageName:"+approvalStageName+" approverUserId:"+approverUserId+" approvalStatus:"+approvalStatus+" status:"+workflowStatus;
    }    
}