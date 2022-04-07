package com.rjtech.register.fixedassets.req;

import com.rjtech.register.fixedassets.dto.SubSequentRentalRecepitsDtlTO;

public class SubSequentRentalRecepitsGetReq extends SubSequentRentalRecepitsDtlTO {

    private static final long serialVersionUID = 1L;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
