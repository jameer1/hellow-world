package com.rjtech.register.service.impl.plant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.repository.PlantServiceClassRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.plant.model.PlantServiceHistoryEntity;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantServiceHistorySaveReq;
import com.rjtech.register.plant.resp.PlantServiceHistoryResp;
import com.rjtech.register.repository.plant.PlantRegProjRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;
import com.rjtech.register.repository.plant.PlantServiceHistoryRepository;
import com.rjtech.register.service.handler.plant.PlantServiceHistoryHandler;
import com.rjtech.register.service.plant.PlantServiceHistoryService;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "plantServiceHistoryService")
@RJSService(modulecode = "plantServiceHistoryService")
@Transactional
public class PlantServiceHistoryServiceImpl implements PlantServiceHistoryService {

    @Autowired
    private PlantServiceHistoryRepository plantServiceHistoryRepository;

    @Autowired
    private PlantServiceClassRepository plantServiceClassRepository;

    @Autowired
    private PlantRegisterRepository plantRegisterRepository;

    @Autowired
    private PlantRegProjRepository plantRegProjRepository;

    public PlantServiceHistoryResp getPlantServiceHistory(PlantProjectDtlGetReq plantProjectDtlGetReq) {

        PlantServiceHistoryResp plantServiceHistoryResp = new PlantServiceHistoryResp();
        List<PlantServiceHistoryEntity> plantServiceHistoryEntites = plantServiceHistoryRepository
                .findPlantPlantServiceHistory(plantProjectDtlGetReq.getPlantId(), plantProjectDtlGetReq.getStatus());

        if (CommonUtil.isListHasData(plantServiceHistoryEntites)) {
            for (PlantServiceHistoryEntity plantServiceHistoryEntity : plantServiceHistoryEntites) {
                plantServiceHistoryResp.getPlantServiceHistoryTOs()
                        .add(PlantServiceHistoryHandler.convertEntityToPOJO(plantServiceHistoryEntity));
            }
        }

        return plantServiceHistoryResp;
    }

    public void savePlantServiceHistory(PlantServiceHistorySaveReq plantServiceHistorySaveReq) {
        plantServiceHistoryRepository.save(PlantServiceHistoryHandler.populatePlantServiceHistoryEntities(
                plantServiceHistorySaveReq.getPlantServiceHistoryTOs(), plantServiceClassRepository,
                plantRegisterRepository, plantRegProjRepository));

    }

}
