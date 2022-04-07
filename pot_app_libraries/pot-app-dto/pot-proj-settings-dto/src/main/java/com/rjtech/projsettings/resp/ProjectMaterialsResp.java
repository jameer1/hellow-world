package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.calendar.dto.CalRegularDaysTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjectMaterialDtlTO;
import com.rjtech.projsettings.dto.ProjectReportsTO;

public class ProjectMaterialsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -810047947764580959L;
    private List<ProjectMaterialDtlTO> projectMaterialDtlTOs = null;
    private List<String> calNonWorkingDays = null;
    private List<String> calSplWorkingDays = null;
    private Map<Long, LabelKeyTO> actualWorkingDayMap = null;
    private Map<Long, LabelKeyTO> materialClassificationMap = null;
    private List<LabelKeyTO> dateWiseActualQuantity = null;
    private CalRegularDaysTO regularHolidays = null;
    private ProjectReportsTO projReportsTo = null;

    public ProjectMaterialsResp() {
        projectMaterialDtlTOs = new ArrayList<ProjectMaterialDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        calNonWorkingDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        calSplWorkingDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        actualWorkingDayMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        dateWiseActualQuantity = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        materialClassificationMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projReportsTo = new ProjectReportsTO();
    }

    public List<ProjectMaterialDtlTO> getProjectMaterialDtlTOs() {
        return projectMaterialDtlTOs;
    }

    public void setProjectMaterialDtlTOs(List<ProjectMaterialDtlTO> projectMaterialDtlTOs) {
        this.projectMaterialDtlTOs = projectMaterialDtlTOs;
    }

    public List<String> getCalNonWorkingDays() {
        return calNonWorkingDays;
    }

    public void setCalNonWorkingDays(List<String> calNonWorkingDays) {
        this.calNonWorkingDays = calNonWorkingDays;
    }

    public Map<Long, LabelKeyTO> getActualWorkingDayMap() {
        return actualWorkingDayMap;
    }

    public void setActualWorkingDayMap(Map<Long, LabelKeyTO> actualWorkingDayMap) {
        this.actualWorkingDayMap = actualWorkingDayMap;
    }

    public Map<Long, LabelKeyTO> getMaterialClassificationMap() {
        return materialClassificationMap;
    }

    public void setMaterialClassificationMap(Map<Long, LabelKeyTO> materialClassificationMap) {
        this.materialClassificationMap = materialClassificationMap;
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
