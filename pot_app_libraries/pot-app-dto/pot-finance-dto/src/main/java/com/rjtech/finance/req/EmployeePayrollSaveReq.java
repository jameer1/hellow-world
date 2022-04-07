package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.EmployeePayRollTaxTO;

public class EmployeePayrollSaveReq extends ClientTO {

    private static final long serialVersionUID = -8320250412628859884L;

    private List<EmployeePayRollTaxTO> employeePayRollTaxTOs = new ArrayList<EmployeePayRollTaxTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EmployeePayRollTaxTO> getEmployeePayRollTaxTOs() {
        return employeePayRollTaxTOs;
    }

    public void setEmployeePayRollTaxTOs(List<EmployeePayRollTaxTO> employeePayRollTaxTOs) {
        this.employeePayRollTaxTOs = employeePayRollTaxTOs;
    }

}
