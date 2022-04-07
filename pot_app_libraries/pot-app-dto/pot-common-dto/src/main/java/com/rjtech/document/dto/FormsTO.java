
package com.rjtech.document.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

// This is the DTO layer of the FormsTO
public class FormsTO{
    private static final long serialVersionUID = 1L;

    private Long formId;
    private String formName;
    private Long templateCategoryId;
    private Long projectTemplateId;
    private String formType;
    private String status;
    private String formJson;
    private Long projectId;
    private String formVersion;
    private Date createdOn;
    private String createdBy;
    private String templCategoryName;
    private String templateJson;
    private String fromSource;
    
    
    public FormsTO() {
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
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
    
    public Long getProjectTemplateId() {
        return projectTemplateId;
    }

    public void setProjectTemplateId(Long projectTemplateId) {
        this.projectTemplateId = projectTemplateId;
    }
        
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(String formVersion) {
        this.formVersion = formVersion;
    }
    
    public String getFormJson() {
        return formJson;
    }

    public void setFormJson(String formJson) {
        this.formJson = formJson;
    }
    
    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
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
    
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId( Long projectId ) {
        this.projectId = projectId;
    }
    
    public String getTemplCategoryName() {
        return templCategoryName;
    }

    public void setTemplCategoryName( String templCategoryName ) {
        this.templCategoryName = templCategoryName;
    }
    
    public String getTemplateJson() {
        return templateJson;
    }

    public void setTemplateJson(String templateJson) {
        this.templateJson = templateJson;
    }
    
    public String getFromSource() {
        return fromSource;
    }

    public void setFromSource(String fromSource) {
        this.fromSource = fromSource;
    }
}