package com.rjtech.projectlib.req;

import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class ProjCostItemGetReq extends ProjectTO {

    private static final long serialVersionUID = -6671175298681215590L;
    private Long projCostId;

    private List<Long> projIds;
    private Integer displayActiveItems;

    public Long getProjCostId() {
        return projCostId;
    }

    public void setProjCostId(Long projCostId) {
        this.projCostId = projCostId;
    }

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public Integer getDisplayActiveItems() {
        return displayActiveItems;
    }

    public void setDisplayActiveItems( Integer displayActiveItems ) {
        this.displayActiveItems = displayActiveItems;
    }
    
}
