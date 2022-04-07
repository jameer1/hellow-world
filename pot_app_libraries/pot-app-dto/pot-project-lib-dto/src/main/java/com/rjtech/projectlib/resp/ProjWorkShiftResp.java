package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjWorkShiftTO;

public class ProjWorkShiftResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjWorkShiftTO> projWorkShiftTOs = null;

    public ProjWorkShiftResp() {
        projWorkShiftTOs = new ArrayList<ProjWorkShiftTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjWorkShiftTO> getProjWorkShiftTOs() {
        return projWorkShiftTOs;
    }

}
