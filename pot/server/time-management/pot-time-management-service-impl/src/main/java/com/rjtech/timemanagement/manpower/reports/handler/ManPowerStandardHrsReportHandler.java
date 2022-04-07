package com.rjtech.timemanagement.manpower.reports.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rjtech.projectlib.repository.ProjEmpClassRepositoryCopy;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerActualHrsTO;
import com.rjtech.timemanagement.manpower.reports.dto.ReportHoursTO;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpCostEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpWageEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;
import com.rjtech.timemanagement.workdairy.repository.EmpCostWorkDairyRepository;

public class ManPowerStandardHrsReportHandler {

    public void getWorkDairyHrs(List<WorkDairyEmpWageEntity> workDairyHrs,
            Map<ManPowerActualHrsTO, List<ReportHoursTO>> manPowerHrsMap,
            ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy,
            EmpCostWorkDairyRepository empCostWorkDairyRepository, List<Long> costIds) {

        ManPowerActualHrsReportHandler manPowerActualHrsReportHandler = new ManPowerActualHrsReportHandler();
        for (WorkDairyEmpWageEntity woDairyEmpWageEntity : workDairyHrs) {
            WorkDairyEntity workDairy = woDairyEmpWageEntity.getWorkDairyEmpDtlEntity().getWorkDairyEntity();
            EmpRegisterDtlEntity empReg = woDairyEmpWageEntity.getWorkDairyEmpDtlEntity().getEmpRegId();
            // get only child objects which have given costIds
            List<WorkDairyEmpCostEntity> empCosts = empCostWorkDairyRepository
                    .getApprovedWorkDairyEmpTimeCostByCostId(costIds, woDairyEmpWageEntity.getId());
            if (!empCosts.isEmpty()) {
                for (WorkDairyEmpCostEntity empCost : empCosts) {
                    ManPowerActualHrsTO manPowerActualHrsTO = new ManPowerActualHrsTO();
                    manPowerActualHrsReportHandler.setEmpRegDetails(empReg, manPowerActualHrsTO,
                            projEmpClassRepositoryCopy);
                    manPowerActualHrsReportHandler.setCostDetails(empCost.getCostId(), manPowerActualHrsTO);
                    manPowerActualHrsReportHandler.setWageDetails(empCost.getWorkDairyEmpWageEntity().getWageId(),
                            manPowerActualHrsTO);
                    if (workDairy != null) {
                        manPowerActualHrsReportHandler.setProjDetails(workDairy.getProjId(), manPowerActualHrsTO);
                        manPowerActualHrsReportHandler.setCrewDetails(workDairy.getCrewId(), manPowerActualHrsTO);
                        List<ReportHoursTO> reportHoursTOs = new ArrayList<>();
                        reportHoursTOs.add(new ReportHoursTO(workDairy.getWorkDairyDate(), empCost.getUsedTime(),
                                empCost.getIdleTime()));

                        manPowerActualHrsReportHandler.updateMap(manPowerHrsMap, manPowerActualHrsTO, reportHoursTOs);
                    }

                }
            }
        }
    }
}
