package com.rjtech.mw.controller.register.emp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.utils.ProcurementCatg;
import com.rjtech.mw.controller.central.handler.ProcurementCategoryHandler;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpRegDeactivateReq;
import com.rjtech.register.emp.req.EmpRegisterGetReq;
import com.rjtech.register.emp.req.EmpRegisterSaveReq;
import com.rjtech.register.emp.req.ManPowerGenderStatisticsReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpRegResp;
import com.rjtech.register.emp.resp.EmpRegisterOnLoadResp;
import com.rjtech.register.emp.resp.EmpRegisterResp;
import com.rjtech.register.emp.resp.EmpUniqueCodeMapRep;
import com.rjtech.register.emp.resp.ProjEmpRegLabelKeyTOMapResp;
import com.rjtech.register.emp.resp.ProjEmpRegLabelKeyTOResp;
import com.rjtech.register.manpower.reports.dto.ManPowerGenderStatistics;
import com.rjtech.register.manpower.reports.dto.ManPowerMobilizationStatistics;
import com.rjtech.register.plant.req.PlantRegisterGetReq;
import com.rjtech.register.emp.dto.MasterEmployeeDetailsTO;
import com.rjtech.register.emp.req.ManpowerEmployeeDetailsGetReq;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpRegisterController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @Autowired
    MWProjLibService mwProjLibService;

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<EmpRegisterOnLoadResp> saveEmpRegisters(@RequestBody EmpRegisterSaveReq empRegisterSaveReq) {
        EmpRegisterOnLoadResp resp = mwRegisterService.saveEmpRegisters(empRegisterSaveReq);
        return new ResponseEntity<EmpRegisterOnLoadResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.EMP_REGISTERS_DEACTIVATE, method = RequestMethod.POST)
    public ResponseEntity<EmpRegisterResp> empRegistersDeactivate(
            @RequestBody EmpRegDeactivateReq empRegDeactivateReq) {

        return new ResponseEntity<EmpRegisterResp>(mwRegisterService.empRegistersDeactivate(empRegDeactivateReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.EMP_REGISTERS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<EmpRegisterOnLoadResp> empRegistersOnLoad(@RequestBody EmpRegisterGetReq empRegisterGetReq) {
        EmpRegisterOnLoadResp resp = mwRegisterService.empRegistersOnLoad(empRegisterGetReq);
        resp.setRegisterOnLoadTO(mwCentralLiblService
                .getRegisterOnLoadCmpCatgProCatgClass(
                        ProcurementCategoryHandler.getRegisterOnLoadReq(ProcurementCatg.MAN_POWER))
                .getRegisterOnLoadTO());
        return new ResponseEntity<EmpRegisterOnLoadResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_NONATTENDENCE_EMP_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpRegLabelKeyTOResp> findNonAttendenceEmpRegisters(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {

        return new ResponseEntity<ProjEmpRegLabelKeyTOResp>(
                mwRegisterService.findNonAttendenceEmpRegisters(projEmpRegisterGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_ATTENDENCE_EMP_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpRegLabelKeyTOMapResp> getAttendenceEmpRegisters(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {

        return new ResponseEntity<ProjEmpRegLabelKeyTOMapResp>(
                mwRegisterService.getAttendenceEmpRegisters(projEmpRegisterGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMPS_NOT_IN_USER_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<EmpRegisterOnLoadResp> getEmpsNotInUserProjects(
            @RequestBody EmpRegisterGetReq empRegisterGetReq) {

        return new ResponseEntity<EmpRegisterOnLoadResp>(mwRegisterService.getEmpsNotInUserProjects(empRegisterGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_ALL_REG_EMP, method = RequestMethod.POST)
    public ResponseEntity<EmpRegResp> getAllRegEmp(@RequestBody EmpRegisterGetReq empRegisterGetReq) {

        return new ResponseEntity<EmpRegResp>(mwRegisterService.getAllRegEmp(empRegisterGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MULTI_PROJ_EMP_LIST_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpRegLabelKeyTOMapResp> getMultiProjEmpListMap(
            @RequestBody PlantRegisterGetReq plantRegisterGetReq) {

        return new ResponseEntity<ProjEmpRegLabelKeyTOMapResp>(
                mwRegisterService.getMultiProjEmpListMap(plantRegisterGetReq), HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.IS_EMPCODE_UNIQUE)
    public ResponseEntity<EmpUniqueCodeMapRep> isEmpCodeUnique(@RequestBody EmpRegisterGetReq empRegisterGetReq) {
        return new ResponseEntity<>(mwRegisterService.isEmpCodeUnique(empRegisterGetReq), HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.GET_PROJ_EMPLOYEES)
    public ResponseEntity<EmpRegisterResp> getProjEmployees(@RequestParam("projId") Long projId) {
        return new ResponseEntity<>(mwRegisterService.getProjEmployees(projId), HttpStatus.OK);
    }
    
    @PostMapping(value = RegisterURLConstants.GET_MANPOWER_GENDER_STATISTICS_REPORT)
    public ResponseEntity<List<ManPowerGenderStatistics>> getPlantPeriodicalActualHrsReport(
            @RequestBody ManPowerGenderStatisticsReq manpowerGenderGetReq) {
        return new ResponseEntity<>(mwRegisterService.getManpowerGenderStatisticsReport(manpowerGenderGetReq),
                HttpStatus.OK);
    }
    
    @PostMapping(value = RegisterURLConstants.GET_MANPOWER_PERIODICAL_MOBILISATION_REPORT)
    public ResponseEntity<List<ManPowerMobilizationStatistics>> getManpowerPeriodicalMobilisationReport(
            @RequestBody ManPowerGenderStatisticsReq manpowerMobilisationGetReq) {
        return new ResponseEntity<>(mwRegisterService.getManpowerPeriodicalMobilisationReport(manpowerMobilisationGetReq), HttpStatus.OK);
    }    
    @PostMapping(value = RegisterURLConstants.GET_MANPOWER_EMPLOYEE_DETAILS)
    public ResponseEntity<List<MasterEmployeeDetailsTO>> getManpowerEmployeeDetail(
    		@RequestBody ManpowerEmployeeDetailsGetReq manpowerEmployeeDetailsGetReq){    	
    	return new ResponseEntity<>(mwRegisterService.getManpowerEmployeeDetail(manpowerEmployeeDetailsGetReq), HttpStatus.OK);
    }

}
