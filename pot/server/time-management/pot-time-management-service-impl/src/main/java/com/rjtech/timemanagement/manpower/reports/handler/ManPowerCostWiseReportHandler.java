package com.rjtech.timemanagement.manpower.reports.handler;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.model.EmpWageMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.proj.settings.common.service.impl.copy.EmployeeChargeoutRatesService;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.projectlib.repository.ProjEmpClassRepositoryCopy;
import com.rjtech.register.emp.dto.EmpChargeOutRateTO;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.utils.RegisterCommonUtils;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerCostCodeDailyReportTO;
import com.rjtech.timemanagement.manpower.reports.dto.ReportHoursTO;
//import com.rjtech.timemanagement.register.emp.model.EmpProjRigisterEntityCopy;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpWorkDtlEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpCostEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpWageEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.repository.EmpCostWorkDairyRepository;

public class ManPowerCostWiseReportHandler {

    public void setEmpRegDetails(EmpRegisterDtlEntity empReg,
            ManPowerCostCodeDailyReportTO manPowerCostCodeDailyReportTO,
            ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy, Date selectedDate,
            Map<Long, Map<Date, EmpChargeOutRateTO>> ratesMap,
            EmployeeChargeoutRatesService employeeChargeoutRatesService) {
        if (empReg != null) {
            manPowerCostCodeDailyReportTO.setGender(empReg.getGender());
            EmpChargeOutRateTO chargeRateTo = employeeChargeoutRatesService.getEmpChargeoutRate(ratesMap, selectedDate,
                    empReg.getId());
            if (chargeRateTo != null) {
                manPowerCostCodeDailyReportTO.setIdleRate(chargeRateTo.getIdleRate().doubleValue());
                manPowerCostCodeDailyReportTO.setNormalRate(chargeRateTo.getNormalRate().doubleValue());
            }
            List<EmpProjRigisterEntity> projRegisters = empReg.getProjEmpRigisterEntities();
            for (EmpProjRigisterEntity projReg : projRegisters) {
                if (projReg.getIsLatest().equals(RegisterCommonUtils.IS_LATEST_Y)) {
                    manPowerCostCodeDailyReportTO.setMobilizationDate(projReg.getMobilaizationDate());
                }
            }
            new ManPowerActualHrsReportHandler().setEmpRegDetails(empReg, manPowerCostCodeDailyReportTO,
                    projEmpClassRepositoryCopy);
        }
    }

    public void setCrewDetails(ProjCrewMstrEntity crew,
            ManPowerCostCodeDailyReportTO manPowerCostCodeDailyReportTO) {
        new ManPowerActualHrsReportHandler().setCrewDetails(crew, manPowerCostCodeDailyReportTO);
    }

    public void setCostDetails(ProjCostItemEntity costCode,
            ManPowerCostCodeDailyReportTO manPowerCostCodeDailyReportTO) {
        new ManPowerActualHrsReportHandler().setCostDetails(costCode, manPowerCostCodeDailyReportTO);
    }

    public void setProjDetails(ProjMstrEntity project, ManPowerCostCodeDailyReportTO manPowerCostCodeDailyReportTO) {
        new ManPowerActualHrsReportHandler().setProjDetails(project, manPowerCostCodeDailyReportTO);
    }

    public void setWageDetails(EmpWageMstrEntity wage, ManPowerCostCodeDailyReportTO manPowerCostCodeDailyReportTO) {
        new ManPowerActualHrsReportHandler().setWageDetails(wage, manPowerCostCodeDailyReportTO);
    }

    public List<ManPowerCostCodeDailyReportTO> getWkorDairyHrs(List<WorkDairyEmpWageEntity> workDairyHrs,
            ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy,
            EmployeeChargeoutRatesService employeeChargeoutRatesService,
            EmpCostWorkDairyRepository empCostWorkDairyRepository, boolean setHrsList) {

        List<ManPowerCostCodeDailyReportTO> workDairyList = new ArrayList<>();
        for (WorkDairyEmpWageEntity woDairyEmpWageEntity : workDairyHrs) {
            WorkDairyEntity workDairy = woDairyEmpWageEntity.getWorkDairyEmpDtlEntity().getWorkDairyEntity();
            EmpRegisterDtlEntity empReg = woDairyEmpWageEntity.getWorkDairyEmpDtlEntity().getEmpRegId();
            List<WorkDairyEmpCostEntity> empCosts = empCostWorkDairyRepository
                    .getApprovedWorkDairyEmpTime(woDairyEmpWageEntity.getId());
            if (!empCosts.isEmpty()) {
                for (WorkDairyEmpCostEntity empCost : empCosts) {
                    ManPowerCostCodeDailyReportTO manPowerCostCodeDailyReportTO = new ManPowerCostCodeDailyReportTO();

                    if (workDairy != null) {
                        Long projId = workDairy.getProjId().getProjectId();

                        Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> projChargeoutRateMap = new HashMap<>();
                        Map<Long, Map<Date, EmpChargeOutRateTO>> ratesMap = projChargeoutRateMap.get(projId);
                        if (ratesMap == null) {
                            ratesMap = employeeChargeoutRatesService.getEmpChargeOutRates(projId);
                            projChargeoutRateMap.put(projId, ratesMap);
                        }
                        this.setEmpRegDetails(empReg, manPowerCostCodeDailyReportTO, projEmpClassRepositoryCopy,
                                workDairy.getWorkDairyDate(), ratesMap, employeeChargeoutRatesService);
                    }

                    this.setCostDetails(empCost.getCostId(), manPowerCostCodeDailyReportTO);
                    this.setWageDetails(empCost.getWorkDairyEmpWageEntity().getWageId(), manPowerCostCodeDailyReportTO);
                    if (workDairy != null) {
                        this.setProjDetails(workDairy.getProjId(), manPowerCostCodeDailyReportTO);
                        this.setCrewDetails(workDairy.getCrewId(), manPowerCostCodeDailyReportTO);
                        if (setHrsList) {
                            ReportHoursTO reportHoursTO = new ReportHoursTO(workDairy.getWorkDairyDate(),
                                    empCost.getUsedTime(), empCost.getIdleTime());
                            List<ReportHoursTO> hrsList = manPowerCostCodeDailyReportTO.getHrsList();
                            int index = hrsList.indexOf(reportHoursTO);
                            if (index != -1) {
                                hrsList.get(index).add(reportHoursTO);
                            } else {
                                hrsList.add(reportHoursTO);
                            }
                        }
                    }

                    // check if object is already exists
                    int objIndex = workDairyList.indexOf(manPowerCostCodeDailyReportTO);
                    if (objIndex != -1) {
                        // if object present update hrs list
                        List<ReportHoursTO> existingHrs = workDairyList.get(objIndex).getHrsList();
                        int itemIndex = -1;
                        for (ReportHoursTO newReportHrs : manPowerCostCodeDailyReportTO.getHrsList()) {
                            itemIndex = existingHrs.indexOf(newReportHrs);
                            if (itemIndex != -1) {
                                existingHrs.get(itemIndex).add(newReportHrs);
                            } else {
                                existingHrs.add(newReportHrs);
                            }
                        }
                    } else {
                        workDairyList.add(manPowerCostCodeDailyReportTO);
                    }

                }
            }

        }
        return workDairyList;
    }

    public List<ManPowerCostCodeDailyReportTO> getTimeSheetHrs(List<TimeSheetEmpWorkDtlEntity> timeSheetHrs,
            ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy, Date selectedDate,
            EmployeeChargeoutRatesService employeeChargeoutRatesService) {

        List<ManPowerCostCodeDailyReportTO> timeSheetList = new ArrayList<>();

        for (TimeSheetEmpWorkDtlEntity timeSheetHr : timeSheetHrs) {
            TimeSheetEntity timeSheet = timeSheetHr.getTimeSheetEmpDtlEntity().getTimeSheetEntity();

            Date weekStartDate = new Date(timeSheet.getWeekStartDate().getTime());
            Date nextDay;
            boolean employeeHasTime = false;
            if (weekStartDate.equals(selectedDate) && timeSheetHr.getDay1() != null && timeSheetHr.getDay1() > 0) {
                employeeHasTime = true;
            }
            Instant weekStartInstant = weekStartDate.toInstant();
            nextDay = Date.from(weekStartInstant.plus(1, ChronoUnit.DAYS));
            if (nextDay.equals(selectedDate) && timeSheetHr.getDay2() != null && timeSheetHr.getDay2() > 0) {
                employeeHasTime = true;
            }
            nextDay = Date.from(weekStartInstant.plus(1, ChronoUnit.DAYS));
            if (nextDay.equals(selectedDate) && timeSheetHr.getDay3() != null && timeSheetHr.getDay3() > 0) {
                employeeHasTime = true;
            }
            nextDay = Date.from(weekStartInstant.plus(1, ChronoUnit.DAYS));
            if (nextDay.equals(selectedDate) && timeSheetHr.getDay4() != null && timeSheetHr.getDay4() > 0) {
                employeeHasTime = true;
            }
            nextDay = Date.from(weekStartInstant.plus(1, ChronoUnit.DAYS));
            if (nextDay.equals(selectedDate) && timeSheetHr.getDay5() != null && timeSheetHr.getDay5() > 0) {
                employeeHasTime = true;
            }
            nextDay = Date.from(weekStartInstant.plus(1, ChronoUnit.DAYS));
            if (nextDay.equals(selectedDate) && timeSheetHr.getDay6() != null && timeSheetHr.getDay6() > 0) {
                employeeHasTime = true;
            }
            nextDay = Date.from(weekStartInstant.plus(1, ChronoUnit.DAYS));
            if (nextDay.equals(selectedDate) && timeSheetHr.getDay7() != null && timeSheetHr.getDay7() > 0) {
                employeeHasTime = true;
            }
            if (employeeHasTime) {

                ManPowerCostCodeDailyReportTO manPowerCostCodeDailyReportTO = new ManPowerCostCodeDailyReportTO();
                this.setCrewDetails(timeSheet.getProjCrewMstrEntity(), manPowerCostCodeDailyReportTO);
                this.setCostDetails(timeSheetHr.getProjCostItemEntity(), manPowerCostCodeDailyReportTO);
                this.setProjDetails(timeSheet.getProjMstrEntity(), manPowerCostCodeDailyReportTO);

                Map<Long, Map<Long, Map<Date, EmpChargeOutRateTO>>> projChargeoutRateMap = new HashMap<>();
                Long projId = manPowerCostCodeDailyReportTO.getProjId();
                Map<Long, Map<Date, EmpChargeOutRateTO>> ratesMap = projChargeoutRateMap.get(projId);
                if (ratesMap == null) {
                    ratesMap = employeeChargeoutRatesService.getEmpChargeOutRates(projId);
                    projChargeoutRateMap.put(projId, ratesMap);
                }

                this.setWageDetails(timeSheetHr.getEmpWageMstrEntity(), manPowerCostCodeDailyReportTO);
                this.setEmpRegDetails(timeSheetHr.getTimeSheetEmpDtlEntity().getEmpRegisterDtlEntity(),
                        manPowerCostCodeDailyReportTO, projEmpClassRepositoryCopy, selectedDate, ratesMap,
                        employeeChargeoutRatesService);

                timeSheetList.add(manPowerCostCodeDailyReportTO);
            }

        }

        return timeSheetList;
    }
}
