package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.EmployeeWageRateTO;
import com.rjtech.common.resp.AppResp;


public class EmpWageResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<EmployeeWageRateTO> employeeWageRateTOs = null;

    public EmpWageResp() {
        employeeWageRateTOs = new ArrayList<EmployeeWageRateTO>(5);
    }

    public List<EmployeeWageRateTO> getEmployeeWageRateTOs() {
        return employeeWageRateTOs;
    }

}
