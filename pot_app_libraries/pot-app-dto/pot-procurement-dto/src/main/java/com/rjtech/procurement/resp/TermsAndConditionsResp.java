package com.rjtech.procurement.resp;

import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.procurement.dto.TermsAndConditionsTO;

public class TermsAndConditionsResp extends AppResp {

    private static final long serialVersionUID = -2082680906920319979L;

    private List<TermsAndConditionsTO> termsAndConditionsTOs;

    public List<TermsAndConditionsTO> getTermsAndConditionsTOs() {
        return termsAndConditionsTOs;
    }

    public void setTermsAndConditionsTOs(List<TermsAndConditionsTO> termsAndConditionsTOs) {
        this.termsAndConditionsTOs = termsAndConditionsTOs;
    }

}
