package com.rjtech.projectlib.req;

import com.rjtech.common.dto.ProjectTO;

import java.util.List;

public class ProjPMCMItemGetReq extends ProjectTO {
    private static final long serialVersionUID = 1L;
    private Long pmId;
    private String projStatusDate;
    private String pmFromStatusDate;
    private List<Long> projIds;
    private List<Long> pmIds;

    public Long getPmId() {
        return pmId;
    }

    public void setPmId(Long pmId) {
        this.pmId = pmId;
    }

    public String getProjStatusDate() {
        return projStatusDate;
    }

    public void setProjStatusDate(String projStatusDate) {
        this.projStatusDate = projStatusDate;
    }

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public String getPmFromStatusDate() {
        return pmFromStatusDate;
    }

    public void setPmFromStatusDate(String pmFromStatusDate) {
        this.pmFromStatusDate = pmFromStatusDate;
    }
    
    public void setPmIds(List<Long> pmIds) {
    	this.pmIds = pmIds;
    }
    
    public List<Long> getPmIds() {
        return pmIds;
    }

}
