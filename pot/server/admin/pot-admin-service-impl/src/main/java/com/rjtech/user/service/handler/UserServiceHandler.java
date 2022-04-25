package com.rjtech.user.service.handler;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.rjtech.common.model.UserProjectsEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.UserTypes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.repository.EmpRegisterRepositoryCpy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
//import com.rjtech.register.model.EmpRegisterDtlEntityCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.role.model.RoleMstrEntity;
import com.rjtech.role.model.UserRoleMapEntity;
import com.rjtech.user.dto.ClientRegTO;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.dto.UserProjectTO;
import com.rjtech.user.dto.UserRoleTO;
import com.rjtech.user.dto.UserTO;
import com.rjtech.user.model.ClientRegMstrEntity;
import com.rjtech.user.model.UserEntity;
import com.rjtech.user.repository.ClientRepository;
import com.rjtech.user.req.UserProjSaveReq;
import com.rjtech.user.req.UserSaveReq;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;


public class UserServiceHandler {
    
    public static UserTO convertUserEntityToPOJO(UserEntity userMstr) {
        UserTO userTO = new UserTO();
        userTO.setUserId(userMstr.getUserId());
        List<UserRoleTO> userRoles = new ArrayList<UserRoleTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        UserRoleTO userRoleTO = null;
        if (CommonUtil.isListHasData(userMstr.getUserRoleMstrs())) {
            for (UserRoleMapEntity userRoleMstr : userMstr.getUserRoleMstrs()) {
                if (CommonUtil.objectNotNull(userRoleMstr)) {
                    userTO.setRoleDisplay(userRoleMstr.getRoleMstr().getRoleName());
                    userRoleTO = new UserRoleTO();
                    userRoleTO.setId(userRoleMstr.getUserRoleId());
                    userRoleTO.setUserId(userMstr.getUserId());
                    userRoleTO.setRoleName(userRoleMstr.getRoleMstr().getRoleName());
                    userRoleTO.setRoleId(userRoleMstr.getRoleMstr().getRoleId());
                    userRoles.add(userRoleTO);
                }
            }
        }
        userTO.setUserId(userMstr.getUserId());
        userTO.setUserRoles(userRoles);
        userTO.setUserName(userMstr.getUserName());
		userTO.setPassword(userMstr.getPassword());
        userTO.setFirstName(userMstr.getFirstName());
        userTO.setLastName(userMstr.getLastName());
        userTO.setEmail(userMstr.getEmail());
        userTO.setEmpCode(userMstr.getEmpCode());
        userTO.setEmpDesg(userMstr.getEmpDesg());
        userTO.setUserType(userMstr.getUserType());
        userTO.setPhone(userMstr.getPhoneNo());
        userTO.setMobile(userMstr.getMobileNo());
        userTO.setRemarks(userMstr.getRemarks());
        ClientRegMstrEntity clientRegMstrEntity = userMstr.getClientRegMstrEntity();
        EmpRegisterDtlEntity empRegisterDtlEntity = userMstr.getEmpRegId();
        if (empRegisterDtlEntity != null) {
            userTO.setEmpRegId(empRegisterDtlEntity.getId());
        }
        if(clientRegMstrEntity != null)
        	userTO.setRegisteredUsers(clientRegMstrEntity.getRegisteredUsers());
        userTO.setEmpCode(userMstr.getEmpCode());
        userTO.setEmpCode(userMstr.getEmpCode());

        userTO.setDispName(userMstr.getDisplayName());
        if (CommonUtil.objectNotNull(userMstr.getClientRegMstrEntity())) {
            userTO.setClientCode(userMstr.getClientRegMstrEntity().getCode());
            userTO.setClientId(userMstr.getClientRegMstrEntity().getClientId());
        }

        userTO.setStatus(userMstr.getStatus());

        return userTO;
    }

    public static List<UserEntity> converUserPOJOToEntity(UserSaveReq saveReq,
            EmpRegisterRepositoryCpy empRegisterRepository, ClientRepository clientRepository) {
        List<UserEntity> userMstrEntities = new ArrayList<UserEntity>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        UserEntity userMstr = null;
        for (UserTO userTO : saveReq.getUserTOs()) {
            userMstr = new UserEntity();
            if (CommonUtil.objectNotNull(saveReq) && CommonUtil.isNonBlankLong(userTO.getUserId())) {
                userMstr.setUserId(userTO.getUserId());
            }
            userMstr.setDisplayName(userTO.getDispName());
            userMstr.setUserName(userTO.getUserName());
            userMstr.setPassword(userTO.getPassword());
            userMstr.setEmpCode(userTO.getEmpCode());
            userMstr.setEmpDesg(userTO.getEmpDesg());
            userMstr.setFirstName(userTO.getFirstName());
            userMstr.setLastName(userTO.getLastName());
            userMstr.setEmail(userTO.getEmail());
            userMstr.setMobileNo(userTO.getMobile());
            userMstr.setPhoneNo(userTO.getPhone());
            userMstr.setRemarks(userTO.getRemarks());

            Long empRegId = userTO.getEmpRegId();
            if (empRegId != null) {
                userMstr.setEmpRegId(empRegisterRepository.findOne(userTO.getEmpRegId()));
            }
            userMstr.setStatus(userTO.getStatus());
            if (CommonUtil.isNonBlankLong(AppUserUtils.getClientId())) {
                ClientRegMstrEntity clientRegMstrEntity = clientRepository.findOne(AppUserUtils.getClientId());
                userMstr.setClientRegMstrEntity(clientRegMstrEntity);
                userMstr.setUserType(UserTypes.EXTERNAL.getValue());
            } else if (CommonUtil.isNonBlankLong(userTO.getClientId())) {
                ClientRegMstrEntity clientRegMstrEntity = clientRepository.findOne(userTO.getClientId());
                userMstr.setClientRegMstrEntity(clientRegMstrEntity);
                userMstr.setUserType(UserTypes.EXTERNAL.getValue());
            } else {
                userMstr.setUserType(UserTypes.INTERNAL.getValue());
            }

            List<UserRoleMapEntity> userRoleMstrs = new ArrayList<UserRoleMapEntity>(
                    ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
            UserRoleMapEntity userRoleMstr = null;
            RoleMstrEntity roleMstr = null;
            for (UserRoleTO userRoleTO : userTO.getUserRoles()) {
                userRoleMstr = new UserRoleMapEntity();
                roleMstr = new RoleMstrEntity();
                roleMstr.setRoleId(userRoleTO.getRoleId());
                userRoleMstr.setRoleMstr(roleMstr);
                userRoleMstr.setUserEntity(userMstr);
                userRoleMstrs.add(userRoleMstr);
            }
            userRoleMstr.setRoleMstr(roleMstr);
            userMstr.setUserRoleMstrs(userRoleMstrs);
            userMstrEntities.add(userMstr);
        }
        return userMstrEntities;
    }

    public static List<UserEntity> converVendorUserPOJOToEntity(UserSaveReq saveReq,
            EmpRegisterRepositoryCpy empRegisterRepository) {
        List<UserEntity> userMstrEntities = new ArrayList<UserEntity>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        UserEntity userMstr = null;
        for (UserTO userTO : saveReq.getUserTOs()) {
            userMstr = new UserEntity();
            if (CommonUtil.objectNotNull(saveReq) && CommonUtil.isNonBlankLong(userTO.getUserId())) {
                userMstr.setUserId(userTO.getUserId());
            }
            userMstr.setDisplayName(userTO.getDispName());
            userMstr.setUserName(userTO.getUserName());
            userMstr.setPassword(userTO.getPassword());
            userMstr.setEmpCode(userTO.getEmpCode());
            userMstr.setEmpDesg(userTO.getEmpDesg());
            userMstr.setFirstName(userTO.getFirstName());
            userMstr.setLastName(userTO.getLastName());
            userMstr.setEmail(userTO.getEmail());
            userMstr.setMobileNo(userTO.getMobile());
            userMstr.setPhoneNo(userTO.getPhone());
            userMstr.setRemarks(userTO.getRemarks());
            userMstr.setClient(true);
            Long empRegId = userTO.getEmpRegId();
            if (empRegId != null) {
                userMstr.setEmpRegId(empRegisterRepository.findOne(userTO.getEmpRegId()));
            }

            userMstr.setStatus(userTO.getStatus());

            userMstr.setUserType(UserTypes.INTERNAL.getValue());
            List<UserRoleMapEntity> userRoleMstrs = new ArrayList<UserRoleMapEntity>(
                    ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
            UserRoleMapEntity userRoleMstr = null;
            RoleMstrEntity roleMstr = null;
            for (UserRoleTO userRoleTO : userTO.getUserRoles()) {
                userRoleMstr = new UserRoleMapEntity();
                roleMstr = new RoleMstrEntity();
                roleMstr.setRoleId(userRoleTO.getRoleId());
                userRoleMstr.setRoleMstr(roleMstr);
                userRoleMstr.setUserEntity(userMstr);
                userRoleMstrs.add(userRoleMstr);
            }
            userRoleMstr.setRoleMstr(roleMstr);
            userMstr.setUserRoleMstrs(userRoleMstrs);
            userMstrEntities.add(userMstr);
        }
        return userMstrEntities;
    }

    public static ClientRegTO convertClientEntiryToPOJO(ClientRegMstrEntity clientRegMstrEntity) {
        ClientRegTO clientRegTO = new ClientRegTO();
        if (CommonUtil.objectNotNull(clientRegMstrEntity)
                && CommonUtil.isNonBlankLong(clientRegMstrEntity.getClientId())) {
            clientRegTO.setId(clientRegMstrEntity.getClientId());
        }
        clientRegTO.setName(clientRegMstrEntity.getName());
        clientRegTO.setCode(clientRegMstrEntity.getCode());
        clientRegTO.setBusinessType(clientRegMstrEntity.getBusinessType());
        clientRegTO.setCountry(clientRegMstrEntity.getCountry());
        clientRegTO.setWebSiteURL(clientRegMstrEntity.getWebSiteURL());
        clientRegTO.setContactPerson(clientRegMstrEntity.getContactPerson());
        clientRegTO.setAddress(clientRegMstrEntity.getAddress());
        clientRegTO.setRegisteredUsers(clientRegMstrEntity.getRegisteredUsers());
        if (CommonUtil.isNotBlankDate(clientRegMstrEntity.getLicence())) {
            clientRegTO.setLicence(CommonUtil.convertDateToString(clientRegMstrEntity.getLicence()));
        }
        clientRegTO.setFax(clientRegMstrEntity.getFax());
        clientRegTO.setEmail(clientRegMstrEntity.getEmail());
        clientRegTO.setMobile(clientRegMstrEntity.getMobile());
        clientRegTO.setPhone(clientRegMstrEntity.getPhone());
        clientRegTO.setRemarks(clientRegMstrEntity.getRemarks());
        clientRegTO.setStatus(clientRegMstrEntity.getStatus());
        clientRegTO.setClientDetails(clientRegMstrEntity.getClientDetails());
        clientRegTO.setClientFileType(clientRegMstrEntity.getClientFileType());
        clientRegTO.setClientDetailsFileName(clientRegMstrEntity.getClientDetailsFileName());

        return clientRegTO;
    }
	
	 public static ClientRegTO convertClientUserEntiryToPOJO(ClientRegMstrEntity clientRegMstrEntity, UserTO userEntity) {
        ClientRegTO clientRegTO = new ClientRegTO();
        if (CommonUtil.objectNotNull(clientRegMstrEntity)
                && CommonUtil.isNonBlankLong(clientRegMstrEntity.getClientId())) {
            clientRegTO.setId(clientRegMstrEntity.getClientId());
        }
        clientRegTO.setName(clientRegMstrEntity.getName());
        clientRegTO.setCode(clientRegMstrEntity.getCode());
        clientRegTO.setBusinessType(clientRegMstrEntity.getBusinessType());
        clientRegTO.setCountry(clientRegMstrEntity.getCountry());
        clientRegTO.setWebSiteURL(clientRegMstrEntity.getWebSiteURL());
        clientRegTO.setContactPerson(clientRegMstrEntity.getContactPerson());
        clientRegTO.setAddress(clientRegMstrEntity.getAddress());
        clientRegTO.setRegisteredUsers(clientRegMstrEntity.getRegisteredUsers());
        if (CommonUtil.isNotBlankDate(clientRegMstrEntity.getLicence())) {
            clientRegTO.setLicence(CommonUtil.convertDateToString(clientRegMstrEntity.getLicence()));
        }
        clientRegTO.setFax(clientRegMstrEntity.getFax());
        clientRegTO.setEmail(clientRegMstrEntity.getEmail());
        clientRegTO.setMobile(clientRegMstrEntity.getMobile());
        clientRegTO.setPhone(clientRegMstrEntity.getPhone());
        clientRegTO.setRemarks(clientRegMstrEntity.getRemarks());
		clientRegTO.setUserTO(userEntity);
        clientRegTO.setStatus(clientRegMstrEntity.getStatus());
        clientRegTO.setClientDetails(clientRegMstrEntity.getClientDetails());
        clientRegTO.setClientFileType(clientRegMstrEntity.getClientFileType());
        clientRegTO.setClientDetailsFileName(clientRegMstrEntity.getClientDetailsFileName());

        return clientRegTO;
    }

    public static ClientRegMstrEntity convertClientPOJOToEntity(ClientRegMstrEntity clientFromDb,
            ClientRegTO clientRegTO, ProjDocFolderRepository projDocFolderRepository, ProjDocFileRepository projDocFileRepository, String folder_name) {
        ClientRegMstrEntity clientRegMstrEntity = new ClientRegMstrEntity();
        if (CommonUtil.objectNotNull(clientRegTO) && CommonUtil.isNonBlankLong(clientRegTO.getId())) {
            clientRegMstrEntity.setClientId(clientRegTO.getId());
        }
        clientRegMstrEntity.setName(clientRegTO.getName());
        clientRegMstrEntity.setCode(clientRegTO.getCode());
        clientRegMstrEntity.setBusinessType(clientRegTO.getBusinessType());
        clientRegMstrEntity.setCountry(clientRegTO.getCountry());
        clientRegMstrEntity.setWebSiteURL(clientRegTO.getWebSiteURL());
        clientRegMstrEntity.setContactPerson(clientRegTO.getContactPerson());
        clientRegMstrEntity.setAddress(clientRegTO.getAddress());
        clientRegMstrEntity.setLicence(CommonUtil.convertStringToDate(clientRegTO.getLicence()));
        clientRegMstrEntity.setFax(clientRegTO.getFax());
        clientRegMstrEntity.setEmail(clientRegTO.getEmail());
        clientRegMstrEntity.setMobile(clientRegTO.getMobile());
        clientRegMstrEntity.setPhone(clientRegTO.getPhone());
        clientRegMstrEntity.setRemarks(clientRegTO.getRemarks());
        clientRegMstrEntity.setStatus(StatusCodes.ACTIVE.getValue());
        clientRegMstrEntity.setRegisteredUsers(clientRegTO.getRegisteredUsers());
        if (clientRegTO.getClientDetailsFileName() != null) {
            clientRegMstrEntity.setClientDetails(clientRegTO.getClientDetails());
            clientRegMstrEntity.setClientDetailsFileName(clientRegTO.getClientDetailsFileName());
            clientRegMstrEntity.setClientFileType(clientRegTO.getClientFileType());
        } else if (clientFromDb != null) {
            clientRegMstrEntity.setClientDetails(clientFromDb.getClientDetails());
            clientRegMstrEntity.setClientDetailsFileName(clientFromDb.getClientDetailsFileName());
            clientRegMstrEntity.setClientFileType(clientFromDb.getClientFileType());
        }
        
        //String folder_name = clientRegReq.getFolderCategory();
        ProjDocFolderEntity folderEntity = projDocFolderRepository.findByNameAndProjId( folder_name );
        
        //ProjDocFileTO projDocFileTO = new ProjDocFileTO(); 
        ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
        ProjDocFileEntity resProjDocFileEntity = new ProjDocFileEntity();
        projDocFileEntity.setFolderId( folderEntity );
        projDocFileEntity.setFileType( clientRegTO.getClientFileType() );
        //projDocFileEntity.setFileSize( folderEntity.getId() );
        projDocFileEntity.setName( clientRegTO.getClientDetailsFileName() );
        
        resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
        ProjDocFileEntity rProjDocFileEntity = new ProjDocFileEntity();
        rProjDocFileEntity.setId( resProjDocFileEntity.getId() );
        clientRegMstrEntity.setProjDocFile( rProjDocFileEntity );       
        return clientRegMstrEntity;
    }

    public static UserEntity populateUserEntityFromClientTO(ClientRegMstrEntity clientRegMstrEntity,
            ClientRegTO clientRegTO) {
        UserEntity userMstr = new UserEntity();
        userMstr.setUserName(clientRegTO.getUserTO().getUserName());
        userMstr.setPassword(clientRegTO.getUserTO().getPassword());
        userMstr.setDisplayName(clientRegTO.getUserTO().getDispName());
        userMstr.setFirstName(clientRegTO.getUserTO().getFirstName());
        userMstr.setLastName(clientRegTO.getUserTO().getLastName());
        userMstr.setEmail(clientRegTO.getEmail());
        userMstr.setMobileNo(clientRegTO.getMobile());
        userMstr.setRemarks(clientRegTO.getRemarks());
        userMstr.setStatus(StatusCodes.ACTIVE.getValue());
        userMstr.setClient(true);

        userMstr.setUserType(UserTypes.EXTERNAL.getValue());
        userMstr.setPhoneNo(clientRegTO.getPhone());
        userMstr.setClientRegMstrEntity(clientRegMstrEntity);
        return userMstr;
    }

    public static List<UserProjectsEntity> populateUserProjectsFromPOJO(UserProjSaveReq userProjSaveReq,
            LoginRepository loginRepository, EPSProjRepository epsProjRepository) {
        List<UserProjectsEntity> userProjectsEntities = new ArrayList<UserProjectsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        UserProjectsEntity userProjectsEntity = null;
        for (UserProjDetailsTO userProjDetailsTO : userProjSaveReq.getUserProjectTOs()) {
            userProjectsEntity = new UserProjectsEntity();
            Long userId = userProjDetailsTO.getUserId();
            if (CommonUtil.isNonBlankLong(userId)) {
                userProjectsEntity.setUserId(loginRepository.findOne(userId));
            }
            Long projId = userProjDetailsTO.getProjId();
            if (CommonUtil.isNonBlankLong(projId)) {
                userProjectsEntity.setProjectMstrEntity(epsProjRepository.findOne(projId));
            }

            if (userProjDetailsTO.isUsrProj()) {
                userProjectsEntity.setStatus(StatusCodes.ACTIVE.getValue());
            } else {
                userProjectsEntity.setStatus(StatusCodes.DEACIVATE.getValue());
            }
            if (CommonUtil.isNonBlankLong(userProjDetailsTO.getId())) {
                userProjectsEntity.setId(userProjDetailsTO.getId());
                userProjectsEntities.add(userProjectsEntity);
            } else if (userProjDetailsTO.isUsrProj()) {
                userProjectsEntities.add(userProjectsEntity);
            }
        }
        return userProjectsEntities;
    }

    public static List<UserProjectTO> populateUserProjectsFromEntity(List<UserProjectsEntity> userProjectsEntities) {
        List<UserProjectTO> userProjectTOs = new ArrayList<UserProjectTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        UserProjectTO userProjectTO = null;
        for (UserProjectsEntity userProjectsEntity : userProjectsEntities) {
            userProjectTO = new UserProjectTO();
            userProjectTO.setId(userProjectsEntity.getId());
            userProjectTO.setUserId(userProjectsEntity.getUserId().getUserId());
            userProjectTO.setProjId(userProjectsEntity.getProjectMstrEntity().getProjectId());
            userProjectTOs.add(userProjectTO);
        }
        return userProjectTOs;
    }

}
