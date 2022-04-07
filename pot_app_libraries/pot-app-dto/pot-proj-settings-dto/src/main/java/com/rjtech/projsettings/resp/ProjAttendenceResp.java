package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjAttendenceTO;

public class ProjAttendenceResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 7619721289637859608L;
    private List<ProjAttendenceTO> projAttendenceTOs = null;

    public ProjAttendenceResp() {
        projAttendenceTOs = new ArrayList<ProjAttendenceTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjAttendenceTO> getProjAttendenceTOs() {
        return projAttendenceTOs;
    }

    public void setProjAttendenceTOs(List<ProjAttendenceTO> projAttendenceTOs) {
        this.projAttendenceTOs = projAttendenceTOs;
    }

}
