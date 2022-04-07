package com.rjtech.projschedule.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.calendar.dto.CalRegularDaysTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOWItemTO;
import com.rjtech.projectlib.dto.TotalActualTO;
import com.rjtech.projsettings.dto.ProjectReportsTO;

public class ProjScheduleSOWItemResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSOWItemTO> projSOWItemTOs = null;
    private List<String> calNonWorkingDays = null;
    private Map<Long, TotalActualTO> actualWorkingDayMap = null;
    private Map<Long, LabelKeyTO> sowItemMap = null;
    private List<LabelKeyTO> dateWiseActualQuantity = null;
    private CalRegularDaysTO regularHolidays = null;
    private ProjectReportsTO projReportsTo = null;
    private List<String> calSplWorkingDays = null;

    public ProjScheduleSOWItemResp() {
        projSOWItemTOs = new ArrayList<ProjSOWItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        calNonWorkingDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        actualWorkingDayMap = new HashMap<Long, TotalActualTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        sowItemMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        dateWiseActualQuantity = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        regularHolidays = new CalRegularDaysTO();
        projReportsTo = new ProjectReportsTO();
        calSplWorkingDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    }

    public List<String> getCalSplWorkingDays() {
        return calSplWorkingDays;
    }

    public void setCalSplWorkingDays(List<String> calSplWorkingDays) {
        this.calSplWorkingDays = calSplWorkingDays;
    }

    public List<ProjSOWItemTO> getProjSOWItemTOs() {
        return projSOWItemTOs;
    }

    public void setProjSOWItemTOs(List<ProjSOWItemTO> projSOWItemTOs) {
        this.projSOWItemTOs = projSOWItemTOs;
    }

    public List<String> getCalNonWorkingDays() {
        return calNonWorkingDays;
    }

    public void setCalNonWorkingDays(List<String> calNonWorkingDays) {
        this.calNonWorkingDays = calNonWorkingDays;
    }

    public Map<Long, TotalActualTO> getActualWorkingDayMap() {
        return actualWorkingDayMap;
    }

    public void setActualWorkingDayMap(Map<Long, TotalActualTO> actualWorkingDayMap) {
        this.actualWorkingDayMap = actualWorkingDayMap;
    }

    public Map<Long, LabelKeyTO> getSowItemMap() {
        return sowItemMap;
    }

    public void setSowItemMap(Map<Long, LabelKeyTO> sowItemMap) {
        this.sowItemMap = sowItemMap;
    }

    public List<LabelKeyTO> getDateWiseActualQuantity() {
        return dateWiseActualQuantity;
    }

    public void setDateWiseActualQuantity(List<LabelKeyTO> dateWiseActualQuantity) {
        this.dateWiseActualQuantity = dateWiseActualQuantity;
    }

    public CalRegularDaysTO getRegularHolidays() {
        return regularHolidays;
    }

    public void setRegularHolidays(CalRegularDaysTO regularHolidays) {
        this.regularHolidays = regularHolidays;
    }

    public ProjectReportsTO getProjReportsTo() {
        return projReportsTo;
    }

    public void setProjReportsTo(ProjectReportsTO projReportsTo) {
        this.projReportsTo = projReportsTo;
    }

}
