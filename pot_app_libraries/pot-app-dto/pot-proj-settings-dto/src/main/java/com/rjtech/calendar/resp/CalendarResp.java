package com.rjtech.calendar.resp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.calendar.dto.CalTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class CalendarResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6898377454443702835L;
    private List<CalTO> calenderTOs = null;
    private Date currentDate = new Date();

    public CalendarResp() {
        calenderTOs = new ArrayList<CalTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<CalTO> getCalenderTOs() {
        return calenderTOs;
    }

    public void setCalenderTOs(List<CalTO> calenderTOs) {
        this.calenderTOs = calenderTOs;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

}
