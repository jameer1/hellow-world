package com.rjtech.register.service.plant;

import com.rjtech.register.plant.req.PlantDepriciationSalvageSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantDepriciationSalvageResp;

public interface PlantDepriciationSalvageService {

    PlantDepriciationSalvageResp getPlantDepriciationSalvages(PlantProjectDtlGetReq plantProjectDtlGetReq);

    void savePlantDepriciationSalvages(PlantDepriciationSalvageSaveReq plantDepriciationSalvageSaveReq);

}
