package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCrewTO;
import com.rjtech.projsettings.dto.ProjWorkDairyMstrTO;
import com.rjtech.user.dto.UserTO;

public class ProjWorkDairyOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<ProjCrewTO> projCrewTOs = null;
    private List<UserTO> userTOs = null;
    private List<ProjWorkDairyMstrTO> projWorkDairyMstrTOs = null;
    private List<LabelKeyTO> weekDays = null;

    public ProjWorkDairyOnLoadResp() {
        projCrewTOs = new ArrayList<ProjCrewTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        userTOs = new ArrayList<UserTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projWorkDairyMstrTOs = new ArrayList<ProjWorkDairyMstrTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        weekDays = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjCrewTO> getProjCrewTOs() {
        return projCrewTOs;
    }

    public void setProjCrewTOs(List<ProjCrewTO> projCrewTOs) {
        this.projCrewTOs = projCrewTOs;
    }

    public List<UserTO> getUserTOs() {
        return userTOs;
    }

    public void setUserTOs(List<UserTO> userTOs) {
        this.userTOs = userTOs;
    }

    public List<ProjWorkDairyMstrTO> getProjWorkDairyMstrTOs() {
        return projWorkDairyMstrTOs;
    }

    public void setProjWorkDairyMstrTOs(List<ProjWorkDairyMstrTO> projWorkDairyMstrTOs) {
        this.projWorkDairyMstrTOs = projWorkDairyMstrTOs;
    }

    public List<LabelKeyTO> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<LabelKeyTO> weekDays) {
        this.weekDays = weekDays;
    }

}
