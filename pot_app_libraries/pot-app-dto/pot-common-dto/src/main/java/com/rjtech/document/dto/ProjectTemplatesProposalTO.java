
package com.rjtech.document.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ProjectTemplatesProposalTO {
    private static final long serialVersionUID = 1L;

    private Long projectTemplateId;   
    private Long crmId;
    private Long fromProjectId;
    private String reviewerComments;
    private Character allowViewEdit;
    private Character allowIntoCentralTemplates;    
    private Long proposerUserId;
    private String proposerUserDisplayName;
    private Long reviewerUserId;
    private String reviewerUserDisplayName;
    private String status;
    private Date proposalDate;
    private String projectName;
    private Long proposalId;
    private String templateName;
    private String templCategoryName;
    private Integer templateVersion;

    public ProjectTemplatesProposalTO() {
    }

    public Long getProjectTemplateId() {
        return projectTemplateId;
    }

    public void setProjectTemplateId( Long projectTemplateId ) {
        this.projectTemplateId = projectTemplateId;
    }
    
    public Long getProposerUserId() {
        return proposerUserId;
    }

    public void setProposerUserId(Long proposerUserId) {
        this.proposerUserId = proposerUserId;
    }
    
    public Long getReviewerUserId() {
        return reviewerUserId;
    }

    public void setReviewerUserId(Long reviewerUserId) {
        this.reviewerUserId = reviewerUserId;
    }
    
    public Date getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate( Date proposalDate ) {
        this.proposalDate = proposalDate;
    }
    
    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId( Long crmId ) {
        this.crmId = crmId;
    }
    
    public Long getFromProjectId() {
        return fromProjectId;
    }

    public void setFromProjectId( Long fromProjectId ) {
        this.fromProjectId = fromProjectId;
    }
    
    public String getReviewerComments() {
        return reviewerComments;
    }

    public void setReviewerComments( String reviewerComments ) {
        this.reviewerComments = reviewerComments;
    }
    
    public String getProposerUserDisplayName() {
        return proposerUserDisplayName;
    }

    public void setProposerUserDisplayName( String proposerUserDisplayName ) {
        this.proposerUserDisplayName = proposerUserDisplayName;
    }
    
    public String getReviewerUserDisplayName() {
        return reviewerUserDisplayName;
    }

    public void setReviewerUserDisplayName( String reviewerUserDisplayName ) {
        this.reviewerUserDisplayName = reviewerUserDisplayName;
    }
    
    public void setAllowIntoCentralTemplates( Character allowIntoCentralTemplates ) {
    	this.allowIntoCentralTemplates = allowIntoCentralTemplates;
    }
    
    public Character getAllowIntoCentralTemplates() {
    	return allowIntoCentralTemplates;
    }
    
    public void setAllowViewEdit( Character allowViewEdit ) {
    	this.allowViewEdit = allowViewEdit;
    }
    
    public Character getAllowViewEdit() {
    	return allowViewEdit;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }
    
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName( String projectName ) {
        this.projectName = projectName;
    }
    
    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId( Long proposalId ) {
        this.proposalId = proposalId;
    }
    
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName( String templateName ) {
        this.templateName = templateName;
    }
    
    public String getTemplCategoryName() {
        return templCategoryName;
    }

    public void setTemplCategoryName( String templCategoryName ) {
        this.templCategoryName = templCategoryName;
    }
    
    public Integer getTemplateVersion() {
        return templateVersion;
    }

    public void setTemplateVersion( Integer templateVersion ) {
        this.templateVersion = templateVersion;
    } 
    
    public String toString() {
    	return "templateVersion:"+templateVersion+" templCategoryName:"+templCategoryName+" templateName:"+templateName;
    }
}