package com.rjtech.document.req;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.document.dto.TemplatesTO;

public class TransferTemplatesGetReq extends TemplatesTO {
    
    private static final long serialVersionUID = 1L; 
    //private Long templateId;
    private Long templateCategoryId;    
    private Integer categoryMstrId;
    private Long crmId;
    private Long projectId;    
    private String templateType;
    //private Long createdBy;
    private String transferFrom;
    private Long userId;
    private List<TemplatesTO> templatesTOs = new ArrayList<TemplatesTO>();
    
    
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
    
    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }
    
    /*public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }*/
    
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }   
    
    public void setTransferFrom(String transferFrom) {
        this.transferFrom = transferFrom;
    }
    
    public String getTransferFrom() {
        return transferFrom;
    }
    
    public List<TemplatesTO> getTemplatesTOs() {  	
        return templatesTOs;
    }

    public void setTemplatesTOs(List<TemplatesTO> templatesTOs) {
        this.templatesTOs = templatesTOs;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String toString()
    {
    	return "from the TemplateSaveReq templatecategory id:"+getTemplateCategoryId()+" templateType:"+templateType+" crmId:"+crmId+" projId:"+projectId+" categoryMstrId:"+categoryMstrId+" templateId:"+getTemplateId()+" transferFrom:"+transferFrom+" created by:"+getCreatedBy();
    }
}