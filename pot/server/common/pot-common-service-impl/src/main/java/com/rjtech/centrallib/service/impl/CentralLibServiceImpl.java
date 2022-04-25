package com.rjtech.centrallib.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.dto.AddressTO;
import com.rjtech.centrallib.dto.CompanyProjectsTO;
import com.rjtech.centrallib.dto.ContactsTO;
import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.centrallib.dto.WeatherTO;
import com.rjtech.centrallib.dto.EmpClassTO;
import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.centrallib.dto.CompanyTO;
import com.rjtech.centrallib.model.AssetCategoryMstrEntity;
import com.rjtech.centrallib.model.AssetMaintenanceCategoryMstrEntity;
import com.rjtech.centrallib.model.BusinessActivityMstrEntity;
import com.rjtech.centrallib.model.CmpAddressEntity;
import com.rjtech.centrallib.model.CmpContactsEntity;
import com.rjtech.centrallib.model.CmpCurrentProjsEntity;
import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.CostMstrEntity;
import com.rjtech.centrallib.model.CountryProvinceCodeEntity;
import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.EmpWageMstrEntity;
import com.rjtech.centrallib.model.EmployeePayRoleEntity;
import com.rjtech.centrallib.model.FinanceCenterEntity;
import com.rjtech.centrallib.model.FinancialHalfYearEntity;
import com.rjtech.centrallib.model.FinancialQuarterYearEntity;
import com.rjtech.centrallib.model.FinancialYearEntity;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.NonRegularPayAllowanceEntity;
import com.rjtech.centrallib.model.PayDeductionCodeEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.model.PlantServiceClassificationMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.model.ProjLeaveCategoryType;
import com.rjtech.centrallib.model.ProjLeaveTypeEntity;
import com.rjtech.centrallib.model.RegularPayAllowanceEntity;
import com.rjtech.centrallib.model.ServiceMstrEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.model.SuperProvidentFundEntity;
import com.rjtech.centrallib.model.TangibleClassificationEntity;
import com.rjtech.centrallib.model.TaxPaymentReceiverCodeEntity;
import com.rjtech.centrallib.model.TaxRatesRulesCodeEntity;
import com.rjtech.centrallib.model.UnitOfPayRateEntity;
import com.rjtech.centrallib.model.ProjSOEItemEntityMeasureUnitCopy;
import com.rjtech.centrallib.model.WeatherMstrEntity;
import com.rjtech.centrallib.model.WorkDairyEntityWeatherCopy;
import com.rjtech.centrallib.model.PreContractsMaterialDtlEntityProCatCopy;
import com.rjtech.centrallib.model.PreContractsCmpEntityCLCopy; 
import com.rjtech.centrallib.repository.AddressRepository;
import com.rjtech.centrallib.repository.AssetCategoryRepository;
import com.rjtech.centrallib.repository.AssetMaintenanceCategoryRepository;
import com.rjtech.centrallib.repository.BusinessActRepository;
import com.rjtech.centrallib.repository.CompanyCurrentProjRepository;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.ContactsRepository;
import com.rjtech.centrallib.repository.CostCodeRepository;
import com.rjtech.centrallib.repository.CountryProvinceCodeRepository;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.EmpWageRepository;
import com.rjtech.centrallib.repository.FinanceCenterRecordRepository;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.repository.PlantServiceClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.centrallib.repository.ProjLeaveTypeRepository;
import com.rjtech.centrallib.repository.ServiceRepository;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.centrallib.repository.TangibleClassRepository;
import com.rjtech.centrallib.repository.WeatherRepository;
import com.rjtech.centrallib.repository.TaxRatesRulesCodeDtlRepository;
import com.rjtech.centrallib.repository.ProjSOEItemRepositoryMeasureUnitCopy;
import com.rjtech.centrallib.repository.WorkDairyRepositoryWeatherCopy;
import com.rjtech.centrallib.repository.UserProjectsRepositoryEmpCopy;
import com.rjtech.centrallib.repository.PrecontractMaterialRepositoryProcatCopy;
import com.rjtech.centrallib.repository.PreContractCmpRepositoryCopy;
import com.rjtech.centrallib.req.AddressDelReq;
import com.rjtech.centrallib.req.AddressSaveReq;
import com.rjtech.centrallib.req.AssetCategoryDelReq;
import com.rjtech.centrallib.req.AssetCategoryGetReq;
import com.rjtech.centrallib.req.AssetCategorySaveReq;
import com.rjtech.centrallib.req.AssetMaintenanceCategoryDelReq;
import com.rjtech.centrallib.req.AssetMaintenanceCategoryGetReq;
import com.rjtech.centrallib.req.AssetMaintenanceCategorySaveReq;
import com.rjtech.centrallib.req.BusinessActDeleteReq;
import com.rjtech.centrallib.req.CompanyDelReq;
import com.rjtech.centrallib.req.CompanyFilterReq;
import com.rjtech.centrallib.req.CompanyGetReq;
import com.rjtech.centrallib.req.CompanyProjDelReq;
import com.rjtech.centrallib.req.CompanyProjSaveReq;
import com.rjtech.centrallib.req.CompanySaveReq;
import com.rjtech.centrallib.req.ContactDelReq;
import com.rjtech.centrallib.req.ContactsSaveReq;
import com.rjtech.centrallib.req.CompanyBankSaveReq;
import com.rjtech.centrallib.req.CostCodeDelReq;
import com.rjtech.centrallib.req.CostCodeSaveReq;
import com.rjtech.centrallib.req.CountryProvinceCodeDeactiveReq;
import com.rjtech.centrallib.req.CountryProvinceCodeGetReq;
import com.rjtech.centrallib.req.CountryProvinceCodeSaveReq;
import com.rjtech.centrallib.req.EmpClassDelReq;
import com.rjtech.centrallib.req.EmpClassFilterReq;
import com.rjtech.centrallib.req.EmpClassesSaveReq;
import com.rjtech.centrallib.req.EmpWageDelReq;
import com.rjtech.centrallib.req.EmpWageSaveReq;
import com.rjtech.centrallib.req.EmpWagesFilterReq;
import com.rjtech.centrallib.req.FinanceCenterCodeGetReq;
import com.rjtech.centrallib.req.FinanceCenterDeactiveReq;
import com.rjtech.centrallib.req.FinanceCenterSaveReq;
import com.rjtech.centrallib.req.FinanceCenterFilterReq;
import com.rjtech.centrallib.req.MaterialClassDelReq;
import com.rjtech.centrallib.req.MaterialClassFilterReq;
import com.rjtech.centrallib.req.MaterialClassGetReq;
import com.rjtech.centrallib.req.MaterialClassSaveReq;
import com.rjtech.centrallib.req.MeasureDelReq;
import com.rjtech.centrallib.req.MeasureUnitReq;
import com.rjtech.centrallib.req.MesureFilterReq;
import com.rjtech.centrallib.req.PlantClassDelReq;
import com.rjtech.centrallib.req.PlantClassFilterReq;
import com.rjtech.centrallib.req.PlantClassSavereq;
import com.rjtech.centrallib.req.PlantServiceClassDeactivateReq;
import com.rjtech.centrallib.req.PlantServiceClassGetReq;
import com.rjtech.centrallib.req.PlantServiceClassSaveReq;
import com.rjtech.centrallib.req.ProcureCatgDelReq;
import com.rjtech.centrallib.req.ProcureCatgFilterReq;
import com.rjtech.centrallib.req.ProcureCatgSaveReq;
import com.rjtech.centrallib.req.RegisterOnLoadReq;
import com.rjtech.centrallib.req.ServiceClassDelReq;
import com.rjtech.centrallib.req.ServiceFiltterReq;
import com.rjtech.centrallib.req.ServiceSaveReq;
import com.rjtech.centrallib.req.StockDelReq;
import com.rjtech.centrallib.req.StockFilterReq;
import com.rjtech.centrallib.req.StockSaveReq;
import com.rjtech.centrallib.req.TangibleClassDelReq;
import com.rjtech.centrallib.req.TangibleClassFilterReq;
import com.rjtech.centrallib.req.TangibleClassGetReq;
import com.rjtech.centrallib.req.TangibleClassSaveReq;
import com.rjtech.centrallib.req.WeatherDelReq;
import com.rjtech.centrallib.req.WeatherFilterReq;
import com.rjtech.centrallib.req.WeatherReq;
import com.rjtech.centrallib.req.costCodeFilterReq;
import com.rjtech.centrallib.resp.AddressResp;
import com.rjtech.centrallib.resp.AssetCategoryResp;
import com.rjtech.centrallib.resp.AssetMaintenanceCategoryResp;
import com.rjtech.centrallib.resp.CmpCurrentProjsResp;
import com.rjtech.centrallib.resp.CompanyResp;
import com.rjtech.centrallib.resp.ContactsResp;
import com.rjtech.centrallib.resp.CostCodeResp;
import com.rjtech.centrallib.resp.CountryProvinceCodeResp;
import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.EmpWageResp;
import com.rjtech.centrallib.resp.FinanceCenterResp;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.centrallib.resp.MeasureUnitResp;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.centrallib.resp.PlantServiceClassResp;
import com.rjtech.centrallib.resp.ProcureCatgResp;
import com.rjtech.centrallib.resp.ProcurementCompanyResp;
import com.rjtech.centrallib.resp.ServiceResp;
import com.rjtech.centrallib.resp.StockResp;
import com.rjtech.centrallib.resp.TangibleClassResp;
import com.rjtech.centrallib.resp.WeatherResp;
import com.rjtech.centrallib.service.handler.AddressHandler;
import com.rjtech.centrallib.service.handler.AssetCategoryHandler;
import com.rjtech.centrallib.service.handler.AssetMaintenanceCategoryHandler;
import com.rjtech.centrallib.service.handler.CompanyCurrentProjsHandler;
import com.rjtech.centrallib.service.handler.CompanyHandler;
import com.rjtech.centrallib.service.handler.ContactsHandler;
import com.rjtech.centrallib.service.handler.CostCodeHandler;
import com.rjtech.centrallib.service.handler.CountryProvinceCodeHandler;
import com.rjtech.centrallib.service.handler.EmpClassHandler;
import com.rjtech.centrallib.service.handler.EmpWageHandler;
import com.rjtech.centrallib.service.handler.FinanceCenterHandler;
import com.rjtech.centrallib.service.handler.MaterialClassHandler;
import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.centrallib.service.handler.PlantClassHandler;
import com.rjtech.centrallib.service.handler.PlantServiceClassificationMstrHandler;
import com.rjtech.centrallib.service.handler.ProcureCatgHandler;
import com.rjtech.centrallib.service.handler.ServiceClassHandler;
import com.rjtech.centrallib.service.handler.StockHandler;
import com.rjtech.centrallib.service.handler.TangibleClassHandler;
import com.rjtech.centrallib.service.handler.WeatherHandler;
import com.rjtech.centrallib.service.handler.TaxRatesRulesCodeDtlHandler;
import com.rjtech.common.dto.FinanceCenterRecordTo;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserProjectsEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.service.CentralLibService;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.centrallib.req.TaxRatesRulesCodeDtlSaveReq;
import com.rjtech.centrallib.model.TaxRatesRulesCodeDtlEntity;
//import com.rjtech.centrallib.dto.TaxRatesRulesCodeDtlCodeTo;
import com.rjtech.common.dto.TaxRatesRulesCodeDtlCodeTo;
import com.rjtech.centrallib.repository.BankAccountRepository;
import com.rjtech.centrallib.service.handler.BankAccountHandler;
import com.rjtech.centrallib.resp.CmpBankDetailsResp;
import com.rjtech.centrallib.model.CmpBankAccountEntity;
import com.rjtech.centrallib.req.BankDelReq;

@Service(value = "centralLibService")
@RJSService(modulecode = "centralLibService")
@Transactional
public class CentralLibServiceImpl implements CentralLibService {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private EmpClassRepository empClassRepository;

    @Autowired
    private PlantClassRepository plantClassRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private CompanyCurrentProjRepository companyCurrentProjRepository;

    @Autowired
    private StockRepository stockRepository;

    private BusinessActRepository businessActRepository;

    @Autowired
    private EmpWageRepository empWageRepository;

    @Autowired
    private CostCodeRepository costCodeRepository;

    @Autowired
    private ProcureCatgRepository procureCatgRepository;

    @Autowired
    private PlantServiceClassRepository plantServiceClassRepository;

    @Autowired
    private MaterialClassRepository materialClassRepository;

    @Autowired
    private AssetCategoryRepository assetCategoryRepository;

    @Autowired
    private AssetMaintenanceCategoryRepository assetMaintenanceCategoryRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private CountryProvinceCodeRepository countryProvinceCodeRepository;

    @Autowired
    private ClientRegRepository clientRegRepository;

    @Autowired
    private FinanceCenterRecordRepository financeCenterRecordRepository;

    @Autowired
    private ProjLeaveTypeRepository projLeaveTypeRepository;
    
    @Autowired
    private TangibleClassRepository  tangibleClassRepository;
    
    @Autowired
    private TaxRatesRulesCodeDtlRepository taxRatesRulesCodeDtlRepository;
    
    @Autowired
    private ProjSOEItemRepositoryMeasureUnitCopy  projSOEItemRepositoryMeasureUnitCopy; 
    
    @Autowired
    private WorkDairyRepositoryWeatherCopy workDairyRepositoryWeatherCopy;
    
    @Autowired
    private UserProjectsRepositoryEmpCopy UserProjectsRepositoryEmpCopy;

    @Autowired
    private PrecontractMaterialRepositoryProcatCopy precontractMaterialRepositoryProcatCopy;

    @Autowired
    private PreContractCmpRepositoryCopy preContractCmpRepositoryCopy;
        
    @Autowired
    private BankAccountRepository bankAccountRepository;
    
    public MeasureUnitResp getMeasurements(MesureFilterReq mesureFilterReq) {
        // a
        String mesureCode = mesureFilterReq.getMesureCode();
        String mesureName = mesureFilterReq.getMesureName();

        if (CommonUtil.isNotBlankStr(mesureCode)) {
            mesureCode = CommonUtil.appendLikeOperator(mesureCode);
        }

        if (CommonUtil.isNotBlankStr(mesureName)) {
            mesureName = CommonUtil.appendLikeOperator(mesureName);
        }
        MeasureUnitResp measureUnitResp = MeasurementHandler.convertEntityToPOJO(measurementRepository.findMesures(AppUserUtils.getClientId(),
				mesureCode, mesureName, mesureFilterReq.getStatus()));
		List<Long> measureUnitIds = projSOEItemRepositoryMeasureUnitCopy.getAllMeasureUnits();

		for (MeasureUnitTO measureUnitTO : measureUnitResp.getMeasureUnitTOs()) {
			if (measureUnitIds.indexOf(measureUnitTO.getId()) != -1) {
				measureUnitTO.setIsAssigned(true);
			} else {
				measureUnitTO.setIsAssigned(false);
			}
		}
        return measureUnitResp;
    }


    public MeasureUnitResp getMeasuresByProcureType(MesureFilterReq mesureFilterReq) {
        return MeasurementHandler.convertEntityToPOJO(measurementRepository.findByProcureType(
                AppUserUtils.getClientId(), mesureFilterReq.getProcureClassName(), mesureFilterReq.getStatus()));
    }

    public void saveMeasurements(MeasureUnitReq measureUnitReq) {
        measurementRepository.save(MeasurementHandler.convertPOJOToEntity(measureUnitReq.getMeasureUnitTOs()));
    }

    public void deleteMeasurements(MeasureDelReq measureDelReq) {
        List<MeasurmentMstrEntity> measurmentMstrEnties = new ArrayList<MeasurmentMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        List<MeasurmentMstrEntity> measurmentMstrEntitys = measurementRepository.findAll(measureDelReq.getMeasureIds());
        for (MeasurmentMstrEntity measurmentMstrEntity : measurmentMstrEntitys) {
            if (CommonUtil.objectNotNull(measurmentMstrEntity)) {
                measurmentMstrEntity.setStatus(measureDelReq.getStatus());
                measurmentMstrEnties.add(measurmentMstrEntity);
            }
        }
        measurementRepository.save(measurmentMstrEnties);
    }

    /*
     * public WeatherResp getWeatherDetails(UserReq userReq) { return WeatherHandler
     * .convertEntityToPOJO(weatherRepository.findByClientId(userReq.getClientId (),
     * userReq.getStatus())); }
     */

    public WeatherResp getWeatherDetails(WeatherFilterReq weatherFilterReq) {
        String weatherCode = weatherFilterReq.getWeatherCode();
        String weatherName = weatherFilterReq.getWeatherName();

        if (CommonUtil.isNotBlankStr(weatherCode)) {
            weatherCode = CommonUtil.appendLikeOperator(weatherCode);
        }

        if (CommonUtil.isNotBlankStr(weatherName)) {
            weatherName = CommonUtil.appendLikeOperator(weatherName);
        }
        WeatherResp weatherResp = WeatherHandler.convertEntityToPOJO(weatherRepository.findweathers(AppUserUtils.getClientId(),
                weatherCode, weatherName, weatherFilterReq.getStatus()));
        List<Long> weatherList = workDairyRepositoryWeatherCopy.getAllWeatherIds();
        for(WeatherTO weatherTO :weatherResp.getWeatherTOs()) {
        		if(weatherList.indexOf(weatherTO.getId())!=-1) {
        			weatherTO.setWeatherAssigned(true);
        		}else {
        			weatherTO.setWeatherAssigned(false);
        		}
        }
        return weatherResp;
    }

    public void saveWeatherDetails(WeatherReq weatherReq) {
        weatherRepository.save(WeatherHandler.convertPOJOToEntity(weatherReq.getWeatherTOs()));
    }

    public void deleteWeatherDetails(WeatherDelReq weatherDelReqd) {
        List<WeatherMstrEntity> weatherMstrEnties = new ArrayList<WeatherMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        List<WeatherMstrEntity> weatherMstrEntitys = weatherRepository.findAll(weatherDelReqd.getWeatherIds());
        for (WeatherMstrEntity weatherMstrEntity : weatherMstrEntitys) {
            if (CommonUtil.objectNotNull(weatherMstrEntity)) {
                weatherMstrEntity.setStatus(weatherDelReqd.getStatus());
                weatherMstrEnties.add(weatherMstrEntity);
            }
        }
        weatherRepository.save(weatherMstrEnties);
    }

    public EmpClassesResp getEmpClasses(EmpClassFilterReq empClassFilterReq) {
        String empCode = empClassFilterReq.getEmpCode();
        String empName = empClassFilterReq.getEmpName();

        if (CommonUtil.isNotBlankStr(empCode)) {
            empCode = CommonUtil.appendLikeOperator(empCode);
        }

        if (CommonUtil.isNotBlankStr(empName)) {
            empName = CommonUtil.appendLikeOperator(empName);
        }
        List<Long> empList = UserProjectsRepositoryEmpCopy.getUsersOfProjects();
        EmpClassesResp empClassesResp = EmpClassHandler.convertEntityToPOJO(empClassRepository.findEmpClass(AppUserUtils.getClientId(), empCode,
                empName, empClassFilterReq.getStatus()));
        
        for(EmpClassTO empClassTO : empClassesResp.getEmpClassTOs()) {
        	
        	if(empList.indexOf(empClassTO.getId())!=-1) {
        		
        		empClassTO.setEmpAssigned(true);
        	} else {
        		
        		empClassTO.setEmpAssigned(false);
        	}
        }
        return empClassesResp;
    }

    public void saveEmpClasses(EmpClassesSaveReq empClassesSaveReq) {
        empClassRepository.save(EmpClassHandler.convertPOJOToEntity(empClassesSaveReq, measurementRepository));

    }

    public void deleteEmpClasses(EmpClassDelReq empClassDelReq) {
        List<EmpClassMstrEntity> empClassMstrEntities = new ArrayList<EmpClassMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        List<EmpClassMstrEntity> empClassMstrEntitys = empClassRepository.findAll(empClassDelReq.getEmpClassIds());
        for (EmpClassMstrEntity empClassMstrEntity : empClassMstrEntitys) {
            if (CommonUtil.objectNotNull(empClassMstrEntity)) {
                empClassMstrEntity.setStatus(empClassDelReq.getStatus());
                empClassMstrEntities.add(empClassMstrEntity);
            }
        }
        empClassRepository.save(empClassMstrEntities);
    }

    public PlantClassResp getPlantClasses(PlantClassFilterReq plantClassFilterReq) {
        String plantCode = plantClassFilterReq.getPlantCode();
        String plantName = plantClassFilterReq.getPlantName();
        if (CommonUtil.isNotBlankStr(plantCode)) {
            plantCode = CommonUtil.appendLikeOperator(plantCode);
        }

        if (CommonUtil.isNotBlankStr(plantName)) {
            plantName = CommonUtil.appendLikeOperator(plantName);
        }

        return PlantClassHandler.convertEntityToPOJO(plantClassRepository.findPlantClass(AppUserUtils.getClientId(),
                plantCode, plantName, plantClassFilterReq.getStatus()));
    }

    public void savePlantClasses(PlantClassSavereq plantClassSavereq) {
        plantClassRepository.save(PlantClassHandler.convertPOJOToEntity(plantClassSavereq, measurementRepository));

    }

    public void deletePlantClasses(PlantClassDelReq plantClassDelReq) {
        List<PlantMstrEntity> plantMstrEntities = new ArrayList<PlantMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        List<PlantMstrEntity> plantMstrEntitys = plantClassRepository.findAll(plantClassDelReq.getPlantClassIds());
        for (PlantMstrEntity plantMstrEntity : plantMstrEntitys) {
            if (CommonUtil.objectNotNull(plantMstrEntity)) {
                plantMstrEntity.setStatus(plantClassDelReq.getStatus());
                plantMstrEntities.add(plantMstrEntity);
            }
        }
        plantClassRepository.save(plantMstrEntities);
    }

    public ServiceResp getServiceClasses(ServiceFiltterReq serviceFiltterReq) {
        String serviceCode = serviceFiltterReq.getServiceCode();
        String serviceName = serviceFiltterReq.getServiceName();

        if (CommonUtil.isNotBlankStr(serviceCode)) {
            serviceCode = CommonUtil.appendLikeOperator(serviceCode);
        }

        if (CommonUtil.isNotBlankStr(serviceName)) {
            serviceName = CommonUtil.appendLikeOperator(serviceName);
        }

        List<ServiceMstrEntity> serviceMstrEntity = serviceRepository.findServices(AppUserUtils.getClientId(),
                serviceCode, serviceName, serviceFiltterReq.getStatus());

        return ServiceClassHandler.convertEntityToPOJO(serviceMstrEntity);
    }

    public void saveServiceClasses(ServiceSaveReq serviceSaveReq) {
        serviceRepository.save(ServiceClassHandler.convertPOJOToEntity(serviceSaveReq));
    }

    public void deleteServiceClasses(ServiceClassDelReq serviceClassDelReq) {
        List<ServiceMstrEntity> serviceMstrEnties = new ArrayList<ServiceMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        List<ServiceMstrEntity> serviceMstrEntitys = serviceRepository.findAll(serviceClassDelReq.getServiceClassIds());
        for (ServiceMstrEntity serviceMstrEntity : serviceMstrEntitys) {
            if (CommonUtil.objectNotNull(serviceMstrEntity)) {
                serviceMstrEntity.setStatus(serviceClassDelReq.getStatus());
                serviceMstrEnties.add(serviceMstrEntity);
            }
        }
        serviceRepository.save(serviceMstrEnties);
    }

    public void saveCompany(CompanySaveReq companySaveReq) {
        companyRepository.save(CompanyHandler.convertPOJOToEntity(companySaveReq));
    }

    public CompanyResp getCompanies(CompanyFilterReq companyFilterReq) {
        String cmpCode = companyFilterReq.getCmpCode();
        String cmpName = companyFilterReq.getCmpName();

        if (CommonUtil.isNotBlankStr(cmpCode)) {
            cmpCode = CommonUtil.appendLikeOperator(cmpCode);
        }

        if (CommonUtil.isNotBlankStr(cmpName)) {
            cmpName = CommonUtil.appendLikeOperator(cmpName);
        }
        CompanyResp companyResp = CompanyHandler.populateCompanyies(companyRepository.findCompanies(AppUserUtils.getClientId(), cmpCode,
                cmpName, companyFilterReq.getStatus()));
        List<Long> companyIds = preContractCmpRepositoryCopy.getPreContractCmpIds();
        
        for(CompanyTO companyTO: companyResp.getCompanyTOs()) {
			if (companyIds.indexOf(companyTO.getId()) != -1) {
				companyTO.setProjectCompany(true);
			} else {
				companyTO.setProjectCompany(false);
			}
        }
        return companyResp;
    }

    public ProcurementCompanyResp getCompaniesWithDefaultAddressAndContact(CompanyFilterReq companyFilterReq) {
        ProcurementCompanyResp procurementCompanyResp = new ProcurementCompanyResp();
        List<CompanyMstrEntity> companyMstrEntities = companyRepository.findCompanies(AppUserUtils.getClientId(), null,
                null, companyFilterReq.getStatus());
        for (CompanyMstrEntity companyMstrEntity : companyMstrEntities) {
            procurementCompanyResp.getCompanyTOs().add(CompanyHandler.convertEntityFromPOJO(companyMstrEntity));
        }
        procurementCompanyResp.setAddressMap(getDefaultCompanyAddressesByClientId());
        procurementCompanyResp.setContactsMap(getDefaultCompanyContactsByClientId());
        return procurementCompanyResp;
    }

    public CompanyResp getCompanyDetails(CompanyGetReq companyGetReq) {
        return CompanyHandler.populateCompanyDetails(companyRepository.findCompanyDetails(AppUserUtils.getClientId(),
                companyGetReq.getCmpId(), companyGetReq.getStatus()));
    }

    public ProcurementCompanyResp getCompaniesDetailsByCmpIds(CompanyGetReq companyGetReq) {
        return CompanyHandler.populateCompanyDetailsForProcurement(companyRepository.findCompaniesDetailsByCmpIds(
                AppUserUtils.getClientId(), companyGetReq.getCmpIds(), companyGetReq.getStatus()));
    }

    public void deleteCompanies(CompanyDelReq companyDelReq) {
        companyRepository.deactivateCompanies(companyDelReq.getCompanyIds(), companyDelReq.getStatus());
    }

    public void saveAddress(AddressSaveReq AddressSaveReq) {

        addressRepository.save(AddressHandler.convertPOJOToEntity(AddressSaveReq));
    }

    public void deleteAddress(AddressDelReq addressDelReq) {
        addressRepository.deactivateAddress(addressDelReq.getAddressIds(), addressDelReq.getStatus());
    }

    public void saveContacts(ContactsSaveReq contactsSaveReq) {

        contactsRepository.save(ContactsHandler.convertPOJOToEntity(contactsSaveReq));
    }

    public void deleteContacts(ContactDelReq contactDelReq) {
        contactsRepository.deactivateContacts(contactDelReq.getContactIds(), contactDelReq.getStatus());
    }

    public void saveCompanyCurrentProjs(CompanyProjSaveReq companyProjSaveReq) {
        companyCurrentProjRepository
                .save(CompanyCurrentProjsHandler.convertPOJOToEntity(companyProjSaveReq, epsProjRepository));
    }
    
    public void saveCompanyBankDetails(CompanyBankSaveReq companyBankSaveReq) {
    	System.out.println("companyBankSaveReqqqqqqq======@@#@E@$@$@$"+companyBankSaveReq);
    	
   // 	addressRepository.save(AddressHandler.convertPOJOToEntity(AddressSaveReq));
    	bankAccountRepository.save(BankAccountHandler.convertPOJOToEntity(companyBankSaveReq,bankAccountRepository));
    	System.out.println("companyBankSaveReqqqqqqq@@#@E@$@$@$"+companyBankSaveReq);
    	
		/*
		 * companyCurrentProjRepository
		 * .save(CompanyCurrentProjsHandler.convertPOJOToEntity(companyProjSaveReq,
		 * epsProjRepository));
		 */
    }
    
    public void deleteCompanyCurrentProjs(CompanyProjDelReq companyProjDelReq) {
        companyCurrentProjRepository.deactivateCurrentProjs(companyProjDelReq.getCmpProjIds(),
                companyProjDelReq.getStatus());

    }

    public void deleteCompanyClosedProjs(CompanyProjDelReq companyProjDelReq) {
        for (Long CompanyClosProjsId : companyProjDelReq.getCmpProjIds()) {
            companyCurrentProjRepository.delete(CompanyClosProjsId);
        }
    }

    public void moveToCmpClosProjs(CompanyProjSaveReq companyProjSaveReq) {
        List<Long> projIds = new ArrayList<Long>();
        for (CompanyProjectsTO proj : companyProjSaveReq.getCompanyProjectsTOs()) {
            projIds.add(proj.getId());
        }
        if (CommonUtil.isListHasData(projIds))
            companyCurrentProjRepository.closeProjs(projIds);
    }

    public void saveStock(StockSaveReq stockSaveReq) {
        stockRepository.save(StockHandler.convertPOJOToEntity(stockSaveReq));
    }

    public StockResp getStock(StockFilterReq stockFilterReq) {
        String stockCode = stockFilterReq.getStockCode();
        String stockName = stockFilterReq.getStockName();
        if (CommonUtil.isNotBlankStr(stockCode)) {
            stockCode = CommonUtil.appendLikeOperator(stockCode);
        }

        if (CommonUtil.isNotBlankStr(stockName)) {
            stockName = CommonUtil.appendLikeOperator(stockName);
        }

        return StockHandler.convertEntityToPOJO(stockRepository.findStocks(AppUserUtils.getClientId(), stockCode,
                stockName, stockFilterReq.getStatus()));
    }

    public void deleteStock(StockDelReq stockDelReq) {
        List<StockMstrEntity> stockMstrEnties = new ArrayList<StockMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        List<StockMstrEntity> stockMstrEntitys = stockRepository.findAll(stockDelReq.getStockProjsIds());
        for (StockMstrEntity stockMstrEntity : stockMstrEntitys) {
            if (CommonUtil.objectNotNull(stockMstrEntity)) {
                stockMstrEntity.setStatus(stockDelReq.getStatus());
                stockMstrEnties.add(stockMstrEntity);
            }
        }
        stockRepository.save(stockMstrEnties);
    }

    /*public void saveProcures(ProcureSaveReq procureSaveReq) {
    
    procureMstrRepository.save(ProcureHandler.convertPOJOToEntity(procureSaveReq));
    }
    
    public ProcureResp getProcures(ProcureFilterReq procureFilterReq) {
    return ProcureHandler.convertEntityToPOJO(
        procureMstrRepository.findByClientId(AppUserUtils.getClientId(), procureFilterReq.getStatus()));
    }*/

    /*public void deleteProcures(ProcureDelReq ProcureDelReq) {
    List<ProcureMstrEntity> procureMstrEnties = new ArrayList<ProcureMstrEntity>(
        ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    List<ProcureMstrEntity> procureMstrEntitys = procureMstrRepository.findAll(ProcureDelReq.getProcurementIds());
    for (ProcureMstrEntity procureMstrEntity : procureMstrEntitys) {
        if (CommonUtil.objectNotNull(procureMstrEntity)) {
        procureMstrEntity.setStatus(ProcureDelReq.getStatus());
        procureMstrEnties.add(procureMstrEntity);
        }
    }
    procureMstrRepository.save(procureMstrEnties);
    }*/

    public void deleteBusinessActs(BusinessActDeleteReq businessActDeleteReq) {
        List<BusinessActivityMstrEntity> businessActivityMstrEnties = new ArrayList<BusinessActivityMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        List<BusinessActivityMstrEntity> businessActivityMstrEntitys = businessActRepository
                .findAll(businessActDeleteReq.getBusinessActIds());
        for (BusinessActivityMstrEntity businessActivityMstrEntity : businessActivityMstrEntitys) {
            if (CommonUtil.objectNotNull(businessActivityMstrEntity)) {
                businessActivityMstrEntity.setStatus(businessActDeleteReq.getStatus());
                businessActivityMstrEnties.add(businessActivityMstrEntity);
            }
        }
        businessActRepository.save(businessActivityMstrEnties);
    }

    public void saveEmpWages(EmpWageSaveReq empWageSaveReq) {

        empWageRepository.save(EmpWageHandler.convertPOJOToEntity(empWageSaveReq));
    }

    public EmpWageResp getEmpWages(EmpWagesFilterReq empWagesFilterReq) {
        String empWageCode = empWagesFilterReq.getEmpWageCode();
        String empWageName = empWagesFilterReq.getEmpWageName();
        if (CommonUtil.isNotBlankStr(empWageCode)) {
            empWageCode = CommonUtil.appendLikeOperator(empWageCode);
        }

        if (CommonUtil.isNotBlankStr(empWageName)) {
            empWageName = CommonUtil.appendLikeOperator(empWageName);
        }
        return EmpWageHandler.convertEntityToPOJO(empWageRepository.findEmpWages(AppUserUtils.getClientId(),
                empWageCode, empWageName, empWagesFilterReq.getStatus()));
    }

    public void deleteEmpWages(EmpWageDelReq empWageDelReq) {
        List<EmpWageMstrEntity> empWageMstrEnties = new ArrayList<EmpWageMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        List<EmpWageMstrEntity> empWageMstrEntitys = empWageRepository.findAll(empWageDelReq.getEmpWageIds());
        for (EmpWageMstrEntity empWageMstrEntity : empWageMstrEntitys) {
            if (CommonUtil.objectNotNull(empWageMstrEntity)) {
                empWageMstrEntity.setStatus(empWageDelReq.getStatus());
                empWageMstrEnties.add(empWageMstrEntity);
            }
        }
        empWageRepository.save(empWageMstrEnties);
    }

    public void saveCostCodes(CostCodeSaveReq costCodeSaveReq) {

        costCodeRepository.save(CostCodeHandler.convertPOJOToEntity(costCodeSaveReq));
    }

    public CostCodeResp getCostCodes(costCodeFilterReq costCodeFilterReq) {
        String costCodeCode = costCodeFilterReq.getCostCodeCode();
        String costCodeName = costCodeFilterReq.getCostCodeName();
        if (CommonUtil.isNotBlankStr(costCodeCode)) {
            costCodeCode = CommonUtil.appendLikeOperator(costCodeCode);
        }

        if (CommonUtil.isNotBlankStr(costCodeName)) {
            costCodeName = CommonUtil.appendLikeOperator(costCodeName);
        }
        return CostCodeHandler.convertEntityToPOJO(costCodeRepository.findCostCodes(AppUserUtils.getClientId(),
                costCodeCode, costCodeName, costCodeFilterReq.getStatus()));
    }

    public void deleteCostCodes(CostCodeDelReq costCodeDelReq) {
        List<CostMstrEntity> costMstrEntities = new ArrayList<CostMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        List<CostMstrEntity> costMstrEntitys = costCodeRepository.findAll(costCodeDelReq.getCostCodeIds());
        for (CostMstrEntity costMstrEntity : costMstrEntitys) {
            if (CommonUtil.objectNotNull(costMstrEntity)) {
                costMstrEntity.setStatus(costCodeDelReq.getStatus());
                costMstrEntities.add(costMstrEntity);
            }
        }
        costCodeRepository.save(costMstrEntities);
    }

    public void saveProcureCatgs(ProcureCatgSaveReq procureCatgSaveReq) {
        List<ProcureCatgDtlEntity> savedProcures = procureCatgRepository
                .save(ProcureCatgHandler.convertPOJOToEntity(procureCatgSaveReq));
        List<ProcureCatgDtlEntity> manTypes = savedProcures.stream()
                .filter(p -> "Manpower".equalsIgnoreCase(p.getProcureType())).collect(Collectors.toList());
        if (CommonUtil.isListHasData(manTypes)) {
            for (ProcureCatgDtlEntity manType : manTypes) {
                int count = projLeaveTypeRepository.findLeaveTypesWithProcure(AppUserUtils.getClientId(),
                        manType.getId());
                if (count == 0) {
                    List<ProjLeaveTypeEntity> leaveTypes = projLeaveTypeRepository
                            .findLeaveTypesForClient(AppUserUtils.getClientId());
                    for (ProjLeaveTypeEntity leaveType : leaveTypes) {
                        ProjLeaveCategoryType categoryType = new ProjLeaveCategoryType();
                        categoryType.setPayType("Unpaid");
                        categoryType.setProjLeaveTypeEntity(leaveType);
                        categoryType.setProcureCatgDtlEntity(manType);
                        leaveType.getProjLeaveCategoryTypes().add(categoryType);
                    }
                }

            }
        }
    }

    public ProcureCatgResp getProcureCatgs(ProcureCatgFilterReq procureCatgFilterReq) {
        String subProcureCatg = procureCatgFilterReq.getSubProcureName();

        if (CommonUtil.isNotBlankStr(subProcureCatg)) {
            subProcureCatg = CommonUtil.appendLikeOperator(subProcureCatg);
        }

        String procureClassName = procureCatgFilterReq.getProcureClassName();
        if (CommonUtil.isNotBlankStr(procureClassName)) {
            procureClassName = CommonUtil.appendLikeOperator(procureClassName);
        }

        List<ProcureCatgDtlEntity> procureDtlEntities = procureCatgRepository.findProcureCatgsByFilter(
                AppUserUtils.getClientId(), subProcureCatg, procureClassName, procureCatgFilterReq.getStatus());
        ProcureCatgResp procureCatgResp = ProcureCatgHandler.convertEntityToPOJO(procureDtlEntities);
        List<Integer> pcServiceList = serviceRepository.getAllServices();
        for(ProcureMentCatgTO procureMentCatgTO : procureCatgResp.getProcureMentCatgTOs()) {
        	
        	if((pcServiceList.indexOf(procureMentCatgTO.getProCatgId())!=-1)) {
        		procureMentCatgTO.setPcAssigned(true);
        	} else {
        		procureMentCatgTO.setPcAssigned(false);
        	}
        }
        return procureCatgResp;
    }

    public void deleteProcureCatgs(ProcureCatgDelReq procureCatgDelReq) {
        List<ProcureCatgDtlEntity> procureCatgDtlEnties = new ArrayList<ProcureCatgDtlEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        List<ProcureCatgDtlEntity> procureCatgDtlEntitys = procureCatgRepository
                .findAll(procureCatgDelReq.getProcureCatgIds());
        for (ProcureCatgDtlEntity procureCatgDtlEntity : procureCatgDtlEntitys) {
            if (CommonUtil.objectNotNull(procureCatgDtlEntity)) {
                procureCatgDtlEntity.setStatus(procureCatgDelReq.getStatus());
                procureCatgDtlEnties.add(procureCatgDtlEntity);
            }
        }
        procureCatgRepository.save(procureCatgDtlEnties);
    }

    public ContactsResp getContacts(CompanyGetReq userReq) {
        List<CmpContactsEntity> cmpContactsEntitys = contactsRepository.findByClientId(userReq.getCmpId(),
                userReq.getStatus());
        ContactsResp contactsResp = new ContactsResp();
        for (CmpContactsEntity cmpContactsEntity : cmpContactsEntitys) {
            contactsResp.getContactsTOs().add(CompanyHandler.getContactsTO(cmpContactsEntity));
        }
        return contactsResp;
    }

    public AddressResp getAddress(CompanyGetReq userReq) {
        List<CmpAddressEntity> cmpAddressEntitys = addressRepository.findByClientId(userReq.getCmpId(),
                userReq.getStatus());
        AddressResp addressResp = new AddressResp();
        for (CmpAddressEntity cmpAddressEntity : cmpAddressEntitys) {
            addressResp.getAddressTOs().add(CompanyHandler.getAddressTO(cmpAddressEntity));
        }
        return addressResp;
    }

    public CmpCurrentProjsResp getCmpCurrentProjs(CompanyGetReq userReq) {
        List<CmpCurrentProjsEntity> cmpCurrentProjsEntitys = companyCurrentProjRepository
                .findByClientId(userReq.getCmpId(), userReq.getStatus());
        CmpCurrentProjsResp cmpCurrentProjsResp = new CmpCurrentProjsResp();
        for (CmpCurrentProjsEntity cmpCurrentProjsEntity : cmpCurrentProjsEntitys) {
            cmpCurrentProjsResp.getCompanyProjectsTO().add(CompanyHandler.getCurrentProjectsTO(cmpCurrentProjsEntity));
        }
        return cmpCurrentProjsResp;
    }

    public PlantServiceClassResp getPlantServiceClass(PlantServiceClassGetReq plantServiceClassGetReq) {
        PlantServiceClassResp plantServiceClassResp = new PlantServiceClassResp();
        List<PlantServiceClassificationMstrEntity> plantServiceClassificationMstrEntites = plantServiceClassRepository
                .findPlantServiceClass(AppUserUtils.getClientId(), plantServiceClassGetReq.getStatus());

        for (PlantServiceClassificationMstrEntity plantServiceClassificationMstrEntity : plantServiceClassificationMstrEntites) {
            plantServiceClassResp.getPlantServiceClassificationMstrTOs().add(PlantServiceClassificationMstrHandler
                    .convertEntityToPOJO(plantServiceClassificationMstrEntity, true));
        }
        return plantServiceClassResp;
    }

    public void savePlantServiceClass(PlantServiceClassSaveReq plantServiceClassSaveReq) {

        plantServiceClassRepository.save(PlantServiceClassificationMstrHandler
                .convertPOJOsToEntitys(plantServiceClassSaveReq.getPlantServiceClassificationMstrTOs()));
    }

    public PlantServiceClassResp getPlantServiceClassItemsOnly(PlantServiceClassGetReq plantServiceClassGetReq) {

        PlantServiceClassResp plantServiceClassResp = new PlantServiceClassResp();
        List<PlantServiceClassificationMstrEntity> plantServiceClassificationMstrEntites = plantServiceClassRepository
                .findPlantServiceClassItems(AppUserUtils.getClientId(), plantServiceClassGetReq.getStatus());

        for (PlantServiceClassificationMstrEntity plantServiceClassificationMstrEntity : plantServiceClassificationMstrEntites) {
            plantServiceClassResp.getPlantServiceClassificationMstrTOs().add(PlantServiceClassificationMstrHandler
                    .convertEntityToPOJO(plantServiceClassificationMstrEntity, true));
        }
        return plantServiceClassResp;
    }

    public void deactivatePlantServiceClass(PlantServiceClassDeactivateReq plantServiceClassDeactivateReq) {

        plantServiceClassRepository.deactivatePlantServiceClassification(
                plantServiceClassDeactivateReq.getPlantServiceIds(), plantServiceClassDeactivateReq.getStatus());

    }

    public Map<Long, LabelKeyTO> getCompanyMap() {
        Map<Long, LabelKeyTO> compantMap = null;
        List<CompanyMstrEntity> CompanyMstrEntites = companyRepository.findCompanies(AppUserUtils.getClientId(), null,
                null, StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isListHasData(CompanyMstrEntites)) {
            compantMap = new HashMap<Long, LabelKeyTO>();
            for (CompanyMstrEntity companyMstrEntity : CompanyMstrEntites) {
                compantMap.put(companyMstrEntity.getId(),
                        CompanyHandler.convertCmpEntityToLabelKeyTo(companyMstrEntity));
            }
        }
        return compantMap;
    }

    public Map<Long, LabelKeyTO> getProcureCatgMap(RegisterOnLoadReq registerOnLoadReq) {
        Map<Long, LabelKeyTO> procureCatgMap = null;
        List<ProcureCatgDtlEntity> procureCatgDtlEntites = procureCatgRepository.getProcureByType(
                registerOnLoadReq.getProcureCategoryDbConstant(), AppUserUtils.getClientId(),
                StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isListHasData(procureCatgDtlEntites)) {
            procureCatgMap = new HashMap<Long, LabelKeyTO>();
            for (ProcureCatgDtlEntity procureCatgDtlEntity : procureCatgDtlEntites) {
                procureCatgMap.put(procureCatgDtlEntity.getId(),
                        ProcureCatgHandler.convertProcureCatgDtlEntityToLabelKeyTo(procureCatgDtlEntity));
            }
        }
        return procureCatgMap;
    }

    public Map<Long, LabelKeyTO> getEmpClassMap() {
        Map<Long, LabelKeyTO> empClassMap = null;
        List<EmpClassMstrEntity> empClassMstrEntites = empClassRepository.findByClientId(AppUserUtils.getClientId(),
                StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isListHasData(empClassMstrEntites)) {
            empClassMap = new HashMap<Long, LabelKeyTO>();
            for (EmpClassMstrEntity empClassMstrEntity : empClassMstrEntites) {
                empClassMap.put(empClassMstrEntity.getId(),
                        EmpClassHandler.convertEmpClassMstrEntityToLabelKeyTo(empClassMstrEntity));
            }
        }
        return empClassMap;
    }

    public Map<Long, LabelKeyTO> getMaterialClassMap() {
        Map<Long, LabelKeyTO> materClassMap = null;
        List<MaterialClassMstrEntity> materialClassMstrEntities = null;
        if (AppUserUtils.getClientId() == null) {
            materialClassMstrEntities = materialClassRepository.findMaterialItems(AppUserUtils.getClientId(),
                    StatusCodes.ACTIVE.getValue());
        } else {
            materialClassMstrEntities = materialClassRepository.findMaterialItemsForClient(AppUserUtils.getClientId(),
                    StatusCodes.ACTIVE.getValue());
        }
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(materialClassMstrEntities)) {
            materClassMap = new HashMap<Long, LabelKeyTO>();
            for (MaterialClassMstrEntity materialClassMstrEntity : materialClassMstrEntities) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(materialClassMstrEntity.getId());
                labelKeyTO.setCode(materialClassMstrEntity.getCode());
                labelKeyTO.setName(materialClassMstrEntity.getName());
                if (CommonUtil.objectNotNull(materialClassMstrEntity.getMeasurmentMstrEntity())) {
                    labelKeyTO.setUnitOfMeasure(materialClassMstrEntity.getMeasurmentMstrEntity().getName());
                }
                materClassMap.put(materialClassMstrEntity.getId(), labelKeyTO);
            }
        }
        return materClassMap;
    }
    
    public Map<Long, LabelKeyTO> getTangibleClassMap() {
        Map<Long, LabelKeyTO> tangibleClassMap = null;
        List<TangibleClassificationEntity> tangibleClassMstrEntities = null;
        if (AppUserUtils.getClientId() == null) {
        	tangibleClassMstrEntities = tangibleClassRepository.findTangibleItems(AppUserUtils.getClientId(),
                    StatusCodes.ACTIVE.getValue());
        } else {
        	tangibleClassMstrEntities = tangibleClassRepository.findTangibleItemsForClient(AppUserUtils.getClientId(),
                    StatusCodes.ACTIVE.getValue());
        }
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(tangibleClassMstrEntities)) {
        	tangibleClassMap = new HashMap<Long, LabelKeyTO>();
            for (TangibleClassificationEntity tangibleClassificationEntity : tangibleClassMstrEntities) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(tangibleClassificationEntity.getId());
                labelKeyTO.setCode(tangibleClassificationEntity.getCode());
                labelKeyTO.setName(tangibleClassificationEntity.getName());
                if (CommonUtil.objectNotNull(tangibleClassificationEntity.getMeasurmentMstrEntity())) {
                    labelKeyTO.setUnitOfMeasure(tangibleClassificationEntity.getMeasurmentMstrEntity().getName());
                }
                tangibleClassMap.put(tangibleClassificationEntity.getId(), labelKeyTO);
            }
        }
        return tangibleClassMap;
    }
    

    public Map<Long, LabelKeyTO> getPlantClassMap() {
        Map<Long, LabelKeyTO> plantClassMap = null;
        List<PlantMstrEntity> plantClassMstrEntites = plantClassRepository.findByClientId(AppUserUtils.getClientId(),
                StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isListHasData(plantClassMstrEntites)) {
            plantClassMap = new HashMap<Long, LabelKeyTO>();
            for (PlantMstrEntity plantMstrEntity : plantClassMstrEntites) {
                plantClassMap.put(plantMstrEntity.getId(),
                        PlantClassHandler.converPlantClassEntityLabelKeyTO(plantMstrEntity));
            }
        }
        return plantClassMap;
    }

    public Map<Long, LabelKeyTO> getPlantServiceClassItemsMap() {
        Map<Long, LabelKeyTO> plantServiceClassItemMap = null;
        List<PlantServiceClassificationMstrEntity> plantServiceClassificationMstrEntites = plantServiceClassRepository
                .findPlantServiceClassItems(AppUserUtils.getClientId(), StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isListHasData(plantServiceClassificationMstrEntites)) {
            plantServiceClassItemMap = new HashMap<Long, LabelKeyTO>();
            for (PlantServiceClassificationMstrEntity plantServiceClassificationMstrEntity : plantServiceClassificationMstrEntites) {
                plantServiceClassItemMap.put(plantServiceClassificationMstrEntity.getId(),
                        PlantServiceClassificationMstrHandler
                                .convertEntityToLabelKeyTO(plantServiceClassificationMstrEntity));
            }
        }
        return plantServiceClassItemMap;
    }

    private Map<Long, AddressTO> getDefaultCompanyAddressesByClientId() {
        List<CmpAddressEntity> addressEntities = addressRepository
                .findDefaultAddresByClientId(AppUserUtils.getClientId(), StatusCodes.ACTIVE.getValue());
        Map<Long, AddressTO> addressMap = new HashMap<Long, AddressTO>();
        AddressTO addressTO = null;
        for (CmpAddressEntity cmpAddressEntity : addressEntities) {
            addressTO = CompanyHandler.getAddressTO(cmpAddressEntity);
            addressMap.put(addressTO.getCompanyId(), addressTO);
        }
        return addressMap;

    }

    private Map<Long, ContactsTO> getDefaultCompanyContactsByClientId() {
        List<CmpContactsEntity> cmpContactsEntities = contactsRepository
                .findDefaultContactByClientId(AppUserUtils.getClientId(), StatusCodes.ACTIVE.getValue());
        ContactsTO contactsTO = null;
        Map<Long, ContactsTO> contactsMap = new HashMap<Long, ContactsTO>();
        for (CmpContactsEntity cmpContactsEntity : cmpContactsEntities) {
            contactsTO = CompanyHandler.getContactsTO(cmpContactsEntity);
            contactsMap.put(contactsTO.getCompanyId(), contactsTO);
        }
        return contactsMap;

    }

    public MaterialClassResp getMaterialGroups(MaterialClassGetReq MaterialClassGetReq) {
        MaterialClassResp materialClassResp = new MaterialClassResp();
        List<MaterialClassMstrEntity> materialClassMstrEntities = null;
        if (AppUserUtils.getClientId() == null) {
            materialClassMstrEntities = materialClassRepository.findAllMaterials(AppUserUtils.getClientId(),
                    MaterialClassGetReq.getStatus());
        } else {
            materialClassMstrEntities = materialClassRepository.findAllMaterialsForClient(AppUserUtils.getClientId(),
                    MaterialClassGetReq.getStatus());
        }
        for (MaterialClassMstrEntity materialClassMstrEntity : materialClassMstrEntities) {
            materialClassResp.getMaterialClassTOs()
                    .add(MaterialClassHandler.populateMaterialItems(materialClassMstrEntity, true));
        }
        return materialClassResp;
    }

    public MaterialClassResp getMaterialItems(MaterialClassGetReq MaterialClassGetReq) {
        MaterialClassResp materialClassResp = new MaterialClassResp();
        List<MaterialClassMstrEntity> MaterialClassMstrEntity = materialClassRepository
                .findMaterialItems(AppUserUtils.getClientId(), MaterialClassGetReq.getStatus());
        for (MaterialClassMstrEntity materialClassMstrEntities : MaterialClassMstrEntity) {
            materialClassResp.getMaterialClassTOs()
                    .add(MaterialClassHandler.populateMaterialItems(materialClassMstrEntities, true));
        }
        return materialClassResp;
    }

    public void saveMaterialGroups(MaterialClassSaveReq materialClassSaveReq) {
        materialClassRepository.save(MaterialClassHandler.populateEntitiesFromPOJO(AppUserUtils.getClientId(),
                materialClassSaveReq.getMaterialClassTOs(), measurementRepository));
    }

    public void deleteMaterialGroups(MaterialClassDelReq materialClassDelReq) {
        materialClassRepository.deactivateMaterialGroups(materialClassDelReq.getMaterialIds(),
                materialClassDelReq.getStatus());

    }

    public AssetCategoryResp getAssetCategory(AssetCategoryGetReq assetCategoryGetReq) {
        AssetCategoryResp assetCategoryResp = new AssetCategoryResp();
        List<AssetCategoryMstrEntity> assetCategoryMstrEntities = null;
        if (AppUserUtils.getClientId() == null) {
            assetCategoryMstrEntities = assetCategoryRepository.findAllAssetCategories(AppUserUtils.getClientId(),
                    assetCategoryGetReq.getStatus());
        } else {
            assetCategoryMstrEntities = assetCategoryRepository
                    .findAllAssetCategoriesForClient(AppUserUtils.getClientId(), assetCategoryGetReq.getStatus());
        }

        for (AssetCategoryMstrEntity assetCategoryMstrEntity : assetCategoryMstrEntities) {
            assetCategoryResp.getAssetCategoryTOs()
                    .add(AssetCategoryHandler.populateAssetItems(assetCategoryMstrEntity, true));
        }
        return assetCategoryResp;
    }

    public void saveAssetCategory(AssetCategorySaveReq assetCategorySaveReq) {
        assetCategoryRepository
                .save(AssetCategoryHandler.populateEntitiesFromPOJO(assetCategorySaveReq.getAssetCategoryTOs()));

    }

    public void deleteAssetCategory(AssetCategoryDelReq assetCategoryDelReq) {
        assetCategoryRepository.deactivateAssetCategories(assetCategoryDelReq.getAssetIds(),
                assetCategoryDelReq.getStatus());

    }

    public AssetMaintenanceCategoryResp getAssetMaintenanceCategory(
            AssetMaintenanceCategoryGetReq assetMaintenanceCategoryGetReq) {
        AssetMaintenanceCategoryResp assetMaintenanceCategoryResp = new AssetMaintenanceCategoryResp();
        List<AssetMaintenanceCategoryMstrEntity> assetMaintenanceCategoryMstrEntities = null;
        if (AppUserUtils.getClientId() == null) {
            assetMaintenanceCategoryMstrEntities = assetMaintenanceCategoryRepository.findAllAssetMaintenanceCategories(
                    AppUserUtils.getClientId(), assetMaintenanceCategoryGetReq.getStatus());
        } else {
            assetMaintenanceCategoryMstrEntities = assetMaintenanceCategoryRepository
                    .findAllAssetMaintenanceCategoriesForClient(AppUserUtils.getClientId(),
                            assetMaintenanceCategoryGetReq.getStatus());
        }
        for (AssetMaintenanceCategoryMstrEntity assetMaintenanceCategoryMstrEntity : assetMaintenanceCategoryMstrEntities) {
            assetMaintenanceCategoryResp.getAssetMaintenanceCategoryTOs().add(AssetMaintenanceCategoryHandler
                    .populateAssetMaintenanceItems(assetMaintenanceCategoryMstrEntity, true));
        }
        return assetMaintenanceCategoryResp;
    }

    public void saveAssetMaintenanceCategory(AssetMaintenanceCategorySaveReq assetMaintenanceCategorySaveReq) {
        assetMaintenanceCategoryRepository.save(AssetMaintenanceCategoryHandler.populateEntitiesFromPOJO(
                AppUserUtils.getClientId(), assetMaintenanceCategorySaveReq.getAssetMaintenanceCategoryTOs()));

    }

    public void deleteAssetMaintenanceCategory(AssetMaintenanceCategoryDelReq assetMaintenanceCategoryDelReq) {
        assetMaintenanceCategoryRepository.deactivateAssetMaintenanceCategories(
                assetMaintenanceCategoryDelReq.getAssetIds(), assetMaintenanceCategoryDelReq.getStatus());

    }

    public MaterialClassResp getCentralMaterial(MaterialClassFilterReq materialClassFilterReq) {

        MaterialClassResp materialClassResp = new MaterialClassResp();

        String materialCode = materialClassFilterReq.getMaterialCode();
        String materialName = materialClassFilterReq.getMaterialName();

        if (CommonUtil.isNotBlankStr(materialCode)) {
            materialCode = CommonUtil.appendLikeOperator(materialCode);
        }

        if (CommonUtil.isNotBlankStr(materialName)) {
            materialName = CommonUtil.appendLikeOperator(materialName);
        }

        if (AppUserUtils.getClientId() == null) {
            return MaterialClassHandler.convertEntityToPOJO(materialClassRepository.findMaterial(
                    AppUserUtils.getClientId(), materialCode, materialName, materialClassFilterReq.getStatus()));
        } else {
            List<MaterialClassMstrEntity> materialList = materialClassRepository.findMaterialForClient(
                    AppUserUtils.getClientId(), materialCode, materialName, materialClassFilterReq.getStatus());
            for (MaterialClassMstrEntity material : materialList) {
                materialClassResp.getMaterialClassTOs().add(MaterialClassHandler.populateMaterialItems(material, true));
            }
            return materialClassResp;
        }

    }

    @Override
    public void saveCountryProvinceCode(CountryProvinceCodeSaveReq countryProvinceCodeSaveReq) {
        CountryProvinceCodeEntity entity = null;
        FinancialYearEntity financialYearEntity = null;
        FinancialHalfYearEntity financialHalfYearEntity = null;
        FinancialQuarterYearEntity financialQuarterYearEntity = null;
        if (CommonUtil.isNonBlankLong(countryProvinceCodeSaveReq.getId())) {
            entity = countryProvinceCodeRepository.findOne(countryProvinceCodeSaveReq.getId());
            financialYearEntity = entity.getFinancialYearEntity();
            financialHalfYearEntity = entity.getFinancialHalfYearEntity();
            financialQuarterYearEntity = entity.getFinancialQuarterYearEntity();
        } else {
            entity = new CountryProvinceCodeEntity();
            financialYearEntity = new FinancialYearEntity();
            financialHalfYearEntity = new FinancialHalfYearEntity();
            financialQuarterYearEntity = new FinancialQuarterYearEntity();
        }
        countryProvinceCodeRepository
                .save(CountryProvinceCodeHandler.convertCountryProvincePOJOToEntity(countryProvinceCodeSaveReq, entity,
                        financialYearEntity, financialHalfYearEntity, financialQuarterYearEntity));
    }

    @Override
    public CountryProvinceCodeResp getCountryProvinceCodes(CountryProvinceCodeGetReq countryProvinceCodeGetReq) {
        ClientRegEntity clientRegEntity = clientRegRepository.findOne(AppUserUtils.getClientId());
        List<CountryProvinceCodeEntity> countryProvinceCodeEntityList = countryProvinceCodeRepository
                .getCountryProvinceCodes(clientRegEntity.getClientId(), 1);
        return CountryProvinceCodeHandler.convertEntityToPOJO(countryProvinceCodeEntityList);
    }

    public void deactivateCountryProvinceCodes(CountryProvinceCodeDeactiveReq countryProvinceCodeDeactiveReq) {
        List<Long> countryProvinceIds = countryProvinceCodeDeactiveReq.getCountryProvinceIds();
        countryProvinceCodeRepository.deactivateCountryProvinceCodes(countryProvinceIds,
                countryProvinceCodeDeactiveReq.getStatus());
    }

    @Override
    public FinanceCenterRecordTo savefinanceCenterRecords(FinanceCenterSaveReq financeCenterSaveReq) {
    	 System.out.println("====savefinanceCenterRecords======");
    	 //System.out.println("====savefinanceCenterRecords======FinanceCenterSaveReq",financeCenterSaveReq.getFinanceCenterRecordTo());
        Boolean isUpdate = false;
        FinanceCenterEntity entity = null;
        List<UnitOfPayRateEntity> unitOfPayRateEntity = null;
        List<EmployeePayRoleEntity> employeePayRoleEntity = null;
        List<NonRegularPayAllowanceEntity> nonRegularPayAllowanceEntity = null;
		List<RegularPayAllowanceEntity> regularPayAllowanceEntity = null;
		List<SuperProvidentFundEntity> superProvidentFundEntity = null;
		List<TaxRatesRulesCodeEntity> taxRatesRulesCodeEntity = null;
	    List<PayDeductionCodeEntity> payDeductionCodeEntity = null;
		List<TaxPaymentReceiverCodeEntity> taxPaymentReceiverCodeEntity = null;
		 
       
        Long financeCenterId = financeCenterSaveReq.getId();
        if (CommonUtil.isNonBlankLong(financeCenterId)) {
        	//System.out.println("====savefinanceCenterRecords=====if block=");
            entity = financeCenterRecordRepository.findOne(financeCenterId);
            unitOfPayRateEntity = entity.getUnitOfPayRateEntity();
            employeePayRoleEntity = entity.getEmployeePayRoleEntity();
            nonRegularPayAllowanceEntity = entity.getNonRegularPayAllowanceEntity();
			  regularPayAllowanceEntity = entity.getRegularPayAllowanceEntity();
			  //System.out.println("====savefinanceCenterRecords====superProvidentFundEntity==before");
			  superProvidentFundEntity= entity.getSuperProvidentFundEntity();
			  //System.out.println("====savefinanceCenterRecords====superProvidentFundEntity==after");
			 // System.out.println("====savefinanceCenterRecords======");
			  taxRatesRulesCodeEntity = entity.getTaxRatesRulesCodeEntity();
			  payDeductionCodeEntity = entity.getPayDeductionCodeEntity();
			  taxPaymentReceiverCodeEntity=entity.getTaxPaymentReceiverCodeEntity();
			 
            isUpdate = true;
        } else {
        	//System.out.println("====savefinanceCenterRecords=====else block=");
            entity = new FinanceCenterEntity();
            unitOfPayRateEntity = new ArrayList<>();
            employeePayRoleEntity = new ArrayList<>();
            nonRegularPayAllowanceEntity = new ArrayList<>();
			  regularPayAllowanceEntity= new ArrayList<>(); 
			  superProvidentFundEntity= new  ArrayList<>();
			  //System.out.println("====savefinanceCenterRecords=====else block=superProvidentFundEntity");
			  taxRatesRulesCodeEntity = new ArrayList<>();
			  payDeductionCodeEntity = new ArrayList<>(); 
			  taxPaymentReceiverCodeEntity=new  ArrayList<>();
			 
        }
       
		FinanceCenterEntity financeCenterEntity = financeCenterRecordRepository.save(FinanceCenterHandler
				.convertPOJOToEntity(financeCenterSaveReq, entity, unitOfPayRateEntity, employeePayRoleEntity,
						nonRegularPayAllowanceEntity,regularPayAllowanceEntity, superProvidentFundEntity, taxRatesRulesCodeEntity,
						  payDeductionCodeEntity, taxPaymentReceiverCodeEntity,
						  isUpdate));
		//System.out.println("====savefinanceCenterRecords=====before===convertEntityToPOJO");
		return FinanceCenterHandler.convertEntityToPOJO(financeCenterEntity);
    }

    @Override
    public FinanceCenterResp getfinanceCenterRecords(FinanceCenterCodeGetReq financeCenterCodeGetReq) {
        ClientRegEntity clientRegEntity = clientRegRepository.findOne(AppUserUtils.getClientId());
       // System.out.println("controller=====getfinanceCenterRecords");
        FinanceCenterResp financeCenterResp = new FinanceCenterResp();
        List<FinanceCenterRecordTo> financeCenterRecordToList = new ArrayList<>();
        if(financeCenterCodeGetReq.getProvinceCode() != null) {
        	List<FinanceCenterEntity> financeCenterEntityList = financeCenterRecordRepository
                    .getFinanceCenter(clientRegEntity.getClientId(), 1,financeCenterCodeGetReq.getCountryCode(),financeCenterCodeGetReq.getProvinceCode());
        	for (FinanceCenterEntity financeCenterEntity : financeCenterEntityList) {
                FinanceCenterRecordTo financeCenterRecordTo = FinanceCenterHandler.convertEntityToPOJO(financeCenterEntity);
                financeCenterRecordToList.add(financeCenterRecordTo);
               // System.out.println("controller=====financeCenterEntity"+financeCenterEntity);
            }
            financeCenterResp.setFinanceCenterRecordTos(financeCenterRecordToList);
            return financeCenterResp;
        }else {
        List<FinanceCenterEntity> financeCenterEntityList = financeCenterRecordRepository
                .getFinanceCenterCodes(clientRegEntity.getClientId(), 1,financeCenterCodeGetReq.getCountryCode());
        //System.out.println("clientRegEntity.getClientId() "+clientRegEntity.getClientId());
        //System.out.println("financeCenterCodeGetReq.getCountryCode() "+financeCenterCodeGetReq.getCountryCode());
       // System.out.println("controller=====financeCenterEntityList "+financeCenterEntityList.size());
        for (FinanceCenterEntity financeCenterEntity : financeCenterEntityList) {
            FinanceCenterRecordTo financeCenterRecordTo = FinanceCenterHandler.convertEntityToPOJO(financeCenterEntity);
            financeCenterRecordToList.add(financeCenterRecordTo);
           // System.out.println("controller=====financeCenterEntity"+financeCenterEntity);
        }
        financeCenterResp.setFinanceCenterRecordTos(financeCenterRecordToList);
        return financeCenterResp;
        }
    }
    
    @Override
    public FinanceCenterResp getUnitOfRate(FinanceCenterFilterReq financeCenterFilterReq) {
        ClientRegEntity clientRegEntity = clientRegRepository.findOne(AppUserUtils.getClientId());
       // System.out.println("controller==service===getUnitOfRate");
        FinanceCenterResp financeCenterResp = new FinanceCenterResp();
        List<FinanceCenterRecordTo> financeCenterRecordToList = new ArrayList<>();
        //System.out.println("financeCenterFilterReq.getCountryName() "+financeCenterFilterReq.getCountryCode());
        //System.out.println("financeCenterFilterReq.getProvisionName() "+financeCenterFilterReq.getProvisionCode());
        List<FinanceCenterEntity> financeCenterEntityList = financeCenterRecordRepository
                .getUnitOfRate(clientRegEntity.getClientId(), 1,financeCenterFilterReq.getCountryCode(),financeCenterFilterReq.getProvisionCode());
       // System.out.println("clientRegEntity.getClientId() "+clientRegEntity.getClientId());
        //System.out.println("financeCenterCodeGetReq.getCountryCode() "+financeCenterCodeGetReq.getCountryCode());
        //System.out.println("controller==service=impl==getUnitOfRate "+financeCenterEntityList.size());
        for (FinanceCenterEntity financeCenterEntity : financeCenterEntityList) {
            FinanceCenterRecordTo financeCenterRecordTo = FinanceCenterHandler.convertEntityToPOJO(financeCenterEntity);
            financeCenterRecordToList.add(financeCenterRecordTo);
           //// System.out.println("controller==service=impl===financeCenterEntity"+financeCenterEntity);
        }
        financeCenterResp.setFinanceCenterRecordTos(financeCenterRecordToList);
        return financeCenterResp;
    }
    
    
    public void deactivateFinanceCenterRecords(FinanceCenterDeactiveReq financeCenterDeactiveReq) {
        List<Long> financeCenterIds = financeCenterDeactiveReq.getFinanceCenterIds();
        financeCenterRecordRepository.deactivateFinanceCenterRecords(financeCenterIds,
                financeCenterDeactiveReq.getStatus());
    }

    @Override
    public Map<Long, LabelKeyTO> getEmployeeTypes(FinanceCenterCodeGetReq financeCenterCodeGetReq) {
        Map<Long, LabelKeyTO> procureCatgMap = null;
        List<ProcureCatgDtlEntity> procureCatgDtlEntites = procureCatgRepository.getProcureByType("Manpower",
                AppUserUtils.getClientId(), StatusCodes.ACTIVE.getValue());
       // System.out.println("controller==service=impl===getEmployeeTypes");
        //System.out.println("controller==service=impl===StatusCodes.ACTIVE.getValue()"+StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isListHasData(procureCatgDtlEntites)) {
        	// System.out.println("controller==service=impl===procureCatgDtlEntites");
            procureCatgMap = new HashMap<Long, LabelKeyTO>();
            for (ProcureCatgDtlEntity procureCatgDtlEntity : procureCatgDtlEntites) {
            	//System.out.println("controller==service=impl===ProcureCatgDtlEntity");
            	//System.out.println("controller==service=impl===procureCatgDtlEntity.getId()"+procureCatgDtlEntity.getId());
                procureCatgMap.put(procureCatgDtlEntity.getId(),
                        ProcureCatgHandler.convertProcureCatgDtlEntityToLabelKeyTo(procureCatgDtlEntity));
            }
        }
        return procureCatgMap;

    }
    
	public TangibleClassResp getTangibleGroups(TangibleClassGetReq tangibleClassGetReq) {
		TangibleClassResp tangibleClassResp = new TangibleClassResp();
	        List<TangibleClassificationEntity> tangibleClassificationEntities = null;
	        if (AppUserUtils.getClientId() == null) {
	        	tangibleClassificationEntities = tangibleClassRepository.findAllTangible(AppUserUtils.getClientId(),
	        			tangibleClassGetReq.getStatus());
	        } else {
	        	tangibleClassificationEntities = tangibleClassRepository.findAllTangibleForClient(AppUserUtils.getClientId(),
	        			tangibleClassGetReq.getStatus());
	        }
	        for (TangibleClassificationEntity tangibleClassificationEntity : tangibleClassificationEntities) {
	        	tangibleClassResp.getTangibleClassTOs()
	                    .add(TangibleClassHandler.populateTangibleItems(tangibleClassificationEntity, true));
	        }
	        return tangibleClassResp;
	}

	public void saveTangibleGroups(TangibleClassSaveReq tangibleClassSaveReq) {
		tangibleClassRepository.save(TangibleClassHandler.populateEntitiesFromPOJO(AppUserUtils.getClientId(),
				tangibleClassSaveReq.getTangibleClassTOs(), measurementRepository));
		
	}

	public void deleteTangibleGroups(TangibleClassDelReq tangibleClassDelReq) { 
		tangibleClassRepository.deactivateTangibleGroups(tangibleClassDelReq.getTangibleIds(), tangibleClassDelReq.getStatus());
		
	}

	public TangibleClassResp getCentralTangible(TangibleClassFilterReq tangibleClassFilterReq) {

		TangibleClassResp tangibleClassResp = new TangibleClassResp();

        String tangibleCode = tangibleClassFilterReq.getTangibleCode();
        String tangibleName = tangibleClassFilterReq.getTangibleName();

        if (CommonUtil.isNotBlankStr(tangibleCode)) {
        	tangibleCode = CommonUtil.appendLikeOperator(tangibleCode);
        }

        if (CommonUtil.isNotBlankStr(tangibleName)) {
        	tangibleName = CommonUtil.appendLikeOperator(tangibleName);
        }

        if (AppUserUtils.getClientId() == null) {
            return TangibleClassHandler.convertEntityToPOJO(tangibleClassRepository.findTangible(
                    AppUserUtils.getClientId(), tangibleCode, tangibleName, tangibleClassFilterReq.getStatus()));
        } else {
            List<TangibleClassificationEntity> tangibleList = tangibleClassRepository.findTangibleForClient(
                    AppUserUtils.getClientId(), tangibleCode, tangibleName, tangibleClassFilterReq.getStatus());
            for (TangibleClassificationEntity tangible : tangibleList) {
            	tangibleClassResp.getTangibleClassTOs().add(TangibleClassHandler.populateTangibleItems(tangible, true));
            }
            return tangibleClassResp;
        }
		
	}
	 public TangibleClassResp getTangibleItems(TangibleClassGetReq tangibleClassGetReq) {
		 TangibleClassResp tangibleClassResp = new TangibleClassResp();
	        List<TangibleClassificationEntity> tangibleClassificationEntites = tangibleClassRepository.findTangibleItems(AppUserUtils.getClientId(), tangibleClassGetReq.getStatus());
	        for (TangibleClassificationEntity tangibleClassificationEntity : tangibleClassificationEntites) {
	        	tangibleClassResp.getTangibleClassTOs().add(TangibleClassHandler.populateTangibleItems(tangibleClassificationEntity, true));
	        }
	        return tangibleClassResp;
	    }
	 public void saveTaxAndRules(TaxRatesRulesCodeDtlSaveReq taxRatesRulesCodeDtlSaveReq) {
	        
	        /*System.out.println("controller==service===saveTaxAndRules");
	        System.out.println(taxRatesRulesCodeDtlSaveReq);*/
	        taxRatesRulesCodeDtlRepository.save(TaxRatesRulesCodeDtlHandler.convertPOJOToEntity(taxRatesRulesCodeDtlSaveReq));
	    }
	 
	 public CmpBankDetailsResp getBank(CompanyGetReq userReq) {
	        List<CmpBankAccountEntity> cmpBankAccountEntitys = bankAccountRepository.findByClientId(userReq.getCmpId(), userReq.getStatus());
	        CmpBankDetailsResp cmpBankDetailsResp = new CmpBankDetailsResp();
	        for (CmpBankAccountEntity cmpBankAccountEntity : cmpBankAccountEntitys) {
	        	cmpBankDetailsResp.getBankTOs().add(CompanyHandler.getBankTO(cmpBankAccountEntity));
	        }
	        return cmpBankDetailsResp;
	    }
	 
	 public void deleteBank(BankDelReq bankDelReq) {		 
		 bankAccountRepository.deactivateBank(bankDelReq.getBankAccountIds(), bankDelReq.getStatus());
	    }


}
