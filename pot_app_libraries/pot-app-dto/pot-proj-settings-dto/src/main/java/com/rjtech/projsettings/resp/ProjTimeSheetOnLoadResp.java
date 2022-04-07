package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;

public class ProjTimeSheetOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<String> weekDays = null;
    private ProjTimeSheetResp projTimeSheetResp = null;

    public ProjTimeSheetOnLoadResp() {
        weekDays = new ArrayList<String>();
        projTimeSheetResp = new ProjTimeSheetResp();
    }

    public ProjTimeSheetResp getProjTimeSheetResp() {
        return projTimeSheetResp;
    }

    public void setProjTimeSheetResp(ProjTimeSheetResp projTimeSheetResp) {
        this.projTimeSheetResp = projTimeSheetResp;
    }

    public List<String> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<String> weekDays) {
        this.weekDays = weekDays;
    }

}
