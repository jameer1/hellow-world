package com.rjtech.timemanagement.attendance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.attendance.dto.EmpAttendanceMstrTO;

public class EmpAttendanceSheetResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<EmpAttendanceMstrTO> empAttendanceMstrTOs = null;

    public EmpAttendanceSheetResp() {
        empAttendanceMstrTOs = new ArrayList<EmpAttendanceMstrTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<EmpAttendanceMstrTO> getEmpAttendanceMstrTOs() {
        return empAttendanceMstrTOs;
    }

    public void setEmpAttendanceMstrTOs(List<EmpAttendanceMstrTO> empAttendanceMstrTOs) {
        this.empAttendanceMstrTOs = empAttendanceMstrTOs;
    }

}
