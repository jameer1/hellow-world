
package com.rjtech.document.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the categories_mstr database table.
 * 
 */
@Entity
@Table(name = "categories_mstr")
public class CategoriesMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_MSTR_ID")
    private Integer categoryMstrId;

    @Column(name = "TEMPLATE_CATEGORY_NAME")
    private String templateCategoryName;

    public Integer getCategoryMstrId() {
        return categoryMstrId;
    }

    public void setCategoryMstrId(Integer categoryMstrId) {
        this.categoryMstrId = categoryMstrId;
    }

    public String getTemplateCategoryName() {
        return templateCategoryName;
    }

    public void setTemplateCategoryName(String templateCategoryName) {
        this.templateCategoryName = templateCategoryName;
    }
    
    public String toString() {
    	return "templateCategoryName:"+templateCategoryName+" categoryMstrId:"+categoryMstrId;
    }
        
}