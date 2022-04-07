package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.RentalValueDtlTO;

public class RentalValueDeactiveReq extends RentalValueDtlTO {

    private static final long serialVersionUID = -7671175298681215590L;
    private List<Long> rentalValueIds = new ArrayList<Long>();

    public List<Long> getRentalValueIds() {
        return rentalValueIds;
    }

    public void setRentalValueIds(List<Long> rentalValueIds) {
        this.rentalValueIds = rentalValueIds;
    }

}
