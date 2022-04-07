package com.rjtech.mw.service.user;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.req.UserGetReq;
import com.rjtech.common.resp.UserModulePermissionResp;
import com.rjtech.user.dto.ClientRegTO;
import com.rjtech.user.dto.UserTO;
import com.rjtech.user.req.ActionReq;
import com.rjtech.user.req.ClientGetReq;
import com.rjtech.user.req.ClientRegReq;
import com.rjtech.user.req.ClientReq;
import com.rjtech.user.req.EmailSettingDelReq;
import com.rjtech.user.req.EmailSettingGetReq;
import com.rjtech.user.req.EmailSettingSaveReq;
import com.rjtech.user.req.UserDeleteReq;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.req.UserProjSaveReq;
import com.rjtech.user.req.UserSaveReq;
import com.rjtech.user.req.userOnLoadReq;
import com.rjtech.user.resp.ClientRegResp;
import com.rjtech.user.resp.EmailSettingResp;
import com.rjtech.user.resp.UserOnLoadResp;
import com.rjtech.user.resp.UserProjResp;
import com.rjtech.user.resp.UserResp;
import com.rjtech.user.resp.UsersByClientResp;

public interface MWUserService {

    ClientRegResp getClients(ClientGetReq clientGetReq);

    ClientRegResp saveClient(MultipartFile file, ClientRegReq clientRegReq);

    UserResp getUsers(ClientReq clientReq);

    UserResp getVendorUsers(ClientReq clientReq);

    UserProjResp getUserProjects(UserProjGetReq userProjGetReq);

    UserProjResp saveUserProjects(UserProjSaveReq userProjSaveReq);

    UserResp saveUser(UserSaveReq userSaveReq);

    UserResp saveVendorUser(UserSaveReq userSaveReq);

    UserResp deactivateUsers(UserDeleteReq userDeleteReq);

    UserResp activateUsers(UserDeleteReq userDeleteReq);

    ClientRegResp deactivateClient(ClientReq clientReq);

    EmailSettingResp getEmailSettings(EmailSettingGetReq emailSettingGetReq);

    EmailSettingResp saveEmailSettings(EmailSettingSaveReq emailSettingSaveReq);

    EmailSettingResp deactivateEmailSettings(EmailSettingDelReq emailSettingDelReq);

    UserOnLoadResp userOnLoad(userOnLoadReq onLoadReq);

    UserResp getUsersByRoleID(ClientReq clientReq);

    UserModulePermissionResp getUsersByModulePermission(UserGetReq userGetReq);

    UsersByClientResp getUsersByClientId(ActionReq actionReq);

    Long getClientByEmail(ClientGetReq clientGetReq);

    ClientRegTO getClientById(ClientGetReq clientGetReq);

    ResponseEntity<ByteArrayResource> getClientMailTemplate(Long clientId);

    ResponseEntity<UserTO> findByUserId(long userId);

}
