package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class EmpBankAccDeactivateReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -4543819922890869537L;

    List<Long> empBankAccountIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getEmpBankAccountIds() {
        return empBankAccountIds;
    }

    public void setEmpBankAccountIds(List<Long> empBankAccountIds) {
        this.empBankAccountIds = empBankAccountIds;
    }

}
