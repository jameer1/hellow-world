
package com.rjtech.document.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.rjtech.document.model.TemplateCategoriesEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.document.model.ProjectTemplatesEntity;
import com.rjtech.eps.model.ProjMstrEntity;


/**
 * The persistent class for the project_forms database table.
 * 
 */
@Entity
@Table(name = "project_forms")
public class ProjectFormsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FORM_ID")
    private Long formId;

    @Column(name="FORM_NAME")
    private String formName;
    
    @ManyToOne
    @JoinColumn(name = "TEMPLATE_CATEGORY_ID")
    private TemplateCategoriesEntity templateCategoryId;
    
    @ManyToOne
    @JoinColumn(name = "PROJECT_TEMPLATE_ID")
    private ProjectTemplatesEntity projectTemplateId;
    
    @Column(name = "FORM_JSON")
    private String formJson;
    
    @ManyToOne
    @JoinColumn(name="PROJECT_ID")
    private ProjMstrEntity projectId;
    
    @Column(name = "FORM_TYPE")
    private String formType;
    
    @Column(name = "FORM_VERSION")
    private String formVersion;
    
    @Column(name = "STATUS")
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON", updatable = false)
    private Date createdOn;
    
    @Column(name = "TEMPLATE_JSON")
    private String templateJson;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_ON", updatable = true)
    private Date updatedOn;

    public Long getFormId() {
        return formId;
    }

    public void setFormId( Long formId ) {
        this.formId = formId;
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
       
    public void setFormJson(String formJson) {
    	this.formJson = formJson;
    }
    
    public String getFormJson()
    {
    	return formJson;
    }
        
    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }
    
    public ProjMstrEntity getProjectId() {
    	return projectId;
    }
    
    public void setProjectId( ProjMstrEntity projectId ) {
    	this.projectId = projectId;
    }
    
    public String getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(String formVersion) {
        this.formVersion = formVersion;
    }
        
    public ProjectTemplatesEntity getProjectTemplateId() {
    	return projectTemplateId;
    }
    
    public void setProjectTemplateId( ProjectTemplatesEntity projectTemplateId ) {
    	this.projectTemplateId = projectTemplateId;
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
    
    public void setTemplateJson(String templateJson) {
    	this.templateJson = templateJson;
    }
    
    public String getTemplateJson() {
    	return templateJson;
    }
    
    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
    
    public String toString()
    {
    	return "Form Id:"+formId+" Form Name:"+formName+" Status:"+status+" createdBy:"+createdBy+" createdOn:"+createdOn+" form json:"+formJson+" form version:"+formVersion+" form type:"+formType;
    }    
}