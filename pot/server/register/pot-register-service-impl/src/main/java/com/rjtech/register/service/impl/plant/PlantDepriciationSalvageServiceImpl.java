package com.rjtech.register.service.impl.plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.apperror.POTAppErrorConstants;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.plant.dto.PlantDepriciationSalvageTO;
import com.rjtech.register.plant.dto.PlantLogRecordsTO;
import com.rjtech.register.plant.dto.PlantRegProjTO;
import com.rjtech.register.plant.model.PlantDepriciationSalvageEntity;
import com.rjtech.register.plant.req.PlantDepriciationSalvageSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantDepriciationSalvageResp;
import com.rjtech.register.repository.plant.PlantDepriciationSalvageRepository;
import com.rjtech.register.repository.plant.PlantLogRecordRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;
import com.rjtech.register.resource.exception.PlantEffectiveFromBackDatedException;
import com.rjtech.register.service.handler.plant.PlantDepriciationSalvageHandler;
import com.rjtech.register.service.handler.plant.PlantRegProjDtlHandler;
import com.rjtech.register.service.plant.PlantDeploymentService;
import com.rjtech.register.service.plant.PlantDepriciationSalvageService;
import com.rjtech.register.service.plant.PlantLogRecordService;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "plantDepriciationSalvageService")
@RJSService(modulecode = "plantDepriciationSalvageService")
@Transactional
public class PlantDepriciationSalvageServiceImpl implements PlantDepriciationSalvageService {

    @Autowired
    private PlantDepriciationSalvageRepository plantDepriciationSalvagesRepository;

    @Autowired
    private PlantDeploymentService plantDeploymentService;

    @Autowired
    private PlantLogRecordService plantLogRecordsService;

    @Autowired
    private PlantRegisterRepository plantRegisterRepository;

    @Autowired
    private PlantLogRecordRepository plantLogRecordRepository;

    @Autowired
    private ProjGeneralRepositoryCopy projGeneralRepository;

    public PlantDepriciationSalvageResp getPlantDepriciationSalvages(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantDepriciationSalvageResp plantDepriciationSalvageResp = new PlantDepriciationSalvageResp();

        List<PlantDepriciationSalvageEntity> plantDepriciationSalvageEntities = plantDepriciationSalvagesRepository
                .findPlantDepriciationSalvages(plantProjectDtlGetReq.getPlantId());
        if (CommonUtil.isListHasData(plantDepriciationSalvageEntities)) {
            PlantDepriciationSalvageTO plantDepriciationSalvageTO = null;
            for (PlantDepriciationSalvageEntity plantDepriciationSalvageEntity : plantDepriciationSalvageEntities) {
                plantDepriciationSalvageTO = PlantDepriciationSalvageHandler
                        .convertEntityToPOJO(plantDepriciationSalvageEntity);
                plantDepriciationSalvageTO.setPlantRegProjTO(PlantRegProjDtlHandler
                        .convertEntityToPOJO(plantDepriciationSalvageEntity.getPlantRegProjEntity()));
                plantDepriciationSalvageResp.getPlantDepriciationSalvageTOs().add(plantDepriciationSalvageTO);
            }
        } else {
            PlantRegProjTO plantRegProjTO = plantDeploymentService
                    .getLatestPlantDeployment(plantProjectDtlGetReq.getPlantId());
            if (CommonUtil.objectNotNull(plantRegProjTO)) {
                PlantDepriciationSalvageTO salvageTO = new PlantDepriciationSalvageTO();
                PlantLogRecordsTO logRecord = plantLogRecordsService
                        .getMaxOdoMeterReadingByPlantProjId(plantRegProjTO.getId());
                salvageTO.setPlantRegProjTO(plantRegProjTO);
                salvageTO.setEffectiveFrom(plantRegProjTO.getMobDate());
                salvageTO.setLatest(true);
                if (CommonUtil.objectNotNull(logRecord)) {
                    salvageTO.setPlantLogId(logRecord.getId());
                    salvageTO.setEndMeterReading(logRecord.getEndMeter());
                }
                plantDepriciationSalvageResp.getPlantDepriciationSalvageTOs().add(salvageTO);
            }
        }
        return plantDepriciationSalvageResp;
    }

    @Transactional
    public void savePlantDepriciationSalvages(PlantDepriciationSalvageSaveReq plantDepriciationSalvageSaveReq) {

        PlantDepriciationSalvageEntity entity = null;

        List<PlantDepriciationSalvageEntity> plantDepriciationSalvageEntities = new ArrayList<>();

        if (CommonUtil.isNonBlankLong(plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO().getId())) {
            entity = plantDepriciationSalvagesRepository
                    .findOne(plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO().getId());
        } else {
            entity = new PlantDepriciationSalvageEntity();
            entity = PlantDepriciationSalvageHandler.convertPOJOToEntity(entity,
                    plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO(), plantRegisterRepository,
                    plantLogRecordRepository, projGeneralRepository);
            entity.setLatest(true);
            plantDepriciationSalvageEntities.add(entity);
        }

        if (CommonUtil.isNonBlankLong(plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO().getId())
                && CommonUtil.isNotBlankDate(entity.getEffectiveFrom()) && CommonUtil.isNotBlankStr(
                        plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO().getEffectiveFrom())) {

            String existRecordEffectiveFrom = CommonUtil.convertDateToString(entity.getEffectiveFrom());

            if (entity.getEffectiveFrom().before(CommonUtil.convertStringToDate(
                    plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO().getEffectiveFrom()))) {
                PlantDepriciationSalvageEntity plantDepriciationSalvageEntity = null;
                plantDepriciationSalvageEntity = new PlantDepriciationSalvageEntity();

                plantDepriciationSalvageEntity = PlantDepriciationSalvageHandler.convertPOJOToEntity(
                        plantDepriciationSalvageEntity, plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO(),
                        plantRegisterRepository, plantLogRecordRepository, projGeneralRepository);
                plantDepriciationSalvageEntity.setId(null);
                plantDepriciationSalvageEntity.setLatest(true);
                entity.setLatest(false);
                Date inputeffectiveFrom = CommonUtil.convertStringToDate(
                        plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO().getEffectiveFrom());
                entity.setEffectiveTo(CommonUtil.substarctInputDays(inputeffectiveFrom, -1));

                plantDepriciationSalvageEntity.setEffectiveFrom(inputeffectiveFrom);

                plantDepriciationSalvageEntities.add(plantDepriciationSalvageEntity);
                plantDepriciationSalvageEntities.add(entity);
            } else if (existRecordEffectiveFrom.equalsIgnoreCase(
                    plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO().getEffectiveFrom())) {
                PlantDepriciationSalvageHandler.convertPOJOToEntity(entity,
                        plantDepriciationSalvageSaveReq.getPlantDepriciationSalvageTO(), plantRegisterRepository,
                        plantLogRecordRepository, projGeneralRepository);
                plantDepriciationSalvageEntities.add(entity);
            } else {
                throw new PlantEffectiveFromBackDatedException(
                        POTAppErrorConstants.PLANT_EFFECTIVE_FROM_BACK_DATED_DEPRICIATION_SALVAGE, null);
            }

        }

        plantDepriciationSalvagesRepository.save(plantDepriciationSalvageEntities);

    }
}
