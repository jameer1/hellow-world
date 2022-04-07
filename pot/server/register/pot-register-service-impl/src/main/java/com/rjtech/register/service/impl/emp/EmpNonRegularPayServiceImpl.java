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
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.repository.NonRegularAllowanceRepository;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.emp.dto.EmpPaybleRateDetailTO;
import com.rjtech.register.emp.dto.EmpPaybleRateTO;
import com.rjtech.register.emp.model.EmpNonRegularPayDetailEntity;
import com.rjtech.register.emp.model.EmpNonRegularPayRateEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.req.EmpPayRatesSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;
import com.rjtech.register.proc.emp.EmpPayCodeProcRepository;
import com.rjtech.register.repository.emp.EmpNonRegularPayRateDetailRepository;
import com.rjtech.register.repository.emp.EmpNonRegularPayRateRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpNonRegularPayService;
import com.rjtech.register.service.handler.emp.EmpNonRegularPayDetailHandler;
import com.rjtech.register.service.handler.emp.EmpNonRegularPayRateHandler;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.common.dto.RegularPayAllowance;
import com.rjtech.register.service.handler.emp.EmpRegularPayAllowanceHandler;
import com.rjtech.centrallib.model.RegularPayAllowanceEntity;
import com.rjtech.register.repository.emp.EmpRegularPaybleDetailsRepository;
import com.rjtech.register.repository.emp.EmpNonRegularPaybleDetailsRepository;
import com.rjtech.common.dto.NonRegularPayAllowance;
import com.rjtech.register.service.handler.emp.EmpNonRegularPayAllowanceHandler;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.centrallib.model.NonRegularPayAllowanceEntity;

@Service(value = "empNonRegularPayService")
@RJSService(modulecode = "empNonRegularPayService")
@Transactional
public class EmpNonRegularPayServiceImpl implements EmpNonRegularPayService {

    @Autowired
    private EmpNonRegularPayRateRepository empNonRegularPayRateRepository;

    @Autowired
    private EmpNonRegularPayRateDetailRepository empNonRegularPayRateDetailRepository;

    @Autowired
    private EmpPayCodeProcRepository empPayCodeProcRepository;

    @Autowired
    private NonRegularAllowanceRepository nonRegularAllowanceRepository;

    @Autowired
    private EmpProjRegisterRepository empProjRegisterRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private ProjGeneralRepositoryCopy projGeneralRepository;
    
    @Autowired
    private EmpRegularPaybleDetailsRepository empRegularPaybleDetailsRepository;
    
    @Autowired
    private EmpNonRegularPaybleDetailsRepository empNonRegularPaybleDetailsRepository;

    public EmpPaybleRateResp getEmpNonRegularPaybleRates(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpPaybleRateResp empPaybleRateResp = new EmpPaybleRateResp();

        List<EmpNonRegularPayRateEntity> empNonRegularPayRateEntities = empNonRegularPayRateRepository
                .findEmpNonRegularPayRates(projEmpRegisterGetReq.getEmpId(), projEmpRegisterGetReq.getStatus());
        EmpPaybleRateTO empPaybleRateTO = null;
        for (EmpNonRegularPayRateEntity empNonRegularPayRateEntity : empNonRegularPayRateEntities) {
            empPaybleRateTO = EmpNonRegularPayRateHandler.convertEntityToPOJO(empNonRegularPayRateEntity);
            empPaybleRateResp.getEmpPaybleRateTOs().add(empPaybleRateTO);
        }
        return empPaybleRateResp;
    }

    public EmpPaybleRateResp getEmpNonRegularPaybleRateDetails(EmpRegisterReq empRegisterReq) {
        EmpPaybleRateResp empPaybleRateResp = new EmpPaybleRateResp();
        
        System.out.println("==getEmpNonRegularPaybleRateDetails==");

        List<EmpNonRegularPayDetailEntity> nonRegularPayDetailEntities = empNonRegularPayRateDetailRepository
                .findEmpNonRegularPayRateDetails(empRegisterReq.getId(), empRegisterReq.getStatus());
        System.out.println("==serviceimpl=2------==nonRegularPayDetailEntities===="+nonRegularPayDetailEntities.size());
        Map<Long, NonRegularPayAllowance> nonRegularPayCodeMap = new HashMap<Long, NonRegularPayAllowance>();
        System.out.println("==nonRegularPayDetailEntities=="+nonRegularPayDetailEntities);
        System.out.println("==serviceimpl=2------==AppUserUtils.getClientId()===="+AppUserUtils.getClientId());
        System.out.println("==serviceimpl=2------==empRegisterReq.getCountryId()===="+empRegisterReq.getCountryId());
        System.out.println("==serviceimpl=2------==empRegisterReq.getProvinceId()===="+empRegisterReq.getProvinceId());
        System.out.println("==serviceimpl=2------==empRegisterReq.getPayTypeId()===="+empRegisterReq.getPayTypeId());
        List<NonRegularPayAllowanceEntity> nonRegularPayAllowanceEntityList = empNonRegularPaybleDetailsRepository.getEmpRegularPaycodes(AppUserUtils.getClientId(),
                empRegisterReq.getCountryId(), empRegisterReq.getProvinceId(), empRegisterReq.getPayTypeId());
        if (CommonUtil.isListHasData(nonRegularPayDetailEntities)) {
       	 System.out.println("==CommonUtil.isListHasData if  loop====");
           for (EmpNonRegularPayDetailEntity empNonRegularPayDetailEntity : nonRegularPayDetailEntities) {
           	 System.out.println("==EmpRegularPayRateDetailEntity for  loop====");
               empPaybleRateResp.getEmpPaybleRateDetailTOs()
                       .add(EmpNonRegularPayDetailHandler.convertEntityToPOJO(empNonRegularPayDetailEntity));
               System.out.println("==EmpRegularPayRateDetailEntity for  loop===END===");
           }
           for (NonRegularPayAllowanceEntity nonRegularPayAllowanceEntity : nonRegularPayAllowanceEntityList) {
           	System.out.println("==RegularPayAllowanceEntity for  loop===start===");
           	nonRegularPayCodeMap.put(nonRegularPayAllowanceEntity.getId(), EmpNonRegularPayAllowanceHandler.convertEntityToPOJO(nonRegularPayAllowanceEntity));
           	System.out.println("==RegularPayAllowanceEntity for  loop===end===");
           }
           System.out.println("==CommonUtil.isListHasData if  loop===end===");
       }else {
           System.out.println("==serviceimpl=2------==after empRegularPaybleDetailsRepository.getEmpRegularPaycodes====");
           
           System.out.println("==serviceimpl=2------==after labelKeyTOs===="+nonRegularPayAllowanceEntityList.size());
           EmpPaybleRateDetailTO empPaybleRateDetailTO = null;
           for (NonRegularPayAllowanceEntity nonRegularPayAllowanceEntity : nonRegularPayAllowanceEntityList) {
           	  System.out.println("==serviceimpl=2------==inside for loop====");
               empPaybleRateDetailTO = new EmpPaybleRateDetailTO();
               System.out.println("==serviceimpl=2------==empRegisterReq.getId()===="+empRegisterReq.getId());
               empPaybleRateDetailTO.setEmpPaybaleRateId(empRegisterReq.getId());
               System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.getEmpPaybaleRateId()===="+empPaybleRateDetailTO.getEmpPaybaleRateId());
               empPaybleRateDetailTO.setStatus(StatusCodes.ACTIVE.getValue());
               System.out.println("==serviceimpl=2------== empPaybleRateDetailTO.setStatus===="+ empPaybleRateDetailTO.getStatus());
               
               //empPaybleRateDetailTO.setFinanceRegId(Long.valueOf(61));
               empPaybleRateDetailTO.setFinanceRegId(nonRegularPayAllowanceEntity.getId());
               System.out.println("==serviceimpl=2------== empPaybleRateDetailTO.getFinanceRegId===="+ empPaybleRateDetailTO.getFinanceRegId());
               empPaybleRateDetailTO.setCode(nonRegularPayAllowanceEntity.getCode());
               System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.setCode===="+ empPaybleRateDetailTO.getCode());
               empPaybleRateDetailTO.setName(nonRegularPayAllowanceEntity.getDescription());
               System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.setName===="+ empPaybleRateDetailTO.getName());
               
               empPaybleRateResp.getEmpPaybleRateDetailTOs().add(empPaybleRateDetailTO);
               System.out.println("==serviceimpl=2------==before map code====");
              
              //empPaybleRateResp.getEmpPaybleRateDetailTOs().add(EmpRegularPayAllowanceHandler.convertEntityToPOJO(regularPayAllowanceEntity));
               nonRegularPayCodeMap.put(nonRegularPayAllowanceEntity.getId(), EmpNonRegularPayAllowanceHandler.convertEntityToPOJO(nonRegularPayAllowanceEntity));
               System.out.println("==serviceimpl=2------==after map code====");
               
               
               /*procureCatgMap.put(procureCatgDtlEntity.getId(),
                       ProcureCatgHandler.convertProcureCatgDtlEntityToLabelKeyTo(procureCatgDtlEntity));*/

           }
           }
            System.out.println("==regularPayCodeMap====else block=");
     
        empPaybleRateResp.setNonRegularPayCodeMap(nonRegularPayCodeMap);

        return empPaybleRateResp;
    }

    public void saveEmpNonRegularPaybleRates(EmpPayRatesSaveReq empPayRatesSaveReq) {
        List<EmpNonRegularPayRateEntity> empNonRegularPayRateEntities = new ArrayList<EmpNonRegularPayRateEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        EmpNonRegularPayRateEntity empNonRegularPayRateEntity = null;
        for (EmpPaybleRateTO empPaybleRateTO : empPayRatesSaveReq.getEmpPaybleRateTOs()) {
            empNonRegularPayRateEntity = EmpNonRegularPayRateHandler.populateEmpNonRegularPayEntity(empPaybleRateTO,
                    nonRegularAllowanceRepository, empProjRegisterRepository, empRegisterRepository,
                    projGeneralRepository);
            empNonRegularPayRateEntity.setLatest(true);
            empNonRegularPayRateEntities.add(empNonRegularPayRateEntity);
            if (CommonUtil.isNonBlankLong(empPaybleRateTO.getId())) {
                empNonRegularPayRateEntity.setId(null);
                empNonRegularPayRateEntity = empNonRegularPayRateRepository.findOne(empPaybleRateTO.getId());
                empNonRegularPayRateEntity.setLatest(false);
                Calendar cal = Calendar.getInstance();
                cal.setTime(empNonRegularPayRateEntity.getFromDate());
                cal.add(Calendar.DATE, -1);
                empNonRegularPayRateEntity.setToDate(cal.getTime());
            }
        }
        empNonRegularPayRateRepository.save(empNonRegularPayRateEntities);
    }
}
