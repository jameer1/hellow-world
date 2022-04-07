package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;


public class ProjGetReq extends ProjectTO {
    private static final long serialVersionUID = 6526217036270683215L;
    private Long userId;
    private List<Long> projIds = new ArrayList<Long>(5);

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

}
