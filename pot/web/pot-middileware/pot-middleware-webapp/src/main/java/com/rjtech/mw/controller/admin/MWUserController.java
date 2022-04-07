package com.rjtech.mw.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.admin.constans.AdminURLConstants;
import com.rjtech.common.req.UserGetReq;
import com.rjtech.common.resp.UserModulePermissionResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.user.MWUserService;
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

@RestController
@RequestMapping(AdminURLConstants.USER_PARH_URL)
public class MWUserController {

    @Autowired
    private MWUserService mwUserService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @RequestMapping(value = AdminURLConstants.GET_CLIENTS, method = RequestMethod.POST)
    public ResponseEntity<ClientRegResp> getClients(@RequestBody ClientGetReq clientGetReq) {
        return new ResponseEntity<ClientRegResp>(mwUserService.getClients(clientGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_CLIENT_BY_EMAIL, method = RequestMethod.POST)
    public ResponseEntity<Long> getClientByEmail(@RequestBody ClientGetReq clientGetReq) {
        return new ResponseEntity<Long>(mwUserService.getClientByEmail(clientGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_CLIENT_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<ClientRegTO> getClientById(@RequestBody ClientGetReq clientGetReq) {
        return new ResponseEntity<ClientRegTO>(mwUserService.getClientById(clientGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_CLIENT, method = RequestMethod.POST)
    public ResponseEntity<ClientRegResp> saveClient(MultipartFile file, String clientRegReq) {
        return new ResponseEntity<ClientRegResp>(
                mwUserService.saveClient(file, AppUtils.fromJson(clientRegReq, ClientRegReq.class)), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_USERS, method = RequestMethod.POST)
    public ResponseEntity<UserResp> getUsers(@RequestBody ClientReq clientReq) {
        return new ResponseEntity<UserResp>(mwUserService.getUsers(clientReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_VENDOR_USERS, method = RequestMethod.POST)
    public ResponseEntity<UserResp> getVendorUsers(@RequestBody ClientReq clientReq) {
        return new ResponseEntity<UserResp>(mwUserService.getVendorUsers(clientReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_USER_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<UserProjResp> getUserProjects(@RequestBody UserProjGetReq userProjGetReq) {
        return new ResponseEntity<UserProjResp>(mwUserService.getUserProjects(userProjGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_USER_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<UserProjResp> saveUserProjects(@RequestBody UserProjSaveReq userProjSaveReq) {
        return new ResponseEntity<UserProjResp>(mwUserService.saveUserProjects(userProjSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_USER, method = RequestMethod.POST)
    public ResponseEntity<UserResp> saveUser(@RequestBody UserSaveReq userSaveReq) {
        return new ResponseEntity<UserResp>(mwUserService.saveUser(userSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_VENDOR_USER, method = RequestMethod.POST)
    public ResponseEntity<UserResp> saveVendorUser(@RequestBody UserSaveReq userSaveReq) {
        return new ResponseEntity<UserResp>(mwUserService.saveVendorUser(userSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.DELETE_USER, method = RequestMethod.POST)
    public ResponseEntity<UserResp> deactivateUsers(@RequestBody UserDeleteReq userDeleteReq) {
        return new ResponseEntity<UserResp>(mwUserService.deactivateUsers(userDeleteReq), HttpStatus.OK);

    }

    @PostMapping(value = AdminURLConstants.ACTIVATE_USER)
    public ResponseEntity<UserResp> activateUsers(@RequestBody UserDeleteReq userDeleteReq) {
        return new ResponseEntity<UserResp>(mwUserService.activateUsers(userDeleteReq), HttpStatus.OK);

    }

    @RequestMapping(value = AdminURLConstants.DELETE_CLIENT, method = RequestMethod.POST)
    public ResponseEntity<ClientRegResp> deactivateClient(@RequestBody ClientReq clientReq) {
        return new ResponseEntity<ClientRegResp>(mwUserService.deactivateClient(clientReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_EMAIL_SETTINGS, method = RequestMethod.POST)
    public ResponseEntity<EmailSettingResp> getEmailSettings(@RequestBody EmailSettingGetReq emailSettingGetReq) {
        return new ResponseEntity<EmailSettingResp>(mwUserService.getEmailSettings(emailSettingGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_EMAIL_SETTINGS, method = RequestMethod.POST)
    public ResponseEntity<EmailSettingResp> saveEmailSettings(@RequestBody EmailSettingSaveReq emailSettingSaveReq) {
        return new ResponseEntity<EmailSettingResp>(mwUserService.saveEmailSettings(emailSettingSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = AdminURLConstants.DEACTIVATE_EMAIL_SETTINGS, method = RequestMethod.POST)
    public ResponseEntity<EmailSettingResp> deactivateEmailSettings(
            @RequestBody EmailSettingDelReq emailSettingDelReq) {
        return new ResponseEntity<EmailSettingResp>(mwUserService.deactivateEmailSettings(emailSettingDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = AdminURLConstants.USER_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<UserOnLoadResp> userOnLoad(@RequestBody userOnLoadReq onLoadReq) {
        return new ResponseEntity<UserOnLoadResp>(mwUserService.userOnLoad(onLoadReq), HttpStatus.OK);

    }

    @RequestMapping(value = AdminURLConstants.GET_USERS_BY_MODULE_PERMISSION, method = RequestMethod.POST)
    public ResponseEntity<UserModulePermissionResp> getUsersByModulePermission(@RequestBody UserGetReq userGetReq) {
        return new ResponseEntity<UserModulePermissionResp>(mwUserService.getUsersByModulePermission(userGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_USERS_BY_CLIENT_ID, method = RequestMethod.POST)
    public ResponseEntity<UsersByClientResp> getUsersByClientId(@RequestBody ActionReq actionReq) {
        return new ResponseEntity<UsersByClientResp>(mwUserService.getUsersByClientId(actionReq), HttpStatus.OK);
    }

    @GetMapping(value = AdminURLConstants.GET_CLIENT_MAIL_TEMPLATE)
    public ResponseEntity<ByteArrayResource> getClientMailTemplate(@RequestParam("clientId") Long clientId) {
        return mwUserService.getClientMailTemplate(clientId);
    }

    @PostMapping(value = AdminURLConstants.FID_BY_USER_ID)
    public ResponseEntity<UserTO> findByUserId(@RequestParam("userId") Long userId) {
        return mwUserService.findByUserId(userId);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_DEFAULT_CLIENT_DATA, method = RequestMethod.POST)
    public void saveDefaultClientData(@RequestParam("clientId") Long clientId) {
        mwProjLibService.saveDefaultProjLeaveTypes(clientId);
    }

}
