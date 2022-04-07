package com.rjtech.document.req;

public class ProjectFormsGetReq {
    
    private static final long serialVersionUID = 1L;   
    private Long templateCategoryId;
    private Long projectId;
    private Long crmId;
    private Long projectTemplateId;
    private Long createdBy;
    private String formType;
    private String formJson;
    private String templateJson;
    private String status;
    private Long formId;
    private String formVersion;
    private String mode;

    public Long getTemplateCategoryId() {
        return templateCategoryId;
    }

    public void setTemplateCategoryId(Long templateCategoryId) {
        this.templateCategoryId = templateCategoryId;
    }
    
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    
    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }
    
    public Long getProjectTemplateId() {
        return projectTemplateId;
    }

    public void setProjectTemplateId(Long projectTemplateId) {
        this.projectTemplateId = projectTemplateId;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }
    
    public String getFormJson() {
        return formJson;
    }

    public void setFormJson(String formJson) {
        this.formJson = formJson;
    }
    
    public String getTemplateJson() {
        return templateJson;
    }

    public void setTemplateJson(String templateJson) {
        this.templateJson = templateJson;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }
    
    public String getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(String formVersion) {
        this.formVersion = formVersion;
    }
    
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    
    public String toString()
    {
    	return "From ProjectFormsGetReq templatecategory id:"+templateCategoryId+" project id:"+projectId+" template id:"+projectTemplateId+" crmId:"+crmId+" formId:"+formId+" formVersion:"+formVersion+" mode:"+mode;
    }
}