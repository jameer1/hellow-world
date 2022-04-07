package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.OccupancyUtilisationRecordsDtlTO;

public class OccupancyUtilisationRecordsDeleteReq extends OccupancyUtilisationRecordsDtlTO {

    private static final long serialVersionUID = -7671175298681215590L;
    private List<Long> occupancyIds = new ArrayList<Long>();

    public List<Long> getOccupancyIds() {
        return occupancyIds;
    }

    public void setOccupancyIds(List<Long> occupancyIds) {
        this.occupancyIds = occupancyIds;
    }

}
