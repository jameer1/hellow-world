package com.rjtech.register.service.plant;

import java.util.List;

import com.rjtech.register.plant.req.PlantChargeOutRatesSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantChargeOutRatesResp;

public interface PlantChargeOutRatesService {

    PlantChargeOutRatesResp getPlantChargeOutRates(PlantProjectDtlGetReq plantProjectDtlGetReq);

    void savePlantChargeOutRates(PlantChargeOutRatesSaveReq plantChargeOutRatesSaveReq);

    List<String> getPlantChargeOutCatg();
    
    void savePlantPayableRates(PlantChargeOutRatesSaveReq plantChargeOutRatesSaveReq);

}
