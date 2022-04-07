package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.AttendancePlantStatusTO;
import com.rjtech.common.resp.AppResp;


public class AttendancePlantStatusResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<AttendancePlantStatusTO> attendancePlantStatusTOs = null;

    public AttendancePlantStatusResp() {
        attendancePlantStatusTOs = new ArrayList<AttendancePlantStatusTO>(5);
    }

    public List<AttendancePlantStatusTO> getAttendancePlantStatusTOs() {
        return attendancePlantStatusTOs;
    }

    public void setAttendancePlantStatusTOs(List<AttendancePlantStatusTO> attendancePlantStatusTOs) {
        this.attendancePlantStatusTOs = attendancePlantStatusTOs;
    }

}
