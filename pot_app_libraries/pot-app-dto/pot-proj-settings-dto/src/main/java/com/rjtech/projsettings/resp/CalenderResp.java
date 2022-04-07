package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.CalenderTO;

public class CalenderResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6898377454443702835L;
    List<CalenderTO> calenderTOs = null;

    public CalenderResp() {
        calenderTOs = new ArrayList<CalenderTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<CalenderTO> getCalenderTOs() {
        return calenderTOs;
    }

    public void setCalenderTOs(List<CalenderTO> calenderTOs) {
        this.calenderTOs = calenderTOs;
    }

}
