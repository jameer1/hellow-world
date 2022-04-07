
package com.rjtech.document.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class TemplatesTO{
    private static final long serialVersionUID = 1L;

    private Long templateId;
    private String templateName;
    private String formName;
    private Long templateCategoryId;
    private Integer categoryMstrId;
    private String status;
    private String templateJson;
    private Integer versionStatus;
    private Date createdOn;
    private String createdBy;
    private Long crmId;
    private Long projectId;
    private String templateType;
    private Integer formsCount;
    private String templCategoryname;
    private String internalApprovalStatus;
    private String externalApprovalStatus;
    private Character isInternalApproved;
    private Character isExternalApproved;
    private Character isExternalApprovedRequired;
    private Character isExternalApprovalRequired;
    private Long internalApprovedBy;
    private Long externalApprovedBy;
    private Character isNew;
    private String templateCode;
    private Integer templateVersion;
    private String crmName;
    private Long workflowStageId;
    private String fromSource;

    public TemplatesTO() {
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
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
    
    public String getTemplateJson() {
        return templateJson;
    }

    public void setTemplateJson(String templateJson) {
        this.templateJson = templateJson;
    }  
    
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn( Date createdOn ) {
        this.createdOn = createdOn;
    }
    
    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId( Long crmId ) {
        this.crmId = crmId;
    }
    
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId( Long projectId ) {
        this.projectId = projectId;
    }
    
    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType( String templateType ) {
        this.templateType = templateType;
    }
    
    public Integer getFormsCount() {
        return formsCount;
    }

    public void setFormsCount( Integer formsCount ) {
        this.formsCount = formsCount;
    }
    
    public String getTemplCategoryname() {
        return templCategoryname;
    }

    public void setTemplCategoryname( String templCategoryname ) {
        this.templCategoryname = templCategoryname;
    }
    
    public void setInternalApprovalStatus( String internalApprovalStatus ) {
    	this.internalApprovalStatus = internalApprovalStatus;
    }
    
    public String getInternalApprovalStatus() {
    	return internalApprovalStatus;
    }
    
    public void setExternalApprovalStatus( String externalApprovalStatus ) {
    	this.externalApprovalStatus = externalApprovalStatus;
    }
    
    public String getExternalApprovalStatus() {
    	return externalApprovalStatus;
    }
    
    public void setIsInternalApproved( Character isInternalApproved ) {
    	this.isInternalApproved = isInternalApproved;
    }
    
    public Character getIsInternalApproved() {
    	return isInternalApproved;
    }
    
    public void setIsExternalApproved( Character isExternalApproved ) {
    	this.isExternalApproved = isExternalApproved;
    }
    
    public Character getIsExternalApproved() {
    	return isExternalApproved;
    }
    
    public void setIsExternalApprovedRequired( Character isExternalApprovedRequired ) {
    	this.isExternalApprovedRequired = isExternalApprovedRequired;
    }
    
    public Character getIsExternalApprovedRequired() {
    	return isExternalApprovedRequired;
    }
    
    public void setIsExternalApprovalRequired( Character isExternalApprovalRequired ) {
    	this.isExternalApprovalRequired = isExternalApprovalRequired;
    }
    
    public Character getIsExternalApprovalRequired() {
    	return isExternalApprovalRequired;
    }
    
    public void setIsNew( Character isNew ) {
    	this.isNew = isNew;
    }
    
    public Character getIsNew() {
    	return isNew;
    }
    
    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode( String templateCode ) {
        this.templateCode = templateCode;
    }
    
    public Long getExternalApprovedBy() {
        return externalApprovedBy;
    }

    public void setExternalApprovedBy(Long externalApprovedBy) {
        this.externalApprovedBy = externalApprovedBy;
    }
    
    public Long getInternalApprovedBy() {
        return internalApprovedBy;
    }

    public void setInternalApprovedBy( Long internalApprovedBy ) {
        this.internalApprovedBy = internalApprovedBy;
    }
    
    public Integer getTemplateVersion() {
        return templateVersion;
    }

    public void setTemplateVersion( Integer templateVersion ) {
        this.templateVersion = templateVersion;
    }
    
    public void setCrmName( String crmName ) {
        this.crmName = crmName;
    }

    public String getCrmName() {
        return crmName;
    }
    
    public Long getWorkflowStageId() {
        return workflowStageId;
    }

    public void setWorkflowStageId( Long workflowStageId ) {
        this.workflowStageId = workflowStageId;
    }
    
    public void setFromSource( String fromSource ) {
        this.fromSource = fromSource;
    }

    public String getFromSource() {
        return fromSource;
    }
    
    public String toString() {
    	return "templateVersion"+templateVersion+" workflowStageId:"+workflowStageId;
    }
}