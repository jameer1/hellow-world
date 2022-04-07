package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.RentalValueDtlTO;

public class RentalValueResp extends AppResp {
    private static final long serialVersionUID = 1L;

    private List<RentalValueDtlTO> rentalValueDtlTOs = new ArrayList<RentalValueDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<RentalValueDtlTO> getRentalValueDtlTOs() {
        return rentalValueDtlTOs;
    }

    public void setRentalValueDtlTOs(List<RentalValueDtlTO> rentalValueDtlTOs) {
        this.rentalValueDtlTOs = rentalValueDtlTOs;
    }
}
