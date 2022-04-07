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
import com.rjtech.finance.repository.ProvidentFundRepository;
//import com.rjtech.finance.repository.ProvidentFundRepositoryCopy;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.emp.dto.EmpProvidentFundContributionTO;
import com.rjtech.register.emp.dto.EmpProvidentFundTO;
import com.rjtech.register.emp.dto.EmpProvidentFundTaxTO;
import com.rjtech.register.emp.model.EmpProvidentFundContributionEntity;
import com.rjtech.register.emp.model.EmpProvidentFundDetailEntity;
import com.rjtech.register.emp.model.EmpProvidentFundEntity;
import com.rjtech.register.emp.model.EmpProvidentFundTaxEntity;
import com.rjtech.register.emp.req.EmpProvidentFundSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpProvidentFundResp;
import com.rjtech.register.proc.emp.EmpPayCodeProcRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpProvidentFundRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpProvidentFundService;
import com.rjtech.register.service.handler.emp.EmpProvidentFundDetailHandler;
import com.rjtech.register.service.handler.emp.EmpProvidentFundHandler;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.register.emp.model.EmpProvidentFundDetailEntity;
import com.rjtech.register.repository.emp.EmpProvidentFundDtlRepository;
import com.rjtech.common.dto.SuperProvidentFund;
import com.rjtech.centrallib.model.SuperProvidentFundEntity;
import com.rjtech.register.repository.emp.EmpProvidentFundDetailsRepository;
import com.rjtech.register.service.handler.emp.EmpProvidentFundAllowanceHandler;
import com.rjtech.register.emp.dto.EmpProvidentFundDetailTO;

@Service(value = "empProvidentFoundService")
@RJSService(modulecode = "empProvidentFoundService")
@Transactional
public class EmpProvidentFundServiceImpl implements EmpProvidentFundService {

    @Autowired
    private EmpProvidentFundRepository empProvidentFundRepository;

    @Autowired
    private EmpPayCodeProcRepository empPayCodeProcRepository;

    @Autowired
    private ProvidentFundRepository providentFundRepository;

    @Autowired
    private EmpProjRegisterRepository empProjRegisterRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private ProjGeneralRepositoryCopy projGeneralRepository;
    
    @Autowired
    private EmpProvidentFundDtlRepository empProvidentFundDtlRepository;
    
    @Autowired
    private EmpProvidentFundDetailsRepository empProvidentFundDetailsRepository;

    public EmpProvidentFundResp getEmpProvidentFunds(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpProvidentFundResp empProvidentFundResp = new EmpProvidentFundResp();
        List<EmpProvidentFundEntity> empProvidentFundDtlEntites = empProvidentFundRepository
                .findEmpProvidentFunds(projEmpRegisterGetReq.getEmpId(), projEmpRegisterGetReq.getStatus());
        for (EmpProvidentFundEntity empProvidentFundEntity : empProvidentFundDtlEntites) {
            empProvidentFundResp.getEmpProvidentFundTOs()
                    .add(EmpProvidentFundHandler.convertEntityToPOJO(empProvidentFundEntity));
        }
        return empProvidentFundResp;
    }

    public EmpProvidentFundResp getEmpProvidentFundDetails(EmpRegisterReq empRegisterReq) {
    	EmpProvidentFundResp empProvidentFundResp = new EmpProvidentFundResp();
    	System.out.println("==serviceimpl===getEmpProvidentFundDetails====");
    	List<EmpProvidentFundDetailEntity> empProvidentFundEntities = empProvidentFundDtlRepository
                .findEmpProvidentFundDtl(empRegisterReq.getId(), empRegisterReq.getStatus());
    	System.out.println("==serviceimpl=2------==empProvidentFundEntities===="+empProvidentFundEntities.size());
        Map<Long, SuperProvidentFund> superProvidentFundMap = new HashMap<Long, SuperProvidentFund>();
        System.out.println("==serviceimpl=2------==AppUserUtils.getClientId()===="+AppUserUtils.getClientId());
        System.out.println("==serviceimpl=2------==empRegisterReq.getCountryId()===="+empRegisterReq.getCountryId());
        System.out.println("==serviceimpl=2------==empRegisterReq.getProvinceId()===="+empRegisterReq.getProvinceId());
        System.out.println("==serviceimpl=2------==empRegisterReq.getPayTypeId()===="+empRegisterReq.getPayTypeId());
        System.out.println("==serviceimpl=2------==empRegisterReq.getEffectiveDate()===="+empRegisterReq.getEffectiveDate());
        List<SuperProvidentFundEntity> providentFundEntityList = empProvidentFundDetailsRepository.getEmpPayDeductionCodes(AppUserUtils.getClientId(),
                empRegisterReq.getCountryId(), empRegisterReq.getProvinceId(), 1);
        
        EmpProvidentFundEntity empProvidentFundEntity = null;
        
        
        if (CommonUtil.isListHasData(empProvidentFundEntities)) {
       	 System.out.println("==CommonUtil.isListHasData if  loop====");
           for (EmpProvidentFundDetailEntity empProvidentFundDetailEntity : empProvidentFundEntities) {
           	 System.out.println("==EmpRegularPayRateDetailEntity for  loop====");
           	empProvidentFundResp.getEmpProvidentFundDetailTOs()
                       .add(EmpProvidentFundDetailHandler.convertEntityToPOJO(empProvidentFundDetailEntity));
               System.out.println("==EmpRegularPayRateDetailEntity for  loop===END===");
           }
           for (SuperProvidentFundEntity superProvidentFundEntity : providentFundEntityList) {
           	System.out.println("==RegularPayAllowanceEntity for  loop===start===");
           	superProvidentFundMap.put(superProvidentFundEntity.getId(), EmpProvidentFundAllowanceHandler.convertEntityToPOJO(superProvidentFundEntity));
           	System.out.println("==RegularPayAllowanceEntity for  loop===end===");
           }
           System.out.println("==CommonUtil.isListHasData if  loop===end===");
       }else {
    	    System.out.println("==serviceimpl=2------==after empRegularPaybleDetailsRepository.getEmpRegularPaycodes====");
    	    
    	    System.out.println("==serviceimpl=2------==after labelKeyTOs===="+providentFundEntityList.size());
    	    EmpProvidentFundContributionTO empProvidentFundDetailTO = null;
    	    for (SuperProvidentFundEntity superProvidentFundEntity : providentFundEntityList) {
    	    	  System.out.println("==serviceimpl=2------==inside for loop====");
    	    	  empProvidentFundDetailTO = new EmpProvidentFundContributionTO();
    	        System.out.println("==serviceimpl=2------==empRegisterReq.getId()===="+empRegisterReq.getId());
    	        empProvidentFundDetailTO.setProvidentFundId(empRegisterReq.getId());
    	        System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.getEmpPaybaleRateId()===="+empProvidentFundDetailTO.getProvidentFundId());
    	        empProvidentFundDetailTO.setStatus(StatusCodes.ACTIVE.getValue());
    	        System.out.println("==serviceimpl=2------== empPaybleRateDetailTO.setStatus===="+ empProvidentFundDetailTO.getStatus());
    	        
    	        //empPaybleRateDetailTO.setFinanceRegId(Long.valueOf(61));
    	        empProvidentFundDetailTO.setFinanceFundId(superProvidentFundEntity.getId());
    	        System.out.println("==serviceimpl=2------== empPaybleRateDetailTO.getFinanceRegId===="+ empProvidentFundDetailTO.getFinanceFundId());
    	       /* empProvidentFundDetailTO.setCode(superProvidentFundEntity.getCode());
    	        System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.setCode===="+ empProvidentFundDetailTO.getCode());
    	        empProvidentFundDetailTO.setName(superProvidentFundEntity.getDescription());
    	        System.out.println("==serviceimpl=2------==empPaybleRateDetailTO.setName===="+ empProvidentFundDetailTO.getName());*/
    	        
    	        empProvidentFundResp.getEmpProvidentFundContributionTOs().add(empProvidentFundDetailTO);
    	        System.out.println("==serviceimpl=2------==before map code==empProvidentFundResp==");
    	        superProvidentFundMap.put(superProvidentFundEntity.getId(), EmpProvidentFundAllowanceHandler.convertEntityToPOJO(superProvidentFundEntity));
    	        System.out.println("==serviceimpl=2------==after map code====");
    	    }
    	    }
        empProvidentFundResp.setProvidentFundCodeMap(superProvidentFundMap);
        System.out.println("==after setting------==after map code====");
       /* if (CommonUtil.isListHasData(empProvidentFundEntity.getEmpProvidentFundDetailEntities())) {
			System.out.println("==EmpProvidentFundTaxEntity=2------if code====common util====");
            for (EmpProvidentFundDetailEntity empProvidentFundDetailEntity : empProvidentFundEntity
                    .getEmpProvidentFundDetailEntities()) {
            	System.out.println("==EmpProvidentFundTaxEntity=2------for looop inside==start=");
                empProvidentFundResp.getEmpProvidentFundDetailTOs()
                        .add(EmpProvidentFundDetailHandler.convertEntityToPOJO(empProvidentFundDetailEntity));
                System.out.println("==EmpProvidentFundTaxEntity=2------for looop inside==end=");
            }
        }
        	EmpProvidentFundTaxTO empProvidentFundTaxTO = null;
        		if (CommonUtil.isListHasData(empProvidentFundEntity.getEmpProvidentFundTaxEntities())) {
        			System.out.println("==empProvidentFundEntity=2------if code====");
        				for (EmpProvidentFundTaxEntity entity : empProvidentFundEntity.getEmpProvidentFundTaxEntities()) {
        					System.out.println("==EmpProvidentFundTaxEntity=2------for code====");
        					empProvidentFundTaxTO = EmpProvidentFundDetailHandler.convertTaxEntityToPOJO(entity);
        					System.out.println("==EmpProvidentFundTaxEntity=2------first line====");
        					empProvidentFundResp.getEmpProvidentFundTaxTOs().add(empProvidentFundTaxTO);
        					System.out.println("==EmpProvidentFundTaxEntity=2------second libe====");
            }
        } else {
        					empProvidentFundTaxTO = new EmpProvidentFundTaxTO();
        					System.out.println("==EmpProvidentFundTaxEntity=2------else code====");
        					empProvidentFundResp.getEmpProvidentFundTaxTOs().add(empProvidentFundTaxTO);
        }
        		*/
    	    System.out.println("==serviceimpl=2------==after for loop====");
    	   // empProvidentFundResp.setProvidentFundCodeMap(providentFundCodeMap);
    	    
    	    //empProvidentFundResp.setProvidentFundCodeMap(superProvidentFundMap);

    	    return empProvidentFundResp;
    	
    	
    }

    public void saveEmpProvidentFunds(EmpProvidentFundSaveReq empProvidentFundSaveReq) {
        List<EmpProvidentFundEntity> empProvidentFundDtlEntites = new ArrayList<EmpProvidentFundEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        EmpProvidentFundEntity empProvidentFundEntity = null;
        for (EmpProvidentFundTO empProvidentFundDtlTO : empProvidentFundSaveReq.getEmpProvidentFundTOs()) {
            empProvidentFundEntity = EmpProvidentFundHandler.populateProvientFundEntity(empProvidentFundDtlTO,
                    providentFundRepository, empProjRegisterRepository, empRegisterRepository, projGeneralRepository);
            empProvidentFundEntity.setLatest(true);
            empProvidentFundDtlEntites.add(empProvidentFundEntity);
            if (CommonUtil.isNonBlankLong(empProvidentFundDtlTO.getId())) {
                empProvidentFundEntity.setId(null);
                empProvidentFundEntity = empProvidentFundRepository.findOne(empProvidentFundDtlTO.getId());
                empProvidentFundEntity.setLatest(false);
                Calendar cal = Calendar.getInstance();
                cal.setTime(empProvidentFundEntity.getFromDate());
                cal.add(Calendar.DATE, -1);
                empProvidentFundEntity.setToDate(cal.getTime());
            }
        }
        empProvidentFundRepository.save(empProvidentFundDtlEntites);
    }

}
