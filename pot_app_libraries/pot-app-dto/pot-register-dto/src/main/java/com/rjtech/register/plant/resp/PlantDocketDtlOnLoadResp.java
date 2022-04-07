package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantPODocketDtlTO;

public class PlantDocketDtlOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 3177168177572119591L;
    private List<PlantPODocketDtlTO> plantDocketDtlTOs = null;
    private Map<Long, LabelKeyTO> stockMap = null;
    private Map<Long, LabelKeyTO> projStockMap = null;

    public PlantDocketDtlOnLoadResp() {
        stockMap = new HashMap<Long, LabelKeyTO>();
        projStockMap = new HashMap<Long, LabelKeyTO>();
        plantDocketDtlTOs = new ArrayList<PlantPODocketDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public Map<Long, LabelKeyTO> getStockMap() {
        return stockMap;
    }

    public void setStockMap(Map<Long, LabelKeyTO> stockMap) {
        this.stockMap = stockMap;
    }

    public Map<Long, LabelKeyTO> getProjStockMap() {
        return projStockMap;
    }

    public void setProjStockMap(Map<Long, LabelKeyTO> projStockMap) {
        this.projStockMap = projStockMap;
    }

    public List<PlantPODocketDtlTO> getPlantDocketDtlTOs() {
        return plantDocketDtlTOs;
    }

    public void setPlantDocketDtlTOs(List<PlantPODocketDtlTO> plantDocketDtlTOs) {
        this.plantDocketDtlTOs = plantDocketDtlTOs;
    }

}
