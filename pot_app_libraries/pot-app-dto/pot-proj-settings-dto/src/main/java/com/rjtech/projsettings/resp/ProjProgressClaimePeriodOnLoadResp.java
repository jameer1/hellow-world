package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProgressClaimePeriodTO;

public class ProjProgressClaimePeriodOnLoadResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<String> weeakDays = null;
    private List<String> years = null;
    private List<String> monthly = null;
    private List<ProjProgressClaimePeriodTO> projProgressClaimePeriodTOs = null;

    public ProjProgressClaimePeriodOnLoadResp() {
        weeakDays = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        years = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        monthly = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projProgressClaimePeriodTOs = new ArrayList<ProjProgressClaimePeriodTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
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

    public List<String> getMonthly() {
        return monthly;
    }

    public void setMonthly(List<String> monthly) {
        this.monthly = monthly;
    }

    public List<ProjProgressClaimePeriodTO> getProjProgressClaimePeriodTOs() {
        return projProgressClaimePeriodTOs;
    }

    public void setProjProgressClaimePeriodTOs(List<ProjProgressClaimePeriodTO> projProgressClaimePeriodTOs) {
        this.projProgressClaimePeriodTOs = projProgressClaimePeriodTOs;
    }

}
