package com.rjtech.calendar.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.calendar.dto.CalTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class CalSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5270095009496332835L;
    private List<CalTO> calenderTOs = new ArrayList<CalTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long id;
    private String calType;

    public List<CalTO> getCalenderTOs() {
        return calenderTOs;
    }

    public void setCalenderTOs(List<CalTO> calenderTOs) {
        this.calenderTOs = calenderTOs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalType() {
        return calType;
    }

    public void setCalType(String calType) {
        this.calType = calType;
    }

}
