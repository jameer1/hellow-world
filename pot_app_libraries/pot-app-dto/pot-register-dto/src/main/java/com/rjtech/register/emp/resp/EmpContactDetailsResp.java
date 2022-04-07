package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpContactDtlTO;

public class EmpContactDetailsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -2529955399851129744L;

    private List<EmpContactDtlTO> empContactDtlTOs = new ArrayList<EmpContactDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EmpContactDtlTO> getEmpContactDtlTOs() {
        return empContactDtlTOs;
    }

    public void setEmpContactDtlTOs(List<EmpContactDtlTO> empContactDtlTOs) {
        this.empContactDtlTOs = empContactDtlTOs;
    }

}
