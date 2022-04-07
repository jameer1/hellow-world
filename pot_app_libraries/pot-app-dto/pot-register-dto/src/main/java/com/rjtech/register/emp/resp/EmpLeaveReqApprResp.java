package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.LeaveTypeTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpLeaveReqApprTO;

public class EmpLeaveReqApprResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -7424284306237181086L;

    private List<EmpLeaveReqApprTO> empLeaveReqApprTOs = new ArrayList<EmpLeaveReqApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Map<String, String> leaveCodeMap = new HashMap();

    public List<EmpLeaveReqApprTO> getEmpLeaveReqApprTOs() {
        return empLeaveReqApprTOs;
    }

    public void setEmpLeaveReqApprTOs(List<EmpLeaveReqApprTO> empLeaveReqApprTOs) {
        this.empLeaveReqApprTOs = empLeaveReqApprTOs;
    }

    public Map<String, String> getLeaveCodeMap() {
        return leaveCodeMap;
    }

    public void setLeaveCodeMap(Map<String, String> leaveCodeMap) {
        this.leaveCodeMap = leaveCodeMap;
    }

}
