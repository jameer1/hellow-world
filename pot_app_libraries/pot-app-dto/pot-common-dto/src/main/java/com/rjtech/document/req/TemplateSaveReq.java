package com.rjtech.document.req;

import com.rjtech.document.dto.TemplatesTO;
import com.rjtech.document.dto.WorkflowTO;

public class TemplateSaveReq extends WorkflowTO {
    
    private static final long serialVersionUID = 1L; 
    private Long templateId;
    private Long templateCategoryId;
    private Long crmId;
    private Integer categoryMstrId;
    private Long projId;
    private String templateName;
    private String formName;
    private String templateType;
    private String templateJson;
    private Long createdBy;
    private Long projectId;
    private String status;
    private Integer versionStatus;
    private Integer formsCount;
    private Character isNew;
    private Boolean updateWorkflow;
    private Boolean updateTemplate;
    private Character isExternalApprovedRequired;
    private String templateCode;
    private Long internalApproverUserId;
    private Long externalApproverUserId;
    private String mode;
    private String fromSource;
    
    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateCategoryId() {
        return templateCategoryId;
    }

    public void setTemplateCategoryId(Long templateCategoryId) {
        this.templateCategoryId = templateCategoryId;
    }
    
    public Integer getCategoryMstrId() {
        return categoryMstrId;
    }

    public void setCategoryMstrId(Integer categoryMstrId) {
        this.categoryMstrId = categoryMstrId;
    }
    
    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }
    
    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }
    
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    
    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
    
    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }
    
    public String getTemplateJson() {
        return templateJson;
    }

    public void setTemplateJson(String templateJson) {
        this.templateJson = templateJson;
    }
        
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public Long getProjectId() {
        return createdBy;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(Integer versionStatus) {
        this.versionStatus = versionStatus;
    }
    
    public Integer getFormsCount() {
        return formsCount;
    }

    public void setFormsCount(Integer formsCount) {
        this.formsCount = formsCount;
    }
    
    public void setIsNew( Character isNew ) {
    	this.isNew = isNew;
    }
    
    public Character getIsNew() {
    	return isNew;
    }
    
    public void setUpdateWorkflow( Boolean updateWorkflow ) {
    	this.updateWorkflow = updateWorkflow;
    }
    
    public Boolean getUpdateWorkflow() {
    	return updateWorkflow;
    }
    
    public void setUpdateTemplate( Boolean updateTemplate ) {
    	this.updateTemplate = updateTemplate;
    }
    
    public Boolean getUpdateTemplate() {
    	return updateTemplate;
    }
    
    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode( String templateCode ) {
        this.templateCode = templateCode;
    }
    
    public void setIsExternalApprovedRequired( Character isExternalApprovedRequired ) {
    	this.isExternalApprovedRequired = isExternalApprovedRequired;
    }
    
    public Character getIsExternalApprovedRequired() {
    	return isExternalApprovedRequired;
    }
    
    public Long getInternalApproverUserId() {
        return internalApproverUserId;
    }

    public void setInternalApproverUserId(Long internalApproverUserId) {
        this.internalApproverUserId = internalApproverUserId;
    }
    
    public Long getExternalApproverUserId() {
        return externalApproverUserId;
    }

    public void setExternalApproverUserId( Long externalApproverUserId ) {
        this.externalApproverUserId = externalApproverUserId;
    }
    
    public String getMode() {
        return mode;
    }

    public void setMode( String mode ) {
        this.mode = mode;
    }
    
    public String getFromSource() {
        return fromSource;
    }

    public void setFromSource( String fromSource ) {
        this.fromSource = fromSource;
    }
    public String toString()
    {
    	return "from the TemplateSaveReq templatecategory id:"+getTemplateCategoryId()+" templateJson:"+templateJson+" formName:"+formName+" templateType:"+templateType+" templateName:"+templateName+" crmId:"+crmId+" projId:"+projId+" categoryMstrId:"+categoryMstrId+" templateId:"+templateId+" projectId:"+projectId+" status:"+status+" versionStatus:"+versionStatus+" isNew:"+isNew+" updateTemplate:"+updateTemplate+" updateWorkflow:"+updateWorkflow+" Stage1Name:"+this.getStage1Name()+" templateCode:"+templateCode;
    }
}