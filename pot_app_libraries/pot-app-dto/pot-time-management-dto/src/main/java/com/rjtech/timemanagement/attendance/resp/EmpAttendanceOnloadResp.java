package com.rjtech.timemanagement.attendance.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.timemanagement.attendance.dto.EmpAttendanceMstrTO;

public class EmpAttendanceOnloadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -9159056375797097278L;
    private EmpAttendanceMstrTO empAttendanceMstrTO = new EmpAttendanceMstrTO();
    private List<String> attendenceDays = new ArrayList<String>();
    private Map<String, Boolean> attendenceDayMap = new HashMap<String, Boolean>();
    private Map<String, LabelKeyTO> empLeaveTypeMap = new HashMap<String, LabelKeyTO>();
    private Map<Long, LabelKeyTO> empDetailsMap = new HashMap<Long, LabelKeyTO>();
    private List<LabelKeyTO> empRegLabelKeyTOs = new ArrayList<LabelKeyTO>();

    public Map<String, LabelKeyTO> getEmpLeaveTypeMap() {
        return empLeaveTypeMap;
    }

    public void setEmpLeaveTypeMap(Map<String, LabelKeyTO> empLeaveTypeMap) {
        this.empLeaveTypeMap = empLeaveTypeMap;
    }

    public EmpAttendanceMstrTO getEmpAttendanceMstrTO() {
        return empAttendanceMstrTO;
    }

    public void setEmpAttendanceMstrTO(EmpAttendanceMstrTO empAttendanceMstrTO) {
        this.empAttendanceMstrTO = empAttendanceMstrTO;
    }

    public List<String> getAttendenceDays() {
        return attendenceDays;
    }

    public void setAttendenceDays(List<String> attendenceDays) {
        this.attendenceDays = attendenceDays;
    }

    public Map<String, Boolean> getAttendenceDayMap() {
        return attendenceDayMap;
    }

    public void setAttendenceDayMap(Map<String, Boolean> attendenceDayMap) {
        this.attendenceDayMap = attendenceDayMap;
    }

    public Map<Long, LabelKeyTO> getEmpDetailsMap() {
        return empDetailsMap;
    }

    public void setEmpDetailsMap(Map<Long, LabelKeyTO> empDetailsMap) {
        this.empDetailsMap = empDetailsMap;
    }

    public List<LabelKeyTO> getEmpRegLabelKeyTOs() {
        return empRegLabelKeyTOs;
    }

    public void setEmpRegLabelKeyTOs(List<LabelKeyTO> empRegLabelKeyTOs) {
        this.empRegLabelKeyTOs = empRegLabelKeyTOs;
    }

}
