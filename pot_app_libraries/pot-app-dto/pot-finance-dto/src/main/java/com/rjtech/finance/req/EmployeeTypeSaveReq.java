package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.EmployeeWiseCycleTO;

public class EmployeeTypeSaveReq extends ClientTO {

    private static final long serialVersionUID = 4476996781997116196L;
    private List<EmployeeWiseCycleTO> employeeWiseCycleTOs = new ArrayList<EmployeeWiseCycleTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EmployeeWiseCycleTO> getEmployeeWiseCycleTOs() {
        return employeeWiseCycleTOs;
    }

    public void setEmployeeWiseCycleTOs(List<EmployeeWiseCycleTO> employeeWiseCycleTOs) {
        this.employeeWiseCycleTOs = employeeWiseCycleTOs;
    }

}
