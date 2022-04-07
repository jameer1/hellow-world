package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;

public class EmpRegisterSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<EmpRegisterDtlTO> empRegisterTOs = new ArrayList<EmpRegisterDtlTO>();

    public List<EmpRegisterDtlTO> getEmpRegisterTOs() {
        return empRegisterTOs;
    }

    public void setEmpRegisterTOs(List<EmpRegisterDtlTO> empRegisterTOs) {
        this.empRegisterTOs = empRegisterTOs;
    }

}
