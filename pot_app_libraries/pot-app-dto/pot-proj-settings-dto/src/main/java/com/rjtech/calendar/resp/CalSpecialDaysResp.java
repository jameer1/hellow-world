package com.rjtech.calendar.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.calendar.dto.CalSpecialDaysTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class CalSpecialDaysResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6898377454443702835L;
    private List<CalSpecialDaysTO> calenderSpecialDaysTOs = null;

    public CalSpecialDaysResp() {
        calenderSpecialDaysTOs = new ArrayList<CalSpecialDaysTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<CalSpecialDaysTO> getCalenderSpecialDaysTOs() {
        return calenderSpecialDaysTOs;
    }

    public void setCalenderSpecialDaysTOs(List<CalSpecialDaysTO> calenderSpecialDaysTOs) {
        this.calenderSpecialDaysTOs = calenderSpecialDaysTOs;
    }

}
