package com.rjtech.register.service.handler.plant;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.plant.dto.PlantRegisterDtlTO;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class PlantRegisterDtlHandler {

    public static PlantRegisterDtlTO convertEntityToPOJO(PlantRegisterDtlEntity entity) {
        PlantRegisterDtlTO plantRegisterDtlTO = new PlantRegisterDtlTO();

        plantRegisterDtlTO.setId(entity.getId());
        plantRegisterDtlTO.setClientId((entity.getClientId() != null) ? entity.getClientId().getClientId() : null);

        plantRegisterDtlTO
                .setProjId((entity.getProjMstrEntity() != null) ? entity.getProjMstrEntity().getProjectId() : null);

        PlantMstrEntity plantMstrEntity = entity.getPlantClassMstrId();
        if (CommonUtil.objectNotNull(plantMstrEntity)) {
            plantRegisterDtlTO.setPlantClassMstrId(plantMstrEntity.getId());
            plantRegisterDtlTO.setPlantClassMstrCode(plantMstrEntity.getCode());
            plantRegisterDtlTO.setPlantClassMstrName(plantMstrEntity.getName());
            MeasurmentMstrEntity measure = plantMstrEntity.getMeasurmentMstrEntity();
            if (CommonUtil.objectNotNull(measure))
                plantRegisterDtlTO.setPlantClassMeasureName(measure.getName());
        }

        CompanyMstrEntity companyMstrEntity = entity.getCmpId();
        if (CommonUtil.objectNotNull(companyMstrEntity)) {
            plantRegisterDtlTO.setCmpId(companyMstrEntity.getId());
            plantRegisterDtlTO.setCmpCode(companyMstrEntity.getCode());
            plantRegisterDtlTO.setCmpName(companyMstrEntity.getName());
        }

        CompanyMstrEntity ownerCompanyMstrEntity = entity.getOwnerCmpId();
        if (CommonUtil.objectNotNull(ownerCompanyMstrEntity)) {
            plantRegisterDtlTO.setOwnerCmpId(ownerCompanyMstrEntity.getId());
            plantRegisterDtlTO.setOwnerCmpCode(ownerCompanyMstrEntity.getCode());
            plantRegisterDtlTO.setOwnerCmpName(ownerCompanyMstrEntity.getName());
        }

        ProcureCatgDtlEntity procureCatgDtlEntity = entity.getProcurecatgId();
        if (CommonUtil.objectNotNull(procureCatgDtlEntity)) {
            plantRegisterDtlTO.setProcurecatgId(procureCatgDtlEntity.getId());
            plantRegisterDtlTO.setProcurecatgCode(procureCatgDtlEntity.getCode());
            plantRegisterDtlTO.setProcurecatgName(procureCatgDtlEntity.getName());
        }

        plantRegisterDtlTO.setDesc(entity.getDesc());
        plantRegisterDtlTO.setAssertId(entity.getAssertId());
        plantRegisterDtlTO.setRegNumber(entity.getRegNumber());
        plantRegisterDtlTO.setManfacture(entity.getManfacture());
        plantRegisterDtlTO.setModel(entity.getModel());
        plantRegisterDtlTO.setAssetType(entity.getAssetType());
        plantRegisterDtlTO.setStatus(entity.getStatus());
        return plantRegisterDtlTO;
    }

    public static List<PlantRegisterDtlEntity> populatePlantRegisterEntities(
            List<PlantRegisterDtlTO> plantRegisterDtlTOs, EPSProjRepository epsProjRepository,
            ClientRegRepository clientRegRepository, PlantClassRepository plantClassRepository,
            CompanyRepository companyRepository, ProcureCatgRepository procureCatgRepository) {
        List<PlantRegisterDtlEntity> plantRegisterDtlEntites = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (PlantRegisterDtlTO plantRegisterDtlTO : plantRegisterDtlTOs) {
            plantRegisterDtlEntites.add(convertPOJOToEntity(plantRegisterDtlTO, epsProjRepository, clientRegRepository,
                    plantClassRepository, companyRepository, procureCatgRepository));
        }
        return plantRegisterDtlEntites;
    }

    public static PlantRegisterDtlEntity convertPOJOToEntity(PlantRegisterDtlTO plantRegisterDtlTO,
            EPSProjRepository epsProjRepository, ClientRegRepository clientRegRepository,
            PlantClassRepository plantClassRepository, CompanyRepository companyRepository,
            ProcureCatgRepository procureCatgRepository) {
        PlantRegisterDtlEntity entity = new PlantRegisterDtlEntity();

        if (CommonUtil.isNonBlankLong(plantRegisterDtlTO.getId())) {
            entity.setId(plantRegisterDtlTO.getId());
        }
        entity.setProjMstrEntity(
                (plantRegisterDtlTO.getProjId() != null) ? epsProjRepository.findOne(plantRegisterDtlTO.getProjId())
                        : null);
        entity.setClientId(
                (AppUserUtils.getClientId() != null) ? clientRegRepository.findOne(AppUserUtils.getClientId()) : null);
        entity.setAssertId(plantRegisterDtlTO.getAssertId());
        entity.setRegNumber(plantRegisterDtlTO.getRegNumber());
        entity.setDesc(plantRegisterDtlTO.getDesc());
        entity.setManfacture(plantRegisterDtlTO.getManfacture());
        entity.setModel(plantRegisterDtlTO.getModel());
        entity.setAssetType(plantRegisterDtlTO.getAssetType());
        entity.setPlantClassMstrId((plantRegisterDtlTO.getPlantClassMstrId() != null)
                ? plantClassRepository.findOne(plantRegisterDtlTO.getPlantClassMstrId())
                : null);
        entity.setCmpId(
                (plantRegisterDtlTO.getCmpId() != null) ? companyRepository.findOne(plantRegisterDtlTO.getCmpId())
                        : null);
        entity.setOwnerCmpId((plantRegisterDtlTO.getOwnerCmpId() != null)
                ? companyRepository.findOne(plantRegisterDtlTO.getOwnerCmpId())
                : null);
        entity.setProcurecatgId((plantRegisterDtlTO.getProcurecatgId() != null)
                ? procureCatgRepository.findOne(plantRegisterDtlTO.getProcurecatgId())
                : null);
        entity.setStatus(plantRegisterDtlTO.getStatus());
        return entity;
    }

}
