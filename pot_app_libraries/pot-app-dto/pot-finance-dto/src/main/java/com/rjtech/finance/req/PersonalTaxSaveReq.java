package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.PersonalTaxTO;

public class PersonalTaxSaveReq extends ClientTO {

    private static final long serialVersionUID = 3817903396146965565L;

    private List<PersonalTaxTO> personalTaxTOs = new ArrayList<PersonalTaxTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PersonalTaxTO> getPersonalTaxTOs() {
        return personalTaxTOs;
    }

    public void setPersonalTaxTOs(List<PersonalTaxTO> personalTaxTOs) {
        this.personalTaxTOs = personalTaxTOs;
    }

}
