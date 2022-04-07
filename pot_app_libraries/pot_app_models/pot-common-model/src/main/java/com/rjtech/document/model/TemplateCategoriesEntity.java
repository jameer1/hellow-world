
package com.rjtech.document.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the template_categories database table.
 * 
 */
@Entity
@Table(name = "template_categories")
public class TemplateCategoriesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name="COLOR_CODE")
    private String colorCode;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_MSTR_ID")
    private CategoriesMstrEntity categoryMstrId;
    
    @Column(name = "CRM_ID")
    private Long crmId;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON", updatable = false)
    private Date createdOn;

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
    
    public void setCategoryMstrId( CategoriesMstrEntity categoryMstrId ) {
        this.categoryMstrId = categoryMstrId;
    }

    public CategoriesMstrEntity getCategoryMstrId() {
        return categoryMstrId;
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
    
    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId( Long crmId ) {
        this.crmId = crmId;
    }
    
    public String toString()
    {
    	return "Color Code:"+colorCode+" createdBy:"+createdBy+" createdOn:"+createdOn+" categoryId:"+categoryId+" categoryName:"+categoryName;
    }
    
}