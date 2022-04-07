package com.rjtech.projschedule.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.calendar.dto.CalRegularDaysTO;
import com.rjtech.common.dto.CostActualHoursTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projschedule.dto.ProjScheduleCostCodeTO;
import com.rjtech.projsettings.dto.ProjectReportsTO;

public class ProjScheduleCostCodeResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6428639300155396451L;
    private List<ProjScheduleCostCodeTO> projScheduleCostCodeTOs = null;
    private List<String> calNonWorkingDays = null;
    private List<String> calSplWorkingDays = null;
    private Map<Long, CostActualHoursTO> actualWorkingDayMap = null;
    private Map<Long, LabelKeyTO> projCostItemMap = null;
    private List<LabelKeyTO> dateWiseActualQuantity = null;
    private CalRegularDaysTO regularHolidays = null;
    private ProjectReportsTO projReportsTo = null;

    public ProjScheduleCostCodeResp() {
        projScheduleCostCodeTOs = new ArrayList<ProjScheduleCostCodeTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        calNonWorkingDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        calSplWorkingDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        actualWorkingDayMap = new HashMap<Long, CostActualHoursTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projCostItemMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        dateWiseActualQuantity = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        regularHolidays = new CalRegularDaysTO();
        projReportsTo = new ProjectReportsTO();
    }

    public List<ProjScheduleCostCodeTO> getProjScheduleCostCodeTOs() {
        return projScheduleCostCodeTOs;
    }

    public void setProjScheduleCostCodeTOs(List<ProjScheduleCostCodeTO> projScheduleCostCodeTOs) {
        this.projScheduleCostCodeTOs = projScheduleCostCodeTOs;
    }

    public List<String> getCalNonWorkingDays() {
        return calNonWorkingDays;
    }

    public void setCalNonWorkingDays(List<String> calNonWorkingDays) {
        this.calNonWorkingDays = calNonWorkingDays;
    }

    public Map<Long, CostActualHoursTO> getActualWorkingDayMap() {
        return actualWorkingDayMap;
    }

    public void setActualWorkingDayMap(Map<Long, CostActualHoursTO> actualWorkingDayMap) {
        this.actualWorkingDayMap = actualWorkingDayMap;
    }

    public Map<Long, LabelKeyTO> getProjCostItemMap() {
        return projCostItemMap;
    }

    public void setProjCostItemMap(Map<Long, LabelKeyTO> projCostItemMap) {
        this.projCostItemMap = projCostItemMap;
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

    public List<String> getCalSplWorkingDays() {
        return calSplWorkingDays;
    }

    public void setCalSplWorkingDays(List<String> calSplWorkingDays) {
        this.calSplWorkingDays = calSplWorkingDays;
    }

}
