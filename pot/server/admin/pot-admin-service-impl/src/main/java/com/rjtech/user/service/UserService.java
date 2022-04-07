package com.rjtech.user.service;

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
import com.rjtech.user.req.UserProjSaveReq;
import com.rjtech.user.req.UserSaveReq;
import com.rjtech.user.resp.ClientRegResp;
import com.rjtech.user.resp.EmailSettingResp;
import com.rjtech.user.resp.UserResp;
import com.rjtech.user.resp.UsersByClientResp;

public interface UserService {

    public ClientRegResp getClients(ClientGetReq clientGetReq);

    public ClientRegTO saveClient(ClientRegReq clientRegReq);

    public void deleteClientUser(Long clientId);

    public void deactivateClient(ClientReq clientReq);

    public UserResp getUsers(ClientReq clientReq);

    public UserResp getVendorUsers(ClientReq clientReq);

    public UserTO findByUserId(Long userId);

    public void saveUser(UserSaveReq userSaveReq);

    public void saveVendorUser(UserSaveReq userSaveReq);

    public void deactivateUsers(UserDeleteReq userDeleteReq);

    public void saveUserProjects(UserProjSaveReq userProjSaveReq);

    public EmailSettingResp getEmailSettings(EmailSettingGetReq emailSettingGetReq, Long clientId);

    public void saveEmailSettings(EmailSettingSaveReq emailSettingSaveReq);

    public void deactivateEmailSettings(EmailSettingDelReq emailSettingDelReq);

    public UserModulePermissionResp getUsersByModulePermission(UserGetReq userGetReq);

    public UsersByClientResp getUsersByClientId(ActionReq actionReq);

    public Long getClientByEmail(String email);

    public void LienceExpiredMsgAllClient();

    ClientRegTO getClientById(Long clientId);

    public void activateUsers(UserDeleteReq userDeleteReq);
}
