package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpNokTO;

public class EmpNokSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -426112969035692364L;

    private List<EmpNokTO> empNokTOs = new ArrayList<EmpNokTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long empId;

    public List<EmpNokTO> getEmpNokTOs() {
        return empNokTOs;
    }

    public void setEmpNokTOs(List<EmpNokTO> empNokTOs) {
        this.empNokTOs = empNokTOs;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

}
