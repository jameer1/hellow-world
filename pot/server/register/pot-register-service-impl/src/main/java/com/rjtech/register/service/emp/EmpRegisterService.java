package com.rjtech.register.service.emp;

import java.util.List;

import com.rjtech.register.emp.dto.EmpRegisterDropDownTO;
import com.rjtech.register.emp.req.EmpRegDeactivateReq;
import com.rjtech.register.emp.req.EmpRegisterGetReq;
import com.rjtech.register.emp.req.EmpRegisterSaveReq;
import com.rjtech.register.emp.req.ManPowerGenderStatisticsReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpRegResp;
import com.rjtech.register.emp.resp.EmpRegisterResp;
import com.rjtech.register.emp.resp.EmpUniqueCodeMapRep;
import com.rjtech.register.emp.resp.ProjEmpRegLabelKeyTOMapResp;
import com.rjtech.register.emp.resp.ProjEmpRegLabelKeyTOResp;
import com.rjtech.register.manpower.reports.dto.ManPowerGenderStatistics;
import com.rjtech.register.manpower.reports.dto.ManPowerMobilizationStatistics;
import com.rjtech.register.plant.req.PlantRegisterGetReq;
import com.rjtech.register.emp.dto.MasterEmployeeDetailsTO;
import com.rjtech.register.emp.req.ManpowerEmployeeDetailsGetReq; 

public interface EmpRegisterService {

    EmpRegisterResp getEmpRegisters(EmpRegisterGetReq empRegisterGetReq);

    void saveEmpRegisters(EmpRegisterSaveReq empRegisterSaveReq);

    void empRegistersDeactivate(EmpRegDeactivateReq empRegDeactivateReq);

    ProjEmpRegLabelKeyTOResp getNonAttendenceEmpRegisters(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    List<String> getGender();

    List<String> getLocality();

    List<String> getEmployeeType();

    List<String> getEmployeeServiceType();

    ProjEmpRegLabelKeyTOMapResp getAttendenceEmpRegisters(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpRegisterDropDownTO getEmpRegisterDropDown();

    EmpRegisterResp getEmpsNotInUserProjects(EmpRegisterGetReq empRegisterGetReq);

    ProjEmpRegLabelKeyTOMapResp getMultiProjEmpListMap(PlantRegisterGetReq plantRegisterGetReq);

    EmpRegResp getAllRegEmp(EmpRegisterGetReq empRegisterGetReq);

    EmpUniqueCodeMapRep isEmpCodeUnique(EmpRegisterGetReq empRegisterGetReq);

    EmpRegisterResp getProjEmployees(Long projId);

    List<ManPowerGenderStatistics> getManpowerGenderStatisticsReport(
            ManPowerGenderStatisticsReq manPowerGenderStatisticsReq);

    List<ManPowerMobilizationStatistics> getManpowerPeriodicalMobilisationReport(
            ManPowerGenderStatisticsReq manpowerMobilisationGetReq);

    List<MasterEmployeeDetailsTO> getManpowerEmployeeDetail(ManpowerEmployeeDetailsGetReq manpowerEmployeeDetailsGetReq);    
}
