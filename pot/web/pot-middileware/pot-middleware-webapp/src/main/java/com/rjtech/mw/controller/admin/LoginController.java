package com.rjtech.mw.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.mw.service.role.MWRoleService;
import com.rjtech.mw.service.user.MWUserService;
import com.rjtech.rjs.appuser.utils.AppUserDetails;
import com.rjtech.rjs.service.token.RJSTokenService;
import com.rjtech.role.dto.RoleResponse;

/**
 * REST controller for managing the current user's account.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping("/app/")
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MWRoleService mwRoleService;

    @Autowired
    @Qualifier("rjsTokenService")
    private RJSTokenService rjsTokenService;

    @RequestMapping(value = "account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAccount(HttpServletRequest req, HttpServletResponse resp) {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return new ResponseEntity("Error", HttpStatus.NOT_FOUND);
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        resp.setHeader("Access-Control-Allow-Origin", "*");
        return new ResponseEntity(AppUtils.toJson(appUserDetails), HttpStatus.OK);
    }

    @RequestMapping(value = "account/authentication", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authentication(@RequestParam("username") String username,
            @RequestParam("password") String password, @RequestParam("clientcode") String clientcode,
            HttpServletRequest req, HttpServletResponse resp) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return new ResponseEntity("Error", HttpStatus.NOT_FOUND);
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        setRoleDetails(appUserDetails, mwRoleService.getRoleByUser());
        rjsTokenService.updateUserPermission(appUserDetails);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        return new ResponseEntity(AppUtils.toJson(appUserDetails), HttpStatus.OK);
    }

    @RequestMapping(value = "account/internalauth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authentication(@RequestParam("username") String username,
            @RequestParam("password") String password, HttpServletRequest req, HttpServletResponse resp) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return new ResponseEntity("Error", HttpStatus.NOT_FOUND);
        }
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        setRoleDetails(appUserDetails, mwRoleService.getRoleByUser());
        rjsTokenService.updateUserPermission(appUserDetails);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        return new ResponseEntity(AppUtils.toJson(appUserDetails), HttpStatus.OK);
    }

    private void setRoleDetails(AppUserDetails appUserDetails, RoleResponse roleResponse) {
        List<Long> roleIds = new ArrayList<Long>();
        roleIds.add(roleResponse.getRoleId());
        appUserDetails.setRoleIds(roleIds);
        appUserDetails.setAppCodeTemplate(getUserPermissions(roleResponse.getPermissions()));
        appUserDetails.setDisplayRole(roleResponse.getRoleName());
    }

    private Map<String, Boolean> getUserPermissions(List<String> permissions) {
        Map<String, Boolean> appCodeTemplate = null;
        if (CommonUtil.isListHasData(permissions)) {
            appCodeTemplate = new HashMap<String, Boolean>();
            for (String permission : permissions) {
                appCodeTemplate.put(permission, true);
            }
        }
        return appCodeTemplate;
    }

}
