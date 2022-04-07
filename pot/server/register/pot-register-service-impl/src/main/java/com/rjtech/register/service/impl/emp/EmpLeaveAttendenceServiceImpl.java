package com.rjtech.register.service.impl.emp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.emp.dto.EmpLeaveAttendanceDtlTO;
import com.rjtech.register.emp.dto.EmpLeaveAttendanceYearTO;
import com.rjtech.register.emp.model.EmpLeaveAttendanceDtlEntity;
import com.rjtech.register.emp.model.EmpLeaveAttendanceYearEntity;
import com.rjtech.register.emp.req.EmpLeaveAttendanceSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpLeaveAttendanceYearResp;
import com.rjtech.register.repository.emp.EmpLeaveAttendanceRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpLeaveAttendenceService;
import com.rjtech.register.service.handler.emp.EmpLeaveAttendanceDtlHandler;
import com.rjtech.register.service.handler.emp.EmpLeaveAttendanceYearHandler;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceDtlRepositoryCopy;

@Service(value = "empLeaveAttendenceService")
@RJSService(modulecode = "empLeaveAttendenceService")
@Transactional
public class EmpLeaveAttendenceServiceImpl implements EmpLeaveAttendenceService {

    @Autowired
    private EmpLeaveAttendanceRepository empLeaveAttendanceRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private EmpAttendanceDtlRepositoryCopy empAttendanceDtlRepository;

    public EmpLeaveAttendanceYearResp getEmpLeaveAttendanceDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpLeaveAttendanceYearResp empLeaveAttendanceResp = new EmpLeaveAttendanceYearResp();
        long empId = projEmpRegisterGetReq.getEmpId();

        List<EmpLeaveAttendanceYearEntity> empLeaveAttendanceYearEntities = empLeaveAttendanceRepository
                .findEmpLeaveAttenanceDetails(empId, projEmpRegisterGetReq.getStatus());

        // Calculate preceding two years
        List<Integer> years = CommonUtil.getPrecedingYears(2);
        List<Object[]> empAttds = empAttendanceDtlRepository.getEmpLeaveAttendanceYearWise(empId, years);

        Map<String, Map<String, LabelKeyTO>> empYearWiseLeaveCountMap = empLeaveAttendanceResp
                .getEmpYearWiseLeaveCountMap();

        for (Object[] empAttd : empAttds) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            String year = String.valueOf(empAttd[0]);

            labelKeyTO.getDisplayNamesMap().put(RegisterConstants.YEAR, year);
            labelKeyTO.setCode(String.valueOf(empAttd[1]));
            labelKeyTO.getDisplayNamesMap().put(RegisterConstants.TOTAL, String.valueOf(empAttd[2]));

            if (empYearWiseLeaveCountMap.get(year) != null) {
                empYearWiseLeaveCountMap.get(year).put(labelKeyTO.getCode(), labelKeyTO);
            } else {
                Map<String, LabelKeyTO> leaveCountMap = new HashMap<>();
                leaveCountMap.put(labelKeyTO.getCode(), labelKeyTO);
                empYearWiseLeaveCountMap.put(year, leaveCountMap);
            }
        }

        for (EmpLeaveAttendanceYearEntity empLeaveAttendanceYearEntity : empLeaveAttendanceYearEntities) {
            EmpLeaveAttendanceYearTO empLeaveAttendanceYearTO = EmpLeaveAttendanceYearHandler.convertEntityToPOJO(empLeaveAttendanceYearEntity);
            for (EmpLeaveAttendanceDtlEntity empLeaveAttendanceDtlEntity : empLeaveAttendanceYearEntity
                    .getEmpLeaveAttendanceDtlEntities()) {
                EmpLeaveAttendanceDtlTO empLeaveAttendanceDtlTO = EmpLeaveAttendanceDtlHandler
                        .convertEntityToPOJO(empLeaveAttendanceDtlEntity);
                empLeaveAttendanceYearTO.getEmpLeaveAccuredTOs().add(empLeaveAttendanceDtlTO);
            }
            String year = empLeaveAttendanceYearEntity.getYear();
            if (empYearWiseLeaveCountMap.get(year) != null) {
                empLeaveAttendanceYearTO.setEmpLeaveAttendanceMap(empYearWiseLeaveCountMap.get(year));
            }
            empLeaveAttendanceResp.getEmpLeaveAttendanceYearTOs().add(empLeaveAttendanceYearTO);
        }
        return empLeaveAttendanceResp;
    }

    public void saveEmpLeaveAttendanceDetails(EmpLeaveAttendanceSaveReq empLeaveAttendanceSaveReq) {
        List<EmpLeaveAttendanceYearEntity> empLeaveAttendanceYearEntities = new ArrayList<>();
        for (EmpLeaveAttendanceYearTO empLeaveAttendanceTO : empLeaveAttendanceSaveReq.getEmpLeaveAttendanceYearTOs()) {
            EmpLeaveAttendanceYearEntity empLeaveAttendanceYearEntity = EmpLeaveAttendanceYearHandler
                    .convertPOJOToEntity(empLeaveAttendanceTO, empRegisterRepository);
            for (EmpLeaveAttendanceDtlTO empLeaveAttendanceDtlTO : empLeaveAttendanceTO.getEmpLeaveAccuredTOs()) {
                EmpLeaveAttendanceDtlEntity empLeaveAttendanceDtlEntity = EmpLeaveAttendanceDtlHandler
                        .convertPOJOToEntity(empLeaveAttendanceDtlTO);
                empLeaveAttendanceDtlEntity.setEmpLeaveAttendanceYearEntity(empLeaveAttendanceYearEntity);
                empLeaveAttendanceYearEntity.getEmpLeaveAttendanceDtlEntities().add(empLeaveAttendanceDtlEntity);
            }
            empLeaveAttendanceYearEntities.add(empLeaveAttendanceYearEntity);
        }
        empLeaveAttendanceRepository.save(empLeaveAttendanceYearEntities);

    }

}
