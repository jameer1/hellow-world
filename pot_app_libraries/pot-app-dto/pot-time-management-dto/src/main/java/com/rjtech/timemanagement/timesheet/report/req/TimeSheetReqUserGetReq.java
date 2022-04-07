package com.rjtech.timemanagement.timesheet.report.req;

import java.util.ArrayList;
import java.util.List;

public class TimeSheetReqUserGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = -8609613743526719257L;

    private List<Long> projIds = new ArrayList<Long>();

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

}
