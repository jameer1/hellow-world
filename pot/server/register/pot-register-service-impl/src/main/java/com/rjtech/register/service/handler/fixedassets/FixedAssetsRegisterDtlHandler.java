package com.rjtech.register.service.handler.fixedassets;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.fixedassets.dto.FixedAssetDtlTO;
import com.rjtech.register.fixedassets.model.FixedAssetsRegisterDtlEntity;
import com.rjtech.register.fixedassets.req.FixedAssetsSaveReq;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class FixedAssetsRegisterDtlHandler {

    public static FixedAssetDtlTO convertEntityToPOJO(FixedAssetsRegisterDtlEntity entity) {
        FixedAssetDtlTO fixedAssetDtlTO = new FixedAssetDtlTO();

        fixedAssetDtlTO.setFixedAssetid(entity.getFixedAssetid());
        fixedAssetDtlTO.setAssetId(entity.getAssetId());
        fixedAssetDtlTO.setAssetDescription(entity.getAssetDescription());
        fixedAssetDtlTO.setAddress(entity.getAddress());
        fixedAssetDtlTO.setCurrency(entity.getCurrency());
        fixedAssetDtlTO.setProfitCentre(entity.getProfitCentre());
        fixedAssetDtlTO.setYearBuild((entity.getYearBuild()));
        fixedAssetDtlTO.setDateCommissioned(CommonUtil.convertDateToString(entity.getDateCommissioned()));
        fixedAssetDtlTO.setAssetLifeStatus(entity.getAssetLifeStatus());
        fixedAssetDtlTO.setOwnershipStatus(entity.getOwnershipStatus());
        fixedAssetDtlTO.setClientCode(entity.getClientCode());
        fixedAssetDtlTO.setAssetCategoryName(entity.getAssetCategoryName());
        fixedAssetDtlTO.setIsoAlpha3(entity.getIsoAlpha3());
        fixedAssetDtlTO.setCountryName(entity.getCountryName());
        fixedAssetDtlTO.setGeonameId(entity.getGeonameId());
        fixedAssetDtlTO.setSettingStatus(entity.getSettingStatus());
        fixedAssetDtlTO.setProj(false);
        ProjMstrEntity projMstrEntity = entity.getProjMstrEntity();
        if (null != projMstrEntity) {
            fixedAssetDtlTO.setProjectId(projMstrEntity.getProjectId());
            fixedAssetDtlTO.setProjectName(projMstrEntity.getProjName());
            fixedAssetDtlTO.setProjectCode(projMstrEntity.getCode());
        }
        CompanyMstrEntity companyMstrEntity = entity.getCompanyMstrEntity();
        if (null != companyMstrEntity) {
            fixedAssetDtlTO.setCompanyId(companyMstrEntity.getId());
            fixedAssetDtlTO.setCompanyName(companyMstrEntity.getName());
        }
        fixedAssetDtlTO.setProvisionName(entity.getProvisionName());

        ClientRegEntity clientRegEntity = entity.getClientId();
        if (null != clientRegEntity) {
            fixedAssetDtlTO.setClientId(clientRegEntity.getClientId());
        }

        return fixedAssetDtlTO;
    }

    public static FixedAssetDtlTO populateProjectTO(FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity,
            boolean addChild) {

        FixedAssetDtlTO fixedAssetDtlTO = new FixedAssetDtlTO();
        List<FixedAssetDtlTO> childProjs = new ArrayList<FixedAssetDtlTO>();
        fixedAssetDtlTO.setFixedAssetid(fixedAssetsRegisterDtlEntity.getFixedAssetid());
        fixedAssetDtlTO.setAssetId(fixedAssetsRegisterDtlEntity.getAssetId());
        fixedAssetDtlTO.setAssetDescription(fixedAssetsRegisterDtlEntity.getAssetDescription());
        fixedAssetDtlTO.setAddress(fixedAssetsRegisterDtlEntity.getAddress());
        fixedAssetDtlTO.setCurrency(fixedAssetsRegisterDtlEntity.getCurrency());
        fixedAssetDtlTO.setProfitCentre(fixedAssetsRegisterDtlEntity.getProfitCentre());
        fixedAssetDtlTO.setYearBuild((fixedAssetsRegisterDtlEntity.getYearBuild()));
        fixedAssetDtlTO.setDateCommissioned(
                CommonUtil.convertDateToString(fixedAssetsRegisterDtlEntity.getDateCommissioned()));
        fixedAssetDtlTO.setAssetLifeStatus(fixedAssetsRegisterDtlEntity.getAssetLifeStatus());
        fixedAssetDtlTO.setOwnershipStatus(fixedAssetsRegisterDtlEntity.getOwnershipStatus());
        fixedAssetDtlTO.setClientCode(fixedAssetsRegisterDtlEntity.getClientCode());
        fixedAssetDtlTO.setAssetCategoryName(fixedAssetsRegisterDtlEntity.getAssetCategoryName());
        fixedAssetDtlTO.setIsoAlpha3(fixedAssetsRegisterDtlEntity.getIsoAlpha3());
        fixedAssetDtlTO.setCountryName(fixedAssetsRegisterDtlEntity.getCountryName());
        fixedAssetDtlTO.setGeonameId(fixedAssetsRegisterDtlEntity.getGeonameId());
        fixedAssetDtlTO.setProvisionName(fixedAssetsRegisterDtlEntity.getProvisionName());
        fixedAssetDtlTO.setAssignedStatus(fixedAssetsRegisterDtlEntity.getAssignedStatus());
        ProjMstrEntity projMstrEntity = fixedAssetsRegisterDtlEntity.getProjMstrEntity();
        if (null != projMstrEntity) {
            fixedAssetDtlTO.setProjectId(fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjectId());
            fixedAssetDtlTO.setProjectName(fixedAssetsRegisterDtlEntity.getProjMstrEntity().getProjName());
            fixedAssetDtlTO.setProjectCode(projMstrEntity.getCode());
        }
        CompanyMstrEntity companyMstrEntity = fixedAssetsRegisterDtlEntity.getCompanyMstrEntity();
        if (null != companyMstrEntity) {
            fixedAssetDtlTO.setCompanyId(companyMstrEntity.getId());
            fixedAssetDtlTO.setCompanyName(companyMstrEntity.getName());
        }
        ClientRegEntity clientRegEntity = fixedAssetsRegisterDtlEntity.getClientId();
        if (null != clientRegEntity) {
            fixedAssetDtlTO.setClientId(clientRegEntity.getClientId());
        }

        if (CommonConstants.PROJ_VALUE.equals(fixedAssetsRegisterDtlEntity.getProj())) {
            fixedAssetDtlTO.setProj(true);
        } else {
            fixedAssetDtlTO.setProj(false);

        }
        if (fixedAssetsRegisterDtlEntity.getFixedAssetsRegisterDtlEntity() != null) {
            fixedAssetDtlTO
                    .setParentId(fixedAssetsRegisterDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid());
        }
        fixedAssetDtlTO.setAssignedStatus(fixedAssetsRegisterDtlEntity.getAssignedStatus());
        fixedAssetDtlTO.setStatus(fixedAssetsRegisterDtlEntity.getStatus());

        //SubAssetIDetails

        fixedAssetDtlTO.setSubAssetCategory(fixedAssetsRegisterDtlEntity.getSubAssetCategory());
        fixedAssetDtlTO.setSubAssetDescription(fixedAssetsRegisterDtlEntity.getSubAssetDescription());
        fixedAssetDtlTO.setSubAssetId(fixedAssetsRegisterDtlEntity.getSubAssetId());
        if (addChild) {
            addChildProjects(fixedAssetsRegisterDtlEntity, fixedAssetDtlTO, childProjs, addChild);
            fixedAssetDtlTO.setChildProjs(childProjs);
        }
        return fixedAssetDtlTO;

    }

    private static void addChildProjects(FixedAssetsRegisterDtlEntity fixedAssetsRegisterDtlEntity,
            FixedAssetDtlTO fixedAssetDtlTO, List<FixedAssetDtlTO> childProjs, boolean addChild) {
        if (fixedAssetsRegisterDtlEntity.getFixedAssetsRegisterDtlEntity() == null) {
            fixedAssetDtlTO.setProj(false);
            for (FixedAssetsRegisterDtlEntity childProjMstr : fixedAssetsRegisterDtlEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
                    childProjs.add(populateProjectTO(childProjMstr, addChild));
                }
            }
        } else {
            fixedAssetDtlTO
                    .setParentId(fixedAssetsRegisterDtlEntity.getFixedAssetsRegisterDtlEntity().getFixedAssetid());
            for (FixedAssetsRegisterDtlEntity childProjMstr : fixedAssetsRegisterDtlEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
                    childProjs.add(populateProjectTO(childProjMstr, addChild));
                }
            }
        }
    }

    public static List<FixedAssetsRegisterDtlEntity> populateEntitisFromPOJO(FixedAssetsSaveReq fixedAssetsSaveReq,
            FixedAssetsRegisterRepository fixedAssetsRegisterRepository, EPSProjRepository epsProjRepository,
            CompanyRepository companyRepository) {
        List<FixedAssetsRegisterDtlEntity> entities = new ArrayList<FixedAssetsRegisterDtlEntity>();
        FixedAssetsRegisterDtlEntity entity = null;
        for (FixedAssetDtlTO fixedAssetDtlTO : fixedAssetsSaveReq.getFixedAssetRegisterTOs()) {
            entity = new FixedAssetsRegisterDtlEntity();
            converProjPOJOToEntity(entity, fixedAssetDtlTO, fixedAssetsRegisterRepository, epsProjRepository,
                    companyRepository);
            entities.add(entity);
        }
        return entities;
    }

    private static FixedAssetsRegisterDtlEntity converProjPOJOToEntity(FixedAssetsRegisterDtlEntity entity,
            FixedAssetDtlTO fixedAssetDTlTo, FixedAssetsRegisterRepository fixedAssetsRegisterRepository,
            EPSProjRepository epsProjRepository, CompanyRepository companyRepository) {

        ProjMstrEntity projMstrEntity = null;
        CompanyMstrEntity companyMstrEntity = null;

        if (CommonUtil.isNonBlankLong(fixedAssetDTlTo.getFixedAssetid())) {
            entity.setFixedAssetid(fixedAssetDTlTo.getFixedAssetid());
        }
        entity.setAssetId(fixedAssetDTlTo.getAssetId());
        entity.setAssetDescription(fixedAssetDTlTo.getAssetDescription());
        entity.setAssetCategoryName(fixedAssetDTlTo.getAssetCategoryName());
        entity.setAddress(fixedAssetDTlTo.getAddress());
        entity.setCurrency(fixedAssetDTlTo.getCurrency());
        entity.setProfitCentre(fixedAssetDTlTo.getProfitCentre());
        entity.setAssetLifeStatus(fixedAssetDTlTo.getAssetLifeStatus());
        entity.setOwnershipStatus(fixedAssetDTlTo.getOwnershipStatus());
        entity.setAssignedStatus(fixedAssetDTlTo.getAssignedStatus());
        if (CommonUtil.isNotBlankStr(fixedAssetDTlTo.getYearBuild())) {
            entity.setYearBuild((fixedAssetDTlTo.getYearBuild()));
        }
        if (CommonUtil.isNotBlankStr(fixedAssetDTlTo.getDateCommissioned())) {
            entity.setDateCommissioned(CommonUtil.convertStringToDate(fixedAssetDTlTo.getDateCommissioned()));
        }
        Long companyId = fixedAssetDTlTo.getCompanyId();
        if (null != companyId) {
            companyMstrEntity = companyRepository.findOne(companyId);
        }
        if (null != companyMstrEntity) {
            entity.setCompanyMstrEntity(companyMstrEntity);
        }
        Long projectId = fixedAssetDTlTo.getProjectId();
        if (null != projectId) {
            projMstrEntity = epsProjRepository.findOne(projectId);
        }
        if (null != projMstrEntity) {
            entity.setProjMstrEntity(projMstrEntity);
        }
        ClientRegEntity clientRegEntity = entity.getClientId();
        if (null != clientRegEntity) {
            entity.setClientId(clientRegEntity);
        } else {
            ClientRegEntity clientRegEntity1 = new ClientRegEntity();
            clientRegEntity1.setClientId(AppUserUtils.getClientId());
            entity.setClientId(clientRegEntity1);
        }
        entity.setClientCode(AppUserUtils.getClientCode());
        entity.setStatus(fixedAssetDTlTo.getStatus());
        if (CommonUtil.isNotBlankStr(fixedAssetDTlTo.getSettingStatus())) {
            entity.setSettingStatus(fixedAssetDTlTo.getSettingStatus());
        } else if (fixedAssetDTlTo.isProj()) {
            entity.setSettingStatus("N");
        }
        entity.setIsoAlpha3(fixedAssetDTlTo.getIsoAlpha3());
        entity.setCountryName(fixedAssetDTlTo.getCountryName());
        entity.setGeonameId(fixedAssetDTlTo.getGeonameId());
        entity.setProvisionName(fixedAssetDTlTo.getProvisionName());
        FixedAssetsRegisterDtlEntity childEntity = null;
        for (FixedAssetDtlTO childTO : fixedAssetDTlTo.getChildProjs()) {
            childEntity = new FixedAssetsRegisterDtlEntity();
            childEntity.setFixedAssetsRegisterDtlEntity(entity);
            entity.getChildEntities().add(converProjPOJOToEntity(childEntity, childTO, fixedAssetsRegisterRepository,
                    epsProjRepository, companyRepository));
        }
        entity.setSubAssetCategory(fixedAssetDTlTo.getSubAssetCategory());
        entity.setSubAssetDescription(fixedAssetDTlTo.getSubAssetDescription());
        entity.setSubAssetId(fixedAssetDTlTo.getSubAssetId());
        return entity;
    }

}
