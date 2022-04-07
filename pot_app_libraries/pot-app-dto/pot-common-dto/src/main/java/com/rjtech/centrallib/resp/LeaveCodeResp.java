package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.LeaveTypeTO;
import com.rjtech.common.resp.AppResp;


public class LeaveCodeResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<LeaveTypeTO> leaveTypeTOs = null;

    public LeaveCodeResp() {
        leaveTypeTOs = new ArrayList<LeaveTypeTO>(5);
    }

    public List<LeaveTypeTO> getLeaveTypeTOs() {
        return leaveTypeTOs;
    }

}
