package com.rjtech.register.service.impl.emp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.register.emp.model.EmpNokEntity;
import com.rjtech.register.emp.req.EmpNokDeactiveReq;
import com.rjtech.register.emp.req.EmpNokSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpNokResp;
import com.rjtech.register.repository.emp.EmpNokRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpNokService;
import com.rjtech.register.service.handler.emp.EmpNokHandler;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "empNokService")
@RJSService(modulecode = "empNokService")
@Transactional
public class EmpNokServiceImpl implements EmpNokService {

    @Autowired
    private EmpNokRepository empNokRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    public EmpNokResp getEmpNok(ProjEmpRegisterGetReq ProjEmpRegisterGetReq) {
        EmpNokResp empNokResp = new EmpNokResp();
        List<EmpNokEntity> empNokEntites = empNokRepository.findEmpNok(ProjEmpRegisterGetReq.getEmpId(),
                ProjEmpRegisterGetReq.getStatus());
        for (EmpNokEntity empNokEntity : empNokEntites) {
            empNokResp.getEmpNokTOs().add(EmpNokHandler.convertEntityToPOJO(empNokEntity));
        }
        return empNokResp;
    }

    public void saveEmpNok(EmpNokSaveReq empNokSaveReq) {
        List<EmpNokEntity> entities = EmpNokHandler.convertPOJOsToEntitys(empNokSaveReq.getEmpNokTOs(),
                empRegisterRepository);
        empNokRepository.save(entities);
    }

    public void deactivateEmpNok(EmpNokDeactiveReq empNokDeactiveReq) {
        empNokRepository.deactivateEmpNok(empNokDeactiveReq.getEmpNokIds(), empNokDeactiveReq.getStatus());

    }
}
