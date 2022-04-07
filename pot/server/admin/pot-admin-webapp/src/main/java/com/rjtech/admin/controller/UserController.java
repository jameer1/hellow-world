package com.rjtech.admin.controller;

import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.role.req.RoleGetReq;
import com.rjtech.role.service.RoleService;
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
import com.rjtech.user.service.UserService;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.model.ProjDocFolderEntity;

@RestController
@RequestMapping(AdminURLConstants.USER_PARH_URL)
@MultipartConfig
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EPSProjService epsProjService;

    @Autowired
    private RoleService roleService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ProjDocFolderRepository projDocFolderRepository;

    @RequestMapping(value = AdminURLConstants.GET_CLIENTS, method = RequestMethod.POST)
    public ResponseEntity<ClientRegResp> getClients(@RequestBody ClientGetReq clientGetReq, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<ClientRegResp>(userService.getClients(clientGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_CLIENT_BY_EMAIL, method = RequestMethod.POST)
    public ResponseEntity<String> getClientByEmail(@RequestBody ClientGetReq clientGetReq, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<String>(userService.getClientByEmail(clientGetReq.getEmail()).toString(),
                HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_CLIENT_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<ClientRegTO> getClientById(@RequestBody ClientGetReq clientGetReq, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<ClientRegTO>(userService.getClientById(clientGetReq.getClientId()), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_CLIENT, method = RequestMethod.POST)
    public ResponseEntity<ClientRegResp> saveClient(MultipartFile file, String clientRegReq) throws IOException {
        ClientRegReq clientReg = AppUtils.fromJson(clientRegReq, ClientRegReq.class);
        ClientRegTO clientRegTo = clientReg.getClientRegTO();
        if (file != null) {
            clientRegTo.setClientDetails(file.getBytes());
            clientRegTo.setClientDetailsFileName(file.getOriginalFilename());
            clientRegTo.setClientFileType(file.getContentType());
			
			//fileService.storeFile(file);
        }
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( clientReg.getFolderCategory() );		
		String dir_path = folder.getUploadFolder();
        
        ClientRegTO clientTO = userService.saveClient(clientReg);
    	UploadUtil uploadUtil = null;
        
        if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") && file != null )
    	{
        	uploadUtil = new UploadUtil();
    		String[] extras = { String.valueOf( clientTO.getId() ) };
    		uploadUtil.uploadFile( file, "Admin", dir_path, extras );
    	}
        
        ClientRegResp clientRegResp = new ClientRegResp();
        clientRegResp.getClientRegTOs().add(clientTO);
        clientRegResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<>(clientRegResp, HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_USERS, method = RequestMethod.POST)
    public ResponseEntity<UserResp> getUsers(@RequestBody ClientReq clientReq) {
        return new ResponseEntity<UserResp>(userService.getUsers(clientReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_VENDOR_USERS, method = RequestMethod.POST)
    public ResponseEntity<UserResp> getVendorUsers(@RequestBody ClientReq clientReq) {
        return new ResponseEntity<UserResp>(userService.getVendorUsers(clientReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_USER_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<UserProjResp> getUserProjects(@RequestBody UserProjGetReq userProjGetReq) {
        return new ResponseEntity<UserProjResp>(epsProjService.getProjectsByUserId(userProjGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_USER_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<UserProjResp> saveUserProjects(@RequestBody UserProjSaveReq userProjSaveReq) {
        userService.saveUserProjects(userProjSaveReq);

        UserProjGetReq userProjGetReq = new UserProjGetReq();
        userProjGetReq.setUserId(userProjSaveReq.getUserProjectTOs().get(0).getUserId());
        userProjGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        UserProjResp userProjResp = epsProjService.getProjectsByUserId(userProjGetReq);

        userProjResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<UserProjResp>(userProjResp, HttpStatus.OK);

    }

    @RequestMapping(value = AdminURLConstants.SAVE_USER, method = RequestMethod.POST)
    public ResponseEntity<UserResp> saveUser(@RequestBody UserSaveReq userSaveReq) {
        userService.saveUser(userSaveReq);

        ClientReq clientReq = new ClientReq();
        clientReq.setStatus(StatusCodes.ACTIVE.getValue());
        UserResp userResp = userService.getUsers(clientReq);
        userResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<>(userResp, HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_VENDOR_USER, method = RequestMethod.POST)
    public ResponseEntity<UserResp> saveClientUser(@RequestBody UserSaveReq userSaveReq) {

        userService.saveVendorUser(userSaveReq);

        ClientReq clientReq = new ClientReq();
        clientReq.setStatus(StatusCodes.ACTIVE.getValue());

        UserResp userResp = userService.getVendorUsers(clientReq);

        userResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<UserResp>(userResp, HttpStatus.OK);
    }

    @PostMapping(value = AdminURLConstants.FID_BY_USER_ID)
    public ResponseEntity<UserTO> findByUserId(long userId) {
        return new ResponseEntity<>(userService.findByUserId(userId), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.DELETE_USER, method = RequestMethod.POST)
    public ResponseEntity<UserResp> deactivateUsers(@RequestBody UserDeleteReq userDeleteReq) {

        userService.deactivateUsers(userDeleteReq);
        ClientReq clientReq = new ClientReq();
        clientReq.setStatus(StatusCodes.ACTIVE.getValue());

        UserResp userResp = userService.getUsers(clientReq);

        userResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<UserResp>(userResp, HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.DELETE_CLIENT, method = RequestMethod.POST)
    public ResponseEntity<ClientRegResp> deactivateClient(@RequestBody ClientReq clientReq) {
        userService.deactivateClient(clientReq);
        ClientGetReq clientGetReq = new ClientGetReq();
        clientGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ClientRegResp clientRegResp = userService.getClients(clientGetReq);

        clientRegResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<ClientRegResp>(clientRegResp, HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_EMAIL_SETTINGS, method = RequestMethod.POST)
    public ResponseEntity<EmailSettingResp> getEmailSettings(@RequestBody EmailSettingGetReq emailSettingGetReq) {
        return new ResponseEntity<EmailSettingResp>(
                userService.getEmailSettings(emailSettingGetReq, AppUserUtils.getClientId()), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_EMAIL_SETTINGS, method = RequestMethod.POST)
    public ResponseEntity<EmailSettingResp> saveEmailSettings(@RequestBody EmailSettingSaveReq emailSettingSaveReq) {

        userService.saveEmailSettings(emailSettingSaveReq);

        EmailSettingGetReq emailSettingGetReq = new EmailSettingGetReq();
        emailSettingGetReq.setClientId(AppUserUtils.getClientId());
        emailSettingGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        EmailSettingResp emailSettingResp = userService.getEmailSettings(emailSettingGetReq,
                AppUserUtils.getClientId());
        emailSettingResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<EmailSettingResp>(emailSettingResp, HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.DEACTIVATE_EMAIL_SETTINGS, method = RequestMethod.POST)
    public ResponseEntity<EmailSettingResp> deactivateEmailSettings(
            @RequestBody EmailSettingDelReq emailSettingDelReq) {
        userService.deactivateEmailSettings(emailSettingDelReq);

        EmailSettingGetReq emailSettingGetReq = new EmailSettingGetReq();
        emailSettingGetReq.setClientId(AppUserUtils.getClientId());
        emailSettingGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        EmailSettingResp emailSettingResp = userService.getEmailSettings(emailSettingGetReq,
                AppUserUtils.getClientId());

        emailSettingResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<EmailSettingResp>(emailSettingResp, HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.USER_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<UserOnLoadResp> userOnLoad(@RequestBody userOnLoadReq onLoadReq) {

        UserOnLoadResp userOnLoadResp = new UserOnLoadResp();

        RoleGetReq roleGetReq = new RoleGetReq();
        roleGetReq.setStatus(ApplicationConstants.STATUS_ACTIVE);
        userOnLoadResp.setRoleResp(roleService.getRoles(roleGetReq));

        userOnLoadResp.getUserTO().setClientId(onLoadReq.getClientId());

        return new ResponseEntity<UserOnLoadResp>(userOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_USERS_BY_MODULE_PERMISSION, method = RequestMethod.POST)
    public ResponseEntity<UserModulePermissionResp> getUsersByModulePermission(@RequestBody UserGetReq userGetReq) {
        return new ResponseEntity<UserModulePermissionResp>(userService.getUsersByModulePermission(userGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_USERS_BY_CLIENT_ID, method = RequestMethod.POST)
    public ResponseEntity<UsersByClientResp> getUsersByClientId(@RequestBody ActionReq actionReq) {
        return new ResponseEntity<UsersByClientResp>(userService.getUsersByClientId(actionReq), HttpStatus.OK);
    }

    @GetMapping(value = AdminURLConstants.GET_CLIENT_MAIL_TEMPLATE)
    public ResponseEntity<ByteArrayResource> getClientMailTemplate(@RequestParam("clientId") Long clientId) {
        ClientRegTO clientTO = userService.getClientById(clientId);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(clientTO.getClientFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + clientTO.getClientDetailsFileName() + "\"")
                .body(new ByteArrayResource(clientTO.getClientDetails()));

    }

    @PostMapping(value = AdminURLConstants.ACTIVATE_USER)
    public ResponseEntity<UserResp> activateUsers(@RequestBody UserDeleteReq userDeleteReq) {

        userService.activateUsers(userDeleteReq);

        ClientReq clientReq = new ClientReq();
        clientReq.setStatus(StatusCodes.DEACIVATE.getValue());

        UserResp userResp = userService.getUsers(clientReq);
        userResp.cloneAppResp(CommonUtil.getActiveAppResp());
        return new ResponseEntity<UserResp>(userResp, HttpStatus.OK);
    }

}