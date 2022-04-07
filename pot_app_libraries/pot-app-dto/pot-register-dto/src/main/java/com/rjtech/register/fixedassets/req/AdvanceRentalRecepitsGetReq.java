package com.rjtech.register.fixedassets.req;

import com.rjtech.register.fixedassets.dto.AdvanceRentalRecepitsDtlTO;

public class AdvanceRentalRecepitsGetReq extends AdvanceRentalRecepitsDtlTO {

    private static final long serialVersionUID = 1L;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
