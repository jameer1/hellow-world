package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.projsettings.dto.ProjLeaveRequestTO;

public class ProjLeaveRequestResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 7619721289637859608L;
    private List<ProjLeaveRequestTO> projLeaveRequestTOs = new ArrayList<ProjLeaveRequestTO>();

    public List<ProjLeaveRequestTO> getProjLeaveRequestTOs() {
        return projLeaveRequestTOs;
    }

    public void setProjLeaveRequestTOs(List<ProjLeaveRequestTO> projLeaveRequestTOs) {
        this.projLeaveRequestTOs = projLeaveRequestTOs;
    }

}
