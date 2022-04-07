package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.StockAndStoreTO;
import com.rjtech.common.dto.ClientTO;


public class StockSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<StockAndStoreTO> stockAndStoreTOs = new ArrayList<StockAndStoreTO>(
            5);

    public List<StockAndStoreTO> getStockAndStoreTOs() {
        return stockAndStoreTOs;
    }

    public void setStockAndStoreTOs(List<StockAndStoreTO> stockAndStoreTOs) {
        this.stockAndStoreTOs = stockAndStoreTOs;
    }

}
