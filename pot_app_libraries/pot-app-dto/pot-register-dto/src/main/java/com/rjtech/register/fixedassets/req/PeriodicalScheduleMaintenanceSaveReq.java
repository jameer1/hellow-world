package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.PeriodicalScheduleMaintenanceDtlTO;

public class PeriodicalScheduleMaintenanceSaveReq extends PeriodicalScheduleMaintenanceDtlTO {
    private static final long serialVersionUID = 2740448397989044793L;
    private String folderCategory;
    private Long userId;
    private List<PeriodicalScheduleMaintenanceDtlTO> periodicalScheduleMaintenanceDtlTO = new ArrayList<PeriodicalScheduleMaintenanceDtlTO>();

    public List<PeriodicalScheduleMaintenanceDtlTO> getPeriodicalScheduleMaintenanceDtlTO() {
        return periodicalScheduleMaintenanceDtlTO;
    }

    public void setPeriodicalScheduleMaintenanceDtlTO(
            List<PeriodicalScheduleMaintenanceDtlTO> periodicalScheduleMaintenanceDtlTO) {
        this.periodicalScheduleMaintenanceDtlTO = periodicalScheduleMaintenanceDtlTO;
    }

    public void setFolderCategory( String folderCategory ) {
    	this.folderCategory = folderCategory;
    }

    public String getFolderCategory() {
    	return folderCategory;
    }
    
    public void setUserId( Long userId ) {
    	this.userId = userId;
    }

    public Long getUserId() {
    	return userId;
    }
}
