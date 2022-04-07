package com.rjtech.dashboards.req;

import java.util.List;

public class DashBoardGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;
    private List<Long> projIds;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

}
