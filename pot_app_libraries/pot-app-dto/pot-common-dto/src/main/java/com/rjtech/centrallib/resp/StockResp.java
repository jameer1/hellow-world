package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.StockAndStoreTO;
import com.rjtech.common.resp.AppResp;


public class StockResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<StockAndStoreTO> stockAndStoreTOs = null;

    public StockResp() {
        stockAndStoreTOs = new ArrayList<StockAndStoreTO>(5);
    }

    public List<StockAndStoreTO> getStockAndStoreTOs() {
        return stockAndStoreTOs;
    }

    public void setStockAndStoreTOs(List<StockAndStoreTO> stockAndStoreTOs) {
        this.stockAndStoreTOs = stockAndStoreTOs;
    }

}
