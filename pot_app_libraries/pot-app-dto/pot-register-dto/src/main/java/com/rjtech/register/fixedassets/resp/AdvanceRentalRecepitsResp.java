package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.register.fixedassets.dto.AdvanceRentalRecepitsDtlTO;

public class AdvanceRentalRecepitsResp extends AppResp {
    private static final long serialVersionUID = 1L;
    private List<AdvanceRentalRecepitsDtlTO> advanceRentalRecepitsDtlTO = new ArrayList<AdvanceRentalRecepitsDtlTO>();

    public List<AdvanceRentalRecepitsDtlTO> getAdvanceRentalRecepitsDtlTO() {
        return advanceRentalRecepitsDtlTO;
    }

    public void setAdvanceRentalRecepitsDtlTO(List<AdvanceRentalRecepitsDtlTO> advanceRentalRecepitsDtlTO) {
        this.advanceRentalRecepitsDtlTO = advanceRentalRecepitsDtlTO;
    }

}
