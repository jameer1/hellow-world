
package com.rjtech.document.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;

public class TemplateCategoriesTO  extends ClientTO {
    private static final long serialVersionUID = 1L;

    private Long categoryId;
    private String categoryName;
    private String colorCode;
    private Integer categoryMstrId;
    private String mstrCategoryName;

    public TemplateCategoriesTO() {
    }

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
    
    public Integer getCategoryMstrId() {
        return categoryMstrId;
    }

    public void setCategoryMstrId(Integer categoryMstrId) {
        this.categoryMstrId = categoryMstrId;
    }
    
    public String getMstrCategoryName() {
    	return mstrCategoryName;
    }
    
    public void setMstrCategoryName(String mstrCategoryName) {
    	this.mstrCategoryName = mstrCategoryName;
    }
}