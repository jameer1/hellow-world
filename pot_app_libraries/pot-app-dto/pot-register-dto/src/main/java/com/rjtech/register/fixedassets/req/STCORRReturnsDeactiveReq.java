package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.STCORRReturnsDtlTO;

public class STCORRReturnsDeactiveReq extends STCORRReturnsDtlTO {

    private static final long serialVersionUID = -7671175298681215590L;
    private List<Long> stcorrReturnsIds = new ArrayList<Long>();

    public List<Long> getStcorrReturnsIds() {
        return stcorrReturnsIds;
    }

    public void setStcorrReturnsIds(List<Long> stcorrReturnsIds) {
        this.stcorrReturnsIds = stcorrReturnsIds;
    }

}
