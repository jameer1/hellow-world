package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSORTrackTO;

public class ProjSORTrackSaveReq extends ProjectTO {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSORTrackTO> projSORTrackTOs = new ArrayList<ProjSORTrackTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private String actionType;

    public List<ProjSORTrackTO> getProjSORTrackTOs() {
        return projSORTrackTOs;
    }

    public void setProjSORTrackTOs(List<ProjSORTrackTO> projSORTrackTOs) {
        this.projSORTrackTOs = projSORTrackTOs;
    }
    
    public void setActionType(String actionType) {
    	this.actionType = actionType;
    }
    
    public String getActionType() {
    	return actionType;
    }

}
