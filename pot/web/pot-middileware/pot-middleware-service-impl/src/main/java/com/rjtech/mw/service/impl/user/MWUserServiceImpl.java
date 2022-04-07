package com.rjtech.mw.service.impl.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.admin.constans.AdminURLConstants;
import com.rjtech.common.req.UserGetReq;
import com.rjtech.common.resp.UserModulePermissionResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.user.MWUserService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;
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

@Service(value = "mwUserService")
@RJSService(modulecode = "mwUserService")
@Transactional
public class MWUserServiceImpl extends RestConfigServiceImpl implements MWUserService {

    public ClientRegResp getClients(ClientGetReq clientGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_CLIENTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(clientGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ClientRegResp.class);
    }

    public ClientRegResp saveClient(MultipartFile file, ClientRegReq clientRegReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.SAVE_CLIENT);
        strResponse = constructPOSTRestTemplateWithMultipart(url, file, AppUtils.toJson(clientRegReq));
        return AppUtils.fromJson(strResponse.getBody(), ClientRegResp.class);
    }

    public UserResp getUsers(ClientReq clientReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_USERS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(clientReq));
        return AppUtils.fromJson(strResponse.getBody(), UserResp.class);
    }

    public UserResp getVendorUsers(ClientReq clientReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_VENDOR_USERS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(clientReq));
        return AppUtils.fromJson(strResponse.getBody(), UserResp.class);
    }

    public UserProjResp getUserProjects(UserProjGetReq userProjGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_USER_PROJECTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userProjGetReq));
        return AppUtils.fromJson(strResponse.getBody(), UserProjResp.class);
    }

    public UserProjResp saveUserProjects(UserProjSaveReq userProjSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.SAVE_USER_PROJECTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userProjSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), UserProjResp.class);
    }

    public UserResp saveUser(UserSaveReq userSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.SAVE_USER);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), UserResp.class);
    }

    public UserResp saveVendorUser(UserSaveReq userSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.SAVE_VENDOR_USER);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), UserResp.class);
    }

    public UserResp deactivateUsers(UserDeleteReq userDeleteReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.DELETE_USER);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userDeleteReq));
        return AppUtils.fromJson(strResponse.getBody(), UserResp.class);
    }

    public ClientRegResp deactivateClient(ClientReq clientReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.DELETE_CLIENT);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(clientReq));
        return AppUtils.fromJson(strResponse.getBody(), ClientRegResp.class);
    }

    public EmailSettingResp getEmailSettings(EmailSettingGetReq emailSettingGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_EMAIL_SETTINGS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(emailSettingGetReq));
        return AppUtils.fromJson(strResponse.getBody(), EmailSettingResp.class);
    }

    public EmailSettingResp saveEmailSettings(EmailSettingSaveReq emailSettingSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.SAVE_EMAIL_SETTINGS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(emailSettingSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), EmailSettingResp.class);
    }

    public EmailSettingResp deactivateEmailSettings(EmailSettingDelReq emailSettingSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.DEACTIVATE_EMAIL_SETTINGS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(emailSettingSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), EmailSettingResp.class);
    }

    public UserOnLoadResp userOnLoad(userOnLoadReq onLoadReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.USER_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(onLoadReq));
        return AppUtils.fromJson(strResponse.getBody(), UserOnLoadResp.class);
    }

    public UserResp getUsersByRoleID(ClientReq clientReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_USERS_BY_ROLEID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(clientReq));
        return AppUtils.fromJson(strResponse.getBody(), UserResp.class);
    }

    public UserModulePermissionResp getUsersByModulePermission(UserGetReq userGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(
                AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_USERS_BY_MODULE_PERMISSION);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userGetReq));
        return AppUtils.fromJson(strResponse.getBody(), UserModulePermissionResp.class);
    }

    public UsersByClientResp getUsersByClientId(ActionReq actionReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_USERS_BY_CLIENT_ID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(actionReq));
        return AppUtils.fromJson(strResponse.getBody(), UsersByClientResp.class);
    }

    public Long getClientByEmail(ClientGetReq clientGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_CLIENT_BY_EMAIL);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(clientGetReq));
        return AppUtils.fromJson(strResponse.getBody(), Long.class);
    }

    public ClientRegTO getClientById(ClientGetReq clientGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_CLIENT_BY_ID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(clientGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ClientRegTO.class);
    }

    public ResponseEntity<ByteArrayResource> getClientMailTemplate(Long clientId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("clientId", clientId);

        ResponseEntity<ByteArrayResource> response = null;
        String url = AppUtils.getUrl(
                getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_CLIENT_MAIL_TEMPLATE),
                paramsMap);
        response = constructGETRestTemplate(url, ByteArrayResource.class);
        return response;
    }

    public UserResp activateUsers(UserDeleteReq userDeleteReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.ACTIVATE_USER);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userDeleteReq));
        return AppUtils.fromJson(strResponse.getBody(), UserResp.class);
    }

    public ResponseEntity<UserTO> findByUserId(long userId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userId", userId);
        String url = AppUtils.getUrl(
                getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.FID_BY_USER_ID), paramsMap);
        ResponseEntity<String> strResponse = null;
        strResponse = constructPOSTRestTemplate(url, "");
        return new ResponseEntity<>(AppUtils.fromJson(strResponse.getBody(), UserTO.class), HttpStatus.OK);
    }

}
