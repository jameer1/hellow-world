package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOEItemTO;

public class ProjSOEItemSaveReq extends ProjectTO {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSOEItemTO> projSOEItemTOs = new ArrayList<ProjSOEItemTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long loggedInUser;
    private String actionType;

    public List<ProjSOEItemTO> getProjSOEItemTOs() {
        return projSOEItemTOs;
    }

    public void setProjSOEItemTOs(List<ProjSOEItemTO> projSOEItemTOs) {
        this.projSOEItemTOs = projSOEItemTOs;
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
}
