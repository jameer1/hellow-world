package com.rjtech.document.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.document.dto.ProjectTemplatesProposalTO;

public class TemplatesProposalResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<ProjectTemplatesProposalTO> projectTemplatesProposalTOs = new ArrayList<ProjectTemplatesProposalTO>();
    private Integer templatesCount;

    public List<ProjectTemplatesProposalTO> getProjectTemplatesProposalTOs() {  	
        return projectTemplatesProposalTOs;
    }

    public void setProjectTemplatesProposalTemplatesTOs(List<ProjectTemplatesProposalTO> projectTemplatesProposalTOs) {
        this.projectTemplatesProposalTOs = projectTemplatesProposalTOs;
    }

    public Integer getTemplatesCount() {
    	return templatesCount;
    }
    
    public void setTemplatesCount( Integer templatesCount ) {
    	this.templatesCount = templatesCount ;
    }
}