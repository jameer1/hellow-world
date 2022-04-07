package com.rjtech.document.req;

import com.rjtech.document.dto.TemplatesTO;

public class TemplatesGetReq {
    
    private static final long serialVersionUID = 1L;   
    private Long templateCategoryId;
    private Long crmId;
    private Integer categoryMstrId;
    private Long projId;
    private String templateStatus;

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
    
    public String getTemplateStatus() {
    	return templateStatus;
    }
    
    public void setTemplateStatus( String templateStatus ) {
    	this.templateStatus = templateStatus;
    }
    
    public String toString()
    {
    	return "templatecategory id:"+getTemplateCategoryId()+" categorymstrid:"+getCategoryMstrId();
    }
}