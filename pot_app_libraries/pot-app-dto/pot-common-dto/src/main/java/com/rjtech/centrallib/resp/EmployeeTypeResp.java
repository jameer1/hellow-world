package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.EmployeeTypeRecordTo;
import com.rjtech.common.resp.AppResp;

public class EmployeeTypeResp extends AppResp {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<EmployeeTypeRecordTo> employeeTypeRecordTos = new ArrayList<>();

    public List<EmployeeTypeRecordTo> getEmployeeTypeRecordTos() {
        return employeeTypeRecordTos;
    }

    public void setEmployeeTypeRecordTos(List<EmployeeTypeRecordTo> employeeTypeRecordTos) {
        this.employeeTypeRecordTos = employeeTypeRecordTos;
    }
}
