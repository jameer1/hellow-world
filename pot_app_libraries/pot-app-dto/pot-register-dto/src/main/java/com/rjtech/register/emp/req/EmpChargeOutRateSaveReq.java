package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.register.emp.dto.EmpChargeOutRateTO;

public class EmpChargeOutRateSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1013354065600238676L;

    private List<EmpChargeOutRateTO> empChargeOutRateTOs = new ArrayList<EmpChargeOutRateTO>();
    private Long empId;

    public List<EmpChargeOutRateTO> getEmpChargeOutRateTOs() {
        return empChargeOutRateTOs;
    }

    public void setEmpChargeOutRateTOs(List<EmpChargeOutRateTO> empChargeOutRateTOs) {
        this.empChargeOutRateTOs = empChargeOutRateTOs;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

}
