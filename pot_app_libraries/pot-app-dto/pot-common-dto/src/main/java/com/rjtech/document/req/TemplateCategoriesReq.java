package com.rjtech.document.req;

import com.rjtech.document.dto.TemplateCategoriesTO;

public class TemplateCategoriesReq extends TemplateCategoriesTO{

    
    private static final long serialVersionUID = 1L;   
    private Integer categoryMstrId;  

    public Integer getCategoryMstrId() {
        return categoryMstrId;
    }

    public void setCategoryMstrId(Integer categoryMstrId) {
        this.categoryMstrId = categoryMstrId;
    }
    
    public String toString() {
    	return "From TemplateCategoriesReq categoryMstrId:"+categoryMstrId+" categoryId:"+getCategoryId()+" categoryName:"+getCategoryName();
    }
}