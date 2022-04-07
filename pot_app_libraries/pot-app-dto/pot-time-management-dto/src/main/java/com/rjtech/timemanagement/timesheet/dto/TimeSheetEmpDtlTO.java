package com.rjtech.timemanagement.timesheet.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

public class TimeSheetEmpDtlTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long timeSheetId;
    private Long empRegId;
    private Long costId;
    private String apprStatus;
    private String apprComments;
    private LabelKeyTO empDetailLabelKeyTO = new LabelKeyTO();
    private Map<String, TimeSheetEmpTaskTO> timeSheetEmpTaskMap = new HashMap<String, TimeSheetEmpTaskTO>();
    private List<TimeSheetEmpExpenseTO> timeSheetEmpExpenseTOs = new ArrayList<TimeSheetEmpExpenseTO>();
    private List<TimeSheetEmpWorkDtlTO> timeSheetEmpWorkDtlTOs = new ArrayList<TimeSheetEmpWorkDtlTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LabelKeyTO getEmpDetailLabelKeyTO() {
        return empDetailLabelKeyTO;
    }

    public void setEmpDetailLabelKeyTO(LabelKeyTO empDetailLabelKeyTO) {
        this.empDetailLabelKeyTO = empDetailLabelKeyTO;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public List<TimeSheetEmpExpenseTO> getTimeSheetEmpExpenseTOs() {
        return timeSheetEmpExpenseTOs;
    }

    public void setTimeSheetEmpExpenseTOs(List<TimeSheetEmpExpenseTO> timeSheetEmpExpenseTOs) {
        this.timeSheetEmpExpenseTOs = timeSheetEmpExpenseTOs;
    }

    public List<TimeSheetEmpWorkDtlTO> getTimeSheetEmpWorkDtlTOs() {
        return timeSheetEmpWorkDtlTOs;
    }

    public void setTimeSheetEmpWorkDtlTOs(List<TimeSheetEmpWorkDtlTO> timeSheetEmpWorkDtlTOs) {
        this.timeSheetEmpWorkDtlTOs = timeSheetEmpWorkDtlTOs;
    }

    public Map<String, TimeSheetEmpTaskTO> getTimeSheetEmpTaskMap() {
        return timeSheetEmpTaskMap;
    }

    public void setTimeSheetEmpTaskMap(Map<String, TimeSheetEmpTaskTO> timeSheetEmpTaskMap) {
        this.timeSheetEmpTaskMap = timeSheetEmpTaskMap;
    }

}