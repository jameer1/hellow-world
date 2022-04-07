package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpPaybleRateTO;

public class EmpPayRatesSaveReq extends ProjectTO {

    private static final long serialVersionUID = -2657442765498816832L;
    private List<EmpPaybleRateTO> empPaybleRateTOs = new ArrayList<EmpPaybleRateTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long empId;

    public List<EmpPaybleRateTO> getEmpPaybleRateTOs() {
        return empPaybleRateTOs;
    }

    public void setEmpPaybleRateTOs(List<EmpPaybleRateTO> empPaybleRateTOs) {
        this.empPaybleRateTOs = empPaybleRateTOs;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

}
