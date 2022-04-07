
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
 * The persistent class for the project_templates_proposal database table.
 * 
 */
@Entity
@Table(name = "project_templates_proposal")
public class ProjectTemplatesProposalEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROPOSAL_ID")
    private Long proposalId;

    @ManyToOne
    @JoinColumn(name = "PROPOSER_USER_ID")
    private UserMstrEntity proposerUserId;
    
    @ManyToOne
    @JoinColumn(name = "PROJECT_TEMPLATE_ID")
    private ProjectTemplatesEntity projectTemplateId;
    
    @Column(name = "ALLOW_VIEW_EDIT")
    private Character allowViewEdit;
    
    @Column(name = "ALLOW_INTO_CENTRAL_TEMPLATE")
    private Character allowIntoCentralTemplate;
        
    @Column(name = "REVIEWER_COMMENTS")
    private String reviewerComments;
    
    @ManyToOne
    @JoinColumn(name = "CRM_ID", updatable = false)
    private ClientRegEntity crmId;
    
    @ManyToOne
    @JoinColumn(name="FROM_PROJECT_ID")
    private ProjMstrEntity fromProjectId;
    
    @ManyToOne
    @JoinColumn(name = "REVIEWER_USER_ID")
    private UserMstrEntity reviewerUserId;
    
    @Column(name = "STATUS")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PROPOSAL_DATE", updatable = false)
    private Date proposalDate;

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId( Long proposalId ) {
        this.proposalId = proposalId;
    }

    public String getReviewerComments() {
        return reviewerComments;
    }

    public void setReviewerComments(String reviewerComments) {
        this.reviewerComments = reviewerComments;
    }
    
    public ProjMstrEntity getFromProjectId() {
    	return fromProjectId;
    }
    
    public void setFromProjectId( ProjMstrEntity fromProjectId ) {
    	this.fromProjectId = fromProjectId;
    }
        
    public ProjectTemplatesEntity getProjectTemplateId() {
    	return projectTemplateId;
    }
    
    public void setProjectTemplateId( ProjectTemplatesEntity projectTemplateId ) {
    	this.projectTemplateId = projectTemplateId;
    }
    
    public UserMstrEntity getReviewerUserId() {
        return reviewerUserId;
    }

    public void setReviewerUserId(UserMstrEntity reviewerUserId) {
        this.reviewerUserId = reviewerUserId;
    }
    
    public UserMstrEntity getProposerUserId() {
        return proposerUserId;
    }

    public void setProposerUserId( UserMstrEntity proposerUserId ) {
        this.proposerUserId = proposerUserId;
    }
    
    public ClientRegEntity getCrmId() {
    	return crmId;
    }
    
    public void setCrmId( ClientRegEntity crmId ) {
    	this.crmId = crmId;
    }
    
    public Character getAllowViewEdit() {
        return allowViewEdit;
    }

    public void setAllowViewEdit( Character allowViewEdit ) {
        this.allowViewEdit = allowViewEdit;
    }
    
    public Character getAllowIntoCentralTemplate() {
        return allowIntoCentralTemplate;
    }

    public void setAllowIntoCentralTemplate( Character allowIntoCentralTemplate ) {
        this.allowIntoCentralTemplate = allowIntoCentralTemplate;
    }
    
    public Date getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate( Date proposalDate ) {
        this.proposalDate = proposalDate;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String toString()
    {
    	return "ProjectTemplatesProposalEntity Proposal Id:"+proposalId+" allowViewEdit:"+allowViewEdit+" allowIntoCentralTemplate:"+allowIntoCentralTemplate+" crmId:"+crmId+" project id:"+fromProjectId+" reviewerComments:"+reviewerComments;
    }    
}