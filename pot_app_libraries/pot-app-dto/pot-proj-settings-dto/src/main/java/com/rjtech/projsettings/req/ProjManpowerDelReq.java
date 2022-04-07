package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjManpowerDelReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -2705214641510411088L;
    private List<Long> projManpowerIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjManpowerIds() {
        return projManpowerIds;
    }

    public void setProjManpowerIds(List<Long> projManpowerIds) {
        this.projManpowerIds = projManpowerIds;
    }

}
