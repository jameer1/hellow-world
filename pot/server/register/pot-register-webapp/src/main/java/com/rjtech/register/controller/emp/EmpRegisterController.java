package com.rjtech.register.controller.emp;

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

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ProcurementCatg;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.controller.common.RegisterCommonController;
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
import com.rjtech.register.service.emp.EmpRegisterService;
import com.rjtech.register.emp.dto.MasterEmployeeDetailsTO;
import com.rjtech.register.emp.req.ManpowerEmployeeDetailsGetReq;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpRegisterController extends RegisterCommonController {

    @Autowired
    private EmpRegisterService empRegisterService;

    @Autowired
    private EPSProjService epsProjService;

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<EmpRegisterOnLoadResp> saveEmpRegisters(@RequestBody EmpRegisterSaveReq empRegisterSaveReq) {
        EmpRegisterOnLoadResp empRegisterOnLoadResp = new EmpRegisterOnLoadResp();
        empRegisterService.saveEmpRegisters(empRegisterSaveReq);

        EmpRegisterGetReq empRegisterGetReq = new EmpRegisterGetReq();
        populateEmpRegisterOnLoad(empRegisterOnLoadResp, empRegisterGetReq);
        empRegisterOnLoadResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpRegisterOnLoadResp>(empRegisterOnLoadResp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.EMP_REGISTERS_ONLOAD)
    public ResponseEntity<EmpRegisterOnLoadResp> empRegistersOnLoad(@RequestBody EmpRegisterGetReq empRegisterGetReq) {
        EmpRegisterOnLoadResp empRegisterOnLoadResp = new EmpRegisterOnLoadResp();
        populateEmpRegisterOnLoad(empRegisterOnLoadResp, empRegisterGetReq);
        return new ResponseEntity<>(empRegisterOnLoadResp, HttpStatus.OK);
    }

    private void populateEmpRegisterOnLoad(EmpRegisterOnLoadResp empRegisterOnLoadResp,
            EmpRegisterGetReq empRegisterGetReq) {
        EmpRegisterResp empRegisterResp = empRegisterService.getEmpRegisters(empRegisterGetReq);
        empRegisterOnLoadResp.setEmpRegisterDtlTOs(empRegisterResp.getEmpRegisterDtlTOs());
        empRegisterOnLoadResp.setEmpRegisterDropDownTO(empRegisterService.getEmpRegisterDropDown());
        empRegisterOnLoadResp.setRegisterOnLoadTO(getRegisterOnLoadResp(ProcurementCatg.MAN_POWER.getDesc()));
        empRegisterOnLoadResp.setUserProjMap(getUserProjectsMap());
    }

    @RequestMapping(value = RegisterURLConstants.EMP_REGISTERS_DEACTIVATE, method = RequestMethod.POST)
    public ResponseEntity<EmpRegisterOnLoadResp> empRegistersDeactivate(
            @RequestBody EmpRegDeactivateReq empRegDeactivateReq) {

        EmpRegisterOnLoadResp empRegisterOnLoadResp = new EmpRegisterOnLoadResp();

        empRegisterService.empRegistersDeactivate(empRegDeactivateReq);

        EmpRegisterGetReq empRegisterGetReq = new EmpRegisterGetReq();
        empRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        empRegisterGetReq.setProjId(empRegDeactivateReq.getProjId());

        EmpRegisterResp empRegisterResp = empRegisterService.getEmpRegisters(empRegisterGetReq);
        empRegisterOnLoadResp.setEmpRegisterDtlTOs(empRegisterResp.getEmpRegisterDtlTOs());
        empRegisterOnLoadResp.setEmpRegisterDropDownTO(empRegisterService.getEmpRegisterDropDown());

        empRegisterResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpRegisterOnLoadResp>(empRegisterOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_NONATTENDENCE_EMP_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpRegLabelKeyTOResp> getNonAttendenceEmpRegisters(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {

        return new ResponseEntity<ProjEmpRegLabelKeyTOResp>(
                empRegisterService.getNonAttendenceEmpRegisters(projEmpRegisterGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_ATTENDENCE_EMP_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpRegLabelKeyTOMapResp> getAttendenceEmpRegisters(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {

        return new ResponseEntity<ProjEmpRegLabelKeyTOMapResp>(
                empRegisterService.getAttendenceEmpRegisters(projEmpRegisterGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMPS_NOT_IN_USER_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<EmpRegisterOnLoadResp> getEmpsNotInUserProjects(
            @RequestBody EmpRegisterGetReq empRegisterGetReq) {
        EmpRegisterOnLoadResp empRegisterOnLoadResp = new EmpRegisterOnLoadResp();

        List<Long> projIds = epsProjService.getUserProjIds();
        empRegisterGetReq.setProjIds(projIds);
        EmpRegisterResp empRegisterResp = empRegisterService.getEmpsNotInUserProjects(empRegisterGetReq);
        empRegisterOnLoadResp.setEmpRegisterDtlTOs(empRegisterResp.getEmpRegisterDtlTOs());
        return new ResponseEntity<EmpRegisterOnLoadResp>(empRegisterOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MULTI_PROJ_EMP_LIST_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpRegLabelKeyTOMapResp> getMultiProjEmpListMap(
            @RequestBody PlantRegisterGetReq plantRegisterGetReq) {

        return new ResponseEntity<ProjEmpRegLabelKeyTOMapResp>(
                empRegisterService.getMultiProjEmpListMap(plantRegisterGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_ALL_REG_EMP, method = RequestMethod.POST)
    public ResponseEntity<EmpRegResp> getAllRegEmp(@RequestBody EmpRegisterGetReq empRegisterGetReq) {

        return new ResponseEntity<EmpRegResp>(empRegisterService.getAllRegEmp(empRegisterGetReq), HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.IS_EMPCODE_UNIQUE)
    public ResponseEntity<EmpUniqueCodeMapRep> isEmpCodeUnique(@RequestBody EmpRegisterGetReq empRegisterGetReq) {
        return new ResponseEntity<>(empRegisterService.isEmpCodeUnique(empRegisterGetReq), HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.GET_PROJ_EMPLOYEES)
    public ResponseEntity<EmpRegisterResp> getProjEmployees(@RequestParam("projId") Long projId) {
        EmpRegisterResp resp = empRegisterService.getProjEmployees(projId);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.GET_MANPOWER_GENDER_STATISTICS_REPORT)
    public ResponseEntity<List<ManPowerGenderStatistics>> getPlantPeriodicalActualHrsReport(
            @RequestBody ManPowerGenderStatisticsReq manpowerGenderGetReq) {
        return new ResponseEntity<>(empRegisterService.getManpowerGenderStatisticsReport(manpowerGenderGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.GET_MANPOWER_PERIODICAL_MOBILISATION_REPORT)
    public ResponseEntity<List<ManPowerMobilizationStatistics>> getManpowerPeriodicalMobilisationReport(
            @RequestBody ManPowerGenderStatisticsReq manpowerMobilisationGetReq) {
        return new ResponseEntity<>(
                empRegisterService.getManpowerPeriodicalMobilisationReport(manpowerMobilisationGetReq), HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.GET_MANPOWER_EMPLOYEE_DETAILS)
	 public ResponseEntity<List<MasterEmployeeDetailsTO>> getManpowerEmployeeDetail(@RequestBody ManpowerEmployeeDetailsGetReq manpowerEmployeeDetailsGetReq){
	  return new ResponseEntity<>(empRegisterService.getManpowerEmployeeDetail(manpowerEmployeeDetailsGetReq), HttpStatus.OK);
	 }    
}
