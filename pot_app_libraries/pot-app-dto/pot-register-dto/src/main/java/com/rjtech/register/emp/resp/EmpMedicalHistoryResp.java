package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpMedicalHistoryTO;

public class EmpMedicalHistoryResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -4618349203248631145L;
    private List<EmpMedicalHistoryTO> empMedicalHistoryTOs = new ArrayList<EmpMedicalHistoryTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EmpMedicalHistoryTO> getEmpMedicalHistoryTOs() {
        return empMedicalHistoryTOs;
    }

    public void setEmpMedicalHistoryTOs(List<EmpMedicalHistoryTO> empMedicalHistoryTOs) {
        this.empMedicalHistoryTOs = empMedicalHistoryTOs;
    }

}
