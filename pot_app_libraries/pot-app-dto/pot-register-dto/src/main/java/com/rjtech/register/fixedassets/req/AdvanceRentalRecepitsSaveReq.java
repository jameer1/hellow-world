package com.rjtech.register.fixedassets.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.AdvanceRentalRecepitsDtlTO;

public class AdvanceRentalRecepitsSaveReq implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<AdvanceRentalRecepitsDtlTO> advanceRentalRecepitsDtlTO = new ArrayList<AdvanceRentalRecepitsDtlTO>();

    public List<AdvanceRentalRecepitsDtlTO> getAdvanceRentalRecepitsDtlTO() {
        return advanceRentalRecepitsDtlTO;
    }

    public void setAdvanceRentalRecepitsDtlTO(List<AdvanceRentalRecepitsDtlTO> advanceRentalRecepitsDtlTO) {
        this.advanceRentalRecepitsDtlTO = advanceRentalRecepitsDtlTO;
    }

}
