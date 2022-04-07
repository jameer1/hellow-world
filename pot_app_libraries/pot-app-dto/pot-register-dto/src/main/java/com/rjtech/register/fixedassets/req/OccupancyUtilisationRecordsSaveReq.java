package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.OccupancyUtilisationRecordsDtlTO;

public class OccupancyUtilisationRecordsSaveReq extends OccupancyUtilisationRecordsGetReq {
    private static final long serialVersionUID = 1L;

    private List<OccupancyUtilisationRecordsDtlTO> occupancyUtilisationRecordsDtlTOs = new ArrayList<OccupancyUtilisationRecordsDtlTO>();

    public List<OccupancyUtilisationRecordsDtlTO> getOccupancyUtilisationRecordsDtlTOs() {
        return occupancyUtilisationRecordsDtlTOs;
    }

    public void setOccupancyUtilisationRecordsDtlTOs(
            List<OccupancyUtilisationRecordsDtlTO> occupancyUtilisationRecordsDtlTOs) {
        this.occupancyUtilisationRecordsDtlTOs = occupancyUtilisationRecordsDtlTOs;
    }

}
