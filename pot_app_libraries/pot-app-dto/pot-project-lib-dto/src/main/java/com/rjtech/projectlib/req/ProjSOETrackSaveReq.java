package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOETrackTO;

public class ProjSOETrackSaveReq extends ProjectTO {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSOETrackTO> projSOETrackTOs = new ArrayList<ProjSOETrackTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long loggedInUser;
    private String actionType;
    private String resubmitStatus;

    public List<ProjSOETrackTO> getProjSOETrackTOs() {
        return projSOETrackTOs;
    }

    public void setProjSOETrackTOs(List<ProjSOETrackTO> projSOETrackTOs) {
        this.projSOETrackTOs = projSOETrackTOs;
    }
    
    public Long getLoggedInUser() {
    	return loggedInUser;        
    }
    
    public void setLoggedInUser( Long loggedInUser ) {
    	this.loggedInUser = loggedInUser;
    }
    
    public String getActionType() {
    	return actionType;        
    }
    
    public void setActionType( String actionType ) {
    	this.actionType = actionType;
    }
    
    public String getResubmitStatus() {
    	return resubmitStatus;        
    }
    
    public void setResubmitStatus( String resubmitStatus ) {
    	this.resubmitStatus = resubmitStatus;
    }
}
