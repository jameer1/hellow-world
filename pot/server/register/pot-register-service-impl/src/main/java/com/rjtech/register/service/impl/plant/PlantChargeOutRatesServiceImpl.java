package com.rjtech.register.service.impl.plant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.PlantChargeOutCatg;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projsettings.repository.PlantPayableRateRepository;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.plant.dto.PlantChargeOutRatesTO;
import com.rjtech.register.plant.model.PlantChargeOutRatesEntity;
import com.rjtech.register.plant.model.PlantPayableRatesEntity;
import com.rjtech.register.plant.req.PlantChargeOutRatesSaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantChargeOutRatesResp;
import com.rjtech.register.repository.plant.PlantChargeOutRateRepository;
import com.rjtech.register.service.handler.plant.PlantChargeOutRateHandler;
import com.rjtech.register.service.plant.PlantChargeOutRatesService;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "plantChargeOutRatesService")
@RJSService(modulecode = "plantChargeOutRatesService")
@Transactional
public class PlantChargeOutRatesServiceImpl implements PlantChargeOutRatesService {

    @Autowired
    private PlantChargeOutRateRepository plantChargeOutRatesRepository;
    
    @Autowired
    private PlantPayableRateRepository plantPayableRateRepository;

    @Autowired
    private ProjGeneralRepositoryCopy projGeneralRepository;

    @Autowired
    private ProjCostItemRepositoryCopy projCostItemRepository;

    public PlantChargeOutRatesResp getPlantChargeOutRates(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantChargeOutRatesResp plantChargeOutRatesResp = new PlantChargeOutRatesResp();
        List<PlantChargeOutRatesEntity> chargeOutRatesEntities = plantChargeOutRatesRepository
                .findPlantChargeOutRates(plantProjectDtlGetReq.getPlantId());
        if (CommonUtil.isListHasData(chargeOutRatesEntities)) {
            for (PlantChargeOutRatesEntity plantChargeOutRatesEntity : chargeOutRatesEntities) {
                PlantChargeOutRatesTO chargeOutRatesTO = PlantChargeOutRateHandler
                        .convertEntityToPOJO(plantChargeOutRatesEntity);
                plantChargeOutRatesResp.getPlantChargeOutRatesTOs().add(chargeOutRatesTO);
            }
        }
        return plantChargeOutRatesResp;
    }

    public void savePlantChargeOutRates(PlantChargeOutRatesSaveReq plantProjectDtlsSaveReq) {
        PlantChargeOutRatesTO plantChargeOutRatesTO = plantProjectDtlsSaveReq.getPlantChargeOutRatesTO();
        PlantChargeOutRatesEntity entity = PlantChargeOutRateHandler.convertPOJOToEntity(plantChargeOutRatesTO,
                projGeneralRepository, projCostItemRepository);
        entity.setLatest(true);
        if (CommonUtil.isNonBlankLong(plantChargeOutRatesTO.getId())) {
            entity.setId(null);
            PlantChargeOutRatesEntity plantChargeOutRatesEntity = plantChargeOutRatesRepository
                    .findOne(plantChargeOutRatesTO.getId());
            plantChargeOutRatesEntity.setLatest(false);
            Calendar cal = Calendar.getInstance();
            cal.setTime(entity.getEffectiveFrom());
            cal.add(Calendar.DATE, -1);
            plantChargeOutRatesEntity.setEffectiveTo(cal.getTime());
        }
        plantChargeOutRatesRepository.save(entity);

    }

    public List<String> getPlantChargeOutCatg() {
        List<String> strings = new ArrayList<String>();
        for (PlantChargeOutCatg plantChargeOutCatg : PlantChargeOutCatg.values()) {
            strings.add(plantChargeOutCatg.getName());
        }
        return strings;
    }
    
    
    public void savePlantPayableRates(PlantChargeOutRatesSaveReq plantProjectDtlsSaveReq) {
        PlantChargeOutRatesTO plantChargeOutRatesTO = plantProjectDtlsSaveReq.getPlantChargeOutRatesTO();
        
        PlantChargeOutRatesEntity chargeOutentity = PlantChargeOutRateHandler.convertPOJOToEntity(plantChargeOutRatesTO,
                projGeneralRepository, projCostItemRepository);
        
        PlantPayableRatesEntity entity = new PlantPayableRatesEntity();
        entity.setCategory(chargeOutentity.getCategory());
        entity.setChargeOutRates(chargeOutentity.getChargeOutRates());
        entity.setMobChargeOutRate(chargeOutentity.getMobChargeOutRate());
        entity.setCommentes(chargeOutentity.getCommentes());
        entity.setProjMobCostItem(chargeOutentity.getProjDemobCostItem());
        entity.setProjDemobCostItem(chargeOutentity.getProjDemobCostItem());
        entity.setProjGenId(chargeOutentity.getProjGenId());
        entity.setIdleChargeOutRate(chargeOutentity.getIdleChargeOutRate());
        entity.setDeMobChargeOutRate(chargeOutentity.getDeMobChargeOutRate());
        entity.setEffectiveFrom(chargeOutentity.getEffectiveFrom());
        entity.setEffectiveTo(chargeOutentity.getEffectiveTo());
        entity.setRateWithOutFualNRShift(chargeOutentity.getRateWithOutFualNRShift());
        entity.setRateWithoutFualDBShift(chargeOutentity.getRateWithFualDBShift());
        entity.setRateWithFualNRShift(chargeOutentity.getRateWithFualNRShift());
        entity.setPlantRegProjEntity(chargeOutentity.getPlantRegProjEntity());
        entity.setLatest(true);
        if (CommonUtil.isNonBlankLong(plantChargeOutRatesTO.getId())) {
            entity.setId(null);
            PlantChargeOutRatesEntity plantChargeOutRatesEntity = plantChargeOutRatesRepository
                    .findOne(plantChargeOutRatesTO.getId());
            plantChargeOutRatesEntity.setLatest(false);
            Calendar cal = Calendar.getInstance();
            cal.setTime(entity.getEffectiveFrom());
            cal.add(Calendar.DATE, -1);
            plantChargeOutRatesEntity.setEffectiveTo(cal.getTime());
        }
        plantPayableRateRepository.save(entity);

    }

}
