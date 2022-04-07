package com.rjtech.register.service.plant;

import com.rjtech.register.plant.dto.PlantLogRecordsTO;
import com.rjtech.register.plant.req.PlantDeactivateReq;
import com.rjtech.register.plant.req.PlantLogRecordsSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantLogRecordsResp;

public interface PlantLogRecordService {

    PlantLogRecordsResp getPlantLogRecords(PlantProjectDtlGetReq plantProjectDtlGetReq);

    void savePlantLogRecords(PlantLogRecordsSaveReq plantLogRecordsSaveReq);

    void plantLogRecordsDeactivate(PlantDeactivateReq plantDeactivateReq);

    PlantLogRecordsTO getMaxOdoMeterReadingByPlantProjId(Long plantProjId);

}
