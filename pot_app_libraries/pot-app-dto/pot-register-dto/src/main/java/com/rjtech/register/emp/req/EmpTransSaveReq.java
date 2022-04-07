package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpTransferReqApprTO;

public class EmpTransSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 7372859262026692461L;

    private List<EmpTransferReqApprTO> empReqTransTOs = new ArrayList<EmpTransferReqApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private EmpTransReq empTransReq = new EmpTransReq();

    public List<EmpTransferReqApprTO> getEmpReqTransTOs() {
        return empReqTransTOs;
    }

    public void setEmpReqTransTOs(List<EmpTransferReqApprTO> empReqTransTOs) {
        this.empReqTransTOs = empReqTransTOs;
    }

    public EmpTransReq getEmpTransReq() {
        return empTransReq;
    }

    public void setEmpTransReq(EmpTransReq empTransReq) {
        this.empTransReq = empTransReq;
    }

}
