package com.rjtech.register.plant.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class PlantProjectPODocketResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PlantProjectPODocketResp() {

    }

    private Map<Long, LabelKeyTO> projStockMap = new HashMap<Long, LabelKeyTO>();

    private Map<Long, LabelKeyTO> stockMap = new HashMap<Long, LabelKeyTO>();

    public Map<Long, LabelKeyTO> getProjStockMap() {
        return projStockMap;
    }

    public Map<Long, LabelKeyTO> getStockMap() {
        return stockMap;
    }

    public void setProjStockMap(Map<Long, LabelKeyTO> projStockMap) {
        this.projStockMap = projStockMap;
    }

    public void setStockMap(Map<Long, LabelKeyTO> stockMap) {
        this.stockMap = stockMap;
    }

}
