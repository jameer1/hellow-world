package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpLeaveReqApprTO;

public class EmpLeaveReqApprSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -7424284306237181086L;

    private List<EmpLeaveReqApprTO> empLeaveReqApprTOs = new ArrayList<EmpLeaveReqApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private EmpLeaveReq empLeaveReq = new EmpLeaveReq();

    public List<EmpLeaveReqApprTO> getEmpLeaveReqApprTOs() {
        return empLeaveReqApprTOs;
    }

    public void setEmpLeaveReqApprTOs(List<EmpLeaveReqApprTO> empLeaveReqApprTOs) {
        this.empLeaveReqApprTOs = empLeaveReqApprTOs;
    }

    public EmpLeaveReq getEmpLeaveReq() {
        return empLeaveReq;
    }

    public void setEmpLeaveReq(EmpLeaveReq empLeaveReq) {
        this.empLeaveReq = empLeaveReq;
    }

}
