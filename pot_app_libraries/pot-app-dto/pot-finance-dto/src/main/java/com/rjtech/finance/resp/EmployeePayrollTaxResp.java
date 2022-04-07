package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.finance.dto.EmployeePayRollTaxTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class EmployeePayrollTaxResp extends AppResp {

    private static final long serialVersionUID = 6817955807670627633L;
    private List<EmployeePayRollTaxTO> employeePayRollTaxTOs = null;

    public EmployeePayrollTaxResp() {
        employeePayRollTaxTOs = new ArrayList<EmployeePayRollTaxTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<EmployeePayRollTaxTO> getEmployeePayRollTaxTOs() {
        return employeePayRollTaxTOs;
    }

    public void setEmployeePayRollTaxTOs(List<EmployeePayRollTaxTO> employeePayRollTaxTOs) {
        this.employeePayRollTaxTOs = employeePayRollTaxTOs;
    }

}
