package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.calendar.dto.CalTO;
import com.rjtech.centrallib.dto.CompanyTO;
import com.rjtech.common.dto.CountryTO;
import com.rjtech.common.dto.CurrencyTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.TimeZoneTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.ProfitCentreTO;
import com.rjtech.projsettings.dto.ProjGeneralMstrTO;
import com.rjtech.projsettings.dto.ResourceCurveTO;

public class ProjGeneralOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<LabelKeyTO> users = null;
    private ProjGeneralMstrTO projGeneralMstrTO = null;
    private List<CountryTO> countryTOs = null;
    private List<CurrencyTO> currencyTOs = null;
    private List<CalTO> calenderTOs = null;
    private List<ProfitCentreTO> profitCentreTOs = null;
    private List<ResourceCurveTO> projresourceCurveTOs = null;
    private List<LabelKeyTO> ProjGenLabelKeyTO = null;
    private List<TimeZoneTO> timeZoneTOs = null;
    private List<CompanyTO> companyTOs = null;

    public ProjGeneralOnLoadResp() {
        users = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projGeneralMstrTO = new ProjGeneralMstrTO();
        countryTOs = new ArrayList<CountryTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        currencyTOs = new ArrayList<CurrencyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        calenderTOs = new ArrayList<CalTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        profitCentreTOs = new ArrayList<ProfitCentreTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projresourceCurveTOs = new ArrayList<ResourceCurveTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjGenLabelKeyTO = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        timeZoneTOs = new ArrayList<TimeZoneTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        companyTOs = new ArrayList<CompanyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<LabelKeyTO> getUsers() {
        return users;
    }

    public void setUsers(List<LabelKeyTO> users) {
        this.users = users;
    }

    public ProjGeneralMstrTO getProjGeneralMstrTO() {
        return projGeneralMstrTO;
    }

    public void setProjGeneralMstrTO(ProjGeneralMstrTO projGeneralMstrTO) {
        this.projGeneralMstrTO = projGeneralMstrTO;
    }

    public List<CountryTO> getCountryTOs() {
        return countryTOs;
    }

    public void setCountryTOs(List<CountryTO> countryTOs) {
        this.countryTOs = countryTOs;
    }

    public List<CurrencyTO> getCurrencyTOs() {
        return currencyTOs;
    }

    public void setCurrencyTOs(List<CurrencyTO> currencyTOs) {
        this.currencyTOs = currencyTOs;
    }

    public List<ProfitCentreTO> getProfitCentreTOs() {
        return profitCentreTOs;
    }

    public void setProfitCentreTOs(List<ProfitCentreTO> profitCentreTOs) {
        this.profitCentreTOs = profitCentreTOs;
    }

    public List<CalTO> getCalenderTOs() {
        return calenderTOs;
    }

    public void setCalenderTOs(List<CalTO> calenderTOs) {
        this.calenderTOs = calenderTOs;
    }

    public List<ResourceCurveTO> getProjresourceCurveTOs() {
        return projresourceCurveTOs;
    }

    public void setProjresourceCurveTOs(List<ResourceCurveTO> projresourceCurveTOs) {
        this.projresourceCurveTOs = projresourceCurveTOs;
    }

    public List<LabelKeyTO> getProjGenLabelKeyTO() {
        return ProjGenLabelKeyTO;
    }

    public void setProjGenLabelKeyTO(List<LabelKeyTO> projGenLabelKeyTO) {
        ProjGenLabelKeyTO = projGenLabelKeyTO;
    }

    public List<TimeZoneTO> getTimeZoneTOs() {
        return timeZoneTOs;
    }

    public void setTimeZoneTOs(List<TimeZoneTO> timeZoneTOs) {
        this.timeZoneTOs = timeZoneTOs;
    }

    public List<CompanyTO> getCompanyTOs() {
        return companyTOs;
    }

    public void setCompanyTOs(List<CompanyTO> companyTOs) {
        this.companyTOs = companyTOs;
    }

}
