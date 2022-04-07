package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.OccupancyUtilisationRecordsDtlTO;

public class OccupancyUtilisationRecordsResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<OccupancyUtilisationRecordsDtlTO> occupancyUtilisationRecordsDtlTOs = new ArrayList<OccupancyUtilisationRecordsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<OccupancyUtilisationRecordsDtlTO> getOccupancyUtilisationRecordsDtlTOs() {
        return occupancyUtilisationRecordsDtlTOs;
    }

    public void setOccupancyUtilisationRecordsDtlTOs(
            List<OccupancyUtilisationRecordsDtlTO> occupancyUtilisationRecordsDtlTOs) {
        this.occupancyUtilisationRecordsDtlTOs = occupancyUtilisationRecordsDtlTOs;
    }

}
