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
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.repository.ProjGeneralRepository;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.emp.dto.EmpPaybleRateDetailTO;
import com.rjtech.register.emp.dto.EmpPaybleRateTO;
import com.rjtech.register.emp.model.EmpRegularPayRateDetailEntity;
import com.rjtech.register.emp.model.EmpRegularPayRateEntity;
import com.rjtech.register.emp.req.EmpPayRatesSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;
import com.rjtech.register.proc.emp.EmpPayCodeProcRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegularPaybleRateDetailRepository;
import com.rjtech.register.repository.emp.EmpRegularPaybleRateRepository;
import com.rjtech.register.repository.emp.EmpRegularPaybleDetailsRepository;
import com.rjtech.register.service.emp.EmpRegularPayService;
import com.rjtech.register.service.handler.emp.EmpRegularPayRateDetailHandler;
import com.rjtech.register.service.handler.emp.EmpRegularPayRateHandler;
import com.rjtech.centrallib.model.RegularPayAllowanceEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.common.dto.RegularPayAllowance;
import com.rjtech.register.service.handler.emp.EmpRegularPayAllowanceHandler;

@Service(value = "empRegularPayService")
@RJSService(modulecode = "empRegularPayService")
@Transactional
public class EmpRegularPayServiceImpl implements EmpRegularPayService {

    @Autowired
    private EmpRegularPaybleRateRepository empRegularPaybleRateRepository;

    @Autowired
    private EmpRegularPaybleRateDetailRepository empRegularPaybleRateDetailRepository;

    @Autowired
    private EmpPayCodeProcRepository empPayCodeProcRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private ProjGeneralRepository projGeneralRepository;

    @Autowired
    private EmpProjRegisterRepository empProjRegisterRepository;
    
    @Autowired
    private EmpRegularPaybleDetailsRepository empRegularPaybleDetailsRepository;

    public EmpPaybleRateResp getEmpRegularPaybleRates(EmpRegisterReq empRegisterReq) {
        EmpPaybleRateResp empPaybleRateResp = new EmpPaybleRateResp();
        System.out.println("==serviceimpl===getEmpRegularPaybleRates====");
        List<EmpRegularPayRateEntity> empRegularPaybleRateEntites = empRegularPaybleRateRepository
                .findEmpRegularPaybleRates(empRegisterReq.getEmpId(), empRegisterReq.getStatus());
        System.out.println("==serviceimpl===empRegularPaybleRateEntites====");
        EmpPaybleRateTO empPaybleRateTO = null;
        System.out.println("==serviceimpl===empRegularPaybleRateRepository====");
        for (EmpRegularPayRateEntity empRegularPayRateEntity : empRegularPaybleRateEntites) {
        	 System.out.println("==serviceimpl===for loop====");
            empPaybleRateTO = EmpRegularPayRateHandler.convertEntityToPOJO(empRegularPayRateEntity);
            empPaybleRateResp.getEmpPaybleRateTOs().add(empPaybleRateTO);
        }
        System.out.println("==serviceimpl===after for loop====");
        return empPaybleRateResp;
    }

    public EmpPaybleRateResp getEmpRegularPaybleRateDetails(EmpRegisterReq empRegisterReq) {
        EmpPaybleRateResp empPaybleRateResp = new EmpPaybleRateResp();
        System.out.println("==serviceimpl===getEmpRegularPaybleRateDetails====");
        //Date date=CommonUtil.convertStringToDate(empRegisterReq.getEffectiveDate());
        List<EmpRegularPayRateDetailEntity> empRegularPaybleRateEntites = empRegularPaybleRateDetailRepository
                .findEmpRegularPaybleRateDetails(empRegisterReq.getId(), empRegisterReq.getStatus());
        System.out.println("==serviceimpl=2------==getEmpRegularPaybleRateDetails===="+empRegularPaybleRateEntites.size());
        Map<Long, RegularPayAllowance> regularPayCodeMap = new HashMap<Long, RegularPayAllowance>();
        
        System.out.println("==serviceimpl=2------==AppUserUtils.getClientId()===="+AppUserUtils.getClientId());
        System.out.println("==serviceimpl=2------==empRegisterReq.getCountryId()===="+empRegisterReq.getCountryId());
        System.out.println("==serviceimpl=2------==empRegisterReq.getProvinceId()===="+empRegisterReq.getProvinceId());
        System.out.println("==serviceimpl=2------==empRegisterReq.getPayTypeId()===="+empRegisterReq.getPayTypeId());
        System.out.println("==serviceimpl=2------==empRegisterReq.getEffectiveDate()===="+empRegisterReq.getEffectiveDate());
        
        List<RegularPayAllowanceEntity> regularPayAllowanceEntityList = empRegularPaybleDetailsRepository.getEmpRegularPaycodes(AppUserUtils.getClientId(),
                empRegisterReq.getCountryId(), empRegisterReq.getProvinceId(), empRegisterReq.getPayTypeId());
        
        if (CommonUtil.isListHasData(empRegularPaybleRateEntites)) {
        	 System.out.println("==CommonUtil.isListHasData if  loop====");
            for (EmpRegularPayRateDetailEntity empRegularPaybleRateEntity : empRegularPaybleRateEntites) {
            	 System.out.println("==EmpRegularPayRateDetailEntity for  loop====");
                empPaybleRateResp.getEmpPaybleRateDetailTOs()
                        .add(EmpRegularPayRateDetailHandler.convertEntityToPOJO(empRegularPaybleRateEntity));
                System.out.println("==EmpRegularPayRateDetailEntity for  loop===END===");
            }
            for (RegularPayAllowanceEntity regularPayAllowanceEntity : regularPayAllowanceEntityList) {
            	System.out.println("==RegularPayAllowanceEntity for  loop===start===");
            	regularPayCodeMap.put(regularPayAllowanceEntity.getId(), EmpRegularPayAllowanceHandler.convertEntityToPOJO(regularPayAllowanceEntity));
            	System.out.println("==RegularPayAllowanceEntity for  loop===end===");
            }
            System.out.println("==CommonUtil.isListHasData if  loop===end===");
        } else {
        System.out.println("==serviceimpl=2------==after empRegularPaybleDetailsRepository.getEmpRegularPaycodes====");
        
        System.out.println("==serviceimpl=2------==after labelKeyTOs===="+regularPayAllowanceEntityList.size());
        EmpPaybleRateDetailTO empPaybleRateDetailTO = null;
        for (RegularPayAllowanceEntity regularPayAllowanceEntity : regularPayAllowanceEntityList) {
        	  System.out.println("==serviceimpl=2------==inside for loop====");
            empPaybleRateDetailTO = new EmpPaybleRateDetailTO();
            System.out.println("==serviceimpl=2------==empRegisterReq.getId()===="+empRegisterReq.getId());
            empPaybleRateDetailTO.setEmpPaybaleRateId(empRegisterReq.getId());
            System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.getEmpPaybaleRateId()===="+empPaybleRateDetailTO.getEmpPaybaleRateId());
            empPaybleRateDetailTO.setStatus(StatusCodes.ACTIVE.getValue());
            System.out.println("==serviceimpl=2------== empPaybleRateDetailTO.setStatus===="+ empPaybleRateDetailTO.getStatus());
            
            //empPaybleRateDetailTO.setFinanceRegId(Long.valueOf(61));
            empPaybleRateDetailTO.setFinanceRegId(regularPayAllowanceEntity.getId());
            System.out.println("==serviceimpl=2------== empPaybleRateDetailTO.getFinanceRegId===="+ empPaybleRateDetailTO.getFinanceRegId());
            empPaybleRateDetailTO.setCode(regularPayAllowanceEntity.getCode());
            System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.setCode===="+ empPaybleRateDetailTO.getCode());
            empPaybleRateDetailTO.setName(regularPayAllowanceEntity.getDescription());
            System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.setName===="+ empPaybleRateDetailTO.getName());
            
            empPaybleRateResp.getEmpPaybleRateDetailTOs().add(empPaybleRateDetailTO);
            System.out.println("==serviceimpl=2------==before map code====");
           
           //empPaybleRateResp.getEmpPaybleRateDetailTOs().add(EmpRegularPayAllowanceHandler.convertEntityToPOJO(regularPayAllowanceEntity));
            regularPayCodeMap.put(regularPayAllowanceEntity.getId(), EmpRegularPayAllowanceHandler.convertEntityToPOJO(regularPayAllowanceEntity));
            System.out.println("==serviceimpl=2------==after map code====");
            
            
            /*procureCatgMap.put(procureCatgDtlEntity.getId(),
                    ProcureCatgHandler.convertProcureCatgDtlEntityToLabelKeyTo(procureCatgDtlEntity));*/

        }
        }
        System.out.println("==serviceimpl=2------==after for loop====");
        empPaybleRateResp.setRegularPayCodeMap(regularPayCodeMap);

        return empPaybleRateResp;
    }

    public void saveEmpRegularPaybleRates(EmpPayRatesSaveReq empPayRatesSaveReq) {
        List<EmpRegularPayRateEntity> empRegularPaybleRateEntites = new ArrayList<EmpRegularPayRateEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        System.out.println("==middlelayer===serviceimpl==saveEmpRegularPaybleRates");
        EmpRegularPayRateEntity empRegularPayRateEntity = null;
        for (EmpPaybleRateTO empPaybleRateTO : empPayRatesSaveReq.getEmpPaybleRateTOs()) {
            empRegularPayRateEntity = EmpRegularPayRateHandler.populateEmpRegularPayEntity(empPaybleRateTO,
                    empRegisterRepository, projGeneralRepository, empProjRegisterRepository);
            empRegularPayRateEntity.setLatest(true);
            empRegularPaybleRateEntites.add(empRegularPayRateEntity);
            if (CommonUtil.isNonBlankLong(empPaybleRateTO.getId())) {
                empRegularPayRateEntity.setId(null);
                empRegularPayRateEntity = empRegularPaybleRateRepository.findOne(empPaybleRateTO.getId());
                empRegularPayRateEntity.setLatest(false);
                Calendar cal = Calendar.getInstance();
                cal.setTime(empRegularPayRateEntity.getFromDate());
                cal.add(Calendar.DATE, -1);
                empRegularPayRateEntity.setToDate(cal.getTime());
            }
        }
        empRegularPaybleRateRepository.save(empRegularPaybleRateEntites);
    }
}
