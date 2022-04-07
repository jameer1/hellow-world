package com.rjtech.document.req;

import com.rjtech.document.dto.TemplateCategoriesTO;

public class TemplateCategorySaveReq{

    
    private static final long serialVersionUID = 1L;   
    private Long categoryId;
    private String categoryName;
    private String colorCode;
    private Long createdBy;
    private Integer categoryMstrId;
    private Long crmId;
    
    
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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
    
    public String toString() {
    	return "createdBy:"+createdBy+" color code:"+colorCode+" category name:"+categoryName+" crmId:"+crmId;
    }
}