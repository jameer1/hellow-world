package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.CompanyProjectsTO;
import com.rjtech.centrallib.model.CmpCurrentProjsEntity;
import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.req.CompanyProjSaveReq;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.repository.EPSProjRepository;

public class CompanyCurrentProjsHandler {

    public static List<CmpCurrentProjsEntity> convertPOJOToEntity(CompanyProjSaveReq companyProjSaveReq,
            EPSProjRepository epsProjRepository) {
        List<CmpCurrentProjsEntity> cmpCurrentProjsEntitys = new ArrayList<CmpCurrentProjsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        CmpCurrentProjsEntity cmpCurrentProjsEntity = null;
        CompanyMstrEntity companyMstrEntity = null;
        for (CompanyProjectsTO companyProjectsTO : companyProjSaveReq.getCompanyProjectsTOs()) {
            cmpCurrentProjsEntity = new CmpCurrentProjsEntity();
            companyMstrEntity = new CompanyMstrEntity();
            if (CommonUtil.isNonBlankLong(companyProjectsTO.getId())) {
                cmpCurrentProjsEntity.setId(companyProjectsTO.getId());
            }
            companyMstrEntity.setId(companyProjectsTO.getCmpId());
            cmpCurrentProjsEntity.setCompanyMstrEntity(companyMstrEntity);
            cmpCurrentProjsEntity.setProjMstrEntity(epsProjRepository.findOne(companyProjectsTO.getProjId()));

            cmpCurrentProjsEntity.setContractValue(companyProjectsTO.getContractValue());
            cmpCurrentProjsEntity.setStartDate(CommonUtil.convertStringToDate(companyProjectsTO.getStartDate()));
            cmpCurrentProjsEntity.setFinishDate(CommonUtil.convertStringToDate(companyProjectsTO.getFinishDate()));
            cmpCurrentProjsEntity.setPerformance(companyProjectsTO.getPerformance());

            cmpCurrentProjsEntity.setStatus(companyProjectsTO.getStatus());

            cmpCurrentProjsEntitys.add(cmpCurrentProjsEntity);

        }
        return cmpCurrentProjsEntitys;
    }
}
