package com.rjtech.centrallib.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.CostMstrEntity;
import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.EmpWageMstrEntity;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.model.PlantServiceClassificationMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.model.ServiceMstrEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.model.WeatherMstrEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.CostCodeRepository;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.EmpWageRepository;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.repository.PlantServiceClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.centrallib.repository.ServiceRepository;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.centrallib.repository.WeatherRepository;
import com.rjtech.centrallib.req.UserReq;
import com.rjtech.centrallib.resp.CentLibMapResp;
import com.rjtech.centrallib.resp.CompanyListMapResp;
import com.rjtech.centrallib.resp.EmpClassMapResp;
import com.rjtech.centrallib.resp.PlantClassMapResp;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.service.CenterLibMapService;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "centerLibMapService")
@RJSService(modulecode = "centerLibMapService")
@Transactional
public class CentralLibMapServiceImpl implements CenterLibMapService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private EmpClassRepository empClassRepository;

    @Autowired
    private PlantClassRepository plantClassRepository;

    @Autowired
    private MaterialClassRepository materialClassRepository;

    @Autowired
    private CostCodeRepository costCodeRepository;

    @Autowired
    private EmpWageRepository empWageRepository;

    @Autowired
    private ProcureCatgRepository procureCatgRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PlantServiceClassRepository plantServiceClassRepository;

    public CentLibMapResp getCompanyListMap(UserReq userReq) {

        CentLibMapResp companyMapResp = new CentLibMapResp();
        List<CompanyMstrEntity> companyMstrEntites = companyRepository.findAllCompanies(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(companyMstrEntites)) {
            StringBuilder stringBuilder;
            for (CompanyMstrEntity CompanyMstrEntity : companyMstrEntites) {
                if (CommonUtil.isNotBlankStr(CompanyMstrEntity.getCode())) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(CompanyMstrEntity.getCode().toUpperCase().trim() + " ");
                    if (CommonUtil.isNotBlankStr(CompanyMstrEntity.getRegNo()))
                        stringBuilder.append(CompanyMstrEntity.getRegNo().trim() + " ");
                    if (CommonUtil.isNotBlankStr(CompanyMstrEntity.getTaxFileNo()))
                        stringBuilder.append(CompanyMstrEntity.getTaxFileNo().trim());
                    companyMapResp.getUniqueCodeMap().put(stringBuilder.toString(), CompanyMstrEntity.getStatus());
                }
            }
        }
        return companyMapResp;

    }

    public CentLibMapResp measurementMap(UserReq userReq) {
        CentLibMapResp measurementMapResp = new CentLibMapResp();
        List<MeasurmentMstrEntity> measurmentMstrEntites = measurementRepository
                .findAllMesurements(AppUserUtils.getClientId());

        if (CommonUtil.isListHasData(measurmentMstrEntites)) {

            for (MeasurmentMstrEntity measurmentMstrEntity : measurmentMstrEntites) {

                if (CommonUtil.isNotBlankStr(measurmentMstrEntity.getCode())) {

                    measurementMapResp.getUniqueCodeMap()
                            .put(measurmentMstrEntity.getCode().trim() + " "
                                    + measurmentMstrEntity.getName().toUpperCase().trim() + " "
                                    + measurmentMstrEntity.getProcureClassName().trim(),
                                    measurmentMstrEntity.getStatus());

                }
            }
        }
        return measurementMapResp;
    }

    public CentLibMapResp getWeatherDetailsMap(UserReq userReq) {
        CentLibMapResp centLibMapResp = new CentLibMapResp();
        List<WeatherMstrEntity> weatherMstrEntites = weatherRepository.findAllWeathers(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(weatherMstrEntites)) {
            for (WeatherMstrEntity weatherMstrEntity : weatherMstrEntites) {
                if (CommonUtil.isNotBlankStr(weatherMstrEntity.getCode())) {
                    centLibMapResp.getUniqueCodeMap().put(weatherMstrEntity.getCode().toUpperCase().trim(),
                            weatherMstrEntity.getStatus());
                }
            }
        }
        return centLibMapResp;
    }

    public CentLibMapResp getEmpClassMap(UserReq userReq) {
        CentLibMapResp measurementMapResp = new CentLibMapResp();
        List<EmpClassMstrEntity> empClassMstrEntites = empClassRepository.findAllEmpClass(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(empClassMstrEntites)) {
            for (EmpClassMstrEntity empClassMstrEntity : empClassMstrEntites) {
                if (CommonUtil.isNotBlankStr(empClassMstrEntity.getCode())) {
                    measurementMapResp.getUniqueCodeMap().put(empClassMstrEntity.getCode().toUpperCase().trim(),
                            empClassMstrEntity.getStatus());
                }
            }
        }
        return measurementMapResp;
    }

    public CentLibMapResp getPlantClassMap(UserReq userReq) {
        CentLibMapResp centLibMapResp = new CentLibMapResp();
        List<PlantMstrEntity> plantMstrEntities = plantClassRepository.findAllPlantClass(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(plantMstrEntities)) {
            for (PlantMstrEntity plantMstrEntity : plantMstrEntities) {
                if (CommonUtil.isNotBlankStr(plantMstrEntity.getCode())) {
                    centLibMapResp.getUniqueCodeMap()
                            .put(plantMstrEntity.getCode().toUpperCase().trim() + " "
                                    + plantMstrEntity.getMeasurmentMstrEntity().getName().trim(),
                                    plantMstrEntity.getStatus());
                }
            }
        }
        return centLibMapResp;
    }

    public CentLibMapResp getMaterialClassMap(UserReq userReq) {
        CentLibMapResp centLibMapResp = new CentLibMapResp();
        List<MaterialClassMstrEntity> materialClassMstrEntities = materialClassRepository
                .findMaterialItems(AppUserUtils.getClientId(), StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isListHasData(materialClassMstrEntities)) {
            for (MaterialClassMstrEntity materialClassMstrEntity : materialClassMstrEntities) {
                if (CommonUtil.isNotBlankStr(materialClassMstrEntity.getCode())) {
                    centLibMapResp.getUniqueCodeMap()
                            .put(materialClassMstrEntity.getCode().toUpperCase().trim() + " "
                                    + materialClassMstrEntity.getMeasurmentMstrEntity().getName().trim(),
                                    materialClassMstrEntity.getStatus());
                }
            }
        }
        return centLibMapResp;
    }

    public CentLibMapResp getCostCodeClassMap(UserReq userReq) {
        CentLibMapResp centLibMapResp = new CentLibMapResp();
        List<CostMstrEntity> costMstrEntities = costCodeRepository.findAllCostCodes(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(costMstrEntities)) {
            for (CostMstrEntity costMstrEntity : costMstrEntities) {
                if (CommonUtil.isNotBlankStr(costMstrEntity.getCode())) {
                    centLibMapResp.getUniqueCodeMap().put(costMstrEntity.getCode().toUpperCase().trim(),
                            costMstrEntity.getStatus());
                }
            }
        }
        return centLibMapResp;
    }

    public CentLibMapResp getEmpWageFactorMap(UserReq userReq) {
        CentLibMapResp centLibMapResp = new CentLibMapResp();
        List<EmpWageMstrEntity> empWageMstrEntities = empWageRepository.findAllEmpWages(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(empWageMstrEntities)) {
            for (EmpWageMstrEntity empWageMstrEntity : empWageMstrEntities) {
                if (CommonUtil.isNotBlankStr(empWageMstrEntity.getCode())) {
                    centLibMapResp.getUniqueCodeMap().put(empWageMstrEntity.getCode().toUpperCase().trim(),
                            empWageMstrEntity.getStatus());
                }
            }
        }
        return centLibMapResp;
    }

    public CentLibMapResp getProcureCatgClassMap(UserReq userReq) {
        CentLibMapResp centLibMapResp = new CentLibMapResp();
        List<ProcureCatgDtlEntity> procureCatgDtlEntities = procureCatgRepository
                .findAllProcureCatgs(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(procureCatgDtlEntities)) {
            for (ProcureCatgDtlEntity procureCatgDtlEntity : procureCatgDtlEntities) {
                if (CommonUtil.isNotBlankStr(procureCatgDtlEntity.getCode())) {
                    centLibMapResp.getUniqueCodeMap()
                            .put(procureCatgDtlEntity.getProcureType().trim() + " "
                                    + procureCatgDtlEntity.getCode().toUpperCase().trim(),
                                    procureCatgDtlEntity.getStatus());
                }
            }
        }
        return centLibMapResp;
    }

    public CentLibMapResp getServiceClassMap(UserReq userReq) {
        CentLibMapResp centLibMapResp = new CentLibMapResp();
        List<ServiceMstrEntity> serviceMstrEntities = serviceRepository.findAllServices(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(serviceMstrEntities)) {
            for (ServiceMstrEntity serviceMstrEntity : serviceMstrEntities) {
                if (CommonUtil.isNotBlankStr(serviceMstrEntity.getCode())) {
                    centLibMapResp.getUniqueCodeMap().put(serviceMstrEntity.getCode().toUpperCase().trim(),
                            serviceMstrEntity.getStatus());
                }
            }
        }
        return centLibMapResp;
    }

    public CentLibMapResp getWareHouseMap(UserReq userReq) {
        CentLibMapResp centLibMapResp = new CentLibMapResp();
        List<StockMstrEntity> stockMstrEntities = stockRepository.findAllStockYards(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(stockMstrEntities)) {
            for (StockMstrEntity stockMstrEntity : stockMstrEntities) {
                if (CommonUtil.isNotBlankStr(stockMstrEntity.getCode())) {
                    if (CommonUtil.isNotBlankStr(stockMstrEntity.getCategory())) {
                        centLibMapResp.getUniqueCodeMap().put(stockMstrEntity.getCode().toUpperCase().trim() + " "
                                + stockMstrEntity.getCategory().trim(), stockMstrEntity.getStatus());
                    }
                }
            }
        }
        return centLibMapResp;
    }

    public CentLibMapResp getPlantServiceHistoryMap(UserReq userReq) {
        CentLibMapResp centLibMapResp = new CentLibMapResp();
        List<PlantServiceClassificationMstrEntity> classificationMstrEntities = plantServiceClassRepository
                .findAllPlantServices(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(classificationMstrEntities)) {
            for (PlantServiceClassificationMstrEntity plantServiceClassificationMstrEntity : classificationMstrEntities) {
                if (CommonUtil.isNotBlankStr(plantServiceClassificationMstrEntity.getCode())) {
                    centLibMapResp.getUniqueCodeMap().put(
                            plantServiceClassificationMstrEntity.getCode().toUpperCase().trim(),
                            plantServiceClassificationMstrEntity.getStatus());
                }
            }
        }
        return centLibMapResp;
    }

    public CompanyListMapResp getCompaniesMap(UserReq userReq) {
        CompanyListMapResp companyMapResp = new CompanyListMapResp();
        List<CompanyMstrEntity> companyMstrEntites = companyRepository.findAllCompanies(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(companyMstrEntites)) {
            LabelKeyTO companyLabelKeyTO = null;
            for (CompanyMstrEntity CompanyMstrEntity : companyMstrEntites) {
                companyLabelKeyTO = new LabelKeyTO();
                companyLabelKeyTO.setId(CompanyMstrEntity.getId());
                companyLabelKeyTO.setCode(CompanyMstrEntity.getCode());
                companyLabelKeyTO.setName(CompanyMstrEntity.getName());
                companyMapResp.getCompanyListMap().put(CompanyMstrEntity.getId(), companyLabelKeyTO);
            }
        }
        return companyMapResp;
    }

    public EmpClassMapResp getEmpClassificationMap(UserReq userReq) {
        EmpClassMapResp empClassMapResp = new EmpClassMapResp();
        List<EmpClassMstrEntity> empClassMstrEntites = empClassRepository.findByClientId(AppUserUtils.getClientId(),
                userReq.getStatus());
        if (CommonUtil.isListHasData(empClassMstrEntites)) {
            LabelKeyTO empClassLabelKeyTO = null;
            for (EmpClassMstrEntity empClassMstrEntity : empClassMstrEntites) {
                empClassLabelKeyTO = new LabelKeyTO();
                empClassLabelKeyTO.setId(empClassMstrEntity.getId());
                empClassLabelKeyTO.setCode(empClassMstrEntity.getCode());
                empClassLabelKeyTO.setName(empClassMstrEntity.getName());
                if (CommonUtil.objectNotNull(empClassMstrEntity.getMeasurmentMstrEntity())) {
                    empClassLabelKeyTO.setUnitOfMeasure(empClassMstrEntity.getMeasurmentMstrEntity().getName());
                }
                empClassMapResp.getEmpClassMap().put(empClassMstrEntity.getId(), empClassLabelKeyTO);
            }
        }
        return empClassMapResp;
    }

    public PlantClassMapResp getPlantClassificationMap(UserReq userReq) {
        PlantClassMapResp plantClassMapResp = new PlantClassMapResp();
        List<PlantMstrEntity> plantMstrEntities = plantClassRepository.findByClientId(AppUserUtils.getClientId(),
                userReq.getStatus());
        if (CommonUtil.isListHasData(plantMstrEntities)) {
            LabelKeyTO plantClassLabelKeyTO = null;
            for (PlantMstrEntity plantMstrEntity : plantMstrEntities) {
                plantClassLabelKeyTO = new LabelKeyTO();
                plantClassLabelKeyTO.setId(plantMstrEntity.getId());
                plantClassLabelKeyTO.setCode(plantMstrEntity.getCode());
                plantClassLabelKeyTO.setName(plantMstrEntity.getName());
                if (CommonUtil.objectNotNull(plantMstrEntity.getMeasurmentMstrEntity())) {
                    plantClassLabelKeyTO.setUnitOfMeasure(plantMstrEntity.getMeasurmentMstrEntity().getName());
                }
                plantClassMapResp.getPlantClassMap().put(plantMstrEntity.getId(), plantClassLabelKeyTO);
            }
        }
        return plantClassMapResp;
    }

}
