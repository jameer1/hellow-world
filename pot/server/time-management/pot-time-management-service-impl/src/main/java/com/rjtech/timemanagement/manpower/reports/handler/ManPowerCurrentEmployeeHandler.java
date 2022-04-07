package com.rjtech.timemanagement.manpower.reports.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.StatusCodes;
import com.rjtech.projectlib.repository.ProjEmpClassRepositoryCopy;
import com.rjtech.register.emp.model.EmpChargeOutRateEntity;
import com.rjtech.register.emp.model.EmpContactDtlEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpChargeOutRateRepositoryCopy;
import com.rjtech.register.repository.emp.EmpContactDtlRepositoryCopy;
import com.rjtech.register.utils.RegisterCommonUtils;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceEntity;
import com.rjtech.timemanagement.attendance.model.ProjEmpClassMstrEntityCopy;
import com.rjtech.timemanagement.manpower.reports.dto.CurrentEmployeeDetails;
//import com.rjtech.timemanagement.register.emp.model.EmpChargeOutRateEntityCopy;
//import com.rjtech.timemanagement.register.emp.model.EmpContactDtlEntityCopy;
//import com.rjtech.timemanagement.register.emp.model.EmpProjRigisterEntityCopy;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;

public class ManPowerCurrentEmployeeHandler {

    public List<CurrentEmployeeDetails> getCurrentEmployeeList(List<EmpAttendanceEntity> empAttendanceList,
            ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy,
            EmpChargeOutRateRepositoryCopy empChargeOutRateRepositoryCopy,
            EmpContactDtlRepositoryCopy empContactDtlRepositoryCopy) {

        List<CurrentEmployeeDetails> currentEmpList = new ArrayList<>();
        ManPowerActualHrsReportHandler manPowerReportHandler = new ManPowerActualHrsReportHandler();
        for (EmpAttendanceEntity empAtt : empAttendanceList) {
            CurrentEmployeeDetails currentEmployeeDetails = new CurrentEmployeeDetails();
            EmpRegisterDtlEntity empReg = empAtt.getEmpId();
            if (empReg != null) {
                manPowerReportHandler.setEmpRegDetails(empReg, currentEmployeeDetails, projEmpClassRepositoryCopy);
                manPowerReportHandler.setProjDetails(empReg.getProjMstrEntity(), currentEmployeeDetails);
                manPowerReportHandler.setCrewDetails(empAtt.getEmpAttendanceMstrEntity().getCrewId(),
                        currentEmployeeDetails);

                List<EmpProjRigisterEntity> projRegisters = empReg.getProjEmpRigisterEntities();
                for (EmpProjRigisterEntity projReg : projRegisters) {
                    if (projReg.getIsLatest().equals(RegisterCommonUtils.IS_LATEST_Y)) {
                        currentEmployeeDetails.setMobilizationDate(projReg.getMobilaizationDate());
                        currentEmployeeDetails.setDeMobilizationDate(projReg.getExpectedDemobilaizationDate());
                    }
                }
                // Check if employee is already exists
                if (currentEmpList.contains(currentEmployeeDetails)) {
                    // if employee exists then continue
                    continue;
                }

                List<EmpChargeOutRateEntity> chargeRates = empChargeOutRateRepositoryCopy
                        .findEmpChargeOutRates(empReg.getId());
                if (!chargeRates.isEmpty()) {
                    currentEmployeeDetails.setNormalRate(chargeRates.get(0).getNormalRate().doubleValue());
                }

                ProjEmpClassMstrEntityCopy projEmpClass = projEmpClassRepositoryCopy.getUserProjEmpClasses(
                        currentEmployeeDetails.getProjId(), currentEmployeeDetails.getEmpClassId(),
                        StatusCodes.ACTIVE.getValue());
                if (projEmpClass != null) {
                    currentEmployeeDetails.setClassificationPerUnion(projEmpClass.getUnionName());
                }
                EmpContactDtlEntity empContact = empContactDtlRepositoryCopy.findEmpContacts(empReg.getId());
                if (empContact != null) {
                    currentEmployeeDetails.setEmailId(empContact.getEmail());
                    currentEmployeeDetails.setPhoneNum(empContact.getPhoneNumber());
                }
            }
            currentEmpList.add(currentEmployeeDetails);
        }
        return currentEmpList;
    }

}
