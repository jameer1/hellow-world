package com.rjtech.projschedule.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjScheduleSowForReportsGetReq implements Serializable {

    private static final long serialVersionUID = -8317917316366973302L;

    private List<Long> projIds = new ArrayList();

    private List<Long> sowIds = new ArrayList();

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<Long> getSowIds() {
        return sowIds;
    }

    public void setSowIds(List<Long> sowIds) {
        this.sowIds = sowIds;
    }

}
