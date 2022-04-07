package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpPayDeductionTO;

public class EmpPayDeductionSaveReq extends ProjectTO {

    private static final long serialVersionUID = -2657442765498816832L;
    private List<EmpPayDeductionTO> empPayDeductionTOs = new ArrayList<EmpPayDeductionTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long empId;

    public List<EmpPayDeductionTO> getEmpPayDeductionTOs() {
        return empPayDeductionTOs;
    }

    public void setEmpPayDeductionTOs(List<EmpPayDeductionTO> empPayDeductionTOs) {
        this.empPayDeductionTOs = empPayDeductionTOs;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

}
