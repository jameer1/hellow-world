package com.rjtech.timemanagement.manpower.reports.handler;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projschedule.model.ResourceAssignmentDataValueEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerPlannedValuesTO;
import com.rjtech.timemanagement.manpower.reports.dto.ReportHoursTO;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpWorkDtlEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpCostEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpWageEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.repository.EmpCostWorkDairyRepository;

public class ManPowerPlannedValuesReportHandler {

    public void getTimesheetActualHrsHrs(List<TimeSheetEmpWorkDtlEntity> timeSheetWorkHrs,
            List<ManPowerPlannedValuesTO> manPowerPlannedValuesTOList) {

        for (TimeSheetEmpWorkDtlEntity timeSheetWork : timeSheetWorkHrs) {

            TimeSheetEntity timeSheet = timeSheetWork.getTimeSheetEmpDtlEntity().getTimeSheetEntity();
            EmpRegisterDtlEntity empReg = timeSheetWork.getTimeSheetEmpDtlEntity().getEmpRegisterDtlEntity();
            Date weekStartDate = new Date(timeSheet.getWeekStartDate().getTime());
            Date weekEndDate = timeSheet.getWeekEndDate();
            Date nextDay;

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

            for (ReportHoursTO reportHoursTO : reportHoursTOs) {
                ManPowerPlannedValuesTO manPowerPlannedValuesTO = new ManPowerPlannedValuesTO();
                this.setProjDetails(timeSheet.getProjMstrEntity(), manPowerPlannedValuesTO);
                manPowerPlannedValuesTO.setDate(reportHoursTO.getDate());
                manPowerPlannedValuesTO.setActualHrs(reportHoursTO.getUsedHrs()+reportHoursTO.getIdleHrs());
                this.setEmpClassDetails(manPowerPlannedValuesTO, empReg);
                this.checkAndUpdateList(manPowerPlannedValuesTOList, manPowerPlannedValuesTO);
            }

        }
    }

    public void getWorkDairyActualHrs(List<WorkDairyEmpWageEntity> workDairyHrs,
            List<ManPowerPlannedValuesTO> manPowerPlannedValuesTOList,
            EmpCostWorkDairyRepository empCostWorkDairyRepository) {

        for (WorkDairyEmpWageEntity woDairyEmpWageEntity : workDairyHrs) {
            WorkDairyEntity workDairy = woDairyEmpWageEntity.getWorkDairyEmpDtlEntity().getWorkDairyEntity();
            List<WorkDairyEmpCostEntity> empCosts = empCostWorkDairyRepository
                    .getApprovedWorkDairyEmpTime(woDairyEmpWageEntity.getId());

            EmpRegisterDtlEntity empReg = woDairyEmpWageEntity.getWorkDairyEmpDtlEntity().getEmpRegId();
            if (!empCosts.isEmpty()) {
                for (WorkDairyEmpCostEntity empCost : empCosts) {
                    if (empCost.getIsLatest()) {
                        ManPowerPlannedValuesTO manPowerPlannedValuesTO = new ManPowerPlannedValuesTO();
                        if (workDairy != null) {
                            this.setProjDetails(workDairy.getProjId(), manPowerPlannedValuesTO);
                            manPowerPlannedValuesTO.setDate(workDairy.getWorkDairyDate());
                            manPowerPlannedValuesTO.setActualHrs(empCost.getUsedTime()+empCost.getIdleTime());
                            this.setEmpClassDetails(manPowerPlannedValuesTO, empReg);
                            this.checkAndUpdateList(manPowerPlannedValuesTOList, manPowerPlannedValuesTO);
                        }

                    }
                }
            }
        }
    }

    public void setProjDetails(ProjMstrEntity project, ManPowerPlannedValuesTO manPowerPlannedValuesTO) {
        if (project != null) {
            manPowerPlannedValuesTO.setProjId(project.getProjectId());
            manPowerPlannedValuesTO.setProjName(project.getProjName());
            ProjMstrEntity parent = project.getParentProjectMstrEntity();
            if (parent != null) {
                manPowerPlannedValuesTO.setParentProjId(parent.getProjectId());
                manPowerPlannedValuesTO.setParentProjName(parent.getProjName());
            }
        }
    }

    public void checkAndUpdateList(List<ManPowerPlannedValuesTO> manPowerPlannedValuesTOList,
            ManPowerPlannedValuesTO manPowerPlannedValuesTO) {
    	ManPowerPlannedValuesTO checkManPowerPlannedValuesTO = manPowerPlannedValuesTOList.stream().filter(
				o -> o.getProjId().equals(manPowerPlannedValuesTO.getProjId())
				&& o.getDate().equals(manPowerPlannedValuesTO.getDate())).findAny().orElse(null);
        if (checkManPowerPlannedValuesTO == null) {
        	manPowerPlannedValuesTOList.add(manPowerPlannedValuesTO);
        } else {
        	checkManPowerPlannedValuesTO.addActualHrs(manPowerPlannedValuesTO.getActualHrs());
        }
    }

    public void setEarnedValues(List<Object[]> earnedValuesList,
            List<ManPowerPlannedValuesTO> manPowerPlannedValuesTOList) {

        for (Object[] earnedValue : earnedValuesList) {
            Date date = (Date) earnedValue[1];
            Long projId = (Long) earnedValue[0];
            Double value = (Double) earnedValue[2];
            manPowerPlannedValuesTOList.stream().filter(o -> o.getDate().equals(date) && o.getProjId().equals(projId))
                    .forEach(o -> o.setEarnedValue(value));
        }

    }

    /*
     * Remove time sheet work dates which are not between fromDate and toDate
     */
    public void removeTimeSheetDays(Date fromDate, Date toDate,
            List<ManPowerPlannedValuesTO> manPowerPlannedValuesTOList) {

        Date workDate;
        // Remove time sheet work dates which are not between fromDate and toDate
        for (Iterator<ManPowerPlannedValuesTO> iterator = manPowerPlannedValuesTOList.iterator(); iterator.hasNext();) {
            workDate = iterator.next().getDate();
            if (workDate.before(fromDate) || workDate.after(toDate)) {
                iterator.remove();
            }
        }
    }

    private void setEmpClassDetails(ManPowerPlannedValuesTO manPowerPlannedValuesTO, EmpRegisterDtlEntity empReg) {
        if (empReg != null) {
            EmpClassMstrEntity empClassEntity = empReg.getEmpClassMstrEntity();
            if (empClassEntity != null) {
                manPowerPlannedValuesTO.setEmpClassId(empClassEntity.getId());
                manPowerPlannedValuesTO.setEmpClassName(empClassEntity.getName());
            }
        }
    }
    
    public void addPlanValues(List<ResourceAssignmentDataValueEntity> resourceAssignmentDataValueEntities, List<ManPowerPlannedValuesTO> manPowerPlannedValuesTOList) {
    	for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : resourceAssignmentDataValueEntities) {
    		if (!resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType().equals("POT_EMPLOYEE")) continue;
    		ManPowerPlannedValuesTO manPowerPlannedValuesTO = manPowerPlannedValuesTOList.stream().filter(
    				o -> o.getProjId().equals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId())
    				&& o.getDate().equals(resourceAssignmentDataValueEntity.getForecastDate())).findAny().orElse(null);
    		if (manPowerPlannedValuesTO == null) {
    			ManPowerPlannedValuesTO newManPowerPlannedValuesTO = new ManPowerPlannedValuesTO();
    			newManPowerPlannedValuesTO.setProjId(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId());
    			newManPowerPlannedValuesTO.setProjName(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjName());
                ProjMstrEntity parent = resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getParentProjectMstrEntity();
                if (parent != null) {
                	newManPowerPlannedValuesTO.setParentProjId(parent.getProjectId());
                	newManPowerPlannedValuesTO.setParentProjName(parent.getProjName());
                }
                newManPowerPlannedValuesTO.setDate(resourceAssignmentDataValueEntity.getForecastDate());
                newManPowerPlannedValuesTO.setActualHrs(0);
                newManPowerPlannedValuesTO.setPlannedValue(resourceAssignmentDataValueEntity.getBudgetUnits());
                newManPowerPlannedValuesTO.setEmpClassId(null);
                newManPowerPlannedValuesTO.setEmpClassName(null);
                manPowerPlannedValuesTOList.add(newManPowerPlannedValuesTO);
    		} else {
    			manPowerPlannedValuesTO.setPlannedValue(manPowerPlannedValuesTO.getPlannedValue() + resourceAssignmentDataValueEntity.getBudgetUnits());
    		}
    	}
    }
}
