package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.EmployeeWiseCycleTO;

public class EmpPayrollTypeResp extends AppResp {

    private static final long serialVersionUID = -3632645402017523300L;
    private List<EmployeeWiseCycleTO> employeeWiseCycleTOs = null;
    private Map<Long, LabelKeyTO> projEmpClassMap = null;
    private Map<Long, LabelKeyTO> procureCategoryMap = null;
    private Map<Long, LabelKeyTO> payPeriodCycleMap = null;

    public EmpPayrollTypeResp() {
        employeeWiseCycleTOs = new ArrayList<EmployeeWiseCycleTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projEmpClassMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        procureCategoryMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        payPeriodCycleMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<EmployeeWiseCycleTO> getEmployeeWiseCycleTOs() {
        return employeeWiseCycleTOs;
    }

    public void setEmployeeWiseCycleTOs(List<EmployeeWiseCycleTO> employeeWiseCycleTOs) {
        this.employeeWiseCycleTOs = employeeWiseCycleTOs;
    }

    public Map<Long, LabelKeyTO> getProjEmpClassMap() {
        return projEmpClassMap;
    }

    public void setProjEmpClassMap(Map<Long, LabelKeyTO> projEmpClassMap) {
        this.projEmpClassMap = projEmpClassMap;
    }

    public Map<Long, LabelKeyTO> getProcureCategoryMap() {
        return procureCategoryMap;
    }

    public void setProcureCategoryMap(Map<Long, LabelKeyTO> procureCategoryMap) {
        this.procureCategoryMap = procureCategoryMap;
    }

    public Map<Long, LabelKeyTO> getPayPeriodCycleMap() {
        return payPeriodCycleMap;
    }

    public void setPayPeriodCycleMap(Map<Long, LabelKeyTO> payPeriodCycleMap) {
        this.payPeriodCycleMap = payPeriodCycleMap;
    }

}
