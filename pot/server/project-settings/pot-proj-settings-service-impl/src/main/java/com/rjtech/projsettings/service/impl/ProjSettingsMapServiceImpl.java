package com.rjtech.projsettings.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
import com.rjtech.projsettings.model.ProjManpowerEntity;
import com.rjtech.projsettings.repository.ProjGeneralRepository;
import com.rjtech.projsettings.repository.ProjManpowerRepository;
import com.rjtech.projsettings.req.ProjGeneralOnLoadReq;
import com.rjtech.projsettings.resp.ProjGeneralMapResp;
import com.rjtech.projsettings.service.ProjSettingsMapService;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "projSettingsMapService")
@RJSService(modulecode = "projSettingsMapService")
@Transactional
public class ProjSettingsMapServiceImpl implements ProjSettingsMapService {

    @Autowired
    private ProjGeneralRepository projGeneralRepository;

    @Autowired
    private ProjManpowerRepository projManpowerRepository;

    public ProjGeneralMapResp ProjGeneralMap(ProjGeneralOnLoadReq projGeneralOnLoadReq) {
        ProjGeneralMapResp projGeneralMapResp = new ProjGeneralMapResp();
        List<ProjGeneralMstrEntity> projGeneralMstrEntities = projGeneralRepository
                .findAllProjGenerals(projGeneralOnLoadReq.getProjId(), projGeneralOnLoadReq.getStatus());

        String contractNumber = null;
        if (CommonUtil.isListHasData(projGeneralMstrEntities)) {
            for (ProjGeneralMstrEntity projGeneralMstrEntity : projGeneralMstrEntities) {
                if (CommonUtil.isNotBlankStr(projGeneralMstrEntity.getContractNumber())) {
                    contractNumber = projGeneralMstrEntity.getContractNumber();
                    projGeneralMapResp.getProjSettingsUniqueMap().put(contractNumber.toUpperCase().trim(),
                            projGeneralMstrEntity.getStatus());
                }
            }
        }
        return projGeneralMapResp;

    }

    public ProjGeneralMapResp getProjManpowerMap(ProjGeneralOnLoadReq projGeneralOnLoadReq) {
        ProjGeneralMapResp projManpowerMapResp = new ProjGeneralMapResp();

        List<ProjManpowerEntity> projManpowerEntities = projManpowerRepository
                .findManpowersByProject(projGeneralOnLoadReq.getProjId(), projGeneralOnLoadReq.getStatus());

        Long empId = null;
        String empCatgId = null;
        if (CommonUtil.isListHasData(projManpowerEntities)) {
            for (ProjManpowerEntity projManpowerEntity : projManpowerEntities) {
                if (CommonUtil.objectNotNull(projManpowerEntity.getEmpClassMstrEntity())) {
                    if (CommonUtil.objectNotNull(projManpowerEntity.getProjEmpCategory())) {
                        empId = projManpowerEntity.getEmpClassMstrEntity().getId();
                        empCatgId = projManpowerEntity.getProjEmpCategory();
                        projManpowerMapResp.getProjSettingsUniqueMap().put(empId + " " + empCatgId,
                                projManpowerEntity.getStatus());
                    }
                }
            }
        }
        return projManpowerMapResp;
    }
}
