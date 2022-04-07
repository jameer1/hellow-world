package com.rjtech.calendar.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.calendar.dto.CalDaysTO;
import com.rjtech.calendar.dto.CalRegularDaysTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class CalDaysResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6898377454443702835L;
    private Map<Integer, CalDaysTO> calenderDaysMap = null;
    private CalRegularDaysTO calenderRegularDaysTO = null;

    public CalDaysResp() {
        calenderRegularDaysTO = new CalRegularDaysTO();
        calenderDaysMap = new HashMap<Integer, CalDaysTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public CalRegularDaysTO getCalenderRegularDaysTO() {
        return calenderRegularDaysTO;
    }

    public void setCalenderRegularDaysTO(CalRegularDaysTO calenderRegularDaysTO) {
        this.calenderRegularDaysTO = calenderRegularDaysTO;
    }

    public Map<Integer, CalDaysTO> getCalenderDaysMap() {
        return calenderDaysMap;
    }

    public void setCalenderDaysMap(Map<Integer, CalDaysTO> calenderDaysMap) {
        this.calenderDaysMap = calenderDaysMap;
    }

}
