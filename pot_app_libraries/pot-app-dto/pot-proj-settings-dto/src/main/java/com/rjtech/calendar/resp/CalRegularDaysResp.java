package com.rjtech.calendar.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.calendar.dto.CalRegularDaysTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class CalRegularDaysResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6898377454443702835L;
    private List<CalRegularDaysTO> calenderRegularDaysTOs = null;

    public CalRegularDaysResp() {
        calenderRegularDaysTOs = new ArrayList<CalRegularDaysTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<CalRegularDaysTO> getCalenderRegularDaysTOs() {
        return calenderRegularDaysTOs;
    }

    public void setCalenderRegularDaysTOs(List<CalRegularDaysTO> calenderRegularDaysTOs) {
        this.calenderRegularDaysTOs = calenderRegularDaysTOs;
    }

}
