package com.rjtech.register.service.manpower.reports.handler;

import java.util.Date;
import java.util.List;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjEmpClassMstrEntity;
//import com.rjtech.projectlib.model.ProjEmpClassMstrEntityCopy;
import com.rjtech.projectlib.repository.ProjEmpClassRepositoryCopy;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.manpower.reports.dto.ManPowerGenderStatistics;
import com.rjtech.register.manpower.reports.dto.ManPowerMobilizationStatistics;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;

public class ManpowerReportsHandler {

    public void setEmpRegDetails(EmpRegisterDtlEntity empReg, ManPowerGenderStatistics manPowerGenderStatistics,
            ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy) {

        ProjMstrEntity project = empReg.getProjMstrEntity();
        CompanyMstrEntity company = empReg.getCompanyMstrEntity();
        if (company != null) {
            manPowerGenderStatistics.setCompanyId(company.getId());
            manPowerGenderStatistics.setCompanyName(company.getName());
        }
        EmpClassMstrEntity empClass = empReg.getEmpClassMstrEntity();
        if (empClass != null) {
            manPowerGenderStatistics.setEmpClassName(empClass.getName());
            manPowerGenderStatistics.setEmpClassId(empClass.getId());
            ProjEmpClassMstrEntity projEmpClass = projEmpClassRepositoryCopy
                    .getUserProjEmpClasses(project.getProjectId(), empClass.getId(), StatusCodes.ACTIVE.getValue());

            if (projEmpClass != null) {
                manPowerGenderStatistics.setClassificationPerClient(projEmpClass.getTradeContrName());
                manPowerGenderStatistics.setClassificationPerUnion(projEmpClass.getUnionName());
            }
        }
        if (project != null) {
            manPowerGenderStatistics.setProjId(project.getProjectId());
            manPowerGenderStatistics.setProjName(project.getProjName());
            ProjMstrEntity parent = project.getParentProjectMstrEntity();
            if (parent != null) {
                manPowerGenderStatistics.setParentProjId(parent.getProjectId());
                manPowerGenderStatistics.setParentProjName(parent.getProjName());
            }
        }
    }

    public void getManPowerMobilizationStatistics(EmpRegisterDtlEntity empReg,
            List<ManPowerMobilizationStatistics> mobilizationStatisticsList,
            ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy, EmpProjRegisterRepository empProjRegisterRepository,
            Date fromDate, Date toDate) {

        List<EmpProjRigisterEntity> empProjRegisters = empProjRegisterRepository
                .getProjEmpRegForMobilizationStatistics(empReg.getId(), fromDate, toDate);

        for (EmpProjRigisterEntity empProjReg : empProjRegisters) {

            ManPowerMobilizationStatistics manPowerMobilizationStatistics = new ManPowerMobilizationStatistics();
            this.setEmpRegDetails(empReg, manPowerMobilizationStatistics, projEmpClassRepositoryCopy);
            manPowerMobilizationStatistics.setMobilizationDate(empProjReg.getMobilaizationDate());
            manPowerMobilizationStatistics.setDeMobilizationDate(empProjReg.getDeMobilaizationDate());

            int itemIndex = mobilizationStatisticsList.indexOf(manPowerMobilizationStatistics);
            if (itemIndex != -1) {
                calculateMobilizationCounts(mobilizationStatisticsList.get(itemIndex),
                        manPowerMobilizationStatistics.getMobilizationDate(),
                        manPowerMobilizationStatistics.getDeMobilizationDate(), fromDate, toDate);
            } else {
                calculateMobilizationCounts(manPowerMobilizationStatistics,
                        manPowerMobilizationStatistics.getMobilizationDate(),
                        manPowerMobilizationStatistics.getDeMobilizationDate(), fromDate, toDate);
                mobilizationStatisticsList.add(manPowerMobilizationStatistics);
            }

        }
    }

    private void calculateMobilizationCounts(ManPowerMobilizationStatistics manPowerMobilizationStatistics,
            Date mobilizationDate, Date deMobilizationDate, Date fromDate, Date toDate) {

        if (fromDate.after(mobilizationDate)) {
            manPowerMobilizationStatistics.incrementPrevCount();
        } else if (fromDate.before(mobilizationDate) && toDate.after(mobilizationDate)) {
            manPowerMobilizationStatistics.incrementCurrentMobilCount();
        }
        if(deMobilizationDate != null) {
	        if (fromDate.before(deMobilizationDate) && toDate.after(deMobilizationDate)) {
	            manPowerMobilizationStatistics.incrementCurrentDeMobilCount();
	        }
        }

    }

}
