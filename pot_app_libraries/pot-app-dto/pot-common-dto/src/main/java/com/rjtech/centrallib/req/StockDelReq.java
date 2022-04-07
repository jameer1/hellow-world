package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class StockDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> stockProjsIds = new ArrayList<Long>(5);

    public List<Long> getStockProjsIds() {
        return stockProjsIds;
    }

    public void setStockProjsIds(List<Long> stockProjsIds) {
        this.stockProjsIds = stockProjsIds;
    }

}
