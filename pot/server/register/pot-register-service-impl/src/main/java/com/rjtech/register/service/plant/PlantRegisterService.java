package com.rjtech.register.service.plant;

import java.util.List;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantRegisterDeactivateReq;
import com.rjtech.register.plant.req.PlantRegisterDtlSaveReq;
import com.rjtech.register.plant.req.PlantRegisterGetReq;
import com.rjtech.register.plant.resp.PlantCurrentStatusResp;
import com.rjtech.register.plant.resp.PlantRegisterDtlResp;
import com.rjtech.register.plant.resp.ProjPlantRegMapResp;

public interface PlantRegisterService {

    PlantRegisterDtlResp getPlantRegisterDtls(PlantRegisterGetReq plantRegisterGetReq);

    void savePlantRegisterDtls(PlantRegisterDtlSaveReq plantRegisterDtlSaveReq);

    void plantRegistersDeactivate(PlantRegisterDeactivateReq plantRegisterDeactivateReq);

    List<String> getPlantAssertType();

    void deletePlantReqForTrans(Long plantReqForTransId);

    PlantCurrentStatusResp getPlantCurrentStatus(PlantProjectDtlGetReq plantProjectDtlGetReq);

    PlantRegisterDtlResp getPlantsNotInUserProjects(PlantRegisterGetReq plantRegisterGetReq);

    LabelKeyTOResp getPlantAttendence(PlantProjectDtlGetReq plantProjectDtlGetReq);

    ProjPlantRegMapResp getMultiProjPlantListMap(PlantRegisterGetReq plantRegisterGetReq);

    PlantRegisterDtlResp getPlantsInUserProjects(PlantRegisterGetReq plantRegisterGetReq);

    boolean isPlantCodeUnique(String assertId);
}
