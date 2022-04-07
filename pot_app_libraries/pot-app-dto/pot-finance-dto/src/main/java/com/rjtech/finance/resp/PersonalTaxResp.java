package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.finance.dto.PersonalTaxTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class PersonalTaxResp extends AppResp {

    private static final long serialVersionUID = -8166344244240308405L;
    private List<PersonalTaxTO> personalTaxTOs = null;

    public PersonalTaxResp() {
        personalTaxTOs = new ArrayList<PersonalTaxTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PersonalTaxTO> getPersonalTaxTOs() {
        return personalTaxTOs;
    }

    public void setPersonalTaxTOs(List<PersonalTaxTO> personalTaxTOs) {
        this.personalTaxTOs = personalTaxTOs;
    }

}
