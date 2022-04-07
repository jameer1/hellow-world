package com.rjtech.register.service.impl.emp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.finance.repository.ProvidentFundRepository;
//import com.rjtech.finance.repository.ProvidentFundRepositoryCopy;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.emp.dto.EmpPayDeductionDetailTO;
import com.rjtech.register.emp.dto.EmpPayDeductionQuestionTO;
import com.rjtech.register.emp.dto.EmpPayDeductionTO;
import com.rjtech.register.emp.model.EmpPayDeductionDtlEntity;
import com.rjtech.register.emp.model.EmpPayDeductionEntity;
import com.rjtech.register.emp.model.EmpPayDeductionQuestEntity;
import com.rjtech.register.emp.model.EmpPayQuestionaryMstrEntity;
import com.rjtech.register.emp.req.EmpPayDeductionSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpPayDeductionResp;
import com.rjtech.register.proc.emp.EmpPayCodeProcRepository;
import com.rjtech.register.repository.emp.EmpPayDeductionRepository;
import com.rjtech.register.repository.emp.EmpPayQuestionMstrRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpPayDeductionService;
import com.rjtech.register.service.handler.emp.EmpPayDeductionDetailHandler;
import com.rjtech.register.service.handler.emp.EmpPayDeductionHandler;
import com.rjtech.register.service.handler.emp.EmpPayDeductionQuestionHandler;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.register.repository.emp.EmpPayDeductionDetailsRepository;
import com.rjtech.register.emp.model.EmpPayDeductionEntity;
import com.rjtech.centrallib.model.PayDeductionCodeEntity;
import com.rjtech.register.emp.model.EmpPayDeductionDtlEntity;
import com.rjtech.register.service.handler.emp.EmpPayDeductionAllowanceHandler;
//import com.rjtech.register.repository.emp.EmpPayDeductionDetailsRepository;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;
import com.rjtech.common.dto.PayDeductionCodes;
import com.rjtech.register.emp.dto.EmpPaybleRateDetailTO;
import com.rjtech.register.emp.model.EmpPayDeductionDtlEntity;
import com.rjtech.register.repository.emp.EmpPayDeductionDtlRepository;

@Service(value = "empPayDeductionService")
@RJSService(modulecode = "empPayDeductionService")
@Transactional
public class EmpPayDeductionServiceImpl implements EmpPayDeductionService {

    @Autowired
    private EmpPayDeductionRepository empPayDeductionRepository;

    @Autowired
    private EmpPayCodeProcRepository empPayCodeProcRepository;

    @Autowired
    private EmpPayQuestionMstrRepository empPayQuestionMstrRepository;

    @Autowired
    private ProvidentFundRepository providentFundRepository;

    @Autowired
    private EmpProjRegisterRepository empProjRegisterRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private ProjGeneralRepositoryCopy projGeneralRepository;
    
    @Autowired
    private EmpPayDeductionDetailsRepository empPayDeductionDetailsRepository;
    
    @Autowired
    private EmpPayDeductionDtlRepository empPayDeductionDtlRepository;

    public EmpPayDeductionResp getEmpPayDeductions(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpPayDeductionResp empPayDeductionResp = new EmpPayDeductionResp();
        List<EmpPayDeductionEntity> empPayDeductionEntities = empPayDeductionRepository
                .findEmpRegularPaybleRateDetails(projEmpRegisterGetReq.getEmpId(), projEmpRegisterGetReq.getStatus());
        for (EmpPayDeductionEntity empPayDeductionEntity : empPayDeductionEntities) {
            empPayDeductionResp.getEmpPayDeductionTOs()
                    .add(EmpPayDeductionHandler.convertEntityToPOJO(empPayDeductionEntity));
        }
        return empPayDeductionResp;
    }

    public EmpPayDeductionResp getEmpPayDeductionDetails(EmpRegisterReq empRegisterReq) { 
    	EmpPayDeductionResp empPayDeductionResp = new EmpPayDeductionResp();
    System.out.println("==serviceimpl===getEmpPayDeductionDetails====");
    //Date date=CommonUtil.convertStringToDate(empRegisterReq.getEffectiveDate());
    List<EmpPayDeductionDtlEntity> empPayDeductionDtlEntities = empPayDeductionDtlRepository
            .findEmpPayDeductionDtl(empRegisterReq.getId(), empRegisterReq.getStatus());
    
    System.out.println("==serviceimpl=2------==empPayDeductionEntities===="+empPayDeductionDtlEntities.size());
    Map<Long, PayDeductionCodes> payDeductionCodeMap = new HashMap<Long, PayDeductionCodes>();
    
    System.out.println("==serviceimpl=2------==AppUserUtils.getClientId()===="+AppUserUtils.getClientId());
    System.out.println("==serviceimpl=2------==empRegisterReq.getCountryId()===="+empRegisterReq.getCountryId());
    System.out.println("==serviceimpl=2------==empRegisterReq.getProvinceId()===="+empRegisterReq.getProvinceId());
    System.out.println("==serviceimpl=2------==empRegisterReq.getPayTypeId()===="+empRegisterReq.getPayTypeId());
    System.out.println("==serviceimpl=2------==empRegisterReq.getEffectiveDate()===="+empRegisterReq.getEffectiveDate());
    
    List<PayDeductionCodeEntity> payDeductionCodeEntityList = empPayDeductionDetailsRepository.getEmpPayDeductionCodes(AppUserUtils.getClientId(),
            empRegisterReq.getCountryId(), empRegisterReq.getProvinceId(), empRegisterReq.getPayTypeId());
    
    if (CommonUtil.isListHasData(empPayDeductionDtlEntities)) {
    	 System.out.println("==CommonUtil.isListHasData if  loop====");
        for (EmpPayDeductionDtlEntity empPayDeductionDtlEntity : empPayDeductionDtlEntities) {
        	 System.out.println("==EmpRegularPayRateDetailEntity for  loop====");
        	 empPayDeductionResp.getEmpPayDeductionDetailTOs()
                    .add(EmpPayDeductionDetailHandler.convertEntityToPOJO(empPayDeductionDtlEntity));
            System.out.println("==EmpRegularPayRateDetailEntity for  loop===END===");
        }
        for (PayDeductionCodeEntity payDeductionCodeEntity : payDeductionCodeEntityList) {
        	System.out.println("==RegularPayAllowanceEntity for  loop===start===");
        	payDeductionCodeMap.put(payDeductionCodeEntity.getId(), EmpPayDeductionAllowanceHandler.convertEntityToPOJO(payDeductionCodeEntity));
        	System.out.println("==RegularPayAllowanceEntity for  loop===end===");
        }
        System.out.println("==CommonUtil.isListHasData if  loop===end===");
    } else {
    System.out.println("==serviceimpl=2------==after empRegularPaybleDetailsRepository.getEmpRegularPaycodes====");
    
    System.out.println("==serviceimpl=2------==after labelKeyTOs===="+payDeductionCodeEntityList.size());
    EmpPayDeductionDetailTO empPayDeductionDetailTO = null;
    for (PayDeductionCodeEntity payDeductionCodeEntity : payDeductionCodeEntityList) {
    	  System.out.println("==serviceimpl=2------==inside for loop====");
    	  empPayDeductionDetailTO = new EmpPayDeductionDetailTO();
        System.out.println("==serviceimpl=2------==empRegisterReq.getId()===="+empRegisterReq.getId());
        empPayDeductionDetailTO.setPayDeductionId(empRegisterReq.getId());
        System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.getEmpPaybaleRateId()===="+empPayDeductionDetailTO.getPayDeductionId());
        empPayDeductionDetailTO.setStatus(StatusCodes.ACTIVE.getValue());
        System.out.println("==serviceimpl=2------== empPaybleRateDetailTO.setStatus===="+ empPayDeductionDetailTO.getStatus());
        
        //empPaybleRateDetailTO.setFinanceRegId(Long.valueOf(61));
        empPayDeductionDetailTO.setFinanceDeductionId(payDeductionCodeEntity.getId());
        System.out.println("==serviceimpl=2------== empPaybleRateDetailTO.getFinanceRegId===="+ empPayDeductionDetailTO.getFinanceDeductionId());
        empPayDeductionDetailTO.setCode(payDeductionCodeEntity.getCode());
        System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.setCode===="+ empPayDeductionDetailTO.getCode());
        empPayDeductionDetailTO.setName(payDeductionCodeEntity.getDescription());
        System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.setName===="+ empPayDeductionDetailTO.getName());
        
        empPayDeductionResp.getEmpPayDeductionDetailTOs().add(empPayDeductionDetailTO);
        System.out.println("==serviceimpl=2------==before map code====");
        payDeductionCodeMap.put(payDeductionCodeEntity.getId(), EmpPayDeductionAllowanceHandler.convertEntityToPOJO(payDeductionCodeEntity));
        System.out.println("==serviceimpl=2------==after map code====");
    }
    }
    List<EmpPayDeductionEntity> empPayDeductionEntities = empPayDeductionRepository
            .findEmpPayDeductionDetailsById(empRegisterReq.getId(), empRegisterReq.getStatus());
    System.out.println("==empPayDeductionEntities=2------=====");
    EmpPayDeductionEntity empPayDeductionEntity = null;
    if (CommonUtil.isListHasData(empPayDeductionEntities)) {
    	 System.out.println("==empPayDeductionEntities=2------==if loop===");
        empPayDeductionEntity = empPayDeductionEntities.get(0);
    } else {
    	System.out.println("==empPayDeductionEntities=2------==else loop===");
        empPayDeductionEntity = new EmpPayDeductionEntity();
    }
    
    System.out.println("==serviceimpl=2------==after for loop====");
   
    if (CommonUtil.isListHasData(empPayDeductionEntity.getEmpPayDeductionQuestEntities())) {
    	System.out.println("==getEmpPayDeductionQuestEntities=2------==if loop===");
        for (EmpPayDeductionQuestEntity entity : empPayDeductionEntity.getEmpPayDeductionQuestEntities()) {
        	System.out.println("==getEmpPayDeductionQuestEntities=2------==if loop=====for loop=");
            empPayDeductionResp.getEmpPayDeductionQuestionTOs()
                    .add(EmpPayDeductionQuestionHandler.convertEntityToPOJO(entity));
        }
    } else {
    	System.out.println("==getEmpPayDeductionQuestEntities=2------==else loop===");
    	System.out.println("==getEmpPayDeductionQuestEntities=2------==AppUserUtils.getClientId()p==="+AppUserUtils.getClientId());
    	System.out.println("==getEmpPayDeductionQuestEntities=2------==StatusCodes.ACTIVE.getValue()==="+StatusCodes.ACTIVE.getValue());
        List<EmpPayQuestionaryMstrEntity> empPayQuestionaryMstrEntities = empPayQuestionMstrRepository
                .findEmpPayQuestions(AppUserUtils.getClientId(), StatusCodes.ACTIVE.getValue());
        EmpPayDeductionQuestionTO empPayDeductionQuestionTO = null;
        for (EmpPayQuestionaryMstrEntity entity : empPayQuestionaryMstrEntities) {
        	System.out.println("==getEmpPayDeductionQuestEntities=2------==else loop=====for loop=");
            empPayDeductionQuestionTO = new EmpPayDeductionQuestionTO();
            empPayDeductionQuestionTO.setEmpPayQuestionMstrTO(EmpPayDeductionQuestionHandler.convertMstrEntityToPOJO(entity));
            empPayDeductionQuestionTO.setPayDeductionId(empRegisterReq.getId());
            System.out.println("==empPayDeductionQuestionTO=2------==else loop=====for loop="+empPayDeductionQuestionTO.getPayDeductionId());
            empPayDeductionQuestionTO.setStatus(empRegisterReq.getStatus());
            System.out.println("==empPayDeductionQuestionTO=2------==else loop=====for loop="+empPayDeductionQuestionTO.getStatus());
            empPayDeductionResp.getEmpPayDeductionQuestionTOs().add(empPayDeductionQuestionTO);
        }
    }
    empPayDeductionResp.setPayDeductionCodeMap(payDeductionCodeMap);
    return empPayDeductionResp;
    }

    public void saveEmpPayDeductions(EmpPayDeductionSaveReq empPayDeductionSaveReq) {
        EmpPayDeductionEntity empPayDeductionEntity = null;
        List<EmpPayDeductionEntity> empPayDeductionEntities = new ArrayList<EmpPayDeductionEntity>();
        EmpPayDeductionDtlEntity empPayDeductionDtlEntity = null;
        EmpPayDeductionQuestEntity empPayDeductionQuestEntity = null;
        for (EmpPayDeductionTO empPayDeductionTO : empPayDeductionSaveReq.getEmpPayDeductionTOs()) {
            empPayDeductionEntity = EmpPayDeductionHandler.convertPOJOToEntity(empPayDeductionTO,
                    empProjRegisterRepository, empRegisterRepository, projGeneralRepository);
            for (EmpPayDeductionDetailTO empPayDeductionDetailTO : empPayDeductionTO.getEmpPayDeductionDetailTOs()) {
                empPayDeductionDtlEntity = EmpPayDeductionDetailHandler.convertPOJOToEntity(empPayDeductionDetailTO,
                        providentFundRepository);
                empPayDeductionDtlEntity.setEmpPayDeductionEntity(empPayDeductionEntity);
                empPayDeductionEntity.getEmpPayDeductionDtlEntities().add(empPayDeductionDtlEntity);
            }
            for (EmpPayDeductionQuestionTO empPayDeductionQuestionTO : empPayDeductionTO
                    .getEmpPayDeductionQuestionTOs()) {
                empPayDeductionQuestEntity = EmpPayDeductionQuestionHandler
                        .convertPOJOToEntity(empPayDeductionQuestionTO);
                empPayDeductionQuestEntity.setEmpPayDeductionEntity(empPayDeductionEntity);
                empPayDeductionEntity.getEmpPayDeductionQuestEntities().add(empPayDeductionQuestEntity);
            }
            empPayDeductionEntity.setLatest(true);
            empPayDeductionEntities.add(empPayDeductionEntity);
            if (CommonUtil.isNonBlankLong(empPayDeductionTO.getId())) {
                empPayDeductionEntity.setId(null);
                empPayDeductionEntity = empPayDeductionRepository.findOne(empPayDeductionTO.getId());
                empPayDeductionEntity.setLatest(false);
                Calendar cal = Calendar.getInstance();
                cal.setTime(empPayDeductionEntity.getFromDate());
                cal.add(Calendar.DATE, -1);
                empPayDeductionEntity.setToDate(cal.getTime());
            }
        }
        empPayDeductionRepository.save(empPayDeductionEntities);
    }
}
