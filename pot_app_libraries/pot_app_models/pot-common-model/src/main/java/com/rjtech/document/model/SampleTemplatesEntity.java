
package com.rjtech.document.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.rjtech.document.model.TemplateCategoriesEntity;
import com.rjtech.document.model.CategoriesMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.document.model.WorkFlowStagesEntity;


/**
 * The persistent class for the sample_templates database table.
 * 
 */
@Entity
@Table(name = "sample_templates")
public class SampleTemplatesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMPLATE_ID")
    private Long templateId;

    @Column(name = "TEMPLATE_NAME")
    private String templateName;

    @Column(name="FORM_NAME")
    private String formName;
    
    @ManyToOne
    @JoinColumn(name = "TEMPLATE_CATEGORY_ID")
    private TemplateCategoriesEntity templateCategoryId;
    
    @ManyToOne
    @JoinColumn(name = "CATEGORY_MSTR_ID")
    private CategoriesMstrEntity categoryMstrId;
    
    @Column(name = "TEMPLATE_JSON")
    private String templateJson;
    
    @Column(name = "TEMPLATE_TYPE")
    private String templateType;
    
    @Column(name = "VERSION_STATUS")
    private Integer versionStatus;
    
    @Column(name = "FORMS_COUNT")
    private Integer formsCount;
    
    @Column(name = "STATUS")
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON", updatable = false)
    private Date createdOn;
    
    @Column(name="TEMPLATE_CODE")
    private String templateCode;
    
    @Column(name="TEMPLATE_VERSION")
    private Integer templateVersion;
    
    @ManyToOne
    @JoinColumn(name = "INTERNAL_APPROVER_USER_ID", updatable = false)
    private UserMstrEntity internalApproverUserId;
    
    @ManyToOne
    @JoinColumn(name = "EXTERNAL_APPROVER_USER_ID", updatable = false)
    private UserMstrEntity externalApproverUserId;
    
    @Column(name="INTERNAL_APPROVAL_STATUS")
    private String internalApprovalStatus;
    
    @Column(name="EXTERNAL_APPROVAL_STATUS")
    private String externalApprovalStatus;
    
    @Column(name="IS_INTERNAL_APPROVED")
    private Character isInternalApproved;
    
    @Column(name="IS_EXTERNAL_APPROVED")
    private Character isExternalApproved;
    
    @Column(name="IS_EXTERNAL_APPROVAL_REQUIRED")
    private Character isExternalApprovalRequired;
    
    @OneToOne
    @JoinColumn(name="WORKFLOW_ID_FK")
    private WorkFlowStagesEntity workflowStagesId;

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
    
    public void setTemplateCategoryId( TemplateCategoriesEntity templateCategoryId ) {
        this.templateCategoryId = templateCategoryId;
    }

    public TemplateCategoriesEntity getTemplateCategoryId() {
        return templateCategoryId;
    }    
       
    public void setTemplateJson(String templateJson) {
    	this.templateJson = templateJson;
    }
    
    public String getTemplateJson()
    {
    	return templateJson;
    }
        
    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }
    
    public Integer getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(Integer versionStatus) {
        this.versionStatus = versionStatus;
    }
    
    public void setCategoryMstrId( CategoriesMstrEntity categoryMstrId ) {
        this.categoryMstrId = categoryMstrId;
    }

    public CategoriesMstrEntity getCategoryMstrId() {
        return categoryMstrId;
    }
    
    public Integer getFormsCount() {
        return formsCount;
    }

    public void setFormsCount(Integer formsCount) {
        this.formsCount = formsCount;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    
    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode( String templateCode ) {
        this.templateCode = templateCode;
    }
    
    public Integer getTemplateVersion() {
        return templateVersion;
    }

    public void setTemplateVersion(Integer templateVersion) {
        this.templateVersion = templateVersion;
    }
    
    public UserMstrEntity getInternalApproverUserId() {
    	return internalApproverUserId;
    }
    
    public void setInternalApproverUserId( UserMstrEntity internalApproverUserId ) {
    	this.internalApproverUserId = internalApproverUserId;
    }
    
    public UserMstrEntity getExternalApproverUserId() {
    	return externalApproverUserId;
    }
    
    public void setExternalApproverUserId( UserMstrEntity externalApproverUserId ) {
    	this.externalApproverUserId = externalApproverUserId;
    }
    
    public String getInternalApprovalStatus() {
    	return internalApprovalStatus;
    }
    
    public void setInternalApprovalStatus( String internalApprovalStatus ) {
    	this.internalApprovalStatus = internalApprovalStatus;
    }
    
    public String getExternalApprovalStatus() {
    	return externalApprovalStatus;
    }
    
    public void setExternalApprovalStatus( String externalApprovalStatus ) {
    	this.externalApprovalStatus = externalApprovalStatus;
    }
        
    public Character getIsInternalApproved() {
        return isInternalApproved;
    }

    public void setIsInternalApproved( Character isInternalApproved ) {
        this.isInternalApproved = isInternalApproved;
    }
    
    public Character getIsExternalApproved() {
        return isExternalApproved;
    }

    public void setIsExternalApproved( Character isExternalApproved ) {
        this.isExternalApproved = isExternalApproved;
    }
    
    public Character getIsExternalApprovalRequired() {
        return isExternalApprovalRequired;
    }

    public void setIsExternalApprovalRequired( Character isExternalApprovalRequired ) {
        this.isExternalApprovalRequired = isExternalApprovalRequired;
    }
    
    public void setWorkflowStagesId( WorkFlowStagesEntity workflowStagesId ) {
    	this.workflowStagesId = workflowStagesId;
    }
    
    public WorkFlowStagesEntity getWorkflowStagesId() {
    	return workflowStagesId;
    }
    
    public String toString()
    {
    	return "From SampleTemplatesEntity -- Template Id:"+templateId+" Tempalte Name:"+templateName+" Form Name:"+formName+" Status:"+status+" createdBy:"+createdBy+" createdOn:"+createdOn+" form count:"+formsCount+" template json:"+templateJson+" version status:"+versionStatus+" template type:"+templateType+" categoryMstrId:"+categoryMstrId+" template code:"+templateCode+" template version:"+templateVersion;
    }    
}