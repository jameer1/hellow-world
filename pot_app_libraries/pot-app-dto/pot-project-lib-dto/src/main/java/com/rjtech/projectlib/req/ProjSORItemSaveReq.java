package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSORItemTO;

public class ProjSORItemSaveReq extends ProjectTO {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSORItemTO> projSORItemTOs = new ArrayList<ProjSORItemTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private String actionType;

    public List<ProjSORItemTO> getProjSORItemTOs() {
        return projSORItemTOs;
    }

    public void setProjSORItemTOs(List<ProjSORItemTO> projSORItemTOs) {
        this.projSORItemTOs = projSORItemTOs;
    }
    
    public void setActionType(String actionType) {
    	this.actionType = actionType;
    }
    
    public String getActionType() {
    	return actionType;
    }

}
