package com.rjtech.register.service.impl.plant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.PlantServiceClassRepository;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.plant.model.PlantRepairsEntity;
import com.rjtech.register.plant.req.PlantProjRepairGetReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantRepairSaveReq;
import com.rjtech.register.plant.req.PlantRepairsResp;
import com.rjtech.register.repository.material.MaterialDockSchItemRepository;
import com.rjtech.register.repository.plant.PlantRegProcRepository;
import com.rjtech.register.repository.plant.PlantRegProjRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;
import com.rjtech.register.repository.plant.PlantRepairsRepository;
import com.rjtech.register.service.handler.plant.PlantRepairHandler;
import com.rjtech.register.service.plant.PlantRepairService;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "plantRepairsService")
@RJSService(modulecode = "plantRepairsService")
@Transactional
public class PlantRepairServiceImpl implements PlantRepairService {

    @Autowired
    private PlantRepairsRepository plantRepairsRepository;

    @Autowired
    private PlantRegProcRepository plantRegProcRepository;

    @Autowired
    private PlantRegisterRepository plantRegisterRepository;

    @Autowired
    private PlantServiceClassRepository plantServiceClassRepository;

    @Autowired
    private MaterialClassRepository materialClassRepository;

    @Autowired
    private MaterialDockSchItemRepository materialDockSchItemRepository;

    @Autowired
    private PlantRegProjRepository plantRegProjRepository;

    public PlantRepairsResp getPlantRepairs(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantRepairsResp plantRepairsResp = new PlantRepairsResp();

        List<PlantRepairsEntity> plantRepairsEntites = plantRepairsRepository
                .findPlantRepairs(plantProjectDtlGetReq.getPlantId(), plantProjectDtlGetReq.getStatus());
        if (CommonUtil.isListHasData(plantRepairsEntites)) {
            for (PlantRepairsEntity plantRepairsEntity : plantRepairsEntites) {
                plantRepairsResp.getPlantRepairsTOs().add(PlantRepairHandler.convertEntityToPOJO(plantRepairsEntity));
            }
        }
        return plantRepairsResp;
    }

    public void savePlantRepairs(PlantRepairSaveReq plantRepairsSaveReq) {
        plantRepairsRepository.save(PlantRepairHandler.populatePlantRepairEntities(
                plantRepairsSaveReq.getPlantRepairsTOs(), plantRegisterRepository, plantServiceClassRepository,
                materialClassRepository, materialDockSchItemRepository, plantRegProjRepository));

    }

    public LabelKeyTOResp getPlantMaterialProjDocketDetails(PlantProjRepairGetReq plantProjRepairGetReq) {
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        List<LabelKeyTO> labelKeyTOs = plantRegProcRepository.getPlantMaterialProjDocketDetails(
                plantProjRepairGetReq.getMaterialClassId(), plantProjRepairGetReq.getProjId());
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

}
