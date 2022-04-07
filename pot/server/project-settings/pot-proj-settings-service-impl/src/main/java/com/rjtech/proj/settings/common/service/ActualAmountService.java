package com.rjtech.proj.settings.common.service;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.rjtech.common.dto.CostActualHoursTO;
import com.rjtech.projsettings.register.emp.dto.EmpChargeOutRateTO;
import com.rjtech.projsettings.register.plant.dto.PlantChargeOutRateTO;

public interface ActualAmountService {

    /**
     * Calculate actual amount for labor, material and plant
     * 
     * @param projId
     * @return key is costCodeId
     * @return value is actual amount for labor, material and plant
     */
    Map<Long, CostActualHoursTO> getCostStmt(Long projId);

    /**
     * Calculate actual amount for labor
     * 
     * @param projId
     * @return key is costCodeId
     * @return value is actual amount
     */
    Map<Long, Double> getManpowerActualAmount(Long projId);

    /**
     * Calculate actual amount for Material
     * 
     * @param projId
     * @return key is costCodeId
     * @return value is actual amount
     */
    Map<Long, Double> getMaterialActualAmount(Long projId);

    /**
     * Fetch emp chargeout rates
     * 
     * @param projId
     * @return Key1 - ProjId, Key2 - EmpRegId, Value - Respective charge-out rate as
     *         value
     */
    Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> getEmpChargeOutRates(Set<Long> projIds);

    Map<Date, Double> calculateTimesheetDates(Object[] timesheetHr);

    /**
     * Fetch plant chargeout rates
     * 
     * @param projId
     * @return Key1 - ProjId, Key2 - EmpRegId, Value - Respective charge-out rate as
     *         value
     */
    Map<Long, Map<Long, Map<Date, PlantChargeOutRateTO>>> getPlantChargeOutRates(Set<Long> projIds);

}
