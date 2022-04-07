package com.rjtech.projectlib.req;

import java.util.List;

import com.rjtech.common.dto.ClientTO;

public class ProjCrewGetReq extends ClientTO {

    private static final long serialVersionUID = -6671175298681215590L;
    private Long projId;

    private List<Long> projIds;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

}
