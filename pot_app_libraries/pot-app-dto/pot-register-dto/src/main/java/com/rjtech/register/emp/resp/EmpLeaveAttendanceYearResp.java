package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpLeaveAttendanceYearTO;

public class EmpLeaveAttendanceYearResp extends AppResp {

    private static final long serialVersionUID = 3719597858807086400L;

    private List<EmpLeaveAttendanceYearTO> empLeaveAttendanceYearTOs = new ArrayList<EmpLeaveAttendanceYearTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Map<String, Map<String, LabelKeyTO>> empYearWiseLeaveCountMap = new HashMap<>();
    private Map<String, String> leaveCodeMap = new HashMap<String, String>();

    public List<EmpLeaveAttendanceYearTO> getEmpLeaveAttendanceYearTOs() {
        return empLeaveAttendanceYearTOs;
    }

    public void setEmpLeaveAttendanceYearTOs(List<EmpLeaveAttendanceYearTO> empLeaveAttendanceYearTOs) {
        this.empLeaveAttendanceYearTOs = empLeaveAttendanceYearTOs;
    }

    public Map<String, Map<String, LabelKeyTO>> getEmpYearWiseLeaveCountMap() {
        return empYearWiseLeaveCountMap;
    }

    public void setEmpYearWiseLeaveCountMap(Map<String, Map<String, LabelKeyTO>> empYearWiseLeaveCountMap) {
        this.empYearWiseLeaveCountMap = empYearWiseLeaveCountMap;
    }

    public Map<String, String> getLeaveCodeMap() {
        return leaveCodeMap;
    }

    public void setLeaveCodeMap(Map<String, String> leaveCodeMap) {
        this.leaveCodeMap = leaveCodeMap;
    }

}
