package com.rjtech.projsettings.req;

import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class DefaultSettingsReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 9142567387344856907L;

    private List<Long> projIds;

    private Long projId;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

}
