package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpProvidentFundTO;

public class EmpProvidentFundSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 2611692951965775813L;

    private List<EmpProvidentFundTO> empProvidentFundTOs = new ArrayList<EmpProvidentFundTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long empId;

    public List<EmpProvidentFundTO> getEmpProvidentFundTOs() {
        return empProvidentFundTOs;
    }

    public void setEmpProvidentFundTOs(List<EmpProvidentFundTO> empProvidentFundTOs) {
        this.empProvidentFundTOs = empProvidentFundTOs;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

}
