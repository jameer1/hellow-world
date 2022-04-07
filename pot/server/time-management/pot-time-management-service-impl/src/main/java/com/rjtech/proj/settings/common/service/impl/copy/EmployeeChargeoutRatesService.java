package com.rjtech.proj.settings.common.service.impl.copy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjtech.register.emp.dto.EmpChargeOutRateTO;
import com.rjtech.register.emp.model.EmpChargeOutRateEntity;
import com.rjtech.register.repository.emp.EmpProjRegisterRepositoryCopy;
//import com.rjtech.timemanagement.register.emp.model.EmpChargeOutRateEntityCopy;

@Service
public class EmployeeChargeoutRatesService {

    @Autowired
    private EmpProjRegisterRepositoryCopy empProjRegisterRepositoryCopy;

    /**
     * Get emp charge out rate for given day
     * 
     * @param chargeRates
     * @param actualHrsMap
     */
    public EmpChargeOutRateTO getEmpChargeoutRate(Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates,
            Date timesheetDate, Long empRegId) {
        Map<Date, EmpChargeOutRateTO> empRateWithDates = chargeRates.get(empRegId);
        if (empRateWithDates == null || empRateWithDates.values().isEmpty())
            return null;
        Set<Date> effectiveDates = empRateWithDates.keySet().stream().collect(Collectors.toCollection(TreeSet::new));
        List<Date> mappedDate = effectiveDates.stream()
                .filter(d -> d.getTime() == timesheetDate.getTime() || d.before(timesheetDate))
                .collect(Collectors.toList());
        if (mappedDate.isEmpty())
            return null;
        Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
        return empRateWithDates.get(effectiveDate);
    }

    /**
     * Fetch emp chargeout rates
     * 
     * @param projMstrEntity
     * @return Key - EmpRegId, Value - Respective charge-out rate as value
     */
    public Map<Long, Map<Date, EmpChargeOutRateTO>> getEmpChargeOutRates(Long projId) {
        Map<Long, Map<Date, EmpChargeOutRateTO>> empRates = new HashMap<>();
        List<EmpChargeOutRateEntity> rates = empProjRegisterRepositoryCopy.findChargeOutRates(projId);
        for (EmpChargeOutRateEntity rateEntity : rates) {
            if (rateEntity.getEmpRegisterDtlEntity() == null)
                continue;
            Long key = rateEntity.getEmpRegisterDtlEntity().getId();
            empRates.computeIfAbsent(key, v -> new HashMap<>());
            Map<Date, EmpChargeOutRateTO> rateByDate = empRates.get(key);
            rateByDate.put(rateEntity.getFromDate(), processEmpRates(rateEntity));
            empRates.put(key, rateByDate);
        }
        return empRates;
    }

    /**
     * Get EmpReg with rates
     * 
     * @param rateEntity
     * @return
     */
    private EmpChargeOutRateTO processEmpRates(EmpChargeOutRateEntity rateEntity) {
        EmpChargeOutRateTO empRate = new EmpChargeOutRateTO();
        empRate.setEmpRegId(rateEntity.getEmpRegisterDtlEntity().getId());
        empRate.setNormalRate(rateEntity.getNormalRate());
        empRate.setIdleRate(rateEntity.getIdleRate());
        return empRate;
    }

}
