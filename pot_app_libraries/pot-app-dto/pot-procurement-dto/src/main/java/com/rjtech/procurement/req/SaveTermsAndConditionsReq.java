package com.rjtech.procurement.req;

import java.io.Serializable;

import com.rjtech.procurement.dto.TermsAndConditionsTO;

public class SaveTermsAndConditionsReq implements Serializable {

    private static final long serialVersionUID = 1857004801988313183L;

    private TermsAndConditionsTO termsAndConditionsTO;

    public TermsAndConditionsTO getTermsAndConditionsTO() {
        return termsAndConditionsTO;
    }

    public void setTermsAndConditionsTO(TermsAndConditionsTO termsAndConditionsTO) {
        this.termsAndConditionsTO = termsAndConditionsTO;
    }

}
