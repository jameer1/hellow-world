package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjLeaveTypeTO;

public class ProjLeaveTypeResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjLeaveTypeTO> projLeaveTypeTOs = null;

    public ProjLeaveTypeResp() {
        projLeaveTypeTOs = new ArrayList<ProjLeaveTypeTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjLeaveTypeTO> getProjLeaveTypeTOs() {
        return projLeaveTypeTOs;
    }

}
