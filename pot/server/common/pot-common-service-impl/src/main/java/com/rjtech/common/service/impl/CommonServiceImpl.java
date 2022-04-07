package com.rjtech.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProvisionTO;
import com.rjtech.common.model.CountryProvisionEntity;
import com.rjtech.common.model.ResourceCurveEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.CountryProvisionRepository;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.repository.ResourceCurveRepository;
import com.rjtech.common.req.CountryFilterReq;
import com.rjtech.common.req.ProjUsersReq;
import com.rjtech.common.req.ProvisionSaveReq;
import com.rjtech.common.req.ResourceCurveGetReq;
import com.rjtech.common.req.ResourceCurveSaveReq;
import com.rjtech.common.req.ResourceCurvesDeactivateReq;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.resp.ProvisionResp;
import com.rjtech.common.resp.ResourceCurveResp;
import com.rjtech.common.service.CommonService;
import com.rjtech.common.service.handler.CountryProvisionHandler;
import com.rjtech.common.service.handler.ResourceCurveHandler;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.register.model.EmpRegisterDtlEntityCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.role.model.UserRoleMapEntityCopy;
import com.rjtech.user.repository.CommonUserProjectsRepository;
import com.rjtech.user.repository.UserRoleMapRepositoryCpy;

@Service(value = "commonService")
@RJSService(modulecode = "commonService")
@Transactional
public class CommonServiceImpl implements CommonService {

    private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private CountryProvisionRepository countryProvisionRepository;

    @Autowired
    private ResourceCurveRepository resourceCurveRepository;

    @Autowired
    private CommonUserProjectsRepository commonUserProjectsRepository;

    @Autowired
    private UserRoleMapRepositoryCpy userRoleMapRepository;
    
    @Autowired
    private LoginRepository loginRepository;
    
    

    public void saveCountryProvisions(ProvisionSaveReq provisionSaveReq) {
        List<CountryProvisionEntity> countryProvisionEntities = new ArrayList<>();
        for (ProvisionTO provisionTO : provisionSaveReq.getProvisionTOs()) {
            countryProvisionEntities.add(CountryProvisionHandler.convertPOJOToEntity(provisionTO));
        }
        countryProvisionRepository.save(countryProvisionEntities);
    }

    public ResourceCurveResp getResourceCurves(ResourceCurveGetReq resourceCurveGetReq) {

        ResourceCurveResp resourceCurveResp = new ResourceCurveResp();

        List<ResourceCurveEntity> resourceCurveEntits = resourceCurveRepository
                .findResourceCurves(resourceCurveGetReq.getStatus(), AppUserUtils.getClientId());
        for (ResourceCurveEntity resourceCurveEntity : resourceCurveEntits) {
            resourceCurveResp.getProjResourceCurveTOs()
                    .add(ResourceCurveHandler.convertEntityToPOJO(resourceCurveEntity));
        }

        return resourceCurveResp;
    }

    public void saveResourceCurves(ResourceCurveSaveReq resourceCurveSaveReq) {
        resourceCurveRepository
                .save(ResourceCurveHandler.convertPOJOsToEntitys(resourceCurveSaveReq.getResourceCurveTOs()));
    }

    public void resourceCurvesDeactivate(ResourceCurvesDeactivateReq resourceCurvesDeactivateReq) {

        resourceCurveRepository.deactivateResourceurves(resourceCurvesDeactivateReq.getResourceCurveIds(),
                resourceCurvesDeactivateReq.getStatus());
    }

    public LabelKeyTOResp getEmpUsersOnly(ProjUsersReq projUsersReq) {
        List<UserRoleMapEntityCopy> users = userRoleMapRepository.findUserByPermission(projUsersReq.getPermission(),
                AppUserUtils.getClientId());
        return prepareUsersData(users);
    }

    public LabelKeyTOResp getProjUsersOnly(ProjUsersReq projUsersReq) {
        List<Long> projUsers = new ArrayList<>();
        if (CommonUtil.isNonBlankLong(projUsersReq.getProjId())) {
            projUsers = commonUserProjectsRepository.findUsersByProjId(projUsersReq.getProjId(), 1);
        }
        // TODO: remove this log
        log.info("projUsers:{}", projUsers);
        if (projUsers.isEmpty()) {
            return getEmpUsersOnly(projUsersReq);
        } else {
            List<UserRoleMapEntityCopy> users = userRoleMapRepository.findProjUserByPermission(projUsers,
                    projUsersReq.getPermission(), AppUserUtils.getClientId());
            return prepareUsersData(users);
        }
    }

    public ProvisionResp getAllCountryProvisions(CountryFilterReq countryFilterReq) {
        List<CountryProvisionEntity> countryProvisionEntities;
        if (CommonUtil.isNotBlankStr(countryFilterReq.getCountryCode())
                || CommonUtil.isNotBlankStr(countryFilterReq.getCountryName())) {
            countryProvisionEntities = countryProvisionRepository.findProvisions(countryFilterReq.getCountryCode(),
                    countryFilterReq.getCountryName(), AppUserUtils.getClientId());
        } else {
            countryProvisionEntities = countryProvisionRepository.findByClientId(AppUserUtils.getClientId());
        }
        ProvisionResp resp = new ProvisionResp();
        List<ProvisionTO> provisionTOs = resp.getProvisionTOs();
        for (CountryProvisionEntity countryProvisionEntity : countryProvisionEntities) {
            ProvisionTO provisionTO = CountryProvisionHandler.convertEntityToPOJO(countryProvisionEntity);
            provisionTOs.add(provisionTO);
        }
        return resp;
    }

    private LabelKeyTOResp prepareUsersData(List<UserRoleMapEntityCopy> users) {
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        List<LabelKeyTO> labelKeyTOs = new ArrayList<>();
        for (UserRoleMapEntityCopy userRole : users) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            UserMstrEntity userEntity = userRole.getUserEntity();
            // TODO: remove this log
            log.info("userEntity id: {}", userEntity.getUserId());
            labelKeyTO.setId(userEntity.getUserId());
            labelKeyTO.setName(userEntity.getUserName());

            EmpRegisterDtlEntityCopy empRegDtl = userEntity.getEmpRegId();
            labelKeyTO.setCode(empRegDtl.getCode());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.FIRST_NAME, empRegDtl.getFirstName());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.LAST_NAME, empRegDtl.getLastName());

            EmpClassMstrEntity empClass = empRegDtl.getEmpClassMstrEntity();
            if (empClass != null)
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.CLASS_TYPE, empClass.getName());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PHONE, userEntity.getPhoneNo());
            if (CommonUtil.objectNotNull(empRegDtl.getCompanyMstrEntity()))
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_NAME,
                        empRegDtl.getCompanyMstrEntity().getName());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.USR_EMAIL, userEntity.getEmail());
            ProjMstrEntity projMstr = empRegDtl.getProjMstrEntity();
            if (CommonUtil.objectNotNull(projMstr)) {
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJ_ID, projMstr.getProjectId().toString());
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_NAME, projMstr.getProjName());
            }
            labelKeyTOs.add(labelKeyTO);
        }
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }
    
    public LabelKeyTOResp getAllProjUsers(ProjUsersReq projUsersReq) {
        List<Long> projUsers = new ArrayList<>();
        if (CommonUtil.isNonBlankLong(projUsersReq.getProjId())) {
            projUsers = commonUserProjectsRepository.findUsersByProjId(projUsersReq.getProjId(), 1);
        }       
        if (projUsers.isEmpty()) {
            return getEmpUsersOnly(projUsersReq);
        } else {
            List<UserMstrEntity> users = loginRepository.findUserByIds(projUsers,1);                   
            LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
            List<LabelKeyTO> labelKeyTOs = new ArrayList<>();
            for (UserMstrEntity user : users) {
                LabelKeyTO labelKeyTO = new LabelKeyTO();               
                labelKeyTO.setId(user.getUserId());
                labelKeyTO.setName(user.getUserName());
                labelKeyTO.setEmail(user.getEmail());
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.FIRST_NAME, user.getFirstName());
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.LAST_NAME, user.getLastName());
                labelKeyTOs.add(labelKeyTO);
            }
            labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
            return labelKeyTOResp;
        }
    }

}
