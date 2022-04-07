package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.EmployeeWageRateTO;
import com.rjtech.common.dto.ClientTO;


public class EmpWageSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<EmployeeWageRateTO> employeeWageRateTOs = new ArrayList<EmployeeWageRateTO>(
            5);

    public List<EmployeeWageRateTO> getEmployeeWageRateTOs() {
        return employeeWageRateTOs;
    }

    public void setEmployeeWageRateTOs(List<EmployeeWageRateTO> employeeWageRateTOs) {
        this.employeeWageRateTOs = employeeWageRateTOs;
    }

}
