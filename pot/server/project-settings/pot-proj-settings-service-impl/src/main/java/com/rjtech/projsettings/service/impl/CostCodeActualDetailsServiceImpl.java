package com.rjtech.projsettings.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.ProjLeaveTypeEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.ProjLeaveTypeRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.proj.settings.common.service.ActualAmountService;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSowTotalActualRepository;
import com.rjtech.projschedule.model.ResourceAssignmentDataValueEntity;
import com.rjtech.projschedule.repository.ResourceAssignmentDataValueRepository;
import com.rjtech.projsettings.model.ProjCostBudgetEntity;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
import com.rjtech.projsettings.register.emp.dto.EmpChargeOutRateTO;
import com.rjtech.projsettings.register.plant.dto.PlantChargeOutRateTO;
import com.rjtech.projsettings.repository.ProjCostStatementsRepository;
import com.rjtech.projsettings.repository.ProjGeneralRepository;
import com.rjtech.projsettings.service.CostCodeActualDetailsService;
//import com.rjtech.register.EmpChargeOutRateEntityCopy;
//import com.rjtech.register.EmpProjRigisterEntityCopy;
//import com.rjtech.register.EmpRegisterDtlEntityCopy;
//import com.rjtech.register.PlantRegisterDtlEntityCopy;
import com.rjtech.register.emp.model.EmpChargeOutRateEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpProjRegisterRepositoryCopy;
import com.rjtech.register.repository.plant.PlantChargeOutRateRepositoryCopy;
//import com.rjtech.register.timemanagement.attendance.repository.EmpAttendanceRepository;
import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.reports.cost.resp.CostBudgetRatioTO;
import com.rjtech.reports.cost.resp.CostItemActualReportResp;
import com.rjtech.reports.cost.resp.CostItemActualReportTO;
import com.rjtech.reports.cost.resp.CostItemReportTO;
import com.rjtech.reports.cost.resp.CostItemWiseReportResp;
import com.rjtech.reports.cost.resp.CostReportTO;
import com.rjtech.reports.cost.resp.CostValuesTO;
import com.rjtech.reports.cost.resp.DateWiseCostReportResp;
import com.rjtech.reports.cost.resp.PeriodCostTO;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceRepository;
import com.rjtech.timemanagement.timesheet.repository.copy.TimeSheetEmpDtlRepositoryCopy;
import com.rjtech.timemanagement.timesheet.repository.copy.TimeSheetEmpExpenseRepositoryCopy;
import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantDtlEntity;
import com.rjtech.timemanagement.workdairy.repository.copy.EmpCostWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.MaterialStatusWorkDairyRepositoryCopy;
import com.rjtech.timemanagement.workdairy.repository.copy.PlantCostWorkDairyRepositoryCopy;
//import com.rjtech.workdairy.WorkDairyPlantDtlEntityCopy;
//import com.rjtech.workdairy.WorkDairyProgressDtlEntityCopy;

@Service(value = "costCodeActualDetailsServiceImpl")
@Transactional
public class CostCodeActualDetailsServiceImpl implements CostCodeActualDetailsService {

    @Autowired
    private EmpProjRegisterRepositoryCopy empProjRegisterRepository;

    @Autowired
    private ProjGeneralRepository projGeneralRepository;

    @Autowired
    private ProjLeaveTypeRepository leaveTypeRepository;

    @Autowired
    private EPSProjRepository projRepository;

    @Autowired
    private EmpAttendanceRepository empAttendanceRepository;

    @Autowired
    private ActualAmountService actualAmountService;

    @Autowired
    private ProjCostItemRepositoryCopy projCostItemRepository;

    @Autowired
    private EmpCostWorkDairyRepositoryCopy empCostWorkDairyRepository;

    @Autowired
    private TimeSheetEmpDtlRepositoryCopy timeSheetEmpDtlRepository;

    @Autowired
    private TimeSheetEmpExpenseRepositoryCopy timeSheetEmpExpenseRepository;

    @Autowired
    private PlantChargeOutRateRepositoryCopy plantChargeOutRateRepository;

    @Autowired
    private PlantCostWorkDairyRepositoryCopy plantCostWorkDairyRepository;

    @Autowired
    private MaterialStatusWorkDairyRepositoryCopy materialStatusWorkDairyRepository;

    @Autowired
    private ProjCostStatementsRepository projCostStatementsRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProjSowTotalActualRepository projSowTotalActualRepository;
    
    @Autowired
    private ResourceAssignmentDataValueRepository resourceAssignmentDataValueRepository;
    
    @Override
    public DateWiseCostReportResp getDatewiseActualCostDetails(CostReportReq costReportReq) {
        System.out.println("CostCodeActualDetailsServiceImpl:costReportReq:Categories().size:"+costReportReq.getCategories().size());
        System.out.println("CostCodeActualDetailsServiceImpl:costReportReq: CostcodeIds().size:"+costReportReq.getCostcodeIds().size());
        System.out.println("CostCodeActualDetailsServiceImpl:costReportReq: CmpIds().size:"+costReportReq.getCmpIds().size());
        System.out.println("CostCodeActualDetailsServiceImpl:costReportReq: ProjIds().size:"+costReportReq.getProjIds().size());

        System.out.println("FromDate :"+costReportReq.getFromDate());
        System.out.println("ToDate :"+costReportReq.getToDate());

        if(costReportReq.getCostcodeIds()!=null && costReportReq.getCostcodeIds().size()<=0)
        {
            List<Long> costcodeIds = projCostItemRepository.findMultiProjCostIds(costReportReq.getProjIds());
            System.out.println("costcodeIds : size:"+ costcodeIds.size());
            costReportReq.getCostcodeIds().addAll(costcodeIds);
        }
        if(costReportReq.getCmpIds()!=null && costReportReq.getCmpIds().size()<=0)
        {
            costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId()));
        }
        if(costReportReq.getCategories()!=null && costReportReq.getCategories().size()<=0)
        {
            ArrayList<String> categories=new ArrayList<String>();
                                categories.add("direct");
                                categories.add("in-direct");
            costReportReq.getCategories().addAll(categories);
        }

        System.out.println("22 CostCodeActualDetailsServiceImpl:costReportReq:Categories().size:"+costReportReq.getCategories().size());
        System.out.println("22 CostCodeActualDetailsServiceImpl:costReportReq: CostcodeIds().size:"+costReportReq.getCostcodeIds().size());
        System.out.println("22 CostCodeActualDetailsServiceImpl:costReportReq: CmpIds().size:"+costReportReq.getCmpIds().size());
        System.out.println("22 CostCodeActualDetailsServiceImpl:costReportReq: ProjIds().size:"+costReportReq.getProjIds().size());

        DateWiseCostReportResp costReportResp = new DateWiseCostReportResp();
        List<CostReportTO> list = costReportResp.getCostReportResps();
        list.addAll(getEmpData(costReportReq));
        list.addAll(getPlantData(costReportReq));
        list.addAll(getMaterialActual(costReportReq));
        list.addAll(getProgress(costReportReq));
        list.stream().sorted(Comparator.comparing(CostReportTO::getDateObj).thenComparing(CostReportTO::getProjId)
                .thenComparing(CostReportTO::getCostItemId)).collect(Collectors.toList());
        return costReportResp;
    }

    @Override
    public CostItemWiseReportResp getCostCodeBudgetReport(List<Long> projIds) {
        CostItemWiseReportResp resp = new CostItemWiseReportResp();
        List<Object[]> costBudgets = projCostStatementsRepository.getBudgetsByType(projIds,
                new ArrayList<Long>(Arrays.asList(1L, 2L)));
        for (Object[] costBudget : costBudgets) {
            ProjCostStmtDtlEntity stmtDtlEntity = (ProjCostStmtDtlEntity) costBudget[0];
            String currency = (String) costBudget[1];
            CostItemReportTO reportResp = new CostItemReportTO();
            setProjectInfo(stmtDtlEntity.getProjMstrEntity(), reportResp);
            setCostCodeInfo(stmtDtlEntity.getProjCostItemEntity(), reportResp);
            reportResp.setCurrencyCode(currency);
            Optional<ProjCostBudgetEntity> opBudget = stmtDtlEntity.getProjCostBudgetEntites().stream()
                    .filter(b -> b.getBudgetType() == 1L).findFirst();
            ProjCostBudgetEntity oBudget = opBudget.isPresent() ? opBudget.get() : null;
            Optional<ProjCostBudgetEntity> rpBudget = stmtDtlEntity.getProjCostBudgetEntites().stream()
                    .filter(b -> b.getBudgetType() == 2L).findFirst();
            ProjCostBudgetEntity rBudget = rpBudget.isPresent() ? rpBudget.get() : null;
            Double oLabour = (oBudget != null ? CommonUtil.ifNullGetDefaultValue(oBudget.getLabourCost()).doubleValue()
                    : 0);
            reportResp.setLabourAmount(rBudget != null && CommonUtil.isNonBlankBigDecimal(rBudget.getLabourCost())
                    ? rBudget.getLabourCost().doubleValue()
                    : oLabour);
            Double oPlant = (oBudget != null ? CommonUtil.ifNullGetDefaultValue(oBudget.getPlantCost()).doubleValue()
                    : 0);
            reportResp.setPlantAmount(rBudget != null && CommonUtil.isNonBlankBigDecimal(rBudget.getPlantCost())
                    ? rBudget.getPlantCost().doubleValue()
                    : oPlant);
            Double oMat = (oBudget != null ? CommonUtil.ifNullGetDefaultValue(oBudget.getMaterialCost()).doubleValue()
                    : 0);
            reportResp.setMatAmount(rBudget != null && CommonUtil.isNonBlankBigDecimal(rBudget.getMaterialCost())
                    ? rBudget.getMaterialCost().doubleValue()
                    : oMat);
            Double oOther = (oBudget != null ? CommonUtil.ifNullGetDefaultValue(oBudget.getOtherCost()).doubleValue()
                    : 0);
            reportResp.setOtherAmount(rBudget != null && CommonUtil.isNonBlankBigDecimal(rBudget.getOtherCost())
                    ? rBudget.getOtherCost().doubleValue()
                    : oOther);
            resp.getCostReportResps().add(reportResp);
        }
        return resp;
    }

    private List<CostReportTO> getEmpData(CostReportReq costReportReq) {
        List<CostReportTO> list = new ArrayList<>();
        list.addAll(findEmpMobRates(costReportReq));
        list.addAll(findEmpDemobRates(costReportReq));
        Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> chargeRatesByProj = actualAmountService
                .getEmpChargeOutRates(new HashSet<Long>(costReportReq.getProjIds()));
        list.addAll(findEmpLeaveRates(costReportReq, chargeRatesByProj));
        list.addAll(findEmpWorkDairyDetails(costReportReq, chargeRatesByProj));
        list.addAll(findEmpTimeSheetDetails(costReportReq, chargeRatesByProj));
        list.addAll(findEmpTimeSheetExpenses(costReportReq, chargeRatesByProj));
        return list;
    }

    private List<CostReportTO> getPlantData(CostReportReq costReportReq) {
        List<CostReportTO> list = new ArrayList<>();
        list.addAll(findPlantMobRates(costReportReq));
        list.addAll(findPlantDeMobRates(costReportReq));
        list.addAll(findPlantWorkDairyDetails(costReportReq));
        return list;
    }

    private List<CostReportTO> findEmpMobRates(CostReportReq costReportReq) {
        List<CostReportTO> list = new ArrayList<>();
        Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
        List<Object[]> objList = empProjRegisterRepository.findMobRates(costReportReq.getProjIds(),
                costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), costReportReq.getCategories(), fromDate,
                toDate);
        for (Object[] mobRate : objList) {
            EmpProjRigisterEntity empProj = (EmpProjRigisterEntity) mobRate[0];
            String category = (String) mobRate[1];
            list.addAll(processEmpMobToCostReportResp(empProj, category, true));
        }
        return list;
    }

    private List<CostReportTO> findEmpDemobRates(CostReportReq costReportReq) {
        List<CostReportTO> list = new ArrayList<>();
        Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
        List<Object[]> objList = empProjRegisterRepository.findDemobRates(costReportReq.getProjIds(),
                costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), costReportReq.getCategories(), fromDate,
                toDate);
        for (Object[] mobRate : objList) {
            EmpProjRigisterEntity empProj = (EmpProjRigisterEntity) mobRate[0];
            String category = (String) mobRate[1];
            list.addAll(processEmpMobToCostReportResp(empProj, category, false));
        }
        return list;
    }

    private List<CostReportTO> findEmpLeaveRates(CostReportReq costReportReq,
            Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> chargeRatesByProj) {
        List<CostReportTO> list = new ArrayList<>();
        // Get paid leave types
        Map<Long, Map<Date, Map<Long, List<String>>>> paidLeavesByProj = getLeaveTypes(costReportReq.getProjIds());
        if (paidLeavesByProj.keySet().isEmpty())
            return list;
        Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
        // Iterate paid leave for each project
        for (Entry<Long, Map<Date, Map<Long, List<String>>>> entry : paidLeavesByProj.entrySet()) {
            if (entry.getValue().isEmpty())
                continue;
            Long projId = entry.getKey();
            ProjMstrEntity projMstr = projRepository.findOne(projId);
            Set<Long> procureIds = new HashSet<>();
            entry.getValue().values().forEach(p -> procureIds.addAll(p.keySet()));
            // Fetch Emp attendance for this project
            List<Object[]> empAttdList = empAttendanceRepository.findAttendanceByProjs(projMstr, procureIds,
                    costReportReq.getCategories(), costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate,
                    toDate);
            if (empAttdList.isEmpty())
                continue;

            Map<Date, Map<Long, List<String>>> procurePaidLeaveDateMap = entry.getValue();
            // Effective dates of leave types
            Set<Date> effectiveDates = procurePaidLeaveDateMap.keySet().stream()
                    .collect(Collectors.toCollection(TreeSet::new));
            Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates = chargeRatesByProj.get(projId);
            // Iterate for each emp and their attendance date
            empAttdList.forEach(emp -> {
                Date attdMonth = (Date) emp[0];
                EmpRegisterDtlEntity empReg = (EmpRegisterDtlEntity) emp[1];
                Long procureId = empReg.getProcureCatgDtlEntity().getId();
                Date attdDate = (Date) emp[2];
                // Find the effective date for this employee attendance month 
                List<Date> mappedDate = effectiveDates.stream()
                        .filter(d -> d.getTime() == attdMonth.getTime() || d.before(attdMonth))
                        .collect(Collectors.toList());
                if (mappedDate.isEmpty())
                    return;
                Date leaveEffectiveDate = mappedDate.get(mappedDate.size() - 1);
                String attdCode = (String) emp[3];
                // Check if this attendance code is paid leave and calculate rate
                if (procurePaidLeaveDateMap.get(leaveEffectiveDate).get(procureId).contains(attdCode)) {
                    CostReportTO resp = new CostReportTO();
                    setProjectInfo(projMstr, resp);
                    setEmpInfo(empReg, resp);
                    Map<Date, EmpChargeOutRateTO> ratesByDate = chargeRates.get(empReg.getId());
                    if (ratesByDate != null) {
                        Set<Date> effectiveEmpDates = ratesByDate.keySet().stream()
                                .collect(Collectors.toCollection(TreeSet::new));
                        List<Date> mappedAttdDate = effectiveEmpDates.stream()
                                .filter(d -> d.getTime() == attdDate.getTime() || d.before(attdDate))
                                .collect(Collectors.toList());
                        if (mappedAttdDate.isEmpty())
                            return;
                        Date effectiveAttdDate = mappedAttdDate.get(mappedAttdDate.size() - 1);
                        EmpChargeOutRateTO rate = ratesByDate.get(effectiveAttdDate);
                        if (rate != null && rate.getLeaveCostItemId() != null) {
                            ProjCostItemEntity costCode = projCostItemRepository.findOne(rate.getLeaveCostItemId());
                            setCostCodeInfo(costCode, resp);
                            resp.setQuantity(Double.valueOf(1));
                            resp.setCostAmount(rate.getLeaveRate().doubleValue());
                            resp.setRatePerUnit(rate.getLeaveRate().doubleValue());
                        }
                    }
                    resp.setDate(CommonUtil.convertDateToString(attdDate));
                    resp.setCategory((String) emp[4]);
                    resp.setType("Paid Leave");
                    resp.setCurrencyCode((String) emp[5] != null ? (String) emp[5] : "");
                    list.add(resp);
                }
            });
        }
        return list;
    }

    private List<CostReportTO> findEmpWorkDairyDetails(CostReportReq costReportReq,
            Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> chargeRatesByProj) {
        List<CostReportTO> list = new ArrayList<>();
        Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
        List<Object[]> workDairyHrs = empCostWorkDairyRepository.getManpowerWorkDiaryActualHrs(
                costReportReq.getProjIds(), costReportReq.getCategories(), costReportReq.getCmpIds(),
                costReportReq.getCostcodeIds(), fromDate, toDate);
        for (Object[] workDairyHr : workDairyHrs) {
            Date workDairyDate = (Date) workDairyHr[5];
            EmpRegisterDtlEntity empReg = (EmpRegisterDtlEntity) workDairyHr[0];
            ProjMstrEntity projMstr = (ProjMstrEntity) workDairyHr[6];

            ProjCostItemEntity costCode = (ProjCostItemEntity) workDairyHr[1];
            Float wageFactor = (Float) workDairyHr[2];
            Double usedHrs = (Double) workDairyHr[3];
            Double idleHrs = (Double) workDairyHr[4];

            Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates = chargeRatesByProj.get(projMstr.getProjectId());
            Map<Date, EmpChargeOutRateTO> empRateWithDates = chargeRates.get(empReg.getId());
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
            CostReportTO resp = new CostReportTO();
            resp.setDate(CommonUtil.convertDateToString(workDairyDate));
            if (projMstr != null)
                setProjectInfo(projMstr, resp);
            setEmpInfo(empReg, resp);
            resp.setCategory((String) workDairyHr[7]);
            if (CommonUtil.isNonBlankDouble(usedHrs)) {
                CostReportTO empInfoReportClone = new CostReportTO();
                BeanUtils.copyProperties(resp, empInfoReportClone);
                empInfoReportClone.setType("Used WorkDairy");
                setCostCodeInfo(costCode, empInfoReportClone);
                empInfoReportClone.setQuantity(usedHrs);
                if (empRate != null && empRate.getNormalRate() != null) {
                    empInfoReportClone.setCostAmount(usedHrs * wageFactor * empRate.getNormalRate().doubleValue());
                    empInfoReportClone.setRatePerUnit(empRate.getNormalRate().doubleValue());
                    empInfoReportClone.setCurrencyCode(empRate.getCurrency());
                }
                list.add(empInfoReportClone);
            }

            if (CommonUtil.isNonBlankDouble(idleHrs)) {
                CostReportTO empInfoReportClone = new CostReportTO();
                BeanUtils.copyProperties(resp, empInfoReportClone);
                empInfoReportClone.setType("Idle WorkDairy");
                setCostCodeInfo(costCode, empInfoReportClone);
                empInfoReportClone.setQuantity(idleHrs);
                if (empRate != null && empRate.getIdleRate() != null) {
                    empInfoReportClone.setCostAmount(idleHrs * wageFactor * empRate.getIdleRate().doubleValue());
                    empInfoReportClone.setRatePerUnit(empRate.getIdleRate().doubleValue());
                    empInfoReportClone.setCurrencyCode(empRate.getCurrency());
                }
                list.add(empInfoReportClone);
            }
        }
        return list;
    }

    private List<CostReportTO> findEmpTimeSheetDetails(CostReportReq costReportReq,
            Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> chargeRatesByProj) {
        List<CostReportTO> list = new ArrayList<>();
        Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
        List<Object[]> timeSheetHrs = timeSheetEmpDtlRepository.getTimesheetEmpActualHrs(costReportReq.getProjIds(),
                costReportReq.getCategories(), costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate,
                toDate);
        for (Object[] timeSheetHr : timeSheetHrs) {
            ProjMstrEntity projMstr = (ProjMstrEntity) timeSheetHr[11];
            EmpRegisterDtlEntity empReg = (EmpRegisterDtlEntity) timeSheetHr[0];
            Set<Date> effectiveDates = new HashSet<>();
            Map<Long, Map<Date, EmpChargeOutRateTO>> chargeRates = chargeRatesByProj.get(projMstr.getProjectId());
            Map<Date, EmpChargeOutRateTO> empRateWithDates = new HashMap<>();
            if (chargeRates != null) {
                empRateWithDates = chargeRates.get(empReg.getId());
                if (empRateWithDates != null && !empRateWithDates.values().isEmpty())
                    effectiveDates = empRateWithDates.keySet().stream().collect(Collectors.toCollection(TreeSet::new));
            }
            ProjCostItemEntity costCode = (ProjCostItemEntity) timeSheetHr[1];
            Float wageFactor = (Float) timeSheetHr[2];

            Map<Date, Double> hrsPerDays = actualAmountService.calculateTimesheetDates(timeSheetHr);
            CostReportTO resp = new CostReportTO();
            if (projMstr != null)
                setProjectInfo(projMstr, resp);
            setEmpInfo(empReg, resp);
            setCostCodeInfo(costCode, resp);
            resp.setType("Timesheet Hrs");
            resp.setCategory((String) timeSheetHr[12]);
            for (Entry<Date, Double> hrsPerDay : hrsPerDays.entrySet()) {
                Date timesheetDate = hrsPerDay.getKey();
                if (fromDate.compareTo(timesheetDate) * timesheetDate.compareTo(toDate) < 0)
                    continue;
                Double usedHrs = hrsPerDay.getValue();
                CostReportTO empInfoReportClone = new CostReportTO();
                BeanUtils.copyProperties(resp, empInfoReportClone);
                empInfoReportClone.setQuantity(usedHrs);
                empInfoReportClone.setDate(CommonUtil.convertDateToString(timesheetDate));
                list.add(empInfoReportClone);
                List<Date> mappedDate = effectiveDates.stream()
                        .filter(d -> d.getTime() == timesheetDate.getTime() || d.before(timesheetDate))
                        .collect(Collectors.toList());
                if (!mappedDate.isEmpty()) {
                    Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
                    EmpChargeOutRateTO empRate = empRateWithDates.get(effectiveDate);
                    if (empRate != null && empRate.getNormalRate() != null) {
                        empInfoReportClone.setRatePerUnit(empRate.getNormalRate().doubleValue());
                        empInfoReportClone.setCostAmount(usedHrs * wageFactor * empRate.getNormalRate().doubleValue());
                        empInfoReportClone.setCurrencyCode(empRate.getCurrency());
                    }
                }

            }
        }
        return list;
    }

    private List<CostReportTO> findEmpTimeSheetExpenses(CostReportReq costReportReq,
            Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> chargeRatesByProj) {
        List<CostReportTO> list = new ArrayList<>();
        Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
        List<Object[]> expenses = timeSheetEmpExpenseRepository.findExpenses(costReportReq.getProjIds(),
                costReportReq.getCategories(), costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate,
                toDate);
        for (Object[] expense : expenses) {
            ProjMstrEntity projMstr = (ProjMstrEntity) expense[0];
            EmpRegisterDtlEntity empReg = (EmpRegisterDtlEntity) expense[1];
            ProjCostItemEntity costCode = (ProjCostItemEntity) expense[2];

            CostReportTO resp = new CostReportTO();
            setProjectInfo(projMstr, resp);
            setEmpInfo(empReg, resp);
            setCostCodeInfo(costCode, resp);
            resp.setType("Timesheet Expenses");
            resp.setCategory((String) expense[4]);
            resp.setCostAmount(((BigDecimal) expense[3]).doubleValue());
            Date date = (Date) expense[5];
            resp.setDate(CommonUtil.convertDateToString(date));
            list.add(resp);
            Map<Long, Map<Date, EmpChargeOutRateTO>> projRates = chargeRatesByProj.get(projMstr.getProjectId());
            if (projRates.isEmpty())
                continue;
            Map<Date, EmpChargeOutRateTO> empRates = projRates.get(empReg.getId());
            if (empRates.isEmpty())
                continue;
            Set<Date> effectiveDates = empRates.keySet().stream().collect(Collectors.toCollection(TreeSet::new));
            if (effectiveDates.isEmpty())
                continue;
            List<Date> mappedDate = effectiveDates.stream().filter(d -> d.getTime() == date.getTime() || d.before(date))
                    .collect(Collectors.toList());
            if (mappedDate.isEmpty())
                continue;
            Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
            EmpChargeOutRateTO empRate = empRates.get(effectiveDate);
            if (empRate != null) {
                resp.setCurrencyCode(empRate.getCurrency());
            }
        }
        return list;
    }

    // TODO call this from ActualAmountService
    private Map<Long, Map<Date, Map<Long, List<String>>>> getLeaveTypes(List<Long> projIds) {
        Map<Long, Map<Date, Map<Long, List<String>>>> paidLeavesByProj = new HashMap<>();
        for (Long projId : projIds) {
            ProjMstrEntity projMstrEntity = projRepository.findOne(projId);
            System.out.println("getLeaveTypes : findCountry : projId: "+projId);
            String countryCode = projGeneralRepository.findCountry(projMstrEntity);
            List<ProjLeaveTypeEntity> procurePaidLeaveList = leaveTypeRepository.findPaidLeaveCodes(countryCode);
            Map<Date, Map<Long, List<String>>> procurePaidLeaveDateMap = new HashMap<>();
            for (ProjLeaveTypeEntity entity : procurePaidLeaveList) {
                Date effectiveFrom = entity.getEffectiveFrom();
                procurePaidLeaveDateMap.computeIfAbsent(effectiveFrom, v -> new HashMap<>());
                procurePaidLeaveDateMap.computeIfPresent(effectiveFrom, (k, v) -> {
                    entity.getProjLeaveCategoryTypes().forEach(t -> {
                        Long procureId = t.getProcureCatgDtlEntity().getId();
                        String leaveType = t.getProjLeaveTypeEntity().getCode();
                        if ("paid".equalsIgnoreCase(t.getPayType())) {
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
            paidLeavesByProj.put(projId, procurePaidLeaveDateMap);
        }
        return paidLeavesByProj;
    }

    private List<CostReportTO> processEmpMobToCostReportResp(EmpProjRigisterEntity mobRate, String category,
            boolean mob) {
        List<CostReportTO> list = new ArrayList<>();
        EmpRegisterDtlEntity empReg = mobRate.getEmpRegisterDtlEntity();
        if (empReg == null)
            return list;
        CostReportTO resp = new CostReportTO();
        resp.setDate(CommonUtil
                .convertDateToString(mob ? mobRate.getMobilaizationDate() : mobRate.getDeMobilaizationDate()));
        ProjMstrEntity projMstr = mobRate.getProjMstrEntity();
        if (projMstr != null)
            setProjectInfo(projMstr, resp);
        setEmpInfo(empReg, resp);
        for (EmpChargeOutRateEntity charge : mobRate.getEmpchargeOutRateEntities()) {
            CostReportTO empInfoReportClone = new CostReportTO();
            BeanUtils.copyProperties(resp, empInfoReportClone);
            if (mob) {
                if (charge.getMobCostItemEntity() == null || charge.getMobRate() == null)
                    continue;
                empInfoReportClone.setType("Mobilization");
                setCostCodeInfo(charge.getMobCostItemEntity(), empInfoReportClone);
                empInfoReportClone.setCostAmount(charge.getMobRate().doubleValue());
            } else {
                if (charge.getDeMobCostItemEntity() == null || charge.getDeMobRate() == null)
                    continue;
                empInfoReportClone.setType("DeMobilization");
                setCostCodeInfo(charge.getDeMobCostItemEntity(), empInfoReportClone);
                empInfoReportClone.setCostAmount(charge.getDeMobRate().doubleValue());
            }
            empInfoReportClone.setCategory(category);
            empInfoReportClone.setCurrencyCode(
                    charge.getProjGeneralMstrEntity() != null ? charge.getProjGeneralMstrEntity().getCurrency() : "");
            list.add(empInfoReportClone);
        }
        return list;
    }

    private void setCostCodeInfo(ProjCostItemEntity costCode, CostReportTO resp) {
        if (costCode != null) {
            resp.setCostItemId(costCode.getId());
            resp.setCostItemCode(costCode.getCode());
            resp.setCostItemName(costCode.getName());
            ProjCostItemEntity parentCost = costCode.getProjCostItemEntity();
            if (parentCost != null) {
                resp.setCostSubGroupId(parentCost.getId());
                resp.setCostSubGroupCode(parentCost.getCode());
                resp.setCostSubGroupName(parentCost.getName());
            }
        }
    }

    private void setCostCodeInfo(ProjCostItemEntity costCode, CostItemReportTO resp) {
        if (costCode != null) {
            resp.setCostItemId(costCode.getId());
            resp.setCostItemCode(costCode.getCode());
            resp.setCostItemName(costCode.getName());
            ProjCostItemEntity parentCost = costCode.getProjCostItemEntity();
            if (parentCost != null) {
                resp.setCostSubGroupId(parentCost.getId());
                resp.setCostSubGroupCode(parentCost.getCode());
                resp.setCostSubGroupName(parentCost.getName());
            }
        }
    }

    private void setProjectInfo(ProjMstrEntity projMstr, CostReportTO resp) {
        if (null != projMstr) {
            resp.setProjId(projMstr.getProjectId());
            resp.setProjName(projMstr.getProjName());
            ProjMstrEntity parentMstr = projMstr.getParentProjectMstrEntity();
            if (parentMstr != null) {
                resp.setEpsId(parentMstr.getProjectId());
                resp.setEpsName(parentMstr.getProjName());
            }
        }
    }

    private void setProjectInfo(ProjMstrEntity projMstr, CostItemReportTO resp) {
        if (null != projMstr) {
            resp.setProjId(projMstr.getProjectId());
            resp.setProjName(projMstr.getProjName());
            ProjMstrEntity parentMstr = projMstr.getParentProjectMstrEntity();
            if (parentMstr != null) {
                resp.setEpsId(parentMstr.getProjectId());
                resp.setEpsName(parentMstr.getProjName());
            }
        }
    }

    private void setEmpInfo(EmpRegisterDtlEntity empReg, CostReportTO resp) {
        resp.setEmpId(empReg.getId());
        resp.setResourceId(empReg.getCode());
        resp.setEmpFirstName(empReg.getFirstName());
        resp.setEmpLastName(empReg.getLastName());
        resp.setCmpId(empReg.getCompanyMstrEntity().getId());
        resp.setCmpName(empReg.getCompanyMstrEntity().getName());
    }

    private List<CostReportTO> findPlantMobRates(CostReportReq costReportReq) {
        List<CostReportTO> list = new ArrayList<>();
        Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
        List<Object[]> mobRates = plantChargeOutRateRepository.findMobRates(costReportReq.getProjIds(),
                costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate, toDate);
        for (Object[] mobRate : mobRates) {
            ProjMstrEntity projMstr = (ProjMstrEntity) mobRate[0];
            ProjCostItemEntity costCode = (ProjCostItemEntity) mobRate[1];
            PlantRegisterDtlEntity plantReg = (PlantRegisterDtlEntity) mobRate[2];
            Date mobDate = (Date) mobRate[3];
            CostReportTO resp = new CostReportTO();
            resp.setDate(CommonUtil.convertDateToString(mobDate));
            if (projMstr != null)
                setProjectInfo(projMstr, resp);
            setPlantInfo(plantReg, resp);
            setCostCodeInfo(costCode, resp);
            resp.setType("Mobilization");
            resp.setPlantRateType((String) mobRate[5]);
            resp.setCurrencyCode((String) mobRate[6]);
            BigDecimal rate = (BigDecimal) mobRate[4];
            if (rate != null)
                resp.setCostAmount(rate.doubleValue());
            list.add(resp);
        }
        return list;
    }

    private List<CostReportTO> findPlantDeMobRates(CostReportReq costReportReq) {
        List<CostReportTO> list = new ArrayList<>();
        Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
        List<Object[]> mobRates = plantChargeOutRateRepository.findDeMobRates(costReportReq.getProjIds(),
                costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate, toDate);
        for (Object[] mobRate : mobRates) {
            ProjMstrEntity projMstr = (ProjMstrEntity) mobRate[0];
            ProjCostItemEntity costCode = (ProjCostItemEntity) mobRate[1];
            PlantRegisterDtlEntity plantReg = (PlantRegisterDtlEntity) mobRate[2];
            Date mobDate = (Date) mobRate[3];
            CostReportTO resp = new CostReportTO();
            resp.setDate(CommonUtil.convertDateToString(mobDate));
            if (projMstr != null)
                setProjectInfo(projMstr, resp);
            setPlantInfo(plantReg, resp);
            setCostCodeInfo(costCode, resp);
            resp.setType("DeMobilization");
            resp.setPlantRateType((String) mobRate[5]);
            resp.setCurrencyCode((String) mobRate[6]);
            BigDecimal rate = (BigDecimal) mobRate[4];
            if (rate != null)
                resp.setCostAmount(rate.doubleValue());
            list.add(resp);
        }
        return list;
    }

    private List<CostReportTO> findPlantWorkDairyDetails(CostReportReq costReportReq) {
        List<CostReportTO> list = new ArrayList<>();
        Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
        List<Object[]> workDairyHrs = plantCostWorkDairyRepository.getPlantWorkDiaryActualHrs(
                costReportReq.getProjIds(), costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate,
                toDate);
        if (workDairyHrs.isEmpty())
            return list;
        Map<Long, Map<Long, Map<Date, PlantChargeOutRateTO>>> chargeRatesByProj = actualAmountService
                .getPlantChargeOutRates(new HashSet<Long>(costReportReq.getProjIds()));
        for (Object[] workDairyHr : workDairyHrs) {
            ProjMstrEntity projMstr = (ProjMstrEntity) workDairyHr[0];
            Date workDairyDate = (Date) workDairyHr[1];
            WorkDairyPlantDtlEntity plantDtl = (WorkDairyPlantDtlEntity) workDairyHr[2];
            PlantRegisterDtlEntity plantReg = plantDtl.getPlantRegId();
            CostReportTO resp = new CostReportTO();
            resp.setDate(CommonUtil.convertDateToString(workDairyDate));
            setProjectInfo(projMstr, resp);
            setPlantInfo(plantReg, resp);
            setCostCodeInfo((ProjCostItemEntity) workDairyHr[3], resp);
            resp.setCurrencyCode((String) workDairyHr[6]);
            Map<Long, Map<Date, PlantChargeOutRateTO>> chargeRates = chargeRatesByProj.get(projMstr.getProjectId());
            Map<Date, PlantChargeOutRateTO> plantRateWithDates = chargeRates.get(plantReg.getId());
            PlantChargeOutRateTO plantRate = null;
            Double usedHrs = (Double) workDairyHr[4];
            Double idleHrs = (Double) workDairyHr[5];
            if (plantRateWithDates != null && !plantRateWithDates.values().isEmpty()) {
                Set<Date> effectiveDates = plantRateWithDates.keySet().stream()
                        .collect(Collectors.toCollection(TreeSet::new));
                List<Date> mappedDate = effectiveDates.stream()
                        .filter(d -> d.getTime() == workDairyDate.getTime() || d.before(workDairyDate))
                        .collect(Collectors.toList());
                if (mappedDate.isEmpty())
                    continue;
                Date effectiveDate = mappedDate.get(mappedDate.size() - 1);
                plantRate = plantRateWithDates.get(effectiveDate);
            }
            if (CommonUtil.isNonBlankDouble(usedHrs)) {
                CostReportTO plantInfoReportClone = new CostReportTO();
                BeanUtils.copyProperties(resp, plantInfoReportClone);
                plantInfoReportClone.setType("Used WorkDairy");
                plantInfoReportClone.setQuantity(usedHrs);
                if (plantRate != null && plantRate.getNormalRate() != null) {
                    double pRate;
                    if (plantDtl.getShiftName() != null && plantDtl.getShiftName().toLowerCase().contains("single")) {
                        pRate = plantRate.getNormalRate().doubleValue();
                    } else {
                        pRate = plantRate.getDoubleRate().doubleValue();
                    }
                    Double amount = Double.valueOf(0);
                    if (CommonUtil.isNonBlankDouble(pRate)) {
                        amount = ((Double) usedHrs) * pRate;
                    }
                    plantInfoReportClone.setPlantRateType(plantRate.getPlantRateType());
                    plantInfoReportClone.setCostAmount(amount);
                    plantInfoReportClone.setRatePerUnit(pRate);
                }
                list.add(plantInfoReportClone);
            }
            if (CommonUtil.isNonBlankDouble(idleHrs)) {
                CostReportTO plantInfoReportClone = new CostReportTO();
                BeanUtils.copyProperties(resp, plantInfoReportClone);
                plantInfoReportClone.setType("Idle WorkDairy");
                plantInfoReportClone.setQuantity(idleHrs);
                if (plantRate != null && plantRate.getIdleRate() != null) {
                    Double amount = Double.valueOf(0);
                    if (CommonUtil.objectNotNull(plantRate.getIdleRate())
                            && CommonUtil.isNonBlankDouble(plantRate.getIdleRate().doubleValue())) {
                        amount = ((Double) idleHrs) * plantRate.getIdleRate().doubleValue();
                    }
                    plantInfoReportClone.setPlantRateType(plantRate.getPlantRateType());
                    plantInfoReportClone.setCostAmount(amount);
                    double pRate = 0;
                    if (plantRate.getIdleRate() != null) {
                        pRate = plantRate.getIdleRate().doubleValue();
                    }
                    plantInfoReportClone.setRatePerUnit(pRate);
                }
                list.add(plantInfoReportClone);
            }
        }
        return list;
    }

    private void setPlantInfo(PlantRegisterDtlEntity plantReg, CostReportTO resp) {
        resp.setResourceId(plantReg.getAssertId());
        resp.setPlantId(plantReg.getId());
        resp.setPlantDesc(plantReg.getDesc());
        resp.setPlantMake(plantReg.getManfacture());
        resp.setPlantModel(plantReg.getModel());
        resp.setPlantRegNumber(plantReg.getRegNumber());
        resp.setCmpId(plantReg.getCmpId().getId());
        resp.setCmpName(plantReg.getCmpId().getName());
    }

    private List<CostReportTO> getMaterialActual(CostReportReq costReportReq) {
        List<CostReportTO> list = new ArrayList<>();
        Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
        List<Object[]> workDairyHrs = materialStatusWorkDairyRepository.getWorkDairyActualAmount(
                costReportReq.getProjIds(), costReportReq.getCmpIds(), costReportReq.getCostcodeIds(), fromDate,
                toDate);
        for (Object[] workDairyHr : workDairyHrs) {
            CostReportTO resp = new CostReportTO();
            ProjMstrEntity projMstr = (ProjMstrEntity) workDairyHr[0];
            setProjectInfo(projMstr, resp);
            setCostCodeInfo((ProjCostItemEntity) workDairyHr[2], resp);
            resp.setDate(CommonUtil.convertDateToString((Date) workDairyHr[6]));
            resp.setCurrencyCode((String) workDairyHr[5]);

            MaterialClassMstrEntity materialClass = (MaterialClassMstrEntity) workDairyHr[1];
            resp.setResourceId(materialClass.getCode());
            resp.setMatId(materialClass.getId());
            resp.setType("WorkDairy");
            MaterialClassMstrEntity parent = materialClass.getMaterialClassMstrEntity();
            if (null != parent) {
                resp.setMaterialSubGroupId(parent.getId());
                resp.setMaterialSubGroupCode(parent.getCode());
                resp.setMaterialSubGroupName(parent.getName());
            }
            double rate = ((BigDecimal) workDairyHr[3]).doubleValue();
            Double quantity = (Double) workDairyHr[4];
            resp.setRatePerUnit(rate);
            resp.setQuantity(quantity);
            resp.setCostAmount(quantity * rate);
            list.add(resp);
        }
        return list;
    }
    
    private List<CostReportTO> getProgress(CostReportReq costReportReq) {
    	 List<CostReportTO> list = new ArrayList<>();
         Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
         Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
         List<Object[]> workDairyHrs = projSowTotalActualRepository
                 .getDailyEarnedValue(costReportReq.getProjIds(), fromDate, toDate);
         for (Object[] workDairyHr : workDairyHrs) {
             CostReportTO resp = new CostReportTO();
             ProjMstrEntity projMstr = (ProjMstrEntity) workDairyHr[0];
             setProjectInfo(projMstr, resp);
             setCostCodeInfo((ProjCostItemEntity) workDairyHr[1], resp);
             resp.setDate(CommonUtil.convertDateToString((Date) workDairyHr[2]));
             resp.setCurrencyCode((String) workDairyHr[3]);

             /*   MaterialClassMstrEntity materialClass = (MaterialClassMstrEntity) workDairyHr[1];
             resp.setResourceId(materialClass.getCode());
             resp.setMatId(materialClass.getId());
             resp.setType("WorkDairy");
             MaterialClassMstrEntity parent = materialClass.getMaterialClassMstrEntity();
             if (null != parent) {
                 resp.setMaterialSubGroupId(parent.getId());
                 resp.setMaterialSubGroupCode(parent.getCode());
                 resp.setMaterialSubGroupName(parent.getName());
             }
             double rate = ((BigDecimal) workDairyHr[3]).doubleValue();
             Double quantity = (Double) workDairyHr[4];
             resp.setRatePerUnit(rate);
             resp.setQuantity(quantity);
             resp.setCostAmount(quantity * rate);*/
             list.add(resp);
         }
         return list;
    }

    @Override
    public DateWiseCostReportResp getDateWisePlanActualEarned(CostReportReq costReportReq) {
        costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId()));
        DateWiseCostReportResp resp = getDatewiseActualCostDetails(costReportReq);
        Map<String, Double> earnedValues = new HashMap<>();
        if (!resp.getCostReportResps().isEmpty()) {
            Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
            Date toDate = CommonUtil.convertStringToDate(costReportReq.getToDate());
            List<Object[]> dailyEarnedValues = projSowTotalActualRepository
                    .getDailyEarnedValuePerCostCode(costReportReq.getProjIds(), fromDate, toDate);
            for (Object[] dailyEarned : dailyEarnedValues) {
                Long costId = (Long) dailyEarned[0];
                Date date = (Date) dailyEarned[1];
                double value = (double) dailyEarned[2];
                BigDecimal rate = (BigDecimal) dailyEarned[3];
                if (rate != null)
                    earnedValues.merge(CommonUtil.convertDateToString(date) + "-" + costId, value * rate.doubleValue(),
                            Double::sum);
            }
        }
        List<ResourceAssignmentDataValueEntity> resourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllBaselines(costReportReq.getProjIds(), CommonUtil.convertStringToDate(costReportReq.getFromDate()), CommonUtil.convertStringToDate(costReportReq.getToDate()));
        Map<String, CostReportTO> map = new HashMap<>();
        for (CostReportTO costReportResp : resp.getCostReportResps()) {
            String key = costReportResp.getDate() + "-" + costReportResp.getCostItemId();
            costReportResp.setEarnedValue(CommonUtil.ifNullGetDefaultValue(earnedValues.get(key)));
            if (map.containsKey(key)) {
                CostReportTO val = map.get(key);
                val.setCostAmount(val.getCostAmount() + costReportResp.getCostAmount());
                val.setEarnedValue(costReportResp.getEarnedValue());
            } else {
                map.put(key, costReportResp);
            }
        }
        for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : resourceAssignmentDataValueEntities) {
        	if (!resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType().equals("POT_COST")) continue;
        	boolean found = false;
        	for (Map.Entry<String, CostReportTO> entry : map.entrySet()) {
        		if (entry.getValue().getProjId().equals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId()) 
        				&& entry.getValue().getCostItemId().equals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId())
        				&& entry.getValue().getDate().equals(CommonUtil.convertDateToString(resourceAssignmentDataValueEntity.getForecastDate()))) {
        			entry.getValue().setPlannedValue(resourceAssignmentDataValueEntity.getBudgetUnits());
        			found = true;
        			break;
        		}
        	}
        	if (!found) {
        		ProjCostItemEntity projCostItemEntity = projCostItemRepository.findOne(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId());
        		CostReportTO costReportTO = new CostReportTO();
        		costReportTO.setDate(CommonUtil.convertDateToString(resourceAssignmentDataValueEntity.getForecastDate()));
        		costReportTO.setEpsId(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getParentProjectMstrEntity().getProjectId());
        		costReportTO.setEpsName(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getParentProjectMstrEntity().getProjName());
        		costReportTO.setProjId(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId());
        		costReportTO.setProjName(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjName());
        		costReportTO.setCostItemId(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId());
        		costReportTO.setCostItemCode(projCostItemEntity.getCode());
        		costReportTO.setCostItemName(projCostItemEntity.getName());
        		costReportTO.setCostSubGroupId(projCostItemEntity.getCostMstrEntity().getId());
        		costReportTO.setCostSubGroupCode(projCostItemEntity.getCostMstrEntity().getCode());
        		costReportTO.setCostSubGroupName(projCostItemEntity.getCostMstrEntity().getName());
        		costReportTO.setCurrencyCode(projGeneralRepository.findProjGenerals(costReportTO.getProjId(), 1).get(0).getCurrency());
        		costReportTO.setPlannedValue(resourceAssignmentDataValueEntity.getBudgetUnits());
        		String key = costReportTO.getDate() + "-" + costReportTO.getCostItemId();
        		map.put(key, costReportTO);
        	}
        }
        DateWiseCostReportResp newResp = new DateWiseCostReportResp();
        newResp.getCostReportResps()
                .addAll(map
                        .values().stream().sorted(Comparator.comparing(CostReportTO::getDateObj)
                                .thenComparing(CostReportTO::getProjId).thenComparing(CostReportTO::getCostItemId))
                        .collect(Collectors.toList()));
        return newResp;
    }

    @Override
    public List<PeriodCostTO> getPeriodicalWiseReport(CostReportReq costReportReq) {
        Date fromForReport = new Date(0);
        Date fromDate = CommonUtil.convertStringToDate(costReportReq.getFromDate());
        costReportReq.setFromDate(CommonUtil.convertDateToString(fromForReport));
        costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId()));
        DateWiseCostReportResp resp = getDateWisePlanActualEarned(costReportReq);

        Map<Long, PeriodCostTO> map = new HashMap<>();
        for (CostReportTO costReportTO : resp.getCostReportResps()) {
            if (costReportTO.getCostAmount() != 0 || costReportTO.getEarnedValue() != 0) {
                PeriodCostTO periodCostTO = copyMainValues(costReportTO);
                map.computeIfAbsent(periodCostTO.getCostItemId(), v -> periodCostTO);
                map.computeIfPresent(periodCostTO.getCostItemId(), (k, v) -> {
                    CostValuesTO costValuesTO = new CostValuesTO(costReportTO.getDate(), periodCostTO.getCostItemId(),
                            costReportTO.getCostAmount(), costReportTO.getEarnedValue());
                    if (costReportTO.getDateObj().before(fromDate)) {
                        v.setTotalPrevCost(v.getTotalPrevCost() + costReportTO.getCostAmount());
                        v.setTotalPrevEarned(v.getTotalPrevEarned() + costReportTO.getEarnedValue());
                        v.getPrevValues().add(costValuesTO);
                    } else {
                        v.setTotalReportCost(v.getTotalReportCost() + costReportTO.getCostAmount());
                        v.setTotalReportEarned(v.getTotalReportEarned() + costReportTO.getEarnedValue());
                        v.getReportValues().add(costValuesTO);
                    }
                    return v;
                });
            }
        }
        List<ResourceAssignmentDataValueEntity> resourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllBaselines(costReportReq.getProjIds(), CommonUtil.convertStringToDate(costReportReq.getFromDate()), CommonUtil.convertStringToDate(costReportReq.getToDate()));
        for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : resourceAssignmentDataValueEntities) {
        	if (!resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType().equals("POT_COST")) continue;
        	boolean found = false;
        	for (Map.Entry<Long, PeriodCostTO> entry : map.entrySet()) {
        		if (entry.getValue().getProjId().equals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId())
        				&& entry.getValue().getCostItemId().equals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId())) {
        			found = true;
        			entry.getValue().setPlannedValue(entry.getValue().getPlannedValue() + resourceAssignmentDataValueEntity.getBudgetUnits());
        		}
        	}
        	
        	if (!found) {
        		ProjCostItemEntity projCostItemEntity = projCostItemRepository.findOne(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId());
        		ProjGeneralMstrEntity projGeneralMstrEntity = projGeneralRepository.findProjGenerals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId(), 1).get(0);
        		PeriodCostTO periodCostTO = new PeriodCostTO();
        		periodCostTO.setEpsId(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getParentProjectMstrEntity().getProjectId());
        		periodCostTO.setEpsName(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getParentProjectMstrEntity().getProjName());
        		periodCostTO.setProjId(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId());
        		periodCostTO.setProjName(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjName());
        		periodCostTO.setCostItemId(projCostItemEntity.getId());
        		periodCostTO.setCostItemCode(projCostItemEntity.getCode());
        		periodCostTO.setCostItemName(projCostItemEntity.getName());
        		periodCostTO.setCurrencyCode(projGeneralMstrEntity.getCurrency());
        		periodCostTO.setPlannedValue(resourceAssignmentDataValueEntity.getBudgetUnits());
        		
        		map.put(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId(), periodCostTO);
        	}
        }
        
        return new ArrayList<>(map.values());
    }

    @Override
    public List<PeriodCostTO> getCostCodeWiseReport(CostReportReq costReportReq) {
        costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId()));
        DateWiseCostReportResp resp = getDateWisePlanActualEarned(costReportReq);
        Map<Long, PeriodCostTO> map = new HashMap<>();
        for (CostReportTO costReportTO : resp.getCostReportResps()) {
        	PeriodCostTO periodCostTO = copyMainValues(costReportTO);
            map.computeIfAbsent(periodCostTO.getCostItemId(), v -> periodCostTO);
            map.computeIfPresent(periodCostTO.getCostItemId(), (k, v) -> {
                CostValuesTO costValuesTO = new CostValuesTO(costReportTO.getDate(), periodCostTO.getCostItemId(),
                        costReportTO.getCostAmount(), costReportTO.getEarnedValue());
                v.setTotalReportCost(v.getTotalReportCost() + costReportTO.getCostAmount());
                v.setTotalReportEarned(v.getTotalReportEarned() + costReportTO.getEarnedValue());
                v.getReportValues().add(costValuesTO);
                return v;
            });
        }
        List<ResourceAssignmentDataValueEntity> resourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllBaselines(costReportReq.getProjIds(), CommonUtil.convertStringToDate(costReportReq.getFromDate()), CommonUtil.convertStringToDate(costReportReq.getToDate()));
        for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : resourceAssignmentDataValueEntities) {
        	if (!resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType().equals("POT_COST")) continue;
        	for (Map.Entry<Long, PeriodCostTO> entry : map.entrySet()) {
        		if (entry.getValue().getProjId().equals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId())
        				&& entry.getValue().getCostItemId().equals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId())) {
       				entry.getValue().getReportValues().get(0).setPlannedValue(entry.getValue().getReportValues().get(0).getPlannedValue() + resourceAssignmentDataValueEntity.getBudgetUnits());
        		}
        	}
        }
        return new ArrayList<>(map.values());
    }

    private PeriodCostTO copyMainValues(CostReportTO costReportTO) {
        PeriodCostTO periodCostTO = new PeriodCostTO();
        periodCostTO.setEpsId(costReportTO.getEpsId());
        periodCostTO.setEpsName(costReportTO.getEpsName());
        periodCostTO.setProjId(costReportTO.getProjId());
        periodCostTO.setProjName(costReportTO.getProjName());
        periodCostTO.setCostItemId(costReportTO.getCostItemId());
        periodCostTO.setCostItemCode(costReportTO.getCostItemCode());
        periodCostTO.setCostItemName(costReportTO.getCostItemName());
        periodCostTO.setCostSubGroupId(costReportTO.getCostSubGroupId());
        periodCostTO.setCostSubGroupCode(costReportTO.getCostSubGroupCode());
        periodCostTO.setCostSubGroupName(costReportTO.getCostSubGroupName());
        periodCostTO.setCurrencyCode(costReportTO.getCurrencyCode());
        return periodCostTO;
    }

    @Override
    public CostItemActualReportResp getDateProjWiseActualReport(CostReportReq costReportReq) {
        costReportReq.setCostcodeIds(projCostItemRepository.findMultiProjCostIds(costReportReq.getProjIds()));
        costReportReq.setCmpIds(companyRepository.getCmpIds(AppUserUtils.getClientId()));
        Map<String, CostItemActualReportTO> map = new HashMap<>();
        convertLabour(getEmpData(costReportReq), map);
        convertPlant(getPlantData(costReportReq), map);
        convertMat(getMaterialActual(costReportReq), map);

        CostItemWiseReportResp budgetResp = getCostCodeBudgetReport(costReportReq.getProjIds());
        CostItemActualReportResp resp = new CostItemActualReportResp();
        for (CostItemReportTO reportTO : budgetResp.getCostReportResps()) {
            Long costId = reportTO.getCostItemId();
            Double labour = reportTO.getLabourAmount();
            Double plant = reportTO.getPlantAmount();
            Double mat = reportTO.getMatAmount();
            Double other = reportTO.getOtherAmount();
            Double total = labour + plant + mat + other;
            CostBudgetRatioTO ratioTO = new CostBudgetRatioTO();
            ratioTO.setCostId(costId);
            ratioTO.setLabour(labour / total);
            ratioTO.setPlant(plant / total);
            ratioTO.setMat(mat / total);
            ratioTO.setOther(other / total);
            resp.getRatioTOs().add(ratioTO);
        }

        List<ResourceAssignmentDataValueEntity> resourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllBaselines(costReportReq.getProjIds(), CommonUtil.convertStringToDate(costReportReq.getFromDate()), CommonUtil.convertStringToDate(costReportReq.getToDate()));
        for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : resourceAssignmentDataValueEntities) {
        	if (!resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType().equals("POT_COST")) continue;
        	boolean found = false;
        	for (Map.Entry<String, CostItemActualReportTO> entry : map.entrySet()) {
        		if (entry.getKey().equals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId() + CommonUtil.convertDateToString(resourceAssignmentDataValueEntity.getForecastDate()))) {
        			found = true;
        			for (CostBudgetRatioTO ratioTO : resp.getRatioTOs()) {
        				if (ratioTO.getCostId().equals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId())) {
        					entry.getValue().setLabourPlanned(entry.getValue().getLabourPlanned() + (resourceAssignmentDataValueEntity.getBudgetUnits() * ratioTO.getLabour()));
	            			entry.getValue().setPlantPlanned(entry.getValue().getPlantPlanned() + (resourceAssignmentDataValueEntity.getBudgetUnits() * ratioTO.getPlant()));
	            			entry.getValue().setMatPlanned(entry.getValue().getMatPlanned() + (resourceAssignmentDataValueEntity.getBudgetUnits() * ratioTO.getMat()));
	            			entry.getValue().setOtherPlanned(entry.getValue().getOtherPlanned() + (resourceAssignmentDataValueEntity.getBudgetUnits() * ratioTO.getOther()));
	            			entry.getValue().setTotalPlanned(entry.getValue().getLabourPlanned() + entry.getValue().getMatPlanned() + entry.getValue().getPlantPlanned() + entry.getValue().getOtherPlanned());
        				}
        			}
        		}
        	}
        	
        	if (!found) {
        		ProjGeneralMstrEntity projGeneralMstrEntity = projGeneralRepository.findProjGenerals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId(), 1).get(0);
        		CostItemActualReportTO costItemActualReportTO = new CostItemActualReportTO();
        		costItemActualReportTO.setDate(CommonUtil.convertDateToString(resourceAssignmentDataValueEntity.getForecastDate()));
        		costItemActualReportTO.setEpsId(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getParentProjectMstrEntity().getProjectId());
        		costItemActualReportTO.setEpsName(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getParentProjectMstrEntity().getProjName());
        		costItemActualReportTO.setProjId(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId());
        		costItemActualReportTO.setProjName(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjName());
        		costItemActualReportTO.setCurrencyCode(projGeneralMstrEntity.getCurrency());
        		for (CostBudgetRatioTO ratioTO : resp.getRatioTOs()) {
    				if (ratioTO.getCostId().equals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId())) {
    					costItemActualReportTO.setLabourPlanned(resourceAssignmentDataValueEntity.getBudgetUnits() * ratioTO.getLabour());
    	        		costItemActualReportTO.setMatPlanned(resourceAssignmentDataValueEntity.getBudgetUnits() * ratioTO.getMat());
    	        		costItemActualReportTO.setPlantPlanned(resourceAssignmentDataValueEntity.getBudgetUnits() * ratioTO.getPlant());
    	        		costItemActualReportTO.setOtherPlanned(resourceAssignmentDataValueEntity.getBudgetUnits() * ratioTO.getOther());
    				}
    			}
        		costItemActualReportTO.setTotalPlanned(costItemActualReportTO.getLabourPlanned() + costItemActualReportTO.getMatPlanned() + costItemActualReportTO.getPlantPlanned() + costItemActualReportTO.getOtherPlanned());
        		map.put(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId() + CommonUtil.convertDateToString(resourceAssignmentDataValueEntity.getForecastDate()), costItemActualReportTO);
        	}
        }
        
        resp.setCostItemActualReportTOs(
                new ArrayList<>(
                        map.values().stream()
                                .sorted(Comparator.comparing(CostItemActualReportTO::getDateObj)
                                        .thenComparing(CostItemActualReportTO::getProjId))
                                .collect(Collectors.toList())));
        return resp;
    }

    private void convertLabour(List<CostReportTO> costReportTOs, Map<String, CostItemActualReportTO> map) {
        costReportTOs.forEach(c -> {
            String key = c.getProjId() + c.getDate();
            map.computeIfAbsent(key, r -> copyValues(c));
            map.computeIfPresent(key, (k, v) -> {
                v.addLabourActual(c.getCostAmount());
                return v;
            });
        });
    }

    private void convertPlant(List<CostReportTO> costReportTOs, Map<String, CostItemActualReportTO> map) {
        costReportTOs.forEach(c -> {
            String key = c.getProjId() + c.getDate();
            map.computeIfAbsent(key, r -> copyValues(c));
            map.computeIfPresent(key, (k, v) -> {
                v.addPlantActual(c.getCostAmount());
                return v;
            });
        });
    }

    private void convertMat(List<CostReportTO> costReportTOs, Map<String, CostItemActualReportTO> map) {
        costReportTOs.forEach(c -> {
            String key = c.getProjId() + c.getDate();
            map.computeIfAbsent(key, r -> copyValues(c));
            map.computeIfPresent(key, (k, v) -> {
                v.addMatActual(c.getCostAmount());
                return v;
            });
        });
    }

    private CostItemActualReportTO copyValues(CostReportTO c) {
        CostItemActualReportTO to = new CostItemActualReportTO();
        to.setEpsId(c.getEpsId());
        to.setEpsName(c.getEpsName());
        to.setProjId(c.getProjId());
        to.setProjName(c.getProjName());
        to.setCurrencyCode(c.getCurrencyCode());
        to.setDate(c.getDate());
        return to;
    }

}
