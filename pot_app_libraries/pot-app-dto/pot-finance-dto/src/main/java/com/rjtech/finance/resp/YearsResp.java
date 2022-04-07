package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.YearsTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class YearsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 629147984088618486L;
    private List<YearsTO> yearsTOs = null;

    public YearsResp() {
        yearsTOs = new ArrayList<YearsTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<YearsTO> getYearsTOs() {
        return yearsTOs;
    }

    public void setYearsTOs(List<YearsTO> yearsTOs) {
        this.yearsTOs = yearsTOs;
    }

}
