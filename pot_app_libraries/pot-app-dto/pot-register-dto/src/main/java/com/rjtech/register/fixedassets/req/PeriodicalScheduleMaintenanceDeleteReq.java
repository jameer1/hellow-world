package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.PeriodicalScheduleMaintenanceDtlTO;

public class PeriodicalScheduleMaintenanceDeleteReq extends PeriodicalScheduleMaintenanceDtlTO {

    private static final long serialVersionUID = -4543819922890869538L;
    List<Long> periodicalScheduleMaintenanceIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getPeriodicalScheduleMaintenanceIds() {
        return periodicalScheduleMaintenanceIds;
    }

    public void setPeriodicalScheduleMaintenanceIds(List<Long> periodicalScheduleMaintenanceIds) {
        this.periodicalScheduleMaintenanceIds = periodicalScheduleMaintenanceIds;
    }

}
