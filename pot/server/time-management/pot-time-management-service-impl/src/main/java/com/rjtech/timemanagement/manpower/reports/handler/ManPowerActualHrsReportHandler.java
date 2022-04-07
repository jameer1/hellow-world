package com.rjtech.timemanagement.manpower.reports.handler;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.EmpWageMstrEntity;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.projectlib.repository.ProjEmpClassRepositoryCopy;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerActualHrsTO;
import com.rjtech.timemanagement.manpower.reports.dto.ReportHoursTO;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpWorkDtlEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpCostEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpWageEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.repository.EmpCostWorkDairyRepository;

public class ManPowerActualHrsReportHandler {

    public void setEmpRegDetails(EmpRegisterDtlEntity empReg, ManPowerActualHrsTO manPowerActualHrsTO,
            ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy) {
        if (empReg != null) {
            manPowerActualHrsTO.setUserId(empReg.getId());
            manPowerActualHrsTO.setEmpCode(empReg.getCode());
            manPowerActualHrsTO.setEmpFirstname(empReg.getFirstName());
            manPowerActualHrsTO.setEmpLastname(empReg.getLastName());
            CompanyMstrEntity company = empReg.getCompanyMstrEntity();
            if (company != null) {
                manPowerActualHrsTO.setCompanyId(company.getId());
                manPowerActualHrsTO.setCompanyName(company.getName());
            }
            EmpClassMstrEntity empClass = empReg.getEmpClassMstrEntity();
            if (empClass != null) {
                if (projEmpClassRepositoryCopy != null) {
                    manPowerActualHrsTO.setEmpCategoryName(projEmpClassRepositoryCopy
                            .getEmpCategoryNameByEmpClassId(empClass.getId(), empReg.getProjMstrEntity().getProjectId()));
                }

                manPowerActualHrsTO.setEmpClassName(empClass.getName());
                manPowerActualHrsTO.setEmpClassId(empClass.getId());
                MeasurmentMstrEntity measure = empClass.getMeasurmentMstrEntity();
                if (measure != null) {
                    manPowerActualHrsTO.setUnitOfMeasure(measure.getCode());
                    manPowerActualHrsTO.setUnitOfMeasureId(measure.getId());
                }
            }

        }
    }

    public void setCrewDetails(ProjCrewMstrEntity crew, ManPowerActualHrsTO manPowerActualHrsTO) {
        if (crew != null) {
            manPowerActualHrsTO.setCrewName(crew.getDesc());
            manPowerActualHrsTO.setCrewId(crew.getId());
        }
    }

    public void setCostDetails(ProjCostItemEntity projCostItemEntity, ManPowerActualHrsTO manPowerActualHrsTO) {
        if (projCostItemEntity != null) {
            manPowerActualHrsTO.setCostCodeId(projCostItemEntity.getId());
            manPowerActualHrsTO.setCostCodeName(projCostItemEntity.getCode());
            manPowerActualHrsTO.setCostCodeDesc(projCostItemEntity.getName());

            ProjCostItemEntity costParent = projCostItemEntity.getProjCostItemEntity();
            if (costParent != null) {
                manPowerActualHrsTO.setParentCostCode(costParent.getCode());
                manPowerActualHrsTO.setParentCostCodeName(costParent.getName());
            }
        }
    }

    public void setProjDetails(ProjMstrEntity project, ManPowerActualHrsTO manPowerActualHrsTO) {
        if (project != null) {
            manPowerActualHrsTO.setProjId(project.getProjectId());
            manPowerActualHrsTO.setProjName(project.getProjName());
            ProjMstrEntity parent = project.getParentProjectMstrEntity();
            if (parent != null) {
                manPowerActualHrsTO.setParentProjId(parent.getProjectId());
                manPowerActualHrsTO.setParentProjName(parent.getProjName());
            }
        }
    }

    public void setWageDetails(EmpWageMstrEntity wage, ManPowerActualHrsTO manPowerActualHrsTO) {
        if (wage != null) {
            manPowerActualHrsTO.setWageId(wage.getId());
            manPowerActualHrsTO.setWageCode(wage.getCode());
            manPowerActualHrsTO.setWageFactorValue(wage.getWageFactor());
        }
    }

    public void updateMap(Map<ManPowerActualHrsTO, List<ReportHoursTO>> manPowerHrsMap,
            ManPowerActualHrsTO manPowerActualHrsTO, List<ReportHoursTO> reportHoursTOs) {
        if (manPowerHrsMap.containsKey(manPowerActualHrsTO)) {
            List<ReportHoursTO> existingHrs = manPowerHrsMap.get(manPowerActualHrsTO);
            int itemIndex = -1;
            for (ReportHoursTO newReportHrs : reportHoursTOs) {
                itemIndex = existingHrs.indexOf(newReportHrs);
                if (itemIndex != -1) {
                    existingHrs.get(itemIndex).add(newReportHrs);
                } else {
                    existingHrs.add(newReportHrs);
                }
            }

        } else {
            manPowerHrsMap.put(manPowerActualHrsTO, reportHoursTOs);
        }
    }

    public void getTimesheetHrs(List<TimeSheetEmpWorkDtlEntity> timeSheetWorkHrs,
            Map<ManPowerActualHrsTO, List<ReportHoursTO>> manPowerHrsMap,
            ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy) {

        for (TimeSheetEmpWorkDtlEntity timeSheetWork : timeSheetWorkHrs) {
            TimeSheetEntity timeSheet = timeSheetWork.getTimeSheetEmpDtlEntity().getTimeSheetEntity();
            Date weekStartDate = new Date(timeSheet.getWeekStartDate().getTime());
            Date weekEndDate = timeSheet.getWeekEndDate();
            Date nextDay;
            ManPowerActualHrsTO manPowerActualHrsTO = new ManPowerActualHrsTO();
            this.setCrewDetails(timeSheet.getProjCrewMstrEntity(), manPowerActualHrsTO);
            this.setEmpRegDetails(timeSheetWork.getTimeSheetEmpDtlEntity().getEmpRegisterDtlEntity(),
                    manPowerActualHrsTO, projEmpClassRepositoryCopy);
            this.setCostDetails(timeSheetWork.getProjCostItemEntity(), manPowerActualHrsTO);
            this.setProjDetails(timeSheet.getProjMstrEntity(), manPowerActualHrsTO);
            this.setWageDetails(timeSheetWork.getEmpWageMstrEntity(), manPowerActualHrsTO);

            List<ReportHoursTO> reportHoursTOs = new ArrayList<>();

            reportHoursTOs.add(new ReportHoursTO(weekStartDate, timeSheetWork.getDay1(), null));
            Instant weekStartInstant = weekStartDate.toInstant();
            nextDay = Date.from(weekStartInstant.plus(1, ChronoUnit.DAYS));
            reportHoursTOs.add(new ReportHoursTO(nextDay, timeSheetWork.getDay2(), null));

            nextDay = Date.from(weekStartInstant.plus(2, ChronoUnit.DAYS));
            reportHoursTOs.add(new ReportHoursTO(nextDay, timeSheetWork.getDay3(), null));

            nextDay = Date.from(weekStartInstant.plus(3, ChronoUnit.DAYS));
            reportHoursTOs.add(new ReportHoursTO(nextDay, timeSheetWork.getDay4(), null));

            nextDay = Date.from(weekStartInstant.plus(4, ChronoUnit.DAYS));
            reportHoursTOs.add(new ReportHoursTO(nextDay, timeSheetWork.getDay5(), null));

            nextDay = Date.from(weekStartInstant.plus(5, ChronoUnit.DAYS));
            if (nextDay.before(weekEndDate)) {
                reportHoursTOs.add(new ReportHoursTO(nextDay, timeSheetWork.getDay6(), null));
            }

            nextDay = Date.from(weekStartInstant.plus(6, ChronoUnit.DAYS));
            if (nextDay.before(weekEndDate)) {
                reportHoursTOs.add(new ReportHoursTO(nextDay, timeSheetWork.getDay7(), null));
            }

            this.updateMap(manPowerHrsMap, manPowerActualHrsTO, reportHoursTOs);
        }
    }

    public void getWorkDairyHrs(List<WorkDairyEmpWageEntity> workDairyHrs,
            Map<ManPowerActualHrsTO, List<ReportHoursTO>> manPowerHrsMap,
            ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy,
            EmpCostWorkDairyRepository empCostWorkDairyRepository) {

        for (WorkDairyEmpWageEntity woDairyEmpWageEntity : workDairyHrs) {
            WorkDairyEntity workDairy = woDairyEmpWageEntity.getWorkDairyEmpDtlEntity().getWorkDairyEntity();
            EmpRegisterDtlEntity empReg = woDairyEmpWageEntity.getWorkDairyEmpDtlEntity().getEmpRegId();

            List<WorkDairyEmpCostEntity> empCosts = empCostWorkDairyRepository
                    .getApprovedWorkDairyEmpTime(woDairyEmpWageEntity.getId());
            if (!empCosts.isEmpty()) {
                for (WorkDairyEmpCostEntity empCost : empCosts) {
                    ManPowerActualHrsTO manPowerActualHrsTO = new ManPowerActualHrsTO();
                    this.setEmpRegDetails(empReg, manPowerActualHrsTO, projEmpClassRepositoryCopy);
                    this.setCostDetails(empCost.getCostId(), manPowerActualHrsTO);
                    this.setWageDetails(empCost.getWorkDairyEmpWageEntity().getWageId(), manPowerActualHrsTO);
                    if (workDairy != null) {
                        this.setProjDetails(workDairy.getProjId(), manPowerActualHrsTO);
                        this.setCrewDetails(workDairy.getCrewId(), manPowerActualHrsTO);
                        List<ReportHoursTO> reportHoursTOs = new ArrayList<>();
                        reportHoursTOs.add(new ReportHoursTO(workDairy.getWorkDairyDate(), empCost.getUsedTime(),
                                empCost.getIdleTime()));

                        this.updateMap(manPowerHrsMap, manPowerActualHrsTO, reportHoursTOs);
                    }

                }
            }
        }
    }

    /*
     * Remove time sheet work dates which are not between fromDate and toDate
     */
    public void removeTimeSheetDays(Date fromDate, Date toDate,
            Map<ManPowerActualHrsTO, List<ReportHoursTO>> manPowerHrsMap) {
        // Remove time sheet work dates which are not between fromDate and toDate
        Set<ManPowerActualHrsTO> manPowerKeySet = manPowerHrsMap.keySet();
        for (ManPowerActualHrsTO key : manPowerKeySet) {
            List<ReportHoursTO> hrsList = manPowerHrsMap.get(key);
            Date workDate = null;
            for (Iterator<ReportHoursTO> iterator = hrsList.iterator(); iterator.hasNext();) {
                workDate = iterator.next().getDate();
                if (workDate.before(fromDate) || workDate.after(toDate)) {
                    iterator.remove();
                }
            }
        }
    }

    public void setHrsList(Map<ManPowerActualHrsTO, List<ReportHoursTO>> manPowerHrsMap) {
        for (Entry<ManPowerActualHrsTO, List<ReportHoursTO>> entry : manPowerHrsMap.entrySet()) {
            entry.getKey().setHrsList(entry.getValue());
        }
    }

}
