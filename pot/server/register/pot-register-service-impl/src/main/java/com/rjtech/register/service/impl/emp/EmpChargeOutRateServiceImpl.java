package com.rjtech.register.service.impl.emp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
//import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.emp.dto.EmpChargeOutRateTO;
import com.rjtech.register.emp.model.EmpChargeOutRateEntity;
import com.rjtech.register.emp.req.EmpChargeOutRateSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpChargeOutRateResp;
import com.rjtech.register.repository.emp.EmpChargeOutRateRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpChargeOutRateService;
import com.rjtech.register.service.handler.emp.EmpChargeOutRateHandler;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "empChargeOutRatesService")
@RJSService(modulecode = "empChargeOutRatesService")
@Transactional
public class EmpChargeOutRateServiceImpl implements EmpChargeOutRateService {

    @Autowired
    private EmpChargeOutRateRepository empChargeOutRateRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private ProjGeneralRepositoryCopy projGeneralRepository;

    @Autowired
    private ProjCostItemRepositoryCopy projCostItemRepository;

    @Autowired
    private EmpProjRegisterRepository empProjRegisterRepository;

    public EmpChargeOutRateResp getEmpChargeOutRates(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpChargeOutRateResp empChargeOutRateResp = new EmpChargeOutRateResp();
        List<EmpChargeOutRateEntity> empChargeOutRatesEntites = empChargeOutRateRepository
                .findEmpChargeOutRates(projEmpRegisterGetReq.getEmpId(), projEmpRegisterGetReq.getStatus());
        for (EmpChargeOutRateEntity empChargeOutRateEntity : empChargeOutRatesEntites) {
            EmpChargeOutRateTO chargeOutRateTO = EmpChargeOutRateHandler.convertEntityToPOJO(empChargeOutRateEntity);
            empChargeOutRateResp.getEmpChargeOutRateTOs().add(chargeOutRateTO);
        }
        return empChargeOutRateResp;
    }

    public void saveEmpChargeOutRates(EmpChargeOutRateSaveReq empChargeOutRateSaveReq) {
        List<EmpChargeOutRateEntity> empChargeOutRatesEntites = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (EmpChargeOutRateTO empChargeOutRateTO : empChargeOutRateSaveReq.getEmpChargeOutRateTOs()) {
            // Set existing as false
            if (CommonUtil.isNonBlankLong(empChargeOutRateTO.getId())) {
                EmpChargeOutRateEntity entity = empChargeOutRateRepository.findOne(empChargeOutRateTO.getId());
                entity.setLatest(false);
                Calendar cal = Calendar.getInstance();
                cal.setTime(CommonUtil.convertStringToDate(empChargeOutRateTO.getFromDate()));
                cal.add(Calendar.DATE, -1);
                entity.setToDate(cal.getTime());
            }
            // Save new rates            
            EmpChargeOutRateEntity empChargeOutRateEntity = EmpChargeOutRateHandler.convertPOJOToEntity(empChargeOutRateTO,
                    empRegisterRepository, measurementRepository, projGeneralRepository, projCostItemRepository,
                    empProjRegisterRepository);
            empChargeOutRateEntity.setLatest(true);
            empChargeOutRatesEntites.add(empChargeOutRateEntity);
        }
        empChargeOutRateRepository.save(empChargeOutRatesEntites);
    }

}
