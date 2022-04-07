package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.PeriodicalScheduleMaintenanceDtlTO;

public class PeriodicalScheduleMaintenanceResp extends AppResp {
    private static final long serialVersionUID = 1L;
    private List<PeriodicalScheduleMaintenanceDtlTO> periodicalScheduleMaintenanceDtlTOs = new ArrayList<PeriodicalScheduleMaintenanceDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PeriodicalScheduleMaintenanceDtlTO> getPeriodicalScheduleMaintenanceDtlTOs() {
        return periodicalScheduleMaintenanceDtlTOs;
    }

    public void setPeriodicalScheduleMaintenanceDtlTOs(
            List<PeriodicalScheduleMaintenanceDtlTO> periodicalScheduleMaintenanceDtlTOs) {
        this.periodicalScheduleMaintenanceDtlTOs = periodicalScheduleMaintenanceDtlTOs;
    }

}
