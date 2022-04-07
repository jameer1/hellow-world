package com.rjtech.document.req;

import com.rjtech.document.dto.ProjectTemplatesProposalTO;
import java.util.ArrayList;
import java.util.List;

public class TemplateProposalReq {
    
    private static final long serialVersionUID = 1L;
    private Long crmId;
    private Long proposerUserId;
    private Long reviewerUserId;
    private Long fromProjectId;
    private List<ProjectTemplatesProposalTO> projectTemplatesProposalTOs = new ArrayList<ProjectTemplatesProposalTO>();
        
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
    
    public Long getProposerUserId() {
        return proposerUserId;
    }

    public void setProposerUserId( Long proposerUserId ) {
        this.proposerUserId = proposerUserId;
    }
    
    public Long getReviewerUserId() {
        return reviewerUserId;
    }

    public void setReviewerUserId( Long reviewerUserId ) {
        this.reviewerUserId = reviewerUserId;
    }
    
    public List<ProjectTemplatesProposalTO> getProjectTemplatesProposalTOs() {  	
        return projectTemplatesProposalTOs;
    }

    public void setProjectTemplatesProposalTOs( List<ProjectTemplatesProposalTO> projectTemplatesProposalTOs ) {
        this.projectTemplatesProposalTOs = projectTemplatesProposalTOs;
    }
    
    public String toString()
    {
    	return "From TemplateApprovalReq: crmId:"+crmId+" projectId:"+fromProjectId;
    }
}