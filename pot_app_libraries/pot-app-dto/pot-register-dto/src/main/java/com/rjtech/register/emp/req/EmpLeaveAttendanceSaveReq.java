package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpLeaveAttendanceYearTO;

public class EmpLeaveAttendanceSaveReq extends ProjectTO {

    private static final long serialVersionUID = 3719597858807086400L;

    private List<EmpLeaveAttendanceYearTO> empLeaveAttendanceYearTOs = new ArrayList<EmpLeaveAttendanceYearTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long empRegId;

    public List<EmpLeaveAttendanceYearTO> getEmpLeaveAttendanceYearTOs() {
        return empLeaveAttendanceYearTOs;
    }

    public void setEmpLeaveAttendanceYearTOs(List<EmpLeaveAttendanceYearTO> empLeaveAttendanceYearTOs) {
        this.empLeaveAttendanceYearTOs = empLeaveAttendanceYearTOs;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

}
