package com.rjtech.register.service.plant;

import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantServiceHistorySaveReq;
import com.rjtech.register.plant.resp.PlantServiceHistoryResp;

public interface PlantServiceHistoryService {

    PlantServiceHistoryResp getPlantServiceHistory(PlantProjectDtlGetReq plantProjectDtlGetReq);

    void savePlantServiceHistory(PlantServiceHistorySaveReq plantServiceHistorySaveReq);

}
