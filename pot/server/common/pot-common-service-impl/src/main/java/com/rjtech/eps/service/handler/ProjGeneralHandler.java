package com.rjtech.eps.service.handler;

import java.util.Date;


import com.rjtech.centrallib.dto.CompanyTO;
import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.repository.ResourceCurveRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.common.dto.ProjGeneralMstrTO;
import com.rjtech.common.dto.ResourceCurveTO;
import com.rjtech.eps.model.ProjGeneralMstrEntityCopy;

public class ProjGeneralHandler {

    public static ProjGeneralMstrTO convertEntityToPOJO(ProjGeneralMstrEntityCopy entity) {
        ProjGeneralMstrTO projGeneralMstrTO = new ProjGeneralMstrTO();
        projGeneralMstrTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getProjMstrEntity())) {
            projGeneralMstrTO.setProjId(entity.getProjMstrEntity().getProjectId());
        }
       

        projGeneralMstrTO.setCountryName(entity.getCountryName());
        projGeneralMstrTO.setProvisionName(entity.getProvisionName());
        projGeneralMstrTO.setCurrency(entity.getCurrency());
        projGeneralMstrTO.setContractType(entity.getContractType());
        projGeneralMstrTO.setPercentOverCost(entity.getPercentOverCost());
        projGeneralMstrTO.setPrimaveraIntegration(entity.getPrimaveraIntegration());
        System.out.println("Handler entity getPercentOverCost : "+entity.getPercentOverCost());
        System.out.println("Handler projGeneralMstrTO getPercentOverCost :"+projGeneralMstrTO.getPercentOverCost());
        if (CommonUtil.isNotBlankDate(entity.getEffectiveFrom())) {
            projGeneralMstrTO.setEffectiveFrom(CommonUtil.convertDateToString(entity.getEffectiveFrom()));
        }
        if (CommonUtil.isNotBlankDate(entity.getEffectiveFrom())) {
            projGeneralMstrTO.setEffectiveTo(CommonUtil.convertDateToString(entity.getEffectiveTo()));
        }
        projGeneralMstrTO.setIsLatest(entity.getIsLatest());


 
        projGeneralMstrTO.setDefualtHrs(entity.getDefualtHrs());
        projGeneralMstrTO.setMaxHrs(entity.getMaxHrs());
        projGeneralMstrTO.setContractNumber(entity.getContractNumber());

        projGeneralMstrTO.setAddress(entity.getAddress());

        projGeneralMstrTO.setStatus(entity.getStatus());

        if (CommonUtil.objectNotNull(entity.getRespManager())) {
            LabelKeyTO userLabel = projGeneralMstrTO.getUserLabelKeyTO();
            //userLabel.setName(entity.getRespManager().getUserName());
            userLabel.setName(entity.getRespManager().getFirstName() + " " + entity.getRespManager().getLastName());
            userLabel.setId(entity.getRespManager().getUserId());
            projGeneralMstrTO.setUserId(entity.getRespManager().getUserId());
        }

        return projGeneralMstrTO;
    }

    
}