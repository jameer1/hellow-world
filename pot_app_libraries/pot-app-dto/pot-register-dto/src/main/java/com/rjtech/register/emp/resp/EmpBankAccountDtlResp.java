package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpBankAccountDtlTO;

public class EmpBankAccountDtlResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -4170363069809971371L;
    private List<EmpBankAccountDtlTO> empBankAccountDtlTOs = new ArrayList<EmpBankAccountDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EmpBankAccountDtlTO> getEmpBankAccountDtlTOs() {
        return empBankAccountDtlTOs;
    }

    public void setEmpBankAccountDtlTOs(List<EmpBankAccountDtlTO> empBankAccountDtlTOs) {
        this.empBankAccountDtlTOs = empBankAccountDtlTOs;
    }

}
