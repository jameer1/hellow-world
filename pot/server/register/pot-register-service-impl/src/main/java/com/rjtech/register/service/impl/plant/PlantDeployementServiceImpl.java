package com.rjtech.register.service.impl.plant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.PostDeMobilisationStatus;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.plant.dto.PlantRegProjTO;
import com.rjtech.register.plant.model.PlantRegProjEntity;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
import com.rjtech.register.plant.req.PlantDeploymentSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantDeploymentResp;
import com.rjtech.register.repository.plant.PlantRegProjRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;
import com.rjtech.register.service.handler.plant.PlantRegProjDtlHandler;
import com.rjtech.register.service.plant.PlantDeploymentService;
import com.rjtech.register.util.RegisterUtil;
import com.rjtech.register.utils.RegisterCommonUtils;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "plantDeploymentService")
@RJSService(modulecode = "plantDeploymentService")
@Transactional
public class PlantDeployementServiceImpl implements PlantDeploymentService {

    @Autowired
    private PlantRegProjRepository plantProjectDtlRepository;

    @Autowired
    private PlantRegisterRepository plantRegisterDtlRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    public PlantDeploymentResp getPlantDeploymentOnLoad(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantDeploymentResp resp = new PlantDeploymentResp();
        List<PlantRegProjEntity> plantProjectDtlEntities = plantProjectDtlRepository
                .findPlantProjects(plantProjectDtlGetReq.getPlantId(), plantProjectDtlGetReq.getStatus());
        PlantRegProjTO plantProjectTO = null;
        if (CommonUtil.isListHasData(plantProjectDtlEntities)) {
            for (PlantRegProjEntity plantProjectDtlEntity : plantProjectDtlEntities) {
                plantProjectTO = PlantRegProjDtlHandler.convertEntityToPOJO(plantProjectDtlEntity);
                resp.getPlantRegProjTOs().add(plantProjectTO);
            }
        } else {
            plantProjectTO = new PlantRegProjTO();
            plantProjectTO.setPlantId(plantProjectDtlGetReq.getPlantId());
            plantProjectTO.setStatus(StatusCodes.ACTIVE.getValue());
            resp.getPlantRegProjTOs().add(plantProjectTO);
        }
        return resp;
    }

    public void savePlantDeployment(PlantDeploymentSaveReq plantDeploymentSaveReq) {

        PlantRegisterDtlEntity plantRegisterDtlEntity = null;
        PlantRegProjTO plantRegProjTO = plantDeploymentSaveReq.getPlantRegProjTO();
        plantRegProjTO.setLatest(RegisterCommonUtils.IS_LATEST_Y);
        plantRegisterDtlEntity = plantRegisterDtlRepository.findOne(plantRegProjTO.getPlantId());
        plantRegisterDtlEntity.setProjMstrEntity(
                (plantRegProjTO.getProjId() != null) ? epsProjRepository.findOne(plantRegProjTO.getProjId()) : null);
        plantRegisterDtlEntity.setPlantStatus(plantRegProjTO.getDeMobStatus());
        if (CommonUtil.isBlankLong(plantRegProjTO.getId())) {
            plantRegProjTO.setDeploymentId(new Long(1));
            plantRegisterDtlEntity.setDeploymentId(new Long(1));
        } else {
            plantRegisterDtlEntity.setDeploymentId(plantRegProjTO.getDeploymentId());
        }
        if (CommonUtil.isNotBlankStr(plantRegProjTO.getMobDate())) {
            plantRegProjTO.setAssignStatus(RegisterCommonUtils.IS_LATEST_Y);
        }
        if (CommonUtil.isNotBlankStr(plantRegProjTO.getCommissionDate())) {
            plantRegProjTO.setPlantDeliveryStatus(RegisterUtil.PROJECT_PO_STATUS_COMPLETED);
        }
        plantProjectDtlRepository.save(PlantRegProjDtlHandler.convertPOJOToEntity(plantRegProjTO,
                plantRegisterDtlRepository, epsProjRepository));
    }

    public List<String> getPostDeMobStatus() {
        List<String> strings = new ArrayList<String>();
        for (PostDeMobilisationStatus postDeMobilisationStatus : PostDeMobilisationStatus.values()) {
            strings.add(postDeMobilisationStatus.getName());
        }
        return strings;
    }

    public PlantRegProjTO getLatestPlantDeployment(Long plantId) {
        PlantRegProjTO plantRegProjTO = null;
        PlantRegProjEntity plantRegProjEntity = plantProjectDtlRepository.findLatestPlantDeployment(plantId);
        if (CommonUtil.objectNotNull(plantRegProjEntity)) {
            plantRegProjTO = PlantRegProjDtlHandler.convertEntityToPOJO(plantRegProjEntity);
        }
        return plantRegProjTO;
    }

}
