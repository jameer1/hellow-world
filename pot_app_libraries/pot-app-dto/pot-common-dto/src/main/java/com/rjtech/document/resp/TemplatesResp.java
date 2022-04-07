package com.rjtech.document.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.document.dto.TemplatesTO;
import com.rjtech.document.dto.WorkflowTO;

public class TemplatesResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<TemplatesTO> templatesTOs = new ArrayList<TemplatesTO>();
    private List<WorkflowTO> workflowTOs = new ArrayList<WorkflowTO>();
    private Integer templatesCount;

    public List<TemplatesTO> getTemplatesTOs() {  	
        return templatesTOs;
    }

    public void setTemplatesTOs(List<TemplatesTO> templatesTOs) {
        this.templatesTOs = templatesTOs;
    }

    public Integer getTemplatesCount() {
    	return templatesCount;
    }
    
    public void setTemplatesCount( Integer templatesCount ) {
    	this.templatesCount = templatesCount ;
    }
    
    public List<WorkflowTO> getWorkflowTOs() {  	
        return workflowTOs;
    }

    public void setWorkflowTOs(List<WorkflowTO> workflowTOs) {
        this.workflowTOs = workflowTOs;
    }
}