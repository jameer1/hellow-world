package com.rjtech.projsettings.service.handler;

import java.util.Date;

import com.rjtech.calendar.dto.CalTO;
import com.rjtech.calendar.model.GlobalCalEntity;
import com.rjtech.calendar.repository.GlobalCalendarRepository;
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
import com.rjtech.finance.dto.ProfitCentreTO;
import com.rjtech.finance.model.ProfitCentreEntity;
import com.rjtech.finance.repository.ProfitCentreRepositoryCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjGeneralMstrTO;
import com.rjtech.projsettings.dto.ResourceCurveTO;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;

public class ProjGeneralHandler {

    public static ProjGeneralMstrTO convertEntityToPOJO(ProjGeneralMstrEntity entity) {
        ProjGeneralMstrTO projGeneralMstrTO = new ProjGeneralMstrTO();
        projGeneralMstrTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getProjMstrEntity())) {
            projGeneralMstrTO.setProjId(entity.getProjMstrEntity().getProjectId());
        }
        if (CommonUtil.objectNotNull(entity.getCompanyMstrEntity()))
            projGeneralMstrTO.setCompanyId(entity.getCompanyMstrEntity().getId());

        projGeneralMstrTO.setIsoAlpha3(entity.getIsoAlpha3());
        projGeneralMstrTO.setGeonameId(entity.getGeonameId());
        projGeneralMstrTO.setCountryName(entity.getCountryName());
        projGeneralMstrTO.setProvisionName(entity.getProvisionName());
        projGeneralMstrTO.setCurrency(entity.getCurrency());
        projGeneralMstrTO.setTimezone(entity.getTimezone());
        projGeneralMstrTO.setContractType(entity.getContractType());
        projGeneralMstrTO.setPercentOverCost(entity.getPercentOverCost());
        projGeneralMstrTO.setPrimaveraIntegration(entity.getPrimaveraIntegration());
        projGeneralMstrTO.setEarnedHourSource(entity.getEarnedHourSource());
        projGeneralMstrTO.setFinanceCentre(entity.getFinanceCentre());
        if (CommonUtil.isNotBlankDate(entity.getEffectiveFrom())) {
            projGeneralMstrTO.setEffectiveFrom(CommonUtil.convertDateToString(entity.getEffectiveFrom()));
        }
        if (CommonUtil.isNotBlankDate(entity.getEffectiveFrom())) {
            projGeneralMstrTO.setEffectiveTo(CommonUtil.convertDateToString(entity.getEffectiveTo()));
        }
        projGeneralMstrTO.setIsLatest(entity.getIsLatest());

        CalTO calenderTO = new CalTO();
        if (CommonUtil.objectNotNull(entity.getGlobalCalEntity())) {
            calenderTO.setId(entity.getGlobalCalEntity().getId());
            calenderTO.setClientId(entity.getGlobalCalEntity().getClientId().getClientId());
        }
        projGeneralMstrTO.setCalenderTO(calenderTO);

        ProfitCentreTO profitCentreTO = new ProfitCentreTO();
        if (CommonUtil.objectNotNull(entity.getProfitCentreEntity())) {
            profitCentreTO.setId(entity.getProfitCentreEntity().getId());
            profitCentreTO.setCode(entity.getProfitCentreEntity().getCode());
            profitCentreTO.setName(entity.getProfitCentreEntity().getName());
            profitCentreTO.setClientId(entity.getProfitCentreEntity().getClientId().getClientId());
        }
        projGeneralMstrTO.setProfitCentreTO(profitCentreTO);

        if (CommonUtil.objectNotNull(entity.getGlobalCalEntity())) {
            ClientRegEntity clientRegEntity = entity.getGlobalCalEntity().getClientId();
            calenderTO.setId(entity.getGlobalCalEntity().getId());
            calenderTO.setCode(entity.getGlobalCalEntity().getName());
            if (null != clientRegEntity) {
                calenderTO.setClientId(clientRegEntity.getClientId());
            }
            calenderTO.setProjId(entity.getProjMstrEntity().getProjectId());
        }
        projGeneralMstrTO.setCalenderTO(calenderTO);

        ResourceCurveTO projResourceCurveTO = new ResourceCurveTO();
        if (CommonUtil.objectNotNull(entity.getProjResourceCurveEntity())) {
            ClientRegEntity clientprojRegEntity = entity.getProjResourceCurveEntity().getClientId();
            projResourceCurveTO.setId(entity.getProjResourceCurveEntity().getId());
            if (null != clientprojRegEntity) {
                projResourceCurveTO.setClientId(clientprojRegEntity.getClientId());
            }
            projResourceCurveTO.setCurveType(entity.getProjResourceCurveEntity().getCurveType());
        }
        projGeneralMstrTO.setResourceCurveTO(projResourceCurveTO);

        if (CommonUtil.objectNotNull(entity.getCompanyMstrEntity())) {
            CompanyTO companyTO = projGeneralMstrTO.getCompanyTO();
            companyTO.setId(entity.getCompanyMstrEntity().getId());
            companyTO.setCmpName(entity.getCompanyMstrEntity().getName());
            companyTO.setCmpCode(entity.getCompanyMstrEntity().getCode());
            companyTO.setRegNo(entity.getCompanyMstrEntity().getRegNo());
        }

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

    public static ProjGeneralMstrEntity convertOnePojoToEntity(ProjGeneralMstrEntity entity,
            ProjGeneralMstrTO projGeneralMstrTO, CompanyRepository companyRepository,
            EPSProjRepository epsProjRepository, GlobalCalendarRepository globalCalendarRepository,
            ProfitCentreRepositoryCopy profitCentreRepository, ResourceCurveRepository resourceCurveRepository,
            LoginRepository loginRepository) {
        if (CommonUtil.isBlankLong(projGeneralMstrTO.getId())) {
            entity.setId(projGeneralMstrTO.getId());
        }
        if (CommonUtil.isNonBlankLong(projGeneralMstrTO.getProjId())) {
            ProjMstrEntity projEntity = epsProjRepository.findOne(projGeneralMstrTO.getProjId());
            entity.setProjMstrEntity(projEntity);
        }
        entity.setIsoAlpha3(projGeneralMstrTO.getIsoAlpha3());
        entity.setGeonameId(projGeneralMstrTO.getGeonameId());
        entity.setCountryName(projGeneralMstrTO.getCountryName());
        entity.setProvisionName(projGeneralMstrTO.getProvisionName());
        entity.setCurrency(projGeneralMstrTO.getCurrency());
        entity.setTimezone(projGeneralMstrTO.getTimezone());
        entity.setContractType(projGeneralMstrTO.getContractType());
        entity.setPercentOverCost(projGeneralMstrTO.getPercentOverCost());
        entity.setPrimaveraIntegration(projGeneralMstrTO.getPrimaveraIntegration());
        entity.setEarnedHourSource(projGeneralMstrTO.getEarnedHourSource());
        entity.setFinanceCentre(projGeneralMstrTO.getFinanceCentre());
        if (CommonUtil.objectNotNull(projGeneralMstrTO.getUserLabelKeyTO())) {
            UserMstrEntity respManger = loginRepository.findOne(projGeneralMstrTO.getUserLabelKeyTO().getId());
            entity.setRespManager(respManger);
        }
        if (CommonUtil.objectNotNull(projGeneralMstrTO.getGlobalCalId())) {
            GlobalCalEntity globalCalEntity = globalCalendarRepository.findOne(projGeneralMstrTO.getGlobalCalId());
            entity.setGlobalCalEntity(globalCalEntity);
        } else {
        	entity.setGlobalCalEntity(null);
        }

        if (CommonUtil.objectNotNull(projGeneralMstrTO.getProfitCentreTO())) {
            ProfitCentreEntity profit = profitCentreRepository
                    .findOne(projGeneralMstrTO.getProfitCentreTO().getId());
            entity.setProfitCentreEntity(profit);
        }

        if (CommonUtil.objectNotNull(projGeneralMstrTO.getResourceCurveTO())) {
            ResourceCurveEntity resource = resourceCurveRepository
                    .findOne(projGeneralMstrTO.getResourceCurveTO().getId());
            entity.setProjResourceCurveEntity(resource);
        } else {
        	entity.setProjResourceCurveEntity(null);
        }

        if (CommonUtil.objectNotNull(projGeneralMstrTO.getCompanyTO())) {
            CompanyMstrEntity company = companyRepository.findOne(projGeneralMstrTO.getCompanyTO().getId());
            entity.setCompanyMstrEntity(company);
        }

        entity.setIsLatest("Y");
        entity.setEffectiveFrom(new Date());
        entity.setDefualtHrs(projGeneralMstrTO.getDefualtHrs());
        entity.setMaxHrs(projGeneralMstrTO.getMaxHrs());
        entity.setContractNumber(projGeneralMstrTO.getContractNumber());
        entity.setAddress(projGeneralMstrTO.getAddress());
        entity.setStatus(projGeneralMstrTO.getStatus());
        return entity;
    }
}
