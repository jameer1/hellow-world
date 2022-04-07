package com.rjtech.projsettings.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;

public class ProjectDefaultSaveReq implements Serializable {

    private static final long serialVersionUID = -2951822532451927148L;

    private List<Long> projIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

}
