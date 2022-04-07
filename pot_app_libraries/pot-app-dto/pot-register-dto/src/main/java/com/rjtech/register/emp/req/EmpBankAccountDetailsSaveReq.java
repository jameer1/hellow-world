package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpBankAccountDtlTO;

public class EmpBankAccountDetailsSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 7652237979270340626L;

    private List<EmpBankAccountDtlTO> empBankAccountDtlTOs = new ArrayList<EmpBankAccountDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EmpBankAccountDtlTO> getEmpBankAccountDtlTOs() {
        return empBankAccountDtlTOs;
    }

    public void setEmpBankAccountDtlTOs(List<EmpBankAccountDtlTO> empBankAccountDtlTOs) {
        this.empBankAccountDtlTOs = empBankAccountDtlTOs;
    }

}
