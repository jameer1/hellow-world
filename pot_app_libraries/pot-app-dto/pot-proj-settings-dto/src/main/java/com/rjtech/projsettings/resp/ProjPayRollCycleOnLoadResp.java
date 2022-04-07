package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjEmpCatgTO;
import com.rjtech.projsettings.dto.ProjPayRollCycleTO;

public class ProjPayRollCycleOnLoadResp extends AppResp {

    private static final long serialVersionUID = 1L;
    private List<ProjPayRollCycleTO> projPayRollCycleTOs = null;
    private List<String> weeakDays = null;
    private List<String> years = null;
    private List<String> monthly = null;
    private List<String> payPeriodCycles = null;
    private List<ProcureMentCatgTO> procureMentCatgTOs = null;
    private List<ProjEmpCatgTO> projEmpCatgTOs = null;

    public ProjPayRollCycleOnLoadResp() {
        projPayRollCycleTOs = new ArrayList<ProjPayRollCycleTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        years = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        weeakDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        payPeriodCycles = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        monthly = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        procureMentCatgTOs = new ArrayList<ProcureMentCatgTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projEmpCatgTOs = new ArrayList<ProjEmpCatgTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjPayRollCycleTO> getProjPayRollCycleTOs() {
        return projPayRollCycleTOs;
    }

    public void setProjPayRollCycleTOs(List<ProjPayRollCycleTO> projPayRollCycleTOs) {
        this.projPayRollCycleTOs = projPayRollCycleTOs;
    }

    public List<String> getWeeakDays() {
        return weeakDays;
    }

    public void setWeeakDays(List<String> weeakDays) {
        this.weeakDays = weeakDays;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public List<String> getPayPeriodCycles() {
        return payPeriodCycles;
    }

    public void setPayPeriodCycles(List<String> payPeriodCycles) {
        this.payPeriodCycles = payPeriodCycles;
    }

    public List<String> getMonthly() {
        return monthly;
    }

    public void setMonthly(List<String> monthly) {
        this.monthly = monthly;
    }

    public List<ProcureMentCatgTO> getProcureMentCatgTOs() {
        return procureMentCatgTOs;
    }

    public void setProcureMentCatgTOs(List<ProcureMentCatgTO> procureMentCatgTOs) {
        this.procureMentCatgTOs = procureMentCatgTOs;
    }

    public List<ProjEmpCatgTO> getProjEmpCatgTOs() {
        return projEmpCatgTOs;
    }

    public void setProjEmpCatgTOs(List<ProjEmpCatgTO> projEmpCatgTOs) {
        this.projEmpCatgTOs = projEmpCatgTOs;
    }
}
