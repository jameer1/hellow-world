package com.rjtech.timemanagement.attendance.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.attendance.dto.EmpAttendanceTO;

public class EmpAttendanceResp extends AppResp {

    private static final long serialVersionUID = 6526217036270683215L;

    private List<EmpAttendanceTO> empAttendenceTOs = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private LabelKeyTO labelKeyTO = new LabelKeyTO();
    private Map<String, Boolean> empDemobilizationDateMap = new HashMap<>();

    public List<EmpAttendanceTO> getEmpAttendenceTOs() {
        return empAttendenceTOs;
    }

    public void setEmpAttendenceTOs(List<EmpAttendanceTO> empAttendenceTOs) {
        this.empAttendenceTOs = empAttendenceTOs;
    }

    public LabelKeyTO getLabelKeyTO() {
        return labelKeyTO;
    }

    public void setLabelKeyTO(LabelKeyTO labelKeyTO) {
        this.labelKeyTO = labelKeyTO;
    }

    public Map<String, Boolean> getEmpDemobilizationDateMap() {
        return empDemobilizationDateMap;
    }

    public void setEmpDemobilizationDateMap(Map<String, Boolean> empDemobilizationDateMap) {
        this.empDemobilizationDateMap = empDemobilizationDateMap;
    }

}
