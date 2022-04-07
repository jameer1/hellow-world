package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.CreditCycleTO;
import com.rjtech.common.dto.PayPeriodCycleTO;
import com.rjtech.common.dto.TaxableTypeTO;
import com.rjtech.common.dto.YearsTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class PayPeriodCycleResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 629147984088618486L;
    private List<PayPeriodCycleTO> periodCycleTOs = null;
    private List<YearsTO> yearsTOs = null;
    private List<TaxableTypeTO> taxableTypeTOs = null;
    private List<CreditCycleTO> creditCycleTOs = null;

    public PayPeriodCycleResp() {
        periodCycleTOs = new ArrayList<PayPeriodCycleTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        yearsTOs = new ArrayList<YearsTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        taxableTypeTOs = new ArrayList<TaxableTypeTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        creditCycleTOs = new ArrayList<CreditCycleTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PayPeriodCycleTO> getPeriodCycleTOs() {
        return periodCycleTOs;
    }

    public void setPeriodCycleTOs(List<PayPeriodCycleTO> periodCycleTOs) {
        this.periodCycleTOs = periodCycleTOs;
    }

    public List<YearsTO> getYearsTOs() {
        return yearsTOs;
    }

    public void setYearsTOs(List<YearsTO> yearsTOs) {
        this.yearsTOs = yearsTOs;
    }

    public List<TaxableTypeTO> getTaxableTypeTOs() {
        return taxableTypeTOs;
    }

    public void setTaxableTypeTOs(List<TaxableTypeTO> taxableTypeTOs) {
        this.taxableTypeTOs = taxableTypeTOs;
    }

    public List<CreditCycleTO> getCreditCycleTOs() {
        return creditCycleTOs;
    }

    public void setCreditCycleTOs(List<CreditCycleTO> creditCycleTOs) {
        this.creditCycleTOs = creditCycleTOs;
    }

}
