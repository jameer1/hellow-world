package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;

public class EmpRegResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -9222635953451720346L;

    private List<EmpRegisterDtlTO> empRegisterDtlTOs = null;

    public EmpRegResp() {
        empRegisterDtlTOs = new ArrayList<EmpRegisterDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<EmpRegisterDtlTO> getEmpRegisterDtlTOs() {
        return empRegisterDtlTOs;
    }

    public void setEmpRegisterDtlTOs(List<EmpRegisterDtlTO> empRegisterDtlTOs) {
        this.empRegisterDtlTOs = empRegisterDtlTOs;
    }

}
