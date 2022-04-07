package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProgressTO;

public class ProjProgressResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1409029955269495860L;

    private List<ProjProgressTO> projProgressTOs = null;
    private List<LabelKeyTO> calNonWorkingDays = null;
    private Map<Long, List<LabelKeyTO>> actualWorkingDayMap = null;
    private Map<Long, LabelKeyTO> sowItemMap = null;

    public ProjProgressResp() {
        projProgressTOs = new ArrayList<ProjProgressTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        calNonWorkingDays = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        actualWorkingDayMap = new HashMap<Long, List<LabelKeyTO>>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        sowItemMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjProgressTO> getProjProgressTOs() {
        return projProgressTOs;
    }

    public void setProjProgressTOs(List<ProjProgressTO> projProgressTOs) {
        this.projProgressTOs = projProgressTOs;
    }

    public List<LabelKeyTO> getCalNonWorkingDays() {
        return calNonWorkingDays;
    }

    public void setCalNonWorkingDays(List<LabelKeyTO> calNonWorkingDays) {
        this.calNonWorkingDays = calNonWorkingDays;
    }

    public Map<Long, List<LabelKeyTO>> getActualWorkingDayMap() {
        return actualWorkingDayMap;
    }

    public void setActualWorkingDayMap(Map<Long, List<LabelKeyTO>> actualWorkingDayMap) {
        this.actualWorkingDayMap = actualWorkingDayMap;
    }

    public Map<Long, LabelKeyTO> getSowItemMap() {
        return sowItemMap;
    }

    public void setSowItemMap(Map<Long, LabelKeyTO> sowItemMap) {
        this.sowItemMap = sowItemMap;
    }

}
