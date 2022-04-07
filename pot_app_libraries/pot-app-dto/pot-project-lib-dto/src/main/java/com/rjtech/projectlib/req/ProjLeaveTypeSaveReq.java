package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjLeaveTypeTO;

public class ProjLeaveTypeSaveReq extends ProjectTO {

    private static final long serialVersionUID = 295035084219135519L;
    private List<ProjLeaveTypeTO> projLeaveTypeTos = new ArrayList<ProjLeaveTypeTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjLeaveTypeTO> getProjLeaveTypeTos() {
        return projLeaveTypeTos;
    }

    public void setProjLeaveTypeTos(List<ProjLeaveTypeTO> projLeaveTypeTos) {
        this.projLeaveTypeTos = projLeaveTypeTos;
    }

}
