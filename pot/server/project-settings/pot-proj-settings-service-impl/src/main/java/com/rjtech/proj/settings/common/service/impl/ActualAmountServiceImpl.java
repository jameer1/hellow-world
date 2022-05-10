package com.rjtech.proj.settings.common.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.ProjLeaveTypeEntity;
import com.rjtech.centrallib.repository.ProjLeaveTypeRepository;
import com.rjtech.common.dto.CostActualHoursTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.proj.settings.common.service.ActualAmountService;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
import com.rjtech.projsettings.register.emp.dto.EmpChargeOutRateTO;
import com.rjtech.projsettings.register.plant.dto.PlantChargeOutRateTO;
import com.rjtech.projsettings.repository.ProjGeneralRepository;
import com.rjtech.register.emp.model.EmpChargeOutRateEntity;
import com.rjtech.register.emp.model.EmpChargeOutRateEntityCopy;
//import com.rjtech.register.EmpChargeOutRateEntityCopy;
import com.rjtech.register.plant.model.PlantChargeOutRatesEntityCopy;
//import com.rjtech.register.PlantChargeOutRatesEntityCopy;
import com.rjtech.register.repository.emp.EmpProjRegisterRepositoryCopy;
import com.rjtech.register.repository.plant.PlantChargeOutRateRepositoryCopy;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceRepository;
//import com.rjtech.register.timemanagement.attendance.repository.EmpAttendanceRepository;
import com.rjtech.timemanagement.timesheet.repository.copy.TimeSheetEmpDtlRepositoryCopy;
import com.rjtech.timemanagement.timesheet.repository.copy.TimeSheetEmpExpenseRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.EmpCostWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.MaterialStatusWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.PlantCostWorkDairyRepositoryCopy;

@Service(value = "actualAmountServiceImpl")
@Transactional
public class ActualAmountServiceImpl implements ActualAmountService {

    @Autowired
    private EmpProjRegisterRepositoryCopy empProjRegisterRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private ProjLeaveTypeRepository leaveTypeRepository;

    @Autowired
    private EmpAttendanceRepository empAttendanceRepository;

    @Autowired
    private ProjGeneralRepository projGeneralRepository;

    @Autowired
    private EmpCostWorkDairyRepositoryCopy costWorkDairyRepository;

    @Autowired
    private TimeSheetEmpDtlRepositoryCopy timeSheetEmpDtlRepository;

    @Autowired
    private MaterialStatusWorkDairyRepositoryCopy materialStatusWorkDairyRepository;

    @Autowired
    private PlantChargeOutRateRepositoryCopy plantChargeOutRateRepository;

    @Autowired
    private PlantCostWorkDairyRepositoryCopy plantCostWorkDairyRepository;

    @Autowired
    private TimeSheetEmpExpenseRepositoryCopy timeSheetEmpExpenseRepository;

    @Override
    public Map<Long, Double> getManpowerActualAmount(Long projId) {
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
        Map<Long, CostActualHoursTO> actualHrsMap = new HashMap<>();
        getManpowerActualAmount(projMstrEntity, actualHrsMap);
        Map<Long, Double> actualAmountByCost = new HashMap<>();
        actualHrsMap.forEach((k, v) -> {
            actualAmountByCost.computeIfAbsent(k, v1 -> Double.valueOf(0));
            actualAmountByCost.computeIfPresent(k, (k1, v1) -> v1 + v.getLabourCost());
        });
        return actualAmountByCost;
    }

    @Override
    public Map<Long, Double> getMaterialActualAmount(Long projId) {
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
        Map<Long, CostActualHoursTO> actualHrsMap = new HashMap<>();
        getMaterialActualAmount(projMstrEntity, actualHrsMap);
        Map<Long, Double> actualAmountByCost = new HashMap<>();
        actualHrsMap.forEach((k, v) -> {
            actualAmountByCost.computeIfAbsent(k, v1 -> Double.valueOf(0));
            actualAmountByCost.computeIfPresent(k, (k1, v1) -> v1 + v.getMaterialCost());
        });
        return actualAmountByCost;
    }

    @Override
    public Map<Long, CostActualHoursTO> getCostStmt(Long projId) {
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projId);
        Map<Long, CostActualHoursTO> actualHrsMap = new HashMap<>();
        getManpowerActualAmount(projMstrEntity, actualHrsMap);
        getMaterialActualAmount(projMstrEntity, actualHrsMap);
        getPlantActualAmount(projMstrEntity, actualHrsMap);
        return actualHrsMap;
    }

    /**
     * Calculate actual amount for manpower/labor
     * 
     * @param projMstrEntity
     * @param actualHrsMap
     */
    private void getManpowerActualAmount(ProjMstrEntity projMstrEntity, Map<Long, CostActualHoursTO> actualHrsMap) {
        Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates = getEmpChargeOutRates(projMstrEntity);
        if (chargeRates.isEmpty())
            return;
        calculateEmpMobDemobRate(chargeRates, actualHrsMap);
        calculateAmountFromEmpAttd(projMstrEntity, chargeRates, actualHrsMap);
        calculateEmpWorkDairyActualHrs(chargeRates, actualHrsMap);
        calculateEmpTimesheetActualHrs(chargeRates, actualHrsMap);
        calculateEmpTimesheetExpenses(chargeRates, actualHrsMap);
    }

    /**
     * Calculate actual amount for materials from work dairy
     * 
     * @param projMstrEntity
     * @param actualHrsMap
     */
    private void getMaterialActualAmount(ProjMstrEntity projMstrEntity, Map<Long, CostActualHoursTO> actualHrsMap) {
        List<Object[]> materialHrs = materialStatusWorkDairyRepository.getWorkDairyActualAmount(projMstrEntity);
        for (Object[] materialHr : materialHrs) {
            Long costId = (Long) materialHr[0];
            Double matRate = (Double) materialHr[1];
            actualHrsMap.computeIfAbsent(costId, v -> new CostActualHoursTO());
            actualHrsMap.computeIfPresent(costId, (k, v) -> {
                v.setMaterialCost(matRate + v.getMaterialCost());
                return v;
            });
        }
    }

    /**
     * Calculate actual amount for plants
     * 
     * @param projMstrEntity
     * @param actualHrsMap
     */
    private void getPlantActualAmount(ProjMstrEntity projMstrEntity, Map<Long, CostActualHoursTO> actualHrsMap) {
        Map<Long, Map<Date, PlantChargeOutRateTO>> chargeRates = getPlantChargeOutRates(projMstrEntity);
        if (chargeRates.isEmpty())
            return;
        calculatePlantMobDemobRate(chargeRates, actualHrsMap);
        calculatePlantWorkDairyActualHrs(chargeRates, actualHrsMap);
    }

    /**
     * Calculate cost amount from time sheet used hrs
     * 
     * @param chargeRates
     * @param actualHrsMap
     */
    private void calculateEmpTimesheetActualHrs(Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates,
            Map<Long, CostActualHoursTO> actualHrsMap) {
        Set<Long> empIds = chargeRates.keySet();
        List<Object[]> timesheetHrs = timeSheetEmpDtlRepository.getTimesheetEmpActualHrs(empIds);
        for (Object[] timesheetHr : timesheetHrs) {
            Long empId = (Long) timesheetHr[0];
            Map<Date, EmpChargeOutRateTO> empRateWithDates = chargeRates.get(empId);
            if (empRateWithDates == null || empRateWithDates.values().isEmpty())
                continue;
            Long costId = (Long) timesheetHr[1];
            Float wageFactor = (Float) timesheetHr[2];
            Set<Date> effectiveDates = empRateWithDates.keySet().stream()
                    .collect(Collectors.toCollection(TreeSet::new));
            Map<Date, Double> hrsPerDays = calculateTimesheetDates(timesheetHr);
            for (Entry<Date, Double> hrsPerDay : hrsPerDays.entrySet()) {
                Date timesheetDate = hrsPerDay.getKey();
                Double usedHrs = hrsPerDay.getValue();
                List<Date> mappedDate = effectiveDates.stream()
                        .filter(d -> d.getTime() == timesheetDate.getTime() || d.before(timesheetDate))
                        .collect(Collectors.toList());
                if (mappedDate.isEmpty())
                    continue;
                Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
                EmpChargeOutRateTO empRate = empRateWithDates.get(effectiveDate);
                if (empRate == null)
                    continue;
                if (CommonUtil.isNonBlankDouble(usedHrs) && empRate.getNormalRate() != null
                        && !BigDecimal.ZERO.equals(empRate.getNormalRate())) {
                    actualHrsMap.computeIfAbsent(costId, v -> new CostActualHoursTO());
                    actualHrsMap.computeIfPresent(costId, (k, v) -> {
                        Double rate = usedHrs * wageFactor * empRate.getNormalRate().doubleValue();
                        v.setLabourCost(v.getLabourCost() + rate);
                        return v;
                    });
                }
            }
        }
    }

    /**
     * Calculate time sheet additional expenses for employees
     * 
     * @param chargeRates
     * @param actualHrsMap
     */
    private void calculateEmpTimesheetExpenses(Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates,
            Map<Long, CostActualHoursTO> actualHrsMap) {
        Set<Long> empIds = chargeRates.keySet();
        List<Object[]> amountByCost = timeSheetEmpExpenseRepository.findExpenses(empIds);
        for (Object[] object : amountByCost) {
            Long costId = (Long) object[0];
            BigDecimal amount = (BigDecimal) object[1];
            actualHrsMap.computeIfAbsent(costId, v -> new CostActualHoursTO());
            actualHrsMap.computeIfPresent(costId, (k, v) -> {
                v.setLabourCost(v.getLabourCost() + amount.longValue());
                return v;
            });
        }
    }

    /**
     * Map time sheet date with hrs
     * 
     * @param timesheetHr
     * @return
     */
    @Override
    public Map<Date, Double> calculateTimesheetDates(Object[] timesheetHr) {
        Map<Date, Double> dayHrs = new HashMap<>();
        Date weekStartDate = (Date) timesheetHr[3];
        Date nextDay;
        for (int i = 0; i < 7; i++) {
            //Instant weekStartInstant = weekStartDate.toInstant();
        	Instant weekStartInstant =  Instant.ofEpochMilli(weekStartDate.getTime());
              
            nextDay = Date.from(weekStartInstant.plus(i, ChronoUnit.DAYS));
            Double hrs = (Double) timesheetHr[i + 4];
            if (CommonUtil.isNonBlankDouble(hrs)) {
                dayHrs.put(nextDay, hrs);
            }
        }
        return dayHrs;
    }

    /**
     * Calculate cost amount from work dairy used and idle hrs
     * 
     * @param chargeRates
     * @param actualHrsMap
     */
    private void calculateEmpWorkDairyActualHrs(Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates,
            Map<Long, CostActualHoursTO> actualHrsMap) {
        Set<Long> empIds = chargeRates.keySet();
        List<Object[]> workDairyHrs = costWorkDairyRepository.getManpowerWorkDiaryActualHrs(empIds);
        for (Object[] workDairyHr : workDairyHrs) {
            Date workDairyDate = (Date) workDairyHr[5];
            Long empId = (Long) workDairyHr[0];

            Map<Date, EmpChargeOutRateTO> empRateWithDates = chargeRates.get(empId);
            if (empRateWithDates == null || empRateWithDates.values().isEmpty())
                continue;
            Set<Date> effectiveDates = empRateWithDates.keySet().stream()
                    .collect(Collectors.toCollection(TreeSet::new));
            List<Date> mappedDate = effectiveDates.stream()
                    .filter(d -> d.getTime() == workDairyDate.getTime() || d.before(workDairyDate))
                    .collect(Collectors.toList());
            if (mappedDate.isEmpty())
                continue;
            Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
            EmpChargeOutRateTO empRate = empRateWithDates.get(effectiveDate);
            if (empRate == null)
                continue;
            Long costId = (Long) workDairyHr[1];
            Float wageFactor = (Float) workDairyHr[2];
            Double usedHrs = (Double) workDairyHr[3];
            Double idleHrs = (Double) workDairyHr[4];
            if (CommonUtil.isNonBlankDouble(usedHrs) && empRate.getNormalRate() != null) {
                actualHrsMap.computeIfAbsent(costId, v -> new CostActualHoursTO());
                actualHrsMap.computeIfPresent(costId, (k, v) -> {
                    Double rate = usedHrs * wageFactor * empRate.getNormalRate().doubleValue();
                    v.setLabourCost(v.getLabourCost() + rate);
                    return v;
                });
            }
            if (CommonUtil.isNonBlankDouble(idleHrs) && empRate.getIdleRate() != null) {
                actualHrsMap.computeIfAbsent(costId, v -> new CostActualHoursTO());
                actualHrsMap.computeIfPresent(costId, (k, v) -> {
                    Double rate = idleHrs * wageFactor * empRate.getIdleRate().doubleValue();
                    v.setLabourCost(v.getLabourCost() + rate);
                    return v;
                });
            }
        }
    }

    /**
     * Calculate cost amount from plant work dairy used and idle hrs
     * 
     * @param chargeRates
     * @param actualHrsMap
     */
    private void calculatePlantWorkDairyActualHrs(Map<Long, Map<Date, PlantChargeOutRateTO>> chargeRates,
            Map<Long, CostActualHoursTO> actualHrsMap) {
        Set<Long> plantIds = chargeRates.keySet();
        List<Object[]> workDairyHrs = plantCostWorkDairyRepository.getPlantWorkDiaryActualHrs(plantIds);
        for (Object[] workDairyHr : workDairyHrs) {
            Date workDairyDate = (Date) workDairyHr[0];
            Long plantId = (Long) workDairyHr[1];

            Map<Date, PlantChargeOutRateTO> plantRateWithDates = chargeRates.get(plantId);
            if (plantRateWithDates == null || plantRateWithDates.values().isEmpty())
                continue;
            Set<Date> effectiveDates = plantRateWithDates.keySet().stream()
                    .collect(Collectors.toCollection(TreeSet::new));
            List<Date> mappedDate = effectiveDates.stream()
                    .filter(d -> d.getTime() == workDairyDate.getTime() || d.before(workDairyDate))
                    .collect(Collectors.toList());
            if (mappedDate.isEmpty())
                continue;
            Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
            PlantChargeOutRateTO plantRate = plantRateWithDates.get(effectiveDate);
            if (plantRate == null)
                continue;

            Long costId = (Long) workDairyHr[2];
            // Check if this attendance code is paid leave and calculate rate
            actualHrsMap.computeIfAbsent(costId, c -> {
                CostActualHoursTO to = new CostActualHoursTO();
                to.setId(costId);
                return to;
            });
            actualHrsMap.computeIfPresent(costId, (k, v) -> {
                String wageFactor = (String) workDairyHr[3];
                Double usedHrs = (Double) workDairyHr[4];
                Double idleHrs = (Double) workDairyHr[5];
                updatePlantHrs(wageFactor, usedHrs, idleHrs, plantRate, v);
                return v;
            });
        }
    }

    /**
     * Update Plant hours in CostActualHoursTO
     * 
     * @param wageFactor
     * @param usedHrs
     * @param idleHrs
     * @param plantRate
     * @param v
     */
    private void updatePlantHrs(String wageFactor, Double usedHrs, Double idleHrs, PlantChargeOutRateTO plantRate,
            CostActualHoursTO v) {
        Double pRate;
        if (wageFactor != null && wageFactor.toLowerCase().contains("single")) {
            pRate = plantRate.getNormalRate().doubleValue();
        } else {
            pRate = plantRate.getDoubleRate().doubleValue();
        }
        Double usedRate = Double.valueOf(0);
        if (CommonUtil.isNonBlankDouble(pRate)) {
            usedRate = ((Double) usedHrs) * pRate;
        }
        Double idleRate = Double.valueOf(0);
        if (CommonUtil.objectNotNull(plantRate.getIdleRate())
                && CommonUtil.isNonBlankDouble(plantRate.getIdleRate().doubleValue())) {
            idleRate = ((Double) idleHrs) * plantRate.getIdleRate().doubleValue();
        }
        v.setPlantCost(v.getPlantCost() + usedRate + idleRate);
    }

    /**
     * Calculate amount for all paid leave types of each employees
     * 
     * @param projMstrEntity
     * @param chargeRates
     * @param actualHrsMap
     */
    private void calculateAmountFromEmpAttd(ProjMstrEntity projMstrEntity,
            Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates, Map<Long, CostActualHoursTO> actualHrsMap) {
        // Get paid leave codes
        String countryCode = projGeneralRepository.findCountry(projMstrEntity);
        List<ProjLeaveTypeEntity> procurePaidLeaveList = leaveTypeRepository.findPaidLeaveCodes(countryCode);
        Map<Date, Map<Long, List<String>>> procurePaidLeaveDateMap = new HashMap<>();
        Set<Long> procureIds = new HashSet<>();
        for (ProjLeaveTypeEntity entity : procurePaidLeaveList) {
            Date effectiveFrom = entity.getEffectiveFrom();
            procurePaidLeaveDateMap.computeIfAbsent(effectiveFrom, v -> new HashMap<>());
            procurePaidLeaveDateMap.computeIfPresent(effectiveFrom, (k, v) -> {
                entity.getProjLeaveCategoryTypes().forEach(t -> {
                    Long procureId = t.getProcureCatgDtlEntity().getId();
                    String leaveType = t.getProjLeaveTypeEntity().getCode();
                    if ("paid".equalsIgnoreCase(t.getPayType())) {
                        procureIds.add(procureId);
                        v.computeIfAbsent(procureId, v1 -> new ArrayList<>());
                        v.computeIfPresent(procureId, (k1, v1) -> {
                            v1.add(leaveType);
                            return v1;
                        });
                    }
                });
                return v;
            });
        }
        // Fetch employees of that procure types and count of that emp attendance
        Set<Long> empIds = chargeRates.keySet();
        Set<Date> effectiveDates = procurePaidLeaveDateMap.keySet().stream()
                .collect(Collectors.toCollection(TreeSet::new));
        if (!empIds.isEmpty() && !procureIds.isEmpty()) {
            List<Object[]> attdCodesByEmp = empAttendanceRepository.findAttendance(empIds, procureIds);
            for (Object[] attdCodes : attdCodesByEmp) {
                Date attdMonth = (Date) attdCodes[0];
                Long empId = (Long) attdCodes[1];
                Long procureId = (Long) attdCodes[2];
                Date attdDate = (Date) attdCodes[3];
                String attdCode = (String) attdCodes[4];

                List<Date> mappedDate = effectiveDates.stream()
                        .filter(d -> d.getTime() == attdMonth.getTime() || d.before(attdMonth))
                        .collect(Collectors.toList());
                if (mappedDate.isEmpty())
                    continue;
                Date leaveEffectiveDate = mappedDate.get(mappedDate.size() - 1);
                // Check if this attendance code is paid leave and calculate rate
                if (procurePaidLeaveDateMap.get(leaveEffectiveDate) != null
                        && procurePaidLeaveDateMap.get(leaveEffectiveDate).get(procureId) != null
                        && procurePaidLeaveDateMap.get(leaveEffectiveDate).get(procureId).contains(attdCode)) {
                    Map<Date, EmpChargeOutRateTO> ratesByDate = chargeRates.get(empId);
                    Set<Date> effectiveEmpDates = ratesByDate.keySet().stream()
                            .collect(Collectors.toCollection(TreeSet::new));
                    List<Date> mappedAttdDate = effectiveEmpDates.stream()
                            .filter(d -> d.getTime() == attdDate.getTime() || d.before(attdDate))
                            .collect(Collectors.toList());
                    if (mappedAttdDate.isEmpty())
                        continue;
                    Date effectiveAttdDate = mappedAttdDate.get(mappedAttdDate.size() - 1);
                    EmpChargeOutRateTO rate = ratesByDate.get(effectiveAttdDate);
                    actualHrsMap.computeIfAbsent(rate.getLeaveCostItemId(), c -> {
                        CostActualHoursTO to = new CostActualHoursTO();
                        to.setId(rate.getLeaveCostItemId());
                        return to;
                    });
                    Double empRate = rate.getLeaveRate().doubleValue();
                    actualHrsMap.computeIfPresent(rate.getLeaveCostItemId(), (k, v) -> {
                        v.setLabourCost(v.getLabourCost() + empRate);
                        return v;
                    });
                }
            }
        }
    }

    /**
     * Calculate mob and demob rate w.r.t cost code
     * 
     * @param chargeRates
     * @param actualHrsMap - Key - CostCodeId, Value - CostActualHoursTO as value,
     *                     which has calculated labourCost
     */
    private void calculateEmpMobDemobRate(Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates,
            Map<Long, CostActualHoursTO> actualHrsMap) {
        for (Map<Date, EmpChargeOutRateTO> rateByDate : chargeRates.values()) {
            for (EmpChargeOutRateTO chargeRate : rateByDate.values()) {
                if (chargeRate.getMobCostItemId() != null && chargeRate.getMobRate() != null
                        && !BigDecimal.ZERO.equals(chargeRate.getMobRate())) {
                    actualHrsMap.computeIfAbsent(chargeRate.getMobCostItemId(), v -> {
                        CostActualHoursTO to = new CostActualHoursTO();
                        to.setId(chargeRate.getMobCostItemId());
                        return to;
                    });
                    actualHrsMap.computeIfPresent(chargeRate.getMobCostItemId(), (k, v) -> {
                        v.setLabourCost(v.getLabourCost() + chargeRate.getMobRate().doubleValue());
                        return v;
                    });
                }
                if (chargeRate.getDeMobCostItemId() != null && chargeRate.getDeMobRate() != null
                        && !BigDecimal.ZERO.equals(chargeRate.getDeMobRate())) {
                    actualHrsMap.computeIfAbsent(chargeRate.getDeMobCostItemId(), v -> {
                        CostActualHoursTO to = new CostActualHoursTO();
                        to.setId(chargeRate.getDeMobCostItemId());
                        return to;
                    });
                    actualHrsMap.computeIfPresent(chargeRate.getDeMobCostItemId(), (k, v) -> {
                        v.setLabourCost(v.getLabourCost() + chargeRate.getDeMobRate().doubleValue());
                        return v;
                    });
                }
            }
        }
    }

    /**
     * Calculate mob and demob rate w.r.t cost code
     * 
     * @param chargeRates
     * @param actualHrsMap - Key - CostCodeId, Value - CostActualHoursTO as value,
     *                     which has calculated plantCost
     */
    private void calculatePlantMobDemobRate(Map<Long, Map<Date, PlantChargeOutRateTO>> chargeRates,
            Map<Long, CostActualHoursTO> actualHrsMap) {
        for (Map<Date, PlantChargeOutRateTO> chargeRateByDate : chargeRates.values()) {
            for (PlantChargeOutRateTO chargeRate : chargeRateByDate.values()) {
                if (chargeRate.getMobCostItemId() != null && chargeRate.getMobRate() != null
                        && !BigDecimal.ZERO.equals(chargeRate.getMobRate())) {
                    actualHrsMap.computeIfAbsent(chargeRate.getMobCostItemId(), v -> {
                        CostActualHoursTO to = new CostActualHoursTO();
                        to.setId(chargeRate.getMobCostItemId());
                        return to;
                    });
                    actualHrsMap.computeIfPresent(chargeRate.getMobCostItemId(), (k, v) -> {
                        v.setPlantCost(v.getPlantCost() + chargeRate.getMobRate().doubleValue());
                        return v;
                    });
                }
                if (chargeRate.getDeMobCostItemId() != null && chargeRate.getDeMobRate() != null
                        && !BigDecimal.ZERO.equals(chargeRate.getDeMobRate())) {
                    actualHrsMap.computeIfAbsent(chargeRate.getDeMobCostItemId(), v -> {
                        CostActualHoursTO to = new CostActualHoursTO();
                        to.setId(chargeRate.getDeMobCostItemId());
                        return to;
                    });
                    actualHrsMap.computeIfPresent(chargeRate.getDeMobCostItemId(), (k, v) -> {
                        v.setPlantCost(v.getPlantCost() + chargeRate.getDeMobRate().doubleValue());
                        return v;
                    });
                }
            }
        }
    }

    /**
     * Fetch plant chargeout rates
     * 
     * @param projMstrEntity
     * @return Key - PlantRegId, Value - Respective charge-out rate as value
     */
    private Map<Long, Map<Date, PlantChargeOutRateTO>> getPlantChargeOutRates(ProjMstrEntity projMstrEntity) {
        Map<Long, Map<Date, PlantChargeOutRateTO>> plantRates = new HashMap<>();
        List<PlantChargeOutRatesEntityCopy> rates = plantChargeOutRateRepository
                .findPlantChargeOutRatesNew(projMstrEntity);
        for (PlantChargeOutRatesEntityCopy rateEntity : rates) {
            if (rateEntity.getPlantRegProjEntity() == null
                    || rateEntity.getPlantRegProjEntity().getPlantRegisterDtlEntity() == null
                    || rateEntity.getEffectiveFrom() == null) {
                continue;
            }
            Long key = rateEntity.getPlantRegProjEntity().getPlantRegisterDtlEntity().getId();
            plantRates.computeIfAbsent(key, v -> new HashMap<>());
            Map<Date, PlantChargeOutRateTO> rateByDate = plantRates.get(key);
            rateByDate.put(rateEntity.getEffectiveFrom(), processPlantRates(rateEntity));
            plantRates.put(key, rateByDate);
        }
        return plantRates;
    }

    /**
     * Map entity to required field in TO
     * 
     * @param rateEntity
     * @return
     */
    private PlantChargeOutRateTO processPlantRates(PlantChargeOutRatesEntityCopy rateEntity) {
        PlantChargeOutRateTO plantChargeOutRateTO = new PlantChargeOutRateTO();
        plantChargeOutRateTO.setPlantRegId(rateEntity.getPlantRegProjEntity().getPlantRegisterDtlEntity().getId());
        ProjCostItemEntity mobCost = rateEntity.getProjMobCostItem();
        if (null != mobCost) {
            plantChargeOutRateTO.setMobCostItemId(mobCost.getId());
            plantChargeOutRateTO.setMobRate(rateEntity.getMobChargeOutRate());
        }

        ProjCostItemEntity demobCost = rateEntity.getProjDemobCostItem();
        if (null != demobCost) {
            plantChargeOutRateTO.setDeMobCostItemId(demobCost.getId());
            plantChargeOutRateTO.setDeMobRate(rateEntity.getDeMobChargeOutRate());
        }
        plantChargeOutRateTO.setIdleRate(rateEntity.getIdleChargeOutRate());
        plantChargeOutRateTO.setPlantRateType(rateEntity.getCategory());
        if (rateEntity.getCategory() != null && rateEntity.getCategory().contains("WITHOUT")) {
            plantChargeOutRateTO.setNormalRate(rateEntity.getRateWithOutFualNRShift());
            plantChargeOutRateTO.setDoubleRate(rateEntity.getRateWithoutFualDBShift());
        } else {
            plantChargeOutRateTO.setNormalRate(rateEntity.getRateWithFualNRShift());
            plantChargeOutRateTO.setDoubleRate(rateEntity.getRateWithFualDBShift());
        }
        return plantChargeOutRateTO;
    }

    /**
     * Fetch emp chargeout rates
     * 
     * @param projMstrEntity
     * @return Key - EmpRegId, Value - Respective charge-out rate as value
     */
    private Map<Long, Map<Date, EmpChargeOutRateTO>> getEmpChargeOutRates(ProjMstrEntity projMstrEntity) {
        Map<Long, Map<Date, EmpChargeOutRateTO>> empRates = new HashMap<>();
        //changed from EmpChargeOutRateEntityCopy to EmpChargeOutRateEntity
        List<EmpChargeOutRateEntity> rates = empProjRegisterRepository.findChargeOutRates(projMstrEntity);
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
     * Map entity to required field in TO
     * 
     * @param rateEntity
     * @return
     */
    private EmpChargeOutRateTO processEmpRates(EmpChargeOutRateEntity rateEntity) {
        EmpChargeOutRateTO empRate = new EmpChargeOutRateTO();
        empRate.setEmpRegId(rateEntity.getEmpRegisterDtlEntity().getId());
        ProjCostItemEntity leaveCost = rateEntity.getLeaveCostItemEntity();
        if (null != leaveCost) {
            empRate.setLeaveCostItemId(leaveCost.getId());
            empRate.setLeaveCostItemCode(leaveCost.getCode());
            empRate.setLeaveRate(rateEntity.getPaidLeaveRate());
        }
        ProjCostItemEntity mobCost = rateEntity.getMobCostItemEntity();
        if (null != mobCost) {
            empRate.setMobCostItemId(mobCost.getId());
            empRate.setMobCostItemCode(mobCost.getCode());
            empRate.setMobRate(rateEntity.getMobRate());
        }
        ProjCostItemEntity demobCost = rateEntity.getDeMobCostItemEntity();
        if (null != demobCost) {
            empRate.setDeMobCostItemId(demobCost.getId());
            empRate.setDeMobCostItemCode(demobCost.getCode());
            empRate.setDeMobRate(rateEntity.getDeMobRate());
        }
        ProjGeneralMstrEntity general = rateEntity.getProjGeneralMstrEntity();
        if (general != null)
            empRate.setCurrency(general.getCurrency());
        empRate.setNormalRate(rateEntity.getNormalRate());
        empRate.setIdleRate(rateEntity.getIdleRate());
        return empRate;
    }

    @Override
    public Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> getEmpChargeOutRates(Set<Long> projIds) {
        Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> map = new HashMap<>();
        for (Long projId : projIds) {
            map.put(projId, getEmpChargeOutRates(epsProjRepository.findOne(projId)));
        }
        return map;
    }

    @Override
    public Map<Long, Map<Long, Map<Date, PlantChargeOutRateTO>>> getPlantChargeOutRates(Set<Long> projIds) {
        Map<Long, Map<Long, Map<Date, PlantChargeOutRateTO>>> map = new HashMap<>();
        for (Long projId : projIds) {
            map.put(projId, getPlantChargeOutRates(epsProjRepository.findOne(projId)));
        }
        return map;
    }

}
