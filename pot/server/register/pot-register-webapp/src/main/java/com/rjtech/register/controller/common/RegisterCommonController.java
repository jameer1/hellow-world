package com.rjtech.register.controller.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.rjtech.centrallib.dto.RegisterOnLoadTO;
import com.rjtech.centrallib.req.RegisterOnLoadReq;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.service.CentralLibService;
import com.rjtech.common.utils.ProcurementCatg;
import com.rjtech.projectlib.service.EPSProjService;

public abstract class RegisterCommonController {
    @Autowired
    private EPSProjService epsProjService;

    @Autowired
    private CentralLibService centralLibService;

    protected RegisterOnLoadTO getRegisterOnLoadResp(String procureCatg) {
        RegisterOnLoadTO resp = new RegisterOnLoadTO();

        if (ProcurementCatg.MAN_POWER.getDesc().equalsIgnoreCase(procureCatg)) {
            RegisterOnLoadReq req = new RegisterOnLoadReq();
            req.setProcureCategoryDbConstant(ProcurementCatg.MAN_POWER.getDbConstValue());
            resp.setProcureCatgMap(centralLibService.getProcureCatgMap(req));
            return resp;
        } else if (ProcurementCatg.PLANT.getDesc().equalsIgnoreCase(procureCatg)) {
            resp.setClassificationMap(centralLibService.getPlantClassMap());
        } else if (ProcurementCatg.MATERIAL.getDesc().equalsIgnoreCase(procureCatg)) {
            resp.setClassificationMap(centralLibService.getMaterialClassMap());
        }

        resp.setCompanyMap(centralLibService.getCompanyMap());
        resp.setEmpClassificationMap(centralLibService.getEmpClassMap());

        return resp;
    }

    protected List<Long> getUserProjectsList() {
        return epsProjService.getUserProjIds();
    }

    protected Map<Long, LabelKeyTO> getUserProjectsMap() {
        return epsProjService.getUserProjects();
    }

    protected RegisterOnLoadTO getRegisterOnLoadServiceHistoryResp(String procureCatg) {
        RegisterOnLoadTO resp = new RegisterOnLoadTO();
        if (ProcurementCatg.MAN_POWER.getDesc().equalsIgnoreCase(procureCatg)) {
            resp.setClassificationMap(centralLibService.getEmpClassMap());
        } else if (ProcurementCatg.PLANT.getDesc().equalsIgnoreCase(procureCatg)) {
            resp.setClassificationMap(centralLibService.getPlantClassMap());
        } else if (ProcurementCatg.MATERIAL.getDesc().equalsIgnoreCase(procureCatg)) {
            resp.setClassificationMap(centralLibService.getMaterialClassMap());
        }
        return resp;
    }
}
