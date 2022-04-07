package com.rjtech.register.service.plant;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.register.plant.req.PlantProjRepairGetReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantRepairSaveReq;
import com.rjtech.register.plant.req.PlantRepairsResp;

public interface PlantRepairService {

    PlantRepairsResp getPlantRepairs(PlantProjectDtlGetReq plantProjectDtlGetReq);

    void savePlantRepairs(PlantRepairSaveReq plantRepairsSaveReq);

    LabelKeyTOResp getPlantMaterialProjDocketDetails(PlantProjRepairGetReq plantProjRepairGetReq);

}
