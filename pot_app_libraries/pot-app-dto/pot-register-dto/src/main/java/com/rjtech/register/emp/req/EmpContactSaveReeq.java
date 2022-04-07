package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpContactDtlTO;

public class EmpContactSaveReeq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -3447658539095485856L;

    private List<EmpContactDtlTO> empContactDtlTOs = new ArrayList<EmpContactDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long empId;

    public List<EmpContactDtlTO> getEmpContactDtlTOs() {
        return empContactDtlTOs;
    }

    public void setEmpContactDtlTOs(List<EmpContactDtlTO> empContactDtlTOs) {
        this.empContactDtlTOs = empContactDtlTOs;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

}