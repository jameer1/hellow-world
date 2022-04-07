package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.AdvanceRentalRecepitsDtlTO;

public class AdvanceRentalRecepitsDeactiveReq extends AdvanceRentalRecepitsDtlTO {

    private static final long serialVersionUID = -7771175298681215590L;
    private List<Long> advanceRentalIds = new ArrayList<Long>();

    public List<Long> getAdvanceRentalIds() {
        return advanceRentalIds;
    }

    public void setAdvanceRentalIds(List<Long> advanceRentalIds) {
        this.advanceRentalIds = advanceRentalIds;
    }

}
