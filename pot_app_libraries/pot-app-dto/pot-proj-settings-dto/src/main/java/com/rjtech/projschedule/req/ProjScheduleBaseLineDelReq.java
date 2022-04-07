package com.rjtech.projschedule.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjScheduleBaseLineDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6513859022394753425L;
    private List<Long> baseLineIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getBaseLineIds() {
        return baseLineIds;
    }

    public void setBaseLineIds(List<Long> baseLineIds) {
        this.baseLineIds = baseLineIds;
    }

}
